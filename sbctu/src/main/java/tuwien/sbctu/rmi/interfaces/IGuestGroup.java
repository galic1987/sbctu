package tuwien.sbctu.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import tuwien.sbctu.models.GuestGroup;
import tuwien.sbctu.models.GuestGroup.GroupStatus;

public interface IGuestGroup extends Remote{

	public void notification(String message) throws RemoteException;
	public void setStatus(GroupStatus groupStatus) throws RemoteException;
	public GuestGroup getGroup() throws RemoteException;
	
}
