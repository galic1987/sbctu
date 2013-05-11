package tuwien.sbctu.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Queue;

import tuwien.sbctu.models.GuestGroup;

public interface IEntryRMI extends Remote{
	
	public GuestGroup getGuestGroup() throws RemoteException;
	public void addSingleGuestGroup(GuestGroup group) throws RemoteException;
	public void waiterSubscribeCallback(IWaiterRMI iw) throws RemoteException;
	public void waiterUnsubscribeCallback() throws RemoteException;
	public void groupSubscribeCallback(IGuestGroupRMI igg) throws RemoteException;
	public void groupUnsubscribeCallback() throws RemoteException;	
	
}
