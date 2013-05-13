package tuwien.sbctu.runtime;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import org.mozartspaces.notifications.Notification;
import org.mozartspaces.notifications.NotificationListener;
import org.mozartspaces.notifications.NotificationManager;
import org.mozartspaces.notifications.Operation;

import tuwien.sbctu.conf.PizzeriaConfiguration;
import tuwien.sbctu.models.GuestGroup;
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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int port = Integer.valueOf(args[0]);
	    id = (long)Integer.valueOf(args[1]);

		 core = DefaultMzsCore.newInstance(port);
         capi = new Capi(core);
         GuestGroup g = new GuestGroup(id);
         g.setStatus(GroupStatus.WELCOME);
		
        try {
			space = new URI(PizzeriaConfiguration.LOCAL_SPACE_URI);

			
			ArrayList<Coordinator> obligatoryCoords = new ArrayList<Coordinator>();
	        obligatoryCoords.add(new FifoCoordinator());
	        obligatoryCoords.add(new KeyCoordinator());
	        
	        
            TransactionReference tx = capi.createTransaction(10000, space);
            TransactionReference tx2 = capi.createTransaction(10000, space);

			entrance = capi.lookupContainer(PizzeriaConfiguration.CONTAINER_NAME_ENTRANCE, space, 0, tx);
		    tables = capi.lookupContainer(PizzeriaConfiguration.CONTAINER_NAME_TABLES, space, 0, tx2);

		    
		    g.setStatus(GroupStatus.ENTERED);
			g.setGroupSize(3);
			

			
		    Entry entry = new Entry(g, Arrays.asList(KeyCoordinator.newCoordinationData(String.valueOf(g.getId())), QueryCoordinator.newCoordinationData()));
		    Entry entry2 = new Entry(g, Arrays.asList(KeyCoordinator.newCoordinationData(String.valueOf(g.getId()+22)), QueryCoordinator.newCoordinationData()));

			
			new RunGuestSBC();
			
			//test shizzle
			//capi.write(entrance, entry); 
			capi.write(entrance, entry);
			capi.write(entrance, entry2);


			
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
		for (Serializable entry : arg2) {
			GuestGroup g = (GuestGroup) entry;
			if(g.getId() == id && (arg1.equals(Operation.WRITE) || arg1.equals(Operation.DELETE))){
				
	            System.out.println("--> Notification: ID" +g.getId() + " " + arg1.toString());

				// its me mario
				if(g.getStatus().equals(GroupStatus.SITTING)){
	        		 g.placeOrder();
	        		 g.setStatus(GroupStatus.ORDERED);
	        		 
	        		 
	     			 try {
						capi.write(tables, new Entry(g));
					} catch (MzsCoreException e) {
						// TODO Auto-generated catch block
						
						e.printStackTrace();
					}

	        	 }
	        	 
	        	 
	        	 if(g.getStatus().equals(GroupStatus.EATING)){
	        		 g.eatPizzaNotDishes();
	        		 g.setStatus(GroupStatus.BILL);
	        		 
	        		 try {
							capi.write(tables, new Entry(g));
						} catch (MzsCoreException e) {
							// TODO Auto-generated catch block
							
							e.printStackTrace();
						}
	        	 }
	        	 
	        	 
	        	 if(arg1.equals(Operation.DELETE)){
	        		 g.leaveTheTableAndThePizzeriaAndGoWOderPFeffer();
	        		 core.shutdown(true);
	        	 }
			}
			
            
        }		
		
		
	}

}
