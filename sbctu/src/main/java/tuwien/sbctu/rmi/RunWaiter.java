package tuwien.sbctu.rmi;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import tuwien.sbctu.models.Waiter.WaiterStatus;
import tuwien.sbctu.rmi.implement.WaiterImpl;
import tuwien.sbctu.rmi.interfaces.IPizzeriaRMI;
import tuwien.sbctu.rmi.interfaces.IWaiterRMI;

public class RunWaiter implements Runnable{

	private boolean isActive;
	private int port;
	private String bindingName;
	
//	private Waiter waiter;
	
	private IWaiterRMI iw;
	private WaiterImpl wi;
	
	private IPizzeriaRMI entry;
	
	/**
	 * 
	 */
	public RunWaiter (Long id, Integer port, String bindingName){
		isActive = true;
		
		this.port = port;
		this.bindingName = bindingName;
		
//		this.waiter = new Waiter(id);
		
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
		entry = getEntry(port, bindingName);
		System.out.println("-- WAITER STARTED -- " + wi.getWaiter().getId() );
		
		wi.setEntry(entry);
		
		while(isActive){
			work();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void work(){
		
		WaiterStatus ws = wi.getWaiter().getWaiterStatus();
		
		switch(ws){
		
		case WELCOME: 			
			beginWork(entry);
			System.out.println("-- Begin Working. --");
			break;
		case WAITING: 
//			System.out.println("Waiter: "+wi.getWaiter().getId() + ", is waiting for work.");
			break;
		case WORKING:
			try {
				Thread.sleep(12000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	
	private void beginWork(IPizzeriaRMI entry){
		try {
			entry.waiterEnteres(iw);
			wi.getWaiter().setWaiterStatus(WaiterStatus.WAITING);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	//TODO getTable RMIInterface
	
	//TODO getTheke RMIInterface

}
