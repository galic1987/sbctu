package tuwien.sbctu.rmi.implement;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import tuwien.sbctu.models.GuestGroup.GroupStatus;
import tuwien.sbctu.rmi.interfaces.IGuestGroupRMI;

public class GuestGroupImpl extends UnicastRemoteObject implements IGuestGroupRMI{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;	
	private GroupStatus gs;

	public GuestGroupImpl(Long id) throws RemoteException {
		super();
		this.id = id;
		this.gs = GroupStatus.WELCOME;
	}

	@Override
	public void notification(String message) throws RemoteException {
		System.out.println(message);
		processNotification(message);
	}
	
	@Override
	public Long getId(){
		return id;
	}

	@Override
	public void setStatus(GroupStatus gs) throws RemoteException {
		this.gs = gs;
	}

	@Override
	public GroupStatus getStatus() throws RemoteException {
		return gs;
	}
	
	public void processNotification(String message){
		if(message.contains("Welcome!"))
			gs = GroupStatus.ENTERED;
		else if(message.contains("table"))
			gs = GroupStatus.SITTING;
		else if (message.contains("order"))
			gs = GroupStatus.ORDERED;
		else if (message.contains("Bon"))
			gs = GroupStatus.EATING;
		else if (message.contains("Bill"))
			gs = GroupStatus.BILL;
			
		
	}
}
