package tuwien.sbctu.rmi.implement;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import tuwien.sbctu.models.GuestGroup;
import tuwien.sbctu.models.GuestGroup.GroupStatus;
import tuwien.sbctu.rmi.interfaces.IGuestGroup;

public class GuestGroupImpl extends UnicastRemoteObject implements IGuestGroup{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	GuestGroup guestGroup;
	
	public GuestGroupImpl(GuestGroup gg) throws RemoteException{
		this.guestGroup = gg;
	}

	public GuestGroup getGg() {
		return guestGroup;
	}

	public void setGg(GuestGroup gg) {
		this.guestGroup = gg;
	}

	@Override
	public void notification(String message) throws RemoteException {
		System.out.println(message);
		processNotification(message);		
	}

	@Override
	public GuestGroup getGroup() throws RemoteException {
		return guestGroup;
	}
	
	@Override
	public void setStatus(GroupStatus groupStatus) throws RemoteException{
		guestGroup.setStatus(groupStatus);
	}
	
	public void processNotification(String message){
		if(message.contains("!welcome"))
			guestGroup.setStatus(GroupStatus.ENTERED);
		else if(message.contains("!table"))
			guestGroup.setStatus(GroupStatus.SITTING);
		else if (message.contains("!order"))
			guestGroup.setStatus(GroupStatus.ORDERED);
		else if (message.contains("!eat"))
			guestGroup.setStatus(GroupStatus.EATING);
		else if (message.contains("!bill"))
			guestGroup.setStatus(GroupStatus.BILL);
	}

}