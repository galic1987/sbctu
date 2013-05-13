package tuwien.sbctu;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mozartspaces.capi3.AnyCoordinator;
import org.mozartspaces.capi3.Coordinator;
import org.mozartspaces.capi3.FifoCoordinator;
import org.mozartspaces.capi3.KeyCoordinator;
import org.mozartspaces.capi3.QueryCoordinator;
import org.mozartspaces.capi3.Selector;
import org.mozartspaces.core.Capi;
import org.mozartspaces.core.ContainerReference;
import org.mozartspaces.core.DefaultMzsCore;
import org.mozartspaces.core.Entry;
import org.mozartspaces.core.MzsConstants;
import org.mozartspaces.core.MzsCore;
import org.mozartspaces.core.MzsCoreException;
import org.mozartspaces.core.TransactionReference;
import org.mozartspaces.notifications.Notification;
import org.mozartspaces.notifications.NotificationListener;
import org.mozartspaces.notifications.NotificationManager;
import org.mozartspaces.notifications.Operation;
import tuwien.sbctu.conf.*;

import tuwien.sbctu.models.*;
import tuwien.sbctu.models.GuestGroup.GroupStatus;
import tuwien.sbctu.rmi.PizzeriaGUI;

public class Pizzeria implements NotificationListener {

	
	
	
    final PizzeriaGUISBC pizz = new PizzeriaGUISBC();


	private final MzsCore core;
	private final Capi capi;
	private final URI space;
	private NotificationManager manager;
	
	
	private ContainerReference entrance;
	private ContainerReference tables;
	private ContainerReference bar;
	
	// 3 containers
//	private final FifoContainerXvsm<GuestGroup> entranceContainer;
//	private final IdContainerXvsm tableContainer;
//	private final IdContainerXvsm barContainer;

	
	
	public Pizzeria() throws MzsCoreException,
	InterruptedException {
		
		
		
	        
	        
		
		core = DefaultMzsCore.newInstance();
		capi = new Capi(core);

		try {
			space = new URI(PizzeriaConfiguration.LOCAL_SPACE_URI);
		} catch (final URISyntaxException e) {
			throw new IllegalStateException(
					"Unexpected state: Unparseable space URI="
							+ PizzeriaConfiguration.LOCAL_SPACE_URI);
		}

		manager = new NotificationManager(core);
		
		ArrayList<Coordinator> obligatoryCoords = new ArrayList<Coordinator>();
        obligatoryCoords.add(new FifoCoordinator());
        obligatoryCoords.add(new KeyCoordinator());
        obligatoryCoords.add(new AnyCoordinator());


        
        ArrayList<Coordinator> tableCoords = new ArrayList<Coordinator>();
        tableCoords.add(new KeyCoordinator());
        tableCoords.add(new QueryCoordinator());
        tableCoords.add(new AnyCoordinator());


		
        // guest groups as objects
	    entrance = capi.createContainer(PizzeriaConfiguration.CONTAINER_NAME_ENTRANCE, space, MzsConstants.Container.UNBOUNDED, obligatoryCoords, 
	    		null,null);
	    
	    
	    // tables as objects
	    tables = capi.createContainer(PizzeriaConfiguration.CONTAINER_NAME_TABLES, space, MzsConstants.Container.UNBOUNDED, tableCoords, 
	    		null,null);
	    
	    // orders as objects
	    bar = capi.createContainer(PizzeriaConfiguration.CONTAINER_NAME_BAR, space, MzsConstants.Container.UNBOUNDED, tableCoords, 
	    		null,null);

	    
	    // TODO: hook the modafacka
	    
	 // Create notification
        manager = new NotificationManager(core);
        Set<Operation> operations = new HashSet<Operation>();
        //operations.add(Operation.ALL);
        //operations.add(Operation.DELETE);
        manager.createNotification(tables, this, Operation.WRITE, null, null);
        manager.createNotification(entrance, this, Operation.WRITE, null, null);
       // manager.createNotification(tables, this, Operation.DELETE, null, null);
        manager.createNotification(bar, this, Operation.WRITE, null, null);

        
        
        
        
        
		   
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PizzeriaGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PizzeriaGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PizzeriaGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PizzeriaGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
     
        pizz.setVisible(true);
      //  pizz.subscribeToSBC();
      //  pizz.updateFields();
		
	}
	
	
	
	@Override
	public void entryOperationFinished(Notification arg0, Operation arg1,
			List<? extends Serializable> arg2) {
		//for (Serializable entry : arg2) {
			
			
			
		//	Order o = (Order) ((Entry) entry).getValue();
				
	            System.out.println("--> Notification: ID " + arg2.toString() + " " + arg1.toString());

	            
	            
	            
	            
	            
	            // read all relevant data
		        ArrayList<Order> orders = new ArrayList<Order>();
		        ArrayList<Table> tablesArr = new ArrayList<Table>();
		        ArrayList<GuestGroup> guestsEntry = new ArrayList<GuestGroup>();

	            
	            // put it to gui
		        try {
					orders = capi.read(bar,new FifoCoordinator().newSelector(Selector.COUNT_ALL),0,null);
					
					// update the gui
					
				} catch (MzsCoreException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
		        
		        try {
					tablesArr = capi.read(tables);
					
					// update the gui
					
				} catch (MzsCoreException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
		        
		        try {
				
					guestsEntry = capi.read(entrance);
					
					// update the gui
					
				} catch (MzsCoreException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
		        
		        
		        
				pizz.updateFields(orders, tablesArr, guestsEntry);

				
			// TODO: here can we get all notficications and append them to the gui interface
			
            
      //  }		
		
	}
		
		
	
	
	

	
	
	
	
	
}
