package tuwien.sbctu.rmi.implement;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import tuwien.sbctu.models.GuestGroup;
import tuwien.sbctu.models.GuestGroup.GroupStatus;
import tuwien.sbctu.rmi.interfaces.IGuestGroupRMI;

public class GuestGroupImpl extends UnicastRemoteObject implements IGuestGroupRMI{
	private GuestGroup guestGroup;
	
	public GuestGroupImpl(Long id) throws RemoteException {
		
		setGuestGroup(new GuestGroup(id));
		
	}
	
	@Override
	public void tableNotify() throws RemoteException {
		guestGroup.setStatus(GroupStatus.SITTING);

		System.out.println("We have found a table for you. GroupStatus - "+guestGroup.getStatus());
	}

	@Override
	public void finishedOrderNotify() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	public GuestGroup getGuestGroup() {
		return guestGroup;
	}

	public void setGuestGroup(GuestGroup guestGroup) {
		this.guestGroup = guestGroup;
	}

}
