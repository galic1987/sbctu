package tuwien.sbctu.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IGuestGroupRMI extends Remote{
	
	public void tableNotify() throws RemoteException;
	public void successfullyOrdered() throws RemoteException;
	public void finishedOrderNotify() throws RemoteException;
	
}
