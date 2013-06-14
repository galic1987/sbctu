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
import org.mozartspaces.capi3.QueryCoordinator;
import org.mozartspaces.capi3.RandomCoordinator;
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
import tuwien.sbctu.models.GuestGroup;
import tuwien.sbctu.models.Order;
import tuwien.sbctu.models.Pizza;
import tuwien.sbctu.models.Table;
import tuwien.sbctu.models.GuestGroup.GroupStatus;

public class RunGuestSBC implements NotificationListener {

	/**
	 * @param args[0] - int port unique 
	 * args[1] - int id unique
	 * 
	 */
	
	protected static long id;
	protected static MzsCore core;
	protected static Capi capi;
	protected static URI space;
	protected static ContainerReference entrance;
	protected static ContainerReference tables;
	protected static GuestGroup g;
	
	
	protected static AtomicBoolean working;
	protected static int timeOut;
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int port = Integer.valueOf(args[0]);
	    id = (long)Integer.valueOf(args[1]);

		 core = DefaultMzsCore.newInstance(port);
         capi = new Capi(core);
         
         timeOut = 1000;
         
         working = new AtomicBoolean();
         working.set(false);
         
         g = new GuestGroup(id);
         g.setStatus(GroupStatus.WELCOME);
		    System.out.println(g.getStatus());

        try {
			space = new URI(PizzeriaConfiguration.LOCAL_SPACE_URI);

			
			ArrayList<Coordinator> obligatoryCoords = new ArrayList<Coordinator>();
	        obligatoryCoords.add(new FifoCoordinator());
	        obligatoryCoords.add(new KeyCoordinator());
	        
	        
            TransactionReference tx = capi.createTransaction(timeOut, space);

			entrance = capi.lookupContainer(PizzeriaConfiguration.CONTAINER_NAME_ENTRANCE, space, 1000, tx);
		    tables = capi.lookupContainer(PizzeriaConfiguration.CONTAINER_NAME_TABLES, space, 1000, tx);

		    
		    g.setStatus(GroupStatus.ENTERED);
			g.setGroupSize(3);
			

			
		    Entry entry = new Entry(g, Arrays.asList(KeyCoordinator.newCoordinationData(String.valueOf(g.getId()))));
		  //Entry entry2 = new Entry(g, Arrays.asList(KeyCoordinator.newCoordinationData(String.valueOf(g.getId()+22)), QueryCoordinator.newCoordinationData()));

			
			new RunGuestSBC();
			
			//test shizzle
			//capi.write(entrance, entry); 
			capi.write(entrance, entry);
			//capi.write(entrance, entry2);


			
		} catch (URISyntaxException | MzsCoreException | InterruptedException e) {
			// TODO Auto-generated catch block
			
			//capi.rollbackTransaction(tx);
			e.printStackTrace();
			
		}
         
        
		
		
		
	}
	
	
	public RunGuestSBC() throws MzsCoreException, InterruptedException{
		// Create notification
        NotificationManager notifManager = new NotificationManager(core);
        Set<Operation> operations = new HashSet<Operation>();
        operations.add(Operation.WRITE);
        operations.add(Operation.DELETE);
        notifManager.createNotification(tables, this, operations, null, null);
	}

	@Override
	public void entryOperationFinished(Notification arg0, Operation arg1,
			List<? extends Serializable> arg2) {
        //System.out.println("> Notification: ID" +arg2.toString() + " " + arg1.toString());

        if(arg1.equals(Operation.DELETE)){
        	Table t = (Table)arg2.get(0);

    		//System.out.println("fgasf" + t.getGroup().getId());

        	if(t.getGroup().getId() == id){
        		
        		System.out.println("EXIT");
        		g.leaveTheTableAndThePizzeriaAndGoWOderPFeffer();
        		core.shutdown(true);
        		System.exit(1);
        	}
        }		
        
        Iterator<? extends Serializable> iterator =  arg2.iterator();
    	while (iterator.hasNext()) {
        //for (Serializable entry : arg2) {
			Table t = (Table) ((Entry) iterator.next()).getValue();
			GuestGroup g = t.getGroup();
            //System.out.println("--> Notification: ID" +g.getId() + " " + arg1.toString());
			
			if(g.getId() == id && arg1.equals(Operation.WRITE)){
				
	            System.out.println("Status "+ g.getStatus().toString()+  " ID " +g.getId() );

				// its me mario
				if(g.getStatus().equals(GroupStatus.SITTING)){
	     			 makeOrder();
	        	 }
	        	 
	        	 
	        	 if(g.getStatus().equals(GroupStatus.EATING)){
	        		 g.eatPizzaNotDishes();
	        		demandBill();
	        	 }
	        	 
	        	 
	        	 
			}
			
            
        }		
		
		
	}
	
	
	public static void makeOrder(){
		TransactionReference tx;
        try {
        	working.set(true);
        	tx = capi.createTransaction(timeOut, space);
	        ArrayList<Table> entries = new ArrayList<Table>();
	        
	        // take it from tables
            entries = capi.take(tables, Arrays.asList(KeyCoordinator.newSelector(String.valueOf(id), 1)) , RequestTimeout.INFINITE, tx);
            Table t = entries.get(0);
            
            
            // make order
            t.getGroup().setStatus(GroupStatus.ORDERED);
            Order o = new Order(id);
            o.addPizzaToOrder(new Pizza("Margarite", 10.0f, 5));
            o.addPizzaToOrder(new Pizza("Fungi", 1.0f, 3));
            o.addPizzaToOrder(new Pizza("Rusticana", 12.0f, 2));
            t.setOrder(o);
            
		    Entry entry = new Entry(t, Arrays.asList(KeyCoordinator.newCoordinationData(String.valueOf(t.getId())), QueryCoordinator.newCoordinationData()));
			
		    // write it again
			capi.write(entry, tables,timeOut,tx);
			capi.commitTransaction(tx);

		  //  capi.write(tables, entry);
			
		} catch ( Exception e) {
			// AutoRollback
			
			e.printStackTrace();
		}finally{
        	working.set(false);
		}
	}

	
	public static void demandBill(){
		TransactionReference tx;
        try {
        	working.set(true);
        	tx = capi.createTransaction(timeOut, space);
	        ArrayList<Table> entries = new ArrayList<Table>();
	        
	        // take it from tables
            entries = capi.take(tables, Arrays.asList(KeyCoordinator.newSelector(String.valueOf(id), 1)) , RequestTimeout.INFINITE, tx);
            Table t = entries.get(0);
            
            
            // make order
            t.getGroup().setStatus(GroupStatus.BILL);
           
            
		    Entry entry = new Entry(t, Arrays.asList(KeyCoordinator.newCoordinationData(String.valueOf(t.getId())), QueryCoordinator.newCoordinationData()));
			
		    // write it again
			capi.write(entry, tables,timeOut,tx);
			capi.commitTransaction(tx);

		  //  capi.write(tables, entry);
			
		} catch ( Exception e) {
			// AutoRollback
			
			e.printStackTrace();
		}finally{
        	working.set(false);
		}
	}

}
