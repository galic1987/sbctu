package tuwien.sbctu.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import tuwien.sbctu.models.Cook.CookStatus;

public interface ICookRMI extends Remote{
	
	public void notification(String message) throws RemoteException;
	public void setStatus(CookStatus ws) throws RemoteException;
	public CookStatus getStatus() throws RemoteException;
	public Long getId() throws RemoteException;
	
}
