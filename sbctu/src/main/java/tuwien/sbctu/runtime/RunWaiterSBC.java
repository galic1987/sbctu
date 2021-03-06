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

import org.mozartspaces.capi3.AnyCoordinator;
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
import tuwien.sbctu.models.GuestGroup;
import tuwien.sbctu.models.GuestGroup.GroupStatus;
import tuwien.sbctu.models.Order;
import tuwien.sbctu.models.Order.OrderStatus;
import tuwien.sbctu.models.Table;
import tuwien.sbctu.models.Waiter;

public class RunWaiterSBC implements NotificationListener{

	/**
	 * @param args
	 */

	protected  long id;
	protected  MzsCore core;
	protected  Capi capi;
	protected  URI space;
	protected  ContainerReference entrance;
	protected  ContainerReference tables;
	protected  ContainerReference bar;
	protected  ContainerReference delivery;

	protected  Waiter w;
	protected  AtomicBoolean working;
	protected  AtomicBoolean checkDelivery;
	protected  String spaceAddress;

	protected  int timeOut;


	public  void main(String[] args) {
		// TODO Auto-generated method stub
		try {
		int port = Integer.valueOf(args[0]);
		id = (long)Integer.valueOf(args[1]);
		spaceAddress = args[2];
		
		core = DefaultMzsCore.newInstance(port);
		capi = new Capi(core);
		w = new Waiter(id);
		timeOut = 10000;

		working = new AtomicBoolean();
		working.set(false);
		checkDelivery  = new AtomicBoolean();
		checkDelivery.set(false);

		
			space = new URI(spaceAddress);


			ArrayList<Coordinator> obligatoryCoords = new ArrayList<Coordinator>();
			obligatoryCoords.add(new FifoCoordinator());
			obligatoryCoords.add(new KeyCoordinator());


			TransactionReference tx = capi.createTransaction(timeOut, space); 

			entrance = capi.lookupContainer(PizzeriaConfiguration.CONTAINER_NAME_ENTRANCE, space, timeOut, tx);
			tables = capi.lookupContainer(PizzeriaConfiguration.CONTAINER_NAME_TABLES, space, timeOut, tx);
			bar = capi.lookupContainer(PizzeriaConfiguration.CONTAINER_NAME_BAR, space, timeOut, tx);
			delivery = capi.lookupContainer(PizzeriaConfiguration.CONTAINER_NAME_DELIVERY, space, timeOut, tx);

		      NotificationManager notifManager = new NotificationManager(core);
		        Set<Operation> operations = new HashSet<Operation>();
		        operations.add(Operation.WRITE);
		        notifManager.createNotification(delivery, (NotificationListener) this, operations, null, null);
		        notifManager.createNotification(entrance, (NotificationListener) this, operations, null, null);


			for (;;) {
				try {
				//System.out.println("-----> Starting all over again" + spaceAddress);

				//	if (working.get()) continue;

				if(checkTheDeliveryOrders()) continue;
				
				
				// 1. entrance check -> check entrance, and bring the guestgroups to table -> make table
				entranceTake();
				//if (working.get()) continue;

				// 2. tables -> if order ORDERED -> put it on the theke -> status ORDERONBAR
				ordersTake();
				//if (working.get()) continue;

				// 3. if order FINISHED , take it from theke and take the table and write table
				putThemToEat();
				//if (working.get()) continue;

				
				// 4. if there BILL request read it -> log it and delete it		
				doTheBilling();
				//if (working.get()) continue;

				// 5. take the deliveries to bar

				// sleep for a while, it is hard day
				
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}


		} catch (URISyntaxException | MzsCoreException e) {
			// TODO Auto-generated catch block
			System.out.println(">>>usage RunWaiterSBC <myPort> <myId> <addressOfSpace>");

			//capi.rollbackTransaction(tx);
			e.printStackTrace();

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public  void entranceTake(){
		TransactionReference tx;
		try {
			working.set(true);
			tx = capi.createTransaction(timeOut, space);
			ArrayList<GuestGroup> entries = new ArrayList<GuestGroup>();

			entries = capi.take(entrance, Arrays.asList(FifoCoordinator.newSelector()) , RequestTimeout.TRY_ONCE, tx);
			GuestGroup g = entries.get(0);

			g.setStatus(GroupStatus.SITTING);
			Table t = new Table(g.getId());
			t.setGroup(g);
			Entry entry = new Entry(t, Arrays.asList(KeyCoordinator.newCoordinationData(String.valueOf(t.getId())), QueryCoordinator.newCoordinationData()));
			capi.write(entry, tables,timeOut,tx);

			System.out.println("Entry -> Tables with id " +  g.getId());
			capi.commitTransaction(tx);

		} catch ( Exception e) {
			// AutoRollback

			printExp(e);
		}finally{
			working.set(false);
		}

	}


	public  void ordersTake(){
		TransactionReference tx;
		try {
			working.set(true);
			tx = capi.createTransaction(timeOut, space);
			ArrayList<Table> entries = new ArrayList<Table>();

			// query coordinator   
			// 
			Query q = new Query().sql("group.currentStatus = 'ORDERED' LIMIT 1");

			//System.out.println("*****" +capi.test(tables));

			entries = capi.take(tables, Arrays.asList(QueryCoordinator.newSelector(q)) , RequestTimeout.TRY_ONCE, tx);
			Table t = entries.get(0);
			Order o = t.getOrder();


			o.setOrderstatus(OrderStatus.ORDERED);
			t.getGroup().setStatus(GroupStatus.ORDERONBAR);


			Entry orderEntry = new Entry(o, Arrays.asList(KeyCoordinator.newCoordinationData(String.valueOf(o.getId())), QueryCoordinator.newCoordinationData()));
			capi.write(orderEntry, bar,timeOut,tx);

			System.out.println("******----->  " +o.toString());


			Entry tableEntry = new Entry(t, Arrays.asList(KeyCoordinator.newCoordinationData(String.valueOf(t.getId())), QueryCoordinator.newCoordinationData()));
			capi.write(tableEntry, tables,timeOut,tx);
			capi.commitTransaction(tx);


		} catch ( Exception e) {
			// AutoRollback

			printExp(e);
		}finally{
			working.set(false);
		}

	}


	public  void putThemToEat(){
		TransactionReference tx;
		try {
			working.set(true);
			tx = capi.createTransaction(timeOut, space);
			ArrayList<Order> orders = new ArrayList<Order>();
			ArrayList<Table> tablesArr = new ArrayList<Table>();

			// query coordinator
			Query qo = new Query().sql("status = 'COOKED' LIMIT 1");

			orders = capi.take(bar, Arrays.asList(QueryCoordinator.newSelector(qo)) , RequestTimeout.TRY_ONCE, tx);
			Order o = orders.get(0);


			tablesArr = capi.take(tables, Arrays.asList(KeyCoordinator.newSelector(o.getId().toString(),1)) , RequestTimeout.TRY_ONCE, tx);
			Table t = tablesArr.get(0);

			t.getGroup().setStatus(GroupStatus.EATING);
			t.setOrder(o);
			o.setOrderstatus(OrderStatus.SERVING);


			Entry tableEntry = new Entry(t, Arrays.asList(KeyCoordinator.newCoordinationData(String.valueOf(t.getId())), QueryCoordinator.newCoordinationData()));
			capi.write(tableEntry, tables,timeOut,tx);
			capi.commitTransaction(tx);


		} catch ( Exception e) {
			// AutoRollback

			printExp(e);
		}finally{
			working.set(false);
		}

	}


	public  void doTheBilling(){
		TransactionReference tx;
		try {
			working.set(true);

			//if(capi.test(tables, Arrays.asList(QueryCoordinator.newSelector(q)) , RequestTimeout., null)); 

			tx = capi.createTransaction(timeOut, space);
			ArrayList<Table> entries = new ArrayList<Table>();

			// query coordinator
			Query q = new Query().sql("group.currentStatus = 'BILL' LIMIT 1");

			entries = capi.read(tables, Arrays.asList(QueryCoordinator.newSelector(q)) , RequestTimeout.TRY_ONCE, tx);
			Table t = entries.get(0);
			Order o = t.getOrder();



			capi.delete(tables, Arrays.asList(QueryCoordinator.newSelector(q),KeyCoordinator.newSelector(t.getId().toString())), RequestTimeout.TRY_ONCE, tx);

			System.out.println("Paid bill "+o.getId());


			capi.commitTransaction(tx);


		} catch ( Exception e) {
			// AutoRollback
			printExp(e);
		}finally{
			working.set(false);
		}

	}


	public  boolean checkTheDeliveryOrders(){
		TransactionReference tx;
		try {
			working.set(true);
			tx = capi.createTransaction(timeOut, space);
			ArrayList<Order> entries = new ArrayList<Order>();

			entries = capi.take(delivery, Arrays.asList(AnyCoordinator.newSelector(1)) , RequestTimeout.TRY_ONCE, tx);
			System.out.println(entries.size());
			
			for (Order o : entries){
				o.setOrderstatus(OrderStatus.DELIVERYNEW);
				
				Entry entry = new Entry(o, Arrays.asList(KeyCoordinator.newCoordinationData(String.valueOf(o.getId())), QueryCoordinator.newCoordinationData()));
				capi.write(entry, bar,timeOut,tx);

				System.out.println("Waiterdoes: Delivery -> Bar " +  o.getId());
			}
			
			capi.commitTransaction(tx);
			if(entries.size()==0) return false;
			
			return true;
		} catch ( Exception e) {
			// AutoRollback
			//e.printStackTrace();

			return false;
			//printExp(e);
		}finally{
			working.set(false);
		}	
	}

	
	
	
	

	public  void printExp(Exception e){
		//e.printStackTrace();

	}
	
	public RunWaiterSBC() throws MzsCoreException, InterruptedException{
		// Create notification
  
	}

	@Override
	public void entryOperationFinished(Notification arg0, Operation arg1,
			List<? extends Serializable> arg2) {
		// TODO Auto-generated method stub
		if(working.get() == true) return;
		
		if(arg0.getObservedContainer().getId().equals("4")){
			checkTheDeliveryOrders();
		}
		
		if(arg0.getObservedContainer().getId().equals("1")){
			entranceTake();
		}
		
		
	}




}
