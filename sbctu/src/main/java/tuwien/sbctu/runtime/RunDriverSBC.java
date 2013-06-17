package tuwien.sbctu.runtime;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.mozartspaces.capi3.Coordinator;
import org.mozartspaces.capi3.FifoCoordinator;
import org.mozartspaces.capi3.KeyCoordinator;
import org.mozartspaces.capi3.Query;
import org.mozartspaces.capi3.QueryCoordinator;
import org.mozartspaces.core.Capi;
import org.mozartspaces.core.ContainerReference;
import org.mozartspaces.core.DefaultMzsCore;
import org.mozartspaces.core.Entry;
import org.mozartspaces.core.MzsConstants.Selecting;
import org.mozartspaces.core.MzsCore;
import org.mozartspaces.core.MzsCoreException;
import org.mozartspaces.core.TransactionReference;
import org.mozartspaces.core.MzsConstants.RequestTimeout;
import org.mozartspaces.notifications.Notification;
import org.mozartspaces.notifications.NotificationListener;
import org.mozartspaces.notifications.NotificationManager;
import org.mozartspaces.notifications.Operation;

import tuwien.sbctu.conf.PizzeriaConfiguration;
import tuwien.sbctu.models.Order;
import tuwien.sbctu.models.Order.OrderStatus;

	public class RunDriverSBC implements NotificationListener {

		/**
		 * @param args[0] - int port unique 
		 * args[1] - int id unique
		 * 
		 */
		
		protected  long id;
		protected  MzsCore core;
		protected  Capi capi;
		protected  URI space;
		protected  ContainerReference archive;
		protected  ContainerReference bar;
		
		
		protected  AtomicBoolean working;
		protected  int timeOut;
		protected  String spaceAddress;
		
		
		public  void main(String[] args) {
			// TODO Auto-generated method stub
			try {
			int port = Integer.valueOf(args[0]);
		    id = (long)Integer.valueOf(args[1]);
		    spaceAddress = args[2];
		    
			 core = DefaultMzsCore.newInstance(port);
	         capi = new Capi(core);
	         
	         timeOut = 5000;
	         
	         working = new AtomicBoolean();
	         working.set(false);
	         
			  //  System.out.println(g.getStatus());

	        
				space = new URI(spaceAddress);

				
				ArrayList<Coordinator> obligatoryCoords = new ArrayList<Coordinator>();
		        obligatoryCoords.add(new FifoCoordinator());
		        obligatoryCoords.add(new KeyCoordinator());
		        
		        
	            TransactionReference tx = capi.createTransaction(timeOut, space);

				archive = capi.lookupContainer(PizzeriaConfiguration.CONTAINER_NAME_ARCHIVE, space, RequestTimeout.DEFAULT, tx);
			    bar = capi.lookupContainer(PizzeriaConfiguration.CONTAINER_NAME_BAR, space, RequestTimeout.DEFAULT, tx);

			    System.out.print("fas");
			    //g.setStatus(GroupStatus.ENTERED);
				//g.setGroupSize(3);
				

				
			  //  Entry entry = new Entry(g, Arrays.asList(KeyCoordinator.newCoordinationData(String.valueOf(g.getId()))));
			  //Entry entry2 = new Entry(g, Arrays.asList(KeyCoordinator.newCoordinationData(String.valueOf(g.getId()+22)), QueryCoordinator.newCoordinationData()));

				
			 // Create notification
		        NotificationManager notifManager = new NotificationManager(core);
		        Set<Operation> operations = new HashSet<Operation>();
		        operations.add(Operation.WRITE);
		        //operations.add(Operation.DELETE);
		        notifManager.createNotification(bar, this, operations, null, null);
				
				//test shizzle
				//capi.write(entrance, entry); 
				//capi.write(entrance, entry);
				//capi.write(entrance, entry2);
		        
		        while(true){
		        	try {
		        		if(tryToDeliverAndPutInArchive()) continue;
		        		Thread.sleep(450);
					} catch (Exception e) {
						// TODO: handle exception
					}
		        }


				
			} catch (URISyntaxException | MzsCoreException | InterruptedException e) {
				// TODO Auto-generated catch block
				
				//capi.rollbackTransaction(tx);
				e.printStackTrace();
				System.out.println(">>>usage RunDriverSBC <myPort> <myId> <addressOfSpace>");
				
			}
	         
	        
			
			
			
		}
		
		
		public RunDriverSBC() throws MzsCoreException, InterruptedException{
			
		}

		@Override
		public void entryOperationFinished(Notification arg0, Operation arg1,
				List<? extends Serializable> arg2) {
	       // System.out.println("> Notification: ID" +arg2.toString() + " " + arg1.toString());

			// if working throw it away
	        if(working.get()==true) return;
	        working.set(true);
	        try{
	        	

	        Iterator<? extends Serializable> iterator =  arg2.iterator();
	    	while (iterator.hasNext()) {
	        //for (Serializable entry : arg2) {
				Order o = (Order) ((Entry) iterator.next()).getValue();
				//Order o = t.getGroup();
				
					
		           // System.out.println("Status "+ g.getStatus().toString()+  " ID " +g.getId() );

				
				  // take the pizza now if finished
					if(o.getOrderstatus().equals(OrderStatus.DELIVERYCOOKED)){
			            //System.out.println("--> Notification: ID" +o.toString());

						//tryToDeliverAndPutInArchive();
					}
		        	 
		        	 
				
				
	            
	        }	
	        }catch(Exception e){
	        	
	        }finally{
	        	working.set(false);

	        }
			
			
		}
		
		
		public  boolean tryToDeliverAndPutInArchive(){
			TransactionReference tx = null;
			
			TransactionReference txone;

			Order o = null;
			try {
				//System.out.println("Trying to deliver");
				tx = capi.createTransaction(timeOut, space);
				ArrayList<Order> orders = new ArrayList<Order>();

				// query coordinator
				Query qo = new Query().sql("status = 'DELIVERYCOOKED' LIMIT 1");

				orders = capi.take(bar, Arrays.asList(QueryCoordinator.newSelector(qo,1)) , RequestTimeout.TRY_ONCE, tx);
				 o = orders.get(0);

				 
				// write a bill
				
				
				//System.out.println("Writing bill for order id " + o.getId() + " bill " + o.writeBill() + " trying to deliver");


				// try to deliver it takes 3 seconds
				o.setOrderstatus(OrderStatus.DELIVERYFINISHED);
				o.setDriverId(id);
				//capi.commitTransaction(tx);
				// on new space
				// Bechmark disable
				//Thread.sleep(3000); // lasts for
				
				try {
					URI newspace = new URI(o.getDeliveryAddress().getSpaceAddress());
//					txone = capi.createTransaction(timeOut, newspace);

					ContainerReference deliveryAddress = capi.lookupContainer(o.getDeliveryAddress().getContainerName(), newspace, RequestTimeout.TRY_ONCE, null);
					
					Entry orderEntry1 = new Entry(o);

					capi.write(orderEntry1, deliveryAddress,RequestTimeout.TRY_ONCE,null); 
				} catch (Exception e) {
					if(o ==  null) return false;
					o.setOrderstatus(OrderStatus.DELIVERYFAILED);
					Entry orderEntry = new Entry(o, Arrays.asList(KeyCoordinator.newCoordinationData(String.valueOf(o.getId())), QueryCoordinator.newCoordinationData()));

					capi.write(orderEntry, archive,RequestTimeout.TRY_ONCE,tx);
					capi.commitTransaction(tx);

					return true;
					// TODO: handle exception
				}
				
				
				// write in archive if delivered or not
				Entry orderEntry = new Entry(o, Arrays.asList(KeyCoordinator.newCoordinationData(String.valueOf(o.getId())), QueryCoordinator.newCoordinationData()));
				
				
				capi.write(orderEntry, archive,RequestTimeout.TRY_ONCE,tx);
				capi.commitTransaction(tx);

				// everything ok proceed
				System.out.println("Delivered order with id " + o.getId() + " bill " + o.writeBill() + " ");
								

			} catch ( Exception e) {
				//e.printStackTrace();t
				try {
				capi.rollbackTransaction(tx);
				} catch (Exception e1) {
				
				}

			}finally{
				//working.set(false);
				return true;
			}

		}
		
		

	}
