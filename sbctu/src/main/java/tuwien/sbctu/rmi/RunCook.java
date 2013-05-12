package tuwien.sbctu.rmi;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import tuwien.sbctu.models.Cook;
import tuwien.sbctu.models.Cook.CookStatus;
import tuwien.sbctu.models.Pizza;
import tuwien.sbctu.rmi.implement.CookImpl;
import tuwien.sbctu.rmi.interfaces.ICookRMI;
import tuwien.sbctu.rmi.interfaces.IPizzeriaRMI;

public class RunCook implements Runnable{

	private boolean isActive;
	private int port;
	private String bindingName;
	
	private Cook cook;
	
	private ICookRMI ic;
	private CookImpl ci;
	
	private IPizzeriaRMI pizzeriaRMI;
	
	/**
	 * 
	 */
	public RunCook (Long id, Integer port, String bindingName){
		isActive = true;
		
		this.port = port;
		this.bindingName = bindingName;
		
		this.cook = new Cook(id);
		
		try {
			ci = new CookImpl(id);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

		ic = ci;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		pizzeriaRMI = getEntry(port, bindingName);
		beginWork(pizzeriaRMI);
		
		while(isActive){
			
			try {
				work();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void work()throws RemoteException{
		
		CookStatus cs = ic.getStatus();

		switch(cs){

		case WAITING:
			getOrderAndWork();
			break;
		case WORKING:
			getOrderAndWork();
			break;			
		default:
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			break;
			
		}
		
	}
	
	public void getOrderAndWork() throws RemoteException{
		Pizza pizza = pizzeriaRMI.cookPizza();
		
		if(pizza != null){
			try {
				Thread.sleep(pizza.getPrepareTime());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			pizzeriaRMI.pizzaCooked(pizza);
			System.out.println("Pizza "+pizza.getName()+" cooked.");
		}
		
//		Order order = pizzeriaRMI.checkIfOrdersAvailable();
//		if(order != null){
//			System.out.println("Order from group: "+order.getGroupID()+" is being processed by cook: "+cook.getId());
//			ic.setStatus(CookStatus.WORKING);
//			ArrayList<Pizza> pizzas = order.getPizzaList();for(Pizza p : pizzas){
//			try {
//				p.setStatus(PizzaStatus.INMAKING);
//				Thread.sleep(p.getPrepareTime());
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			p.setCookId(cook.getId());
//			p.setStatus(PizzaStatus.FINISHED);
//		}
//		order.setOrderstatus(OrderStatus.SERVING);
//		pizzeriaRMI.putFinishedOrderToBar(order);
//		}
//		ic.setStatus(CookStatus.WAITING);
	}
	private void beginWork(IPizzeriaRMI entry){
		try {
			entry.cookEnters(cook, ic);
		} catch (RemoteException e) {
			e.printStackTrace();
		}		
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
