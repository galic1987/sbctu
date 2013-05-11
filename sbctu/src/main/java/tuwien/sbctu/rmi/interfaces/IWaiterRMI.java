package tuwien.sbctu.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IWaiterRMI extends Remote{
	
	public void entryNotify() throws RemoteException;
	public void orderNotify() throws RemoteException;
	public void billNotifiy() throws RemoteException;
	
	public void finishedOrderNotify() throws RemoteException;
}
