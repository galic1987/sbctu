package tuwien.sbctu.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import tuwien.sbctu.models.Order;
import tuwien.sbctu.models.Table;

public interface IPizzeriaRMI extends Remote{
	
	
	public void guestGroupEnters(IGuestGroupRMI group) throws RemoteException;
	public IGuestGroupRMI getGuestGroup() throws RemoteException;
	public void makeOrder(IGuestGroupRMI group) throws RemoteException;
//	public void
	
	public void waiterEnteres(IWaiterRMI iw) throws RemoteException;
	public void waiterLeaves() throws RemoteException;
	public Table isTableFree() throws RemoteException;
	public Order isBillRequested() throws RemoteException;
	
	
}
