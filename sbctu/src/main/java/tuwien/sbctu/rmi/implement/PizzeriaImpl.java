package tuwien.sbctu.rmi.implement;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import tuwien.sbctu.models.Cook;
import tuwien.sbctu.models.Cook.CookStatus;
import tuwien.sbctu.models.GuestGroup;
import tuwien.sbctu.models.Order;
import tuwien.sbctu.models.Order.OrderStatus;
import tuwien.sbctu.models.Pizza;
import tuwien.sbctu.models.Pizza.PizzaStatus;
import tuwien.sbctu.models.Table;
import tuwien.sbctu.models.Table.TableStatus;
import tuwien.sbctu.models.Waiter;
import tuwien.sbctu.rmi.interfaces.ICookRMI;
import tuwien.sbctu.rmi.interfaces.IGuestGroupRMI;
import tuwien.sbctu.rmi.interfaces.ILoggingRMI;
import tuwien.sbctu.rmi.interfaces.IPizzeriaRMI;
import tuwien.sbctu.rmi.interfaces.IWaiterRMI;

public class PizzeriaImpl extends UnicastRemoteObject implements IPizzeriaRMI{
	ILoggingRMI logrec;
	
	Queue<GuestGroup> waitingEntry = new ConcurrentLinkedQueue<GuestGroup>();
	List<GuestGroup> guests = new ArrayList<GuestGroup>();
	
	List<Table> freeTables = Collections.synchronizedList(new ArrayList<Table>());
	List<Table> usedTables = Collections.synchronizedList(new ArrayList<Table>());	
	
	Queue<Order> newOrders = new ConcurrentLinkedQueue<Order>();
	List<Order> processingOrder = new ArrayList<Order>();
	Queue<Order> finishedOrder = new ConcurrentLinkedQueue<Order>();
	
	Queue<Pizza> pizzas = new ConcurrentLinkedQueue<Pizza>();
	
	List<IWaiterRMI> iw = new ArrayList<IWaiterRMI>();
	List<ICookRMI> ic = new ArrayList<ICookRMI>();
	List<IGuestGroupRMI> groupSubscribers = new ArrayList<IGuestGroupRMI>();
	
	private static final long serialVersionUID = 1L;

	public PizzeriaImpl(Integer tableSize) throws RemoteException {
		super();
		for(int i = 1; i <= tableSize; i++){
			Long tabId = Long.valueOf(i);
			freeTables.add(new Table(tabId));
//			System.out.println("* Created table, id: "+i);
		}
	}

