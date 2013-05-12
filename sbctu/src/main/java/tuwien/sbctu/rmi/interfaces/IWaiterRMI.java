package tuwien.sbctu.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IWaiterRMI extends Remote{
	
	public void notification(String message) throws RemoteException;
	public void setStatus(int ws) throws RemoteException;
	public int getStatus() throws RemoteException;
	public Long getId() throws RemoteException;
}
