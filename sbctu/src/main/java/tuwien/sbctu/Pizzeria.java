package tuwien.sbctu;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mozartspaces.capi3.AnyCoordinator;
import org.mozartspaces.capi3.Coordinator;
import org.mozartspaces.capi3.FifoCoordinator;
import org.mozartspaces.capi3.KeyCoordinator;
import org.mozartspaces.capi3.Query;
import org.mozartspaces.capi3.QueryCoordinator;
import org.mozartspaces.capi3.Selector;
import org.mozartspaces.core.Capi;
import org.mozartspaces.core.ContainerReference;
import org.mozartspaces.core.DefaultMzsCore;
import org.mozartspaces.core.Entry;
import org.mozartspaces.core.MzsConstants;
import org.mozartspaces.core.MzsCore;
import org.mozartspaces.core.MzsCoreException;
import org.mozartspaces.core.Server;
import org.mozartspaces.core.TransactionReference;
import org.mozartspaces.core.MzsConstants.RequestTimeout;
import org.mozartspaces.core.MzsConstants.Selecting;
import org.mozartspaces.notifications.Notification;
import org.mozartspaces.notifications.NotificationListener;
import org.mozartspaces.notifications.NotificationManager;
import org.mozartspaces.notifications.Operation;
import tuwien.sbctu.conf.*;

import tuwien.sbctu.models.*;
import tuwien.sbctu.models.GuestGroup.GroupStatus;
import tuwien.sbctu.models.Order.OrderStatus;
//import tuwien.sbctu.rmi.PizzeriaGUI;

public class Pizzeria implements NotificationListener {




	//final PizzeriaGUISBC pizz = new PizzeriaGUISBC();


	private final MzsCore core;
	private final Capi capi;
	private final URI space;
	private NotificationManager manager;


	private ContainerReference entrance;
	private ContainerReference tables;
	private ContainerReference bar;
	private ContainerReference delivery;
	private ContainerReference archive;

	private double load = 0;
	private long timeOut = 10000;

	
	private String address = "";

	// 3 containers
	//	private final FifoContainerXvsm<GuestGroup> entranceContainer;
	//	private final IdContainerXvsm tableContainer;
	//	private final IdContainerXvsm barContainer;



