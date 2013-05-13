package tuwien.sbctu.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ILoggingRMI extends Remote{
	
	public void guestInfo(String message) throws RemoteException;
	public void waiterInfo(String message) throws RemoteException;
	public void cookInfo(String message) throws RemoteException;
	public void tableInfo(String message) throws RemoteException;
	public void pizzaInfo(String message) throws RemoteException;
	public void orderInfo(String message) throws RemoteException;
	public void billInfo(String message) throws RemoteException;
	
	public String getGuest() throws RemoteException;
	public String getWaiter() throws RemoteException;
	public String getCook() throws RemoteException;
	public String getTable() throws RemoteException;
	public String getPizza() throws RemoteException;
	public String getOrder() throws RemoteException;
	public String getBill() throws RemoteException;
	
	public void testMe() throws RemoteException;
	
}
