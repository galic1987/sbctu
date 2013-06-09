package tuwien.sbctu.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import tuwien.sbctu.models.GuestDelivery;
import tuwien.sbctu.models.GuestGroup;
import tuwien.sbctu.models.Order;
import tuwien.sbctu.models.Table;

public interface ILoggingRMI extends Remote{
	
        public List<GuestDelivery> getGd() throws RemoteException;
    public void setGd(List<GuestDelivery> gd) throws RemoteException;
    public List<GuestGroup> getGg() throws RemoteException;
    public void setGg(List<GuestGroup> gg) throws RemoteException;
    
    public List<Table> getTables() throws RemoteException;
    public void setTables(List<Table> gd) throws RemoteException;
    public List<Order> getOrders() throws RemoteException;
    public void setOrders(List<Order> gg) throws RemoteException;
    
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
