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
		
		protected static long id;
		protected static MzsCore core;
		protected static Capi capi;
		protected static URI space;
		protected static ContainerReference archive;
		protected static ContainerReference bar;
		
		
		protected static AtomicBoolean working;
		protected static int timeOut;
		protected static String spaceAddress;
		
		
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			try {
			int port = Integer.valueOf(args[0]);
		    id = (long)Integer.valueOf(args[1]);
		    spaceAddress = args[2];
		    
			 core = DefaultMzsCore.newInstance(port);
	         capi = new Capi(core);
	         
	         timeOut = 10000;
	         
	         working = new AtomicBoolean();
	         working.set(false);
	         
			  //  System.out.println(g.getStatus());

	        
				space = new URI(spaceAddress);

				
				ArrayList<Coordinator> obligatoryCoords = new ArrayList<Coordinator>();
		        obligatoryCoords.add(new FifoCoordinator());
		        obligatoryCoords.add(new KeyCoordinator());
		        
		        
	            TransactionReference tx = capi.createTransaction(timeOut, space);

				archive = capi.lookupContainer(PizzeriaConfiguration.CONTAINER_NAME_ARCHIVE, space, 1000, tx);
			    bar = capi.lookupContainer(PizzeriaConfiguration.CONTAINER_NAME_BAR, space, 1000, tx);

			    
			    //g.setStatus(GroupStatus.ENTERED);
				//g.setGroupSize(3);
				

				
			  //  Entry entry = new Entry(g, Arrays.asList(KeyCoordinator.newCoordinationData(String.valueOf(g.getId()))));
			  //Entry entry2 = new Entry(g, Arrays.asList(KeyCoordinator.newCoordinationData(String.valueOf(g.getId()+22)), QueryCoordinator.newCoordinationData()));

				
				new RunDriverSBC();
				
				//test shizzle
				//capi.write(entrance, entry); 
				//capi.write(entrance, entry);
				//capi.write(entrance, entry2);


				
			} catch (URISyntaxException | MzsCoreException | InterruptedException e) {
				// TODO Auto-generated catch block
				
				//capi.rollbackTransaction(tx);
				e.printStackTrace();
				System.out.println(">>>usage RunDriverSBC <myPort> <myId> <addressOfSpace>");
				
			}
	         
	        
			
			
			
		}
		
		
		public RunDriverSBC() throws MzsCoreException, InterruptedException{
			// Create notification
	        NotificationManager notifManager = new NotificationManager(core);
	        Set<Operation> operations = new HashSet<Operation>();
	        operations.add(Operation.WRITE);
	        //operations.add(Operation.DELETE);
	        notifManager.createNotification(bar, this, operations, null, null);
		}

		@Override
		public void entryOperationFinished(Notification arg0, Operation arg1,
				List<? extends Serializable> arg2) {
	        //System.out.println("> Notification: ID" +arg2.toString() + " " + arg1.toString());

			// if working throw it away
	        if(working.get()) return;
	        working.set(true);
	        try{
	        	

	        Iterator<? extends Serializable> iterator =  arg2.iterator();
	    	while (iterator.hasNext()) {
	        //for (Serializable entry : arg2) {
				Order o = (Order) ((Entry) iterator.next()).getValue();
				//Order o = t.getGroup();
	            //System.out.println("--> Notification: ID" +g.getId() + " " + arg1.toString());
				
				if(arg1.equals(Operation.WRITE)){
					
		           // System.out.println("Status "+ g.getStatus().toString()+  " ID " +g.getId() );

				  // take the pizza now if finished
					if(o.getStatus().equals(OrderStatus.DELIVERYCOOKED)){
						tryToDeliverAndPutInArchive();
					}
		        	 
		        	 
				}
				
	            
	        }	
	        }catch(Exception e){
	        	
	        }finally{
	        	working.set(false);

	        }
			
			
		}
		
		
		public static void tryToDeliverAndPutInArchive(){
			TransactionReference tx;
			Order o = null;
			try {
				
				tx = capi.createTransaction(timeOut, space);
				ArrayList<Order> orders = new ArrayList<Order>();

				// query coordinator
				Query qo = new Query().sql("status = 'DELIVERYCOOKED' LIMIT 1");

				orders = capi.take(bar, Arrays.asList(QueryCoordinator.newSelector(qo)) , RequestTimeout.TRY_ONCE, tx);
				 o = orders.get(0);

				 
				// write a bill
				
				
				System.out.println("Writing bill for order id " + o.getId() + " bill " + o.writeBill() + " trying to deliver");

				capi.commitTransaction(tx);

				// try to deliver it takes 3 seconds
				o.setOrderstatus(OrderStatus.DELIVERYFINISHED);
				o.setDriverId(id);
				
				// on new space
				
				Thread.sleep(3000); // lasts for
				URI newspace = new URI(o.getDeliveryAddress().getSpaceAddress());
				ContainerReference deliveryAddress = capi.lookupContainer(o.getDeliveryAddress().getContainerName(), newspace, 1000, null);
				
				
				// write in archive if delivered or not
				Entry orderEntry = new Entry(o, Arrays.asList(KeyCoordinator.newCoordinationData(String.valueOf(o.getId())), QueryCoordinator.newCoordinationData()));
				
				capi.write(orderEntry, deliveryAddress,timeOut,null); 
				capi.write(orderEntry, archive,timeOut,null);

				// everything ok proceed

				

			} catch ( Exception e) {
				try{
				// write in archive not delivered 
					
				if(o ==  null) return;
				o.setOrderstatus(OrderStatus.DELIVERYFAILED);
				Entry orderEntry = new Entry(o, Arrays.asList(KeyCoordinator.newCoordinationData(String.valueOf(o.getId())), QueryCoordinator.newCoordinationData()));

				capi.write(orderEntry, archive,timeOut,null);

				}catch(Exception e1){
					
				}

			}finally{
				//working.set(false);
			}

		}
		
		

	}
