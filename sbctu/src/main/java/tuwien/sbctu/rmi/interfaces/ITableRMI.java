package tuwien.sbctu.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ITableRMI extends Remote{
	public boolean isTableFree() throws RemoteException;
	
}
