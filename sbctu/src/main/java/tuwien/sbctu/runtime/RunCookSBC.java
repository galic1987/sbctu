package tuwien.sbctu.runtime;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
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
import org.mozartspaces.core.Request;
import org.mozartspaces.core.TransactionReference;
import org.mozartspaces.core.MzsConstants.RequestTimeout;
import org.mozartspaces.core.MzsConstants.Selecting;
import org.mozartspaces.notifications.Notification;
import org.mozartspaces.notifications.NotificationListener;
import org.mozartspaces.notifications.NotificationManager;
import org.mozartspaces.notifications.Operation;

import tuwien.sbctu.conf.PizzeriaConfiguration;
import tuwien.sbctu.models.Cook;
import tuwien.sbctu.models.Order;
import tuwien.sbctu.models.Order.OrderStatus;

public class RunCookSBC implements NotificationListener {

	/**
	 * @param args
	 */
	protected static long id;
	protected static MzsCore core;
	protected static Capi capi;
	protected static URI space;
	protected static ContainerReference bar;
	protected static Cook c;
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
			c = new Cook(id);
			timeOut = 14000;

			working = new AtomicBoolean();
			working.set(false);

			space = new URI(spaceAddress);


			ArrayList<Coordinator> obligatoryCoords = new ArrayList<Coordinator>();
			obligatoryCoords.add(new FifoCoordinator());
			obligatoryCoords.add(new KeyCoordinator());


			TransactionReference tx = capi.createTransaction(timeOut, space);

			//entrance = capi.lookupContainer(PizzeriaConfiguration.CONTAINER_NAME_ENTRANCE, space, 0, tx);
			//tables = capi.lookupContainer(PizzeriaConfiguration.CONTAINER_NAME_TABLES, space, 0, tx);
			bar = capi.lookupContainer(PizzeriaConfiguration.CONTAINER_NAME_BAR, space, 0, tx);




			for (;;) {
				
				try {
				// take order and start cooking  
				//if(cookForDeliveryTransferPriority()) continue; // if there is transfered delivery, 
				if(cookForDelivery()) continue; // if there is delivery, try to cook for delivery again
				
				cook();// othervise cook normal orders
				
				// when finished put it to theke
				
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}


		} catch (URISyntaxException | MzsCoreException e) {
			// TODO Auto-generated catch block

			//capi.rollbackTransaction(tx);
			e.printStackTrace();

		}
	}





	public static void cook(){


		TransactionReference tx;
		try {
			working.set(true);
			tx = capi.createTransaction(timeOut, space);
			ArrayList<Order> orders = new ArrayList<Order>();

			// query coordinator
			Query qo = new Query().sql("status = 'ORDERED' LIMIT 1");

			orders = capi.take(bar, Arrays.asList(QueryCoordinator.newSelector(qo)) , RequestTimeout.TRY_ONCE, tx);
			Order o = orders.get(0);
			System.out.println(o.toString());
			//            for (Pizza p : o.getPizzaList()) {
			
			
			c.cookPizzasFromOrder(o);
			
			
			//Thread.sleep(p.getPrepareTime());
			//			}
			o.setOrderstatus(OrderStatus.COOKED);




			Entry orderEntry = new Entry(o, Arrays.asList(KeyCoordinator.newCoordinationData(String.valueOf(o.getId())), QueryCoordinator.newCoordinationData()));
			capi.write(orderEntry, bar,RequestTimeout.TRY_ONCE,tx);
			capi.commitTransaction(tx);


		} catch ( Exception e) {
			// AutoRollback

			//e.printStackTrace();
		}finally{
			working.set(false);
		}

	}

	public static boolean cookForDelivery(){


		TransactionReference tx;
		try {
			working.set(true);
			tx = capi.createTransaction(RequestTimeout.INFINITE, space);
			ArrayList<Order> orders = new ArrayList<Order>();

			// query coordinator
			Query qo = new Query().sql("status = 'DELIVERYNEW' LIMIT 1");
			
			orders = capi.take(bar, Arrays.asList(QueryCoordinator.newSelector(qo,1)) , RequestTimeout.TRY_ONCE, tx);
			Order o = orders.get(0);
			
			System.out.println(orders.size());

			System.out.println(o.toString());
			//            for (Pizza p : o.getPizzaList()) {
			//c.cookPizzasFromOrder(o);
			//Thread.sleep(p.getPrepareTime());
			//			}
			o.setOrderstatus(OrderStatus.DELIVERYCOOKED);




			Entry orderEntry = new Entry(o, Arrays.asList(KeyCoordinator.newCoordinationData(String.valueOf(o.getId())), QueryCoordinator.newCoordinationData()));
			capi.write(orderEntry, bar,RequestTimeout.TRY_ONCE,tx);
			capi.commitTransaction(tx);
			return true;

		} catch ( Exception e) {
			// AutoRollback

			//e.printStackTrace();
			return false;
		}finally{
			working.set(false);
		}

	}
	
	public static boolean cookForDeliveryTransferPriority(){


		TransactionReference tx;
		try {
			working.set(true);
			tx = capi.createTransaction(RequestTimeout.INFINITE, space);
			ArrayList<Order> orders = new ArrayList<Order>();

			// query coordinator
			Query qo = new Query().sql("status = 'DELIVERYTRANSFERRED' LIMIT 1");
			
			orders = capi.take(bar, Arrays.asList(QueryCoordinator.newSelector(qo)) , RequestTimeout.TRY_ONCE, tx);
			Order o = orders.get(0);
			System.out.println(o.toString());
			//            for (Pizza p : o.getPizzaList()) {
			c.cookPizzasFromOrder(o);
			//Thread.sleep(p.getPrepareTime());
			//			}
			o.setOrderstatus(OrderStatus.DELIVERYCOOKED);




			Entry orderEntry = new Entry(o, Arrays.asList(KeyCoordinator.newCoordinationData(String.valueOf(o.getId())), QueryCoordinator.newCoordinationData()));
			capi.write(orderEntry, bar,RequestTimeout.TRY_ONCE,tx);
			capi.commitTransaction(tx);
			return true;

		} catch ( Exception e) {
			// AutoRollback

			//e.printStackTrace();
			return false;
		}finally{
			working.set(false);
		}

	}
	
	
	public RunCookSBC() throws MzsCoreException, InterruptedException{
		// Create notification
		NotificationManager notifManager = new NotificationManager(core);
		Set<Operation> operations = new HashSet<Operation>();
		operations.add(Operation.WRITE);
		notifManager.createNotification(bar, (NotificationListener) this, operations, null, null);
	}

	@Override
	public void entryOperationFinished(Notification arg0, Operation arg1,
			List<? extends Serializable> arg2) {
		// TODO Auto-generated method stub
		if(working.get() == true) return;

		cook();



	}


}