	@Override
	public void guestGroupEnters(GuestGroup group, IGuestGroupRMI subscriber) throws RemoteException {
		waitingEntry.add(group);
		groupSubscribers.add(subscriber);
		waiterWorkNotifier("Entry : Guests are waiting.");
		System.out.println("GuestGroup entered: "+group.getId());
		subscriber.notification("* RMIPizzeria: Welcome!");
		
		logrec.guestInfo("New group id:"+group.getId()+" entered, they need "+group.getGroupSize()+" chairs.");
	}
	public void waiterWorkNotifier(String message){
		for(IWaiterRMI iwr: iw){
			try {
				if(iwr.getStatus() == 0){
					iwr.notification(message);
				}else
					System.out.println("All waiters are working.");
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public GuestGroup getFromEntry() throws RemoteException {
		GuestGroup gg = waitingEntry.poll();
		return gg;
	}
	
	public synchronized GuestGroup getGuestGroup(Long id) throws RemoteException {
		GuestGroup result = null;
		for(GuestGroup gg : guests){
			if(gg.getId().equals(id))
				result = gg;
		}
		return result;
	}
	
	@Override
	public synchronized void sitdownGuestGroup(GuestGroup group, Table tab) throws RemoteException {
		tab.setGroup(group);
		tab.setGroupID(group.getId());
		tab.setTabStat(TableStatus.USED);
		usedTables.add(tab);
		
		for(IGuestGroupRMI igg : groupSubscribers){
			if (igg.getId().equals(tab.getGroupID()))
				igg.notification("You sit on table : "+tab.getId());			
		}
		System.out.println("Group: "+tab.getGroupID()+" is sitting on table: "+ tab.getId());
		int freeChair = 4 - group.getGroupSize();
		logrec.tableInfo("Table id:"+tab.getId()+" is used.");
		logrec.guestInfo("Group id:"+tab.getGroupID()+" takes place. Chairs free:"+freeChair);
	}

	@Override
	public void makeOrder(Order order) throws RemoteException {
		System.out.println("Group "+ order.getGroupID()+ " wants to order.");
		newOrders.add(order);
		waiterWorkNotifier("Table : Order is waiting.");
		String pizzas = "";
		for(int i = 0; i < order.getPizzaList().size(); i++){
			pizzas += order.getPizzaList().get(i)+" ";
		}
		
		logrec.orderInfo("New order id:"+order.getId()+"\n\t for group id:"+order.getGroupID()+"\n\t\t with pizza:"+pizzas);
	}

	@Override
	public void waiterEnteres(Waiter w, IWaiterRMI iw) throws RemoteException {
		this.iw.add(iw);
		System.out.println("Waiter: "+w.getId()+" entered.");
		logrec.guestInfo("Waiter id:"+iw.getId()+" entered.");
	}

	@Override
	public void waiterLeaves(IWaiterRMI iwr) throws RemoteException {
		Iterator<IWaiterRMI> it = iw.iterator();
		while(it.hasNext()){
			IWaiterRMI i = it.next();
			if(i.getId().equals(iwr.getId())){
				it.remove();
				break;
			}
		}
		logrec.guestInfo("Waier id:"+iwr.getId()+" left.");
	}

	@Override
	public synchronized Table getTableWithStatus(TableStatus tb) throws RemoteException {
		Table result = null;
		
		synchronized (freeTables){
		
		Iterator<Table> it = freeTables.iterator();
		while(it.hasNext()){
			Table t = (Table) it.next();
			if(t.getTabStat().equals(tb))
			{
				result = t;
				it.remove();
				System.out.println("Table: "+t.getId()+" is free.");
				break;
			}
			else
				System.out.println("All tables are idle.");
		} }
		return result;
	}
	
	public synchronized Table getUsedTableWithStatus(TableStatus tb) throws RemoteException {
		Table result = null;
		
		synchronized (usedTables){
		
		Iterator<Table> it = usedTables.iterator();
		while(it.hasNext()){
			Table t = (Table) it.next();
			if(t.getTabStat().equals(tb))
			{
				result = t;
				it.remove();
				System.out.println("Table: "+t.getId()+" is ready for paying.");
				break;
			}
			else
				System.out.println("Tables wont pay.");
		} }
		return result;
	}
	
	public Table getTableWithId(Long tb) throws RemoteException {
		Table result = null;
		
		synchronized (usedTables){
		
		Iterator<Table> it = usedTables.iterator();
		while(it.hasNext()){
			Table t = (Table) it.next();
			System.out.println("Waiter sees table:"+t.getId());
			if(t.getId().equals(tb))
			{
				result = t;
				System.out.println("Got table: "+t.getId()+"");
				break;
			}
			else
				System.out.println("Table not found! Somebody ate it?");
		} }
		return result;
	}

	@Override
	public Table isBillRequested() throws RemoteException {
		Table result = null;
		for(Table tab : usedTables)
		{
			if (tab.requestBill())
				result = tab;
		}
		return result;
	}

	@Override
	public Order isOrderReady() throws RemoteException {
		return finishedOrder.poll();
	}

	@Override
	public void cookEnters(Cook cook, ICookRMI cookrmi) throws RemoteException {
		ic.add(cookrmi);	
		System.out.println("Cook: "+cook.getId()+" entered the kitchen.");
//		if(logrec != null)
		logrec.guestInfo("Cook id:"+cook.getId()+" entered the kitchen.");
	}

	@Override
	public Order isOrderWaiting() throws RemoteException {
//		Order result = null;
//		synchronized(newOrders){
//			Iterator<Order> it = newOrders.iterator();
//			while(it.hasNext()){
//				Order o = (Order) it.next();
//				result = o;
//				System.out.println("Order is being processed");
//				it.remove();
//				break;
//			}
//		}
//		return result;
		
		return newOrders.poll();
	}

	@Override
	public void freeTable(Table table) throws RemoteException {
		synchronized(freeTables){
			freeTables.add(table);
		}
	}

	@Override
	public void returnToEntry(GuestGroup group) throws RemoteException {
		waitingEntry.add(group);
		waiterWorkNotifier("Entry : Guests are waiting.");		
	}

	@Override
	public Long getTableForGroupID(Long id) throws RemoteException {
		System.out.println("... find table for groupID "+id);
		Long tabid = null;
		synchronized(usedTables){
			for(Table t : usedTables){
				if(t.getGroupID().equals(id)){
					tabid = t.getId();
					break;		
				}
			}
		}
		return tabid;
	}
	

	@Override
	public void putNewOrderToBar(Order order) throws RemoteException {
		for(Pizza p : order.getPizzaList())
		{
			pizzas.add(p);
		}
		synchronized(processingOrder){
			processingOrder.add(order);
			System.out.println("Order: "+order.getId()+" is being prepared for table:"+order.getTableID()+".");
		}
		cookWorkNotifier("Todo: Order recieved.");
		logrec.orderInfo("Order id:"+order.getId()+" is being:"+order.getStatus());
	}
	
	
	public Pizza cookPizza() throws RemoteException{
		Pizza p = pizzas.poll();
		if(p!=null)
			logrec.pizzaInfo("Pizza *"+p.getName()+"* is being cooked.");
		return p;
		
	}
	
	public void cookWorkNotifier(String message){
		
		for(ICookRMI icr: ic){
			try {
				if(icr.getStatus().equals(CookStatus.WAITING) ){
					icr.notification(message);
				} else
					System.out.println("All cooks are working.");
			} catch (RemoteException e) {
				e.printStackTrace();	
			}
		}	
		
	}

	@Override
	public void pizzaCooked(Pizza pizza) throws RemoteException {
		boolean found = false;
		
		synchronized(processingOrder){
			for(Order o : processingOrder){
				
				if(o.getOrderstatus().equals(OrderStatus.PROCESSING)){
					//counter for finished pizzas
					int counter = 0;
					int sizeOrder = o.getPizzaList().size();
					
					//iterate pizzas to find the finished one 
					//also increment the counter to determine if order is finished
					for(Pizza p : o.getPizzaList()){
						if(p.getName().equals(pizza.getName()) && !p.getStatus().equals(PizzaStatus.FINISHED)){
							p.setStatus(PizzaStatus.FINISHED);
							logrec.pizzaInfo("Pizza *"+p.getName()+"* from order id:"+o.getId()+" finished");
							found = true;
						}
						if(p.getStatus().equals(PizzaStatus.FINISHED))
							counter++;
						if(counter == sizeOrder){
							o.setOrderstatus(OrderStatus.COOKED);
							finishedOrder.add(o);
							waiterWorkNotifier("Order id:"+o.getId()+" is ready for serving.");
							logrec.orderInfo("Order id:"+o.getId()+" is ready for serving.");
						}
					}	
				}
				if(found==true)
					break;
			}							
		}	
	}

	@Override
	public void putTableBack(Table tab) throws RemoteException {
		synchronized(usedTables){
			usedTables.add(tab);
		}
		guestNotifier("Your meal is ready, Bon appetit!", tab.getGroupID());
		logrec.guestInfo("Order id:"+tab.getOrder().getId()+"\n\t recieved on table id:"+tab.getId()+"\n\t\t for group id:"+tab.getGroupID());
	}
	
	@Override
	public void putTableBill(Table tab) throws RemoteException {
		synchronized(usedTables){
			usedTables.add(tab);
		}
		guestNotifier("Bill to pay: "+tab.getBill(), tab.getGroupID());
		logrec.guestInfo("Group id:"+tab.getGroupID()+" - Your Bill sum:"+tab.getBill()+", table id:"+tab.getId());
	}

	@Override
	public void guestNotifier(String message, Long id) throws RemoteException {
		for(IGuestGroupRMI igg: groupSubscribers){
			try {
				if(igg.getId().equals(id)){
					igg.notification(message);
				}else
					System.out.println("All waiters are working.");
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void requestBill(Long groupId) throws RemoteException {
		
		synchronized(usedTables){
			for(Table t: usedTables){
				if(t.getGroupID().equals(groupId)){
					t.setTabStat(TableStatus.PAY);
				}
			}
		}
		waiterWorkNotifier("Bill is requested.");
		logrec.waiterInfo("Group id:"+groupId+" wants to pay.");
	}

	@Override
	public void payBillNLeave(Long groupId) throws RemoteException {
		synchronized(groupSubscribers){
			Iterator<IGuestGroupRMI> it = groupSubscribers.iterator();
			while(it.hasNext()){
				IGuestGroupRMI ir = it.next();
				if(ir.getId().equals(groupId))
					it.remove();
			}
		}
		
		synchronized(usedTables){
			Table freeTable = null;
			Iterator<Table> it = usedTables.iterator();
			while(it.hasNext()){
				Table t = (Table) it.next();
				if (t.getGroupID().equals(groupId)){
					logrec.tableInfo("Table id:"+t.getId()+" is free.");
					t.leaveTable();
					freeTable = t;
					it.remove();
				}
			}
			freeTables.add(freeTable);
			
			System.out.println("Group left, table:"+freeTable.getId()+" is free.");
		}
		
		logrec.guestInfo("Group id:"+groupId+" left happily the pizzeria.");
	}

	@Override
	public boolean todoEntry() throws RemoteException {		
		return !waitingEntry.isEmpty();
	}

	@Override
	public boolean todoBill() throws RemoteException {
		boolean result = false;
		
		Iterator<Table> it = usedTables.iterator();
		
		while(it.hasNext()){
			Table t = it.next();
			if(t.getTabStat().equals(TableStatus.PAY)){
				result = true;
				break;
			}
		}
		return result;
	}

	@Override
	public boolean todoBar() throws RemoteException {
		return !finishedOrder.isEmpty();
	}

	@Override
	public void subscribeGUI(ILoggingRMI logrmi) throws RemoteException {
		this.logrec = logrmi;
		logrec.testMe();
		System.out.println("GUI registered.");
	}
}