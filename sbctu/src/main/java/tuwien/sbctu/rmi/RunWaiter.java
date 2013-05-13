package tuwien.sbctu.rmi;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import tuwien.sbctu.models.GuestGroup;
import tuwien.sbctu.models.Order;
import tuwien.sbctu.models.Order.OrderStatus;
import tuwien.sbctu.models.Pizza;
import tuwien.sbctu.models.Table;
import tuwien.sbctu.models.Table.TableStatus;
import tuwien.sbctu.models.Waiter;
import tuwien.sbctu.rmi.implement.WaiterImpl;
import tuwien.sbctu.rmi.interfaces.IPizzeriaRMI;
import tuwien.sbctu.rmi.interfaces.IWaiterRMI;

public class RunWaiter implements Runnable{

	private boolean isActive;
	private int port;
	private String bindingName;
	
	private Waiter waiter;
	
	private IWaiterRMI iw;
	private WaiterImpl wi;
	
	private IPizzeriaRMI pizzeriaRMI;
	
	/**
	 * 
	 */
	public RunWaiter (Long id, Integer port, String bindingName){
		isActive = true;
		
		this.port = port;
		this.bindingName = bindingName;
		
		this.waiter = new Waiter(id);
		
		try {
			wi = new WaiterImpl(id);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		iw = wi;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		pizzeriaRMI = getEntry(port, bindingName);
		beginWork(pizzeriaRMI);
		
		while(isActive){
			
			try {
				work();
//				Thread.sleep(500);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void work()throws RemoteException{
		
		int ws = iw.getStatus();

		switch(ws){

		case 0:
			
			lookForWork();
			break;
		case 1:
			bringToTable();
			break;
		case 2:
			processNewOrder();
			break;
		case 3:
			serveOrder();
			break;
		case 4:
			processBill();
			break;
			
		default:
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
			
		}
		
	}
	
	private void beginWork(IPizzeriaRMI entry){
		try {
			entry.waiterEnteres(waiter, iw);
		} catch (RemoteException e) {
			e.printStackTrace();
		}		
	}
	
	private void bringToTable() throws RemoteException{
		GuestGroup gg =	pizzeriaRMI.getFromEntry();

		if(gg != null){
			Table table = pizzeriaRMI.getTableWithStatus(TableStatus.FREE);
			if(table!= null){
				pizzeriaRMI.sitdownGuestGroup(gg, table);
				System.out.println("Served group "+gg.getId());
			}
			else{
				pizzeriaRMI.returnToEntry(gg);
				System.out.println("All tables idle, Group has to wait again.");
			}
		}	
		else
			System.out.println("Group is being served by others.");
		
		iw.setStatus(0);
	}
	
	private void processNewOrder() throws RemoteException{
		System.out.println("processNewOrder");
		
			Order order = pizzeriaRMI.isOrderWaiting();	
			Long tableID = null;
			try{
				
				tableID = pizzeriaRMI.getTableForGroupID(order.getGroupID());
				System.out.println("Order:"+order.getId()+" for table:"+tableID+" recieved.");
			} catch (NullPointerException e){
				iw.setStatus(0);
			}
			if(tableID != null){
				order.setTableID(tableID);
				order.setWaiterTableAssigmentId(tableID);
				order.setOrderstatus(OrderStatus.PROCESSING);
				order.setWaiterOrderTookId(iw.getId());
				pizzeriaRMI.putNewOrderToBar(order);			
			}
			
			System.out.println("..waiting to do more work ...");
			iw.setStatus(0);
		
	}
	
	private void serveOrder() throws RemoteException{
		Order ord = pizzeriaRMI.isOrderReady();
		
		if(ord != null){
			System.out.println("Serving order: "+ord.getId());
//			Long grid = ord.getGroupID();
			Long tabid = ord.getTableID();
			
			Table tab = null;
			tab = pizzeriaRMI.getTableWithId(tabid);
			if(tab != null){
				tab.setOrder(ord);
				pizzeriaRMI.putTableBack(tab);
			}
		}
		else
			System.out.println("Order already serving by others.");
		
		System.out.println("..waiting to do more work ...");
		iw.setStatus(0);
	}
	
	private void processBill() throws RemoteException{
		Table tab = null;
		
		try{
			tab = pizzeriaRMI.getUsedTableWithStatus(TableStatus.PAY);
		
		
		if(tab!= null){
		double bill = 0.0;
		System.out.println("processBill for table:"+tab.getId());
		Order o = tab.getOrder();
		System.out.println("* Order:"+o.getId());
			for(Pizza p : o.getPizzaList()){
				System.out.println(" ** Pizza:"+p.getName());
				bill += p.getPrice();
			}
		
		tab.setBill(bill);
		pizzeriaRMI.putTableBill(tab);
		}
		} catch (NullPointerException npe){
			System.out.println("All payed.");
		}
			
		System.out.println("..waiting to do more work ...");
		iw.setStatus(0);
	}
	
	private void lookForWork() throws RemoteException{
		
//		if(pizzeriaRMI.todoBill())
//			iw.setStatus(4);
//		else if(pizzeriaRMI.todoBar())
//			iw.setStatus(3);
//		else 
			if(pizzeriaRMI.todoEntry())
			iw.setStatus(1);
	}

	private IPizzeriaRMI getEntry(Integer port, String bindingName){
		Registry registry = null;

		try {
			registry = LocateRegistry.getRegistry(port);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}

		IPizzeriaRMI entry = null;

		try {
			entry = (IPizzeriaRMI) registry.lookup(bindingName);
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (AccessException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return entry;
	}
}
