package tuwien.sbctu.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import tuwien.sbctu.models.Waiter.WaiterStatus;

public interface IWaiterRMI extends Remote{
	
	public WaiterStatus waiterStatus() throws RemoteException;
	public void beginWork() throws RemoteException;
	public void entryNotify() throws RemoteException;
	public void orderNotify() throws RemoteException;
	public void billNotifiy() throws RemoteException;
	
	public void finishedOrderNotify() throws RemoteException;
}
