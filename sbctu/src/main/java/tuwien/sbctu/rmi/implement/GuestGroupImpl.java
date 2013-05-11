package tuwien.sbctu.rmi.implement;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import tuwien.sbctu.models.GuestGroup;
import tuwien.sbctu.models.GuestGroup.GroupStatus;
import tuwien.sbctu.rmi.interfaces.IGuestGroupRMI;

public class GuestGroupImpl extends UnicastRemoteObject implements IGuestGroupRMI{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GuestGroup guestGroup;
	
	public GuestGroupImpl(Long id) throws RemoteException {
		
		setGuestGroup(new GuestGroup(id));
		System.out.println("NEW GROUP CREATED "+ id);
	}
	
	@Override
	public void tableNotify() throws RemoteException {
		this.guestGroup.setStatus(GroupStatus.SITTING);

		System.out.println("We have found a table for you. GroupStatus - " + guestGroup.getStatus());
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

	@Override
	public void successfullyOrdered() throws RemoteException {
		// TODO Auto-generated method stub
		guestGroup.setStatus(GroupStatus.ORDERED);
	}

	@Override
	public Long getGroupId() throws RemoteException {
		// TODO Auto-generated method stub
		return guestGroup.getId();
	}

	@Override
	public GroupStatus getGroupStatus() throws RemoteException {
		// TODO Auto-generated method stub
		return guestGroup.getStatus();
	}
	public void setGroupStatus(GroupStatus stat) throws RemoteException {
		// TODO Auto-generated method stub
		guestGroup.setStatus(stat);
	}

}
