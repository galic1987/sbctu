package tuwien.sbctu.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import tuwien.sbctu.models.GuestGroup.GroupStatus;

public interface IGuestGroupRMI extends Remote{
		
	public void notification(String message) throws RemoteException;
	public Long getId() throws RemoteException;
	public void setStatus(GroupStatus gs) throws RemoteException;
	public GroupStatus getStatus() throws RemoteException;
	
}
