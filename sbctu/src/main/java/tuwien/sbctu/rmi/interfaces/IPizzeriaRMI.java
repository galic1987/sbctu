package tuwien.sbctu.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPizzeriaRMI extends Remote{
	
	public IGuestGroupRMI getGuestGroup() throws RemoteException;
	public void addSingleGuestGroup(IGuestGroupRMI group) throws RemoteException;
	public void waiterSubscribeCallback(IWaiterRMI iw) throws RemoteException;
	public void waiterUnsubscribeCallback() throws RemoteException;
	
}