	public Pizzeria(int port) throws MzsCoreException,
	InterruptedException {

		String portString = String.valueOf(port);
		String p1[] = new String [1];
		p1[0] = portString;

		// Space start
		org.mozartspaces.core.Server.main(p1) ;


		core = DefaultMzsCore.newInstance(port+199);
		capi = new Capi(core);

		try {
			address = PizzeriaConfiguration.LOCAL_SPACE_URI+":"+portString;
			space = new URI(PizzeriaConfiguration.LOCAL_SPACE_URI+":"+portString);
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



		// guest groups as objects
		entrance = capi.createContainer(PizzeriaConfiguration.CONTAINER_NAME_ENTRANCE, space, MzsConstants.Container.UNBOUNDED, obligatoryCoords, 
				null,null);


		// tables as objects
		tables = capi.createContainer(PizzeriaConfiguration.CONTAINER_NAME_TABLES, space, MzsConstants.Container.UNBOUNDED, tableCoords, 
				null,null);

		// orders as objects
		bar = capi.createContainer(PizzeriaConfiguration.CONTAINER_NAME_BAR, space, MzsConstants.Container.UNBOUNDED, tableCoords, 
				null,null);

		
		// delivery queue (telephone)
		delivery = capi.createContainer(PizzeriaConfiguration.CONTAINER_NAME_DELIVERY, space, MzsConstants.Container.UNBOUNDED, obligatoryCoords, 
				Arrays.asList(new QueryCoordinator()),null);

		// archive for log
		archive = capi.createContainer(PizzeriaConfiguration.CONTAINER_NAME_ARCHIVE, space, MzsConstants.Container.UNBOUNDED, tableCoords, 
				null,null);
		
		//System.out.println(delivery.getId().equals(PizzeriaConfiguration.CONTAINER_NAME_DELIVERY));
		
		// TODO: hook the modafacka

		// Create notification
		manager = new NotificationManager(core);
		//operations.add(Operation.ALL);
		//operations.add(Operation.DELETE);
		manager.createNotification(tables, this, Operation.WRITE, null, null);
		manager.createNotification(entrance, this, Operation.WRITE, null, null);
		// manager.createNotification(tables, this, Operation.DELETE, null, null);
		manager.createNotification(bar, this, Operation.WRITE, null, null);
		manager.createNotification(delivery, this, Operation.WRITE,null,null);






//		try {
//			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//				if ("Nimbus".equals(info.getName())) {
//					javax.swing.UIManager.setLookAndFeel(info.getClassName());
//					break;
//				}
//			}
//		} catch (ClassNotFoundException ex) {
//			java.util.logging.Logger.getLogger(PizzeriaGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//		} catch (InstantiationException ex) {
//			java.util.logging.Logger.getLogger(PizzeriaGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//		} catch (IllegalAccessException ex) {
//			java.util.logging.Logger.getLogger(PizzeriaGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
//			java.util.logging.Logger.getLogger(PizzeriaGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//		}

		//pizz.setVisible(true);
		//  pizz.subscribeToSBC();
		//  pizz.updateFields();

	}



	@Override
	public void entryOperationFinished(Notification arg0, Operation arg1,
			List<? extends Serializable> arg2) {
		//for (Serializable entry : arg2) {



		//	Order o = (Order) ((Entry) entry).getValue();

		//System.out.println("--> Notification: ID " + arg0.getNotificationContainer().toString() + " \n " + arg2.toString() );






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


		//pizz.updateFields(orders, tablesArr, guestsEntry);


		// TODO: here can we get all notficications and append them to the gui interface


		//  }		

	}




	public Order takeOneDeliveryOrder(){
		TransactionReference tx;
		Order o = null;
		try {
			tx = capi.createTransaction(timeOut, space);
			ArrayList<Order> orders = new ArrayList<Order>();
			ArrayList<Table> tablesArr = new ArrayList<Table>();

			// query coordinator
			Query qo = new Query().sql("status = 'DELIVERYNEW' LIMIT 1");

			orders = capi.take(bar, Arrays.asList(QueryCoordinator.newSelector(qo)) , RequestTimeout.TRY_ONCE, tx);
			o = orders.get(0);

			capi.commitTransaction(tx);

		} catch ( Exception e) {
			// AutoRollback

		}finally{
		}
		return o;
	}




	public double calculatePizzeriaLoad(){
		// saves the load inside
		TransactionReference tx;
		try {
			//working.set(true);
			tx = capi.createTransaction(timeOut , space);
			ArrayList<Order> orders = new ArrayList<Order>();
			// ArrayList<Table> tablesArr = new ArrayList<Table>();

			// query coordinator
			Query qo = new Query().sql("status = 'DELIVERYNEW'");
			//Query qo1 = new Query().cnt(Query.ALL).sql("");

			
			
			//System.out.println(capi.test(bar, Arrays.asList(QueryCoordinator.newSelector(qo1,Selecting.COUNT_ALL)) , RequestTimeout.TRY_ONCE, tx));

			load = (double) capi.test(bar, Arrays.asList(QueryCoordinator.newSelector(qo,Selecting.COUNT_ALL)) , RequestTimeout.TRY_ONCE, tx);
			

			//Order o = orders.get(0);
			
			//System.out.println(capi.read(bar, Arrays.asList(QueryCoordinator.newSelector(qo))  , RequestTimeout.DEFAULT, tx).size());


			capi.commitTransaction(tx);


		} catch ( Exception e) {
			// AutoRollback
			//load = 0;
			//e.printStackTrace();
		}finally{
			//working.set(false);
		}


		return load;
	}


	public double getLoad(){
		return load;
	}


	public boolean putDeliveryOrder(Order a){
		TransactionReference tx;
		try {
	
        	tx = capi.createTransaction(timeOut, space);

			 //a.setOrderstatus(OrderStatus.DELIVERYTRANSFERRED);


			Entry orderEntry = new Entry(a, Arrays.asList(KeyCoordinator.newCoordinationData(String.valueOf(a.getId())), QueryCoordinator.newCoordinationData()));
			capi.write(orderEntry, bar,timeOut,tx);

			capi.commitTransaction(tx);

			return true;
		} catch ( Exception e) {
			// AutoRollback
			return false;
		}
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}






}
