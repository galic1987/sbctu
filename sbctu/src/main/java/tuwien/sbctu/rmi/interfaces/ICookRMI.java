package tuwien.sbctu.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICookRMI extends Remote{
	
	public void ordersWaiting() throws RemoteException;
	
}
