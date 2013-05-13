package tuwien.sbctu.rmi;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import tuwien.sbctu.models.GuestGroup;
import tuwien.sbctu.models.GuestGroup.GroupStatus;
import tuwien.sbctu.models.Menue;
import tuwien.sbctu.models.Menue.MenuePizza;
import tuwien.sbctu.models.Order;
import tuwien.sbctu.models.Order.OrderStatus;
import tuwien.sbctu.models.Pizza;
import tuwien.sbctu.rmi.implement.GuestGroupImpl;
import tuwien.sbctu.rmi.interfaces.IGuestGroupRMI;
import tuwien.sbctu.rmi.interfaces.IPizzeriaRMI;

public class RunGuestGroup implements Runnable{

	private boolean isActive;
	
	private int port;
	private String bindingName;
	
	private Long groupid;
	private GuestGroup guestGroup;
	private IGuestGroupRMI igg;
	
	private IPizzeriaRMI pizzeriaRMI;
	
	/**
	 * 
	 * @param id
	 * @param port
	 * @param bindingName
	 */
	public RunGuestGroup(Long id, Integer port, String bindingName, int size){
		isActive = true;
		this.groupid = id;
		this.port = port;
		this.bindingName = bindingName;
		
		guestGroup = new GuestGroup(groupid);
		guestGroup.setGroupSize(size);
		

		try {
			igg = new GuestGroupImpl(groupid);	
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	@Override
	public void run() {
		
		System.out.println("Started GuestGroup - "+ groupid);
		pizzeriaRMI = getEntry(port, bindingName);
		enterPizzeria(pizzeriaRMI);
		
		while(isActive){			
			try {
				work();
				Thread.sleep(1000);
			} catch (InterruptedException | RemoteException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Going home ...");
		
//		System.exit(0);
	}
	
	public void work() throws RemoteException{
		
		GroupStatus gs = igg.getStatus();
				
		switch(gs){
		
		case ENTERED:
			break;
		case SITTING:
			makeOrder();
			break;
		case ORDERED:
			
			break;
		case EATING:
			int random = (1 + (int)(Math.random() * ((5 - 1) + 1))) -1;
			System.out.println("Yuummi yummi, eating this takes "+random+" seconds ... i know its fast ;)");
			try {
				Thread.sleep(random*1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			igg.setStatus(GroupStatus.FINISHED);
			
			pizzeriaRMI.requestBill(guestGroup.getId());
			break;
		case FINISHED:
			break;
		case BILL:
			System.out.println("Thanks for the delicious meal.");
			pizzeriaRMI.payBillNLeave(guestGroup.getId());
			isActive = false;
			break;
		
		default:
			try {
				Thread.sleep(1000);
				enterPizzeria(pizzeriaRMI);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			break;
			
		}
		
	}
	
	/**
	 * 
	 * @param entry
	 */
	private void enterPizzeria(IPizzeriaRMI entry){
		try {
			entry.guestGroupEnters(guestGroup, igg);
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}		
	}
	
	private void makeOrder() throws RemoteException{
		int groupCount = guestGroup.getGroupSize();
		Order order = new Order();
		while(groupCount>0){
			Long ogid = igg.getId();
			System.out.println("group id "+ogid+" for order.");
			Menue menue = new Menue();
			MenuePizza mp = menue.selectPizza();
			
			Pizza p = new Pizza(
					mp.toString(),
					menue.getPricesFor(mp),
					menue.getTimeFor(mp) );
		
			order.setId((groupid*100)+groupCount);
			order.addPizzaToOrder(p);
			order.setGroupID(ogid);
			order.setOrderstatus(OrderStatus.ORDERED);
			groupCount--;
		}
	
			pizzeriaRMI.makeOrder(order);
			igg.setStatus(GroupStatus.ORDERED);
	}
	
	/**
	 * 
	 * @param port
	 * @param bindingName
	 * @return
	 */
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
