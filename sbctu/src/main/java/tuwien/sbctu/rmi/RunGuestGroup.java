package tuwien.sbctu.rmi;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import tuwien.sbctu.models.GuestGroup.GroupStatus;
import tuwien.sbctu.rmi.implement.GuestGroupImpl;
import tuwien.sbctu.rmi.interfaces.IGuestGroupRMI;
import tuwien.sbctu.rmi.interfaces.IPizzeriaRMI;

public class RunGuestGroup implements Runnable{

	private boolean isActive;
	
	private int port;
	private String bindingName;
	
	private GuestGroupImpl ggi;
	private IGuestGroupRMI igg;
	
	private IPizzeriaRMI entry;
	
	/**
	 * 
	 * @param id
	 * @param port
	 * @param bindingName
	 */
	public RunGuestGroup(Long id, Integer port, String bindingName){
		
		isActive = true;

		this.port = port;
		this.bindingName = bindingName;
		
		try {
			ggi = new GuestGroupImpl(id);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		igg = ggi;
		
	}
	
	@Override
	public void run() {
		System.out.println("Started GuestGroup - "+ggi.getGuestGroup().getId());
		entry = getEntry(port, bindingName);
		
		while(isActive){
			work();			
		}
	}
	
	public void work(){
		
		GroupStatus gs = ggi.getGuestGroup().getStatus();
		
		switch(gs){
		
		case WELCOME:
			System.out.println("Welcome GuestGroup - "+ggi.getGuestGroup().getId()); 
			enterPizzeria(entry);
			break;
		case ENTERED:
			break;
		case SITTING:
			break;
		case ORDERED:			
			break;
		case EATING:
			break;
		case FINISHED:
			break;
		
		default:
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
			
		}
		
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
	
	/**
	 * 
	 * @param entry
	 */
	private void enterPizzeria(IPizzeriaRMI entry){
		try {
			System.out.println("Entered GuestGroup - "+ggi.getGuestGroup().getId());
			entry.guestGroupEnters(igg);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ggi.getGuestGroup().setStatus(GroupStatus.ENTERED);
		
	}

}
