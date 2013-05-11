package tuwien.sbctu.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import tuwien.sbctu.models.GuestGroup.GroupStatus;

public interface IGuestGroupRMI extends Remote{
	
	public Long getGroupId() throws RemoteException;
	public GroupStatus getGroupStatus() throws RemoteException;
	public void setGroupStatus(GroupStatus stat) throws RemoteException;
	public void tableNotify() throws RemoteException;
	public void successfullyOrdered() throws RemoteException;
	public void finishedOrderNotify() throws RemoteException;
	
}
