package tuwien.sbctu.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import tuwien.sbctu.models.Cook;
import tuwien.sbctu.models.GuestGroup;
import tuwien.sbctu.models.Order;
import tuwien.sbctu.models.Pizza;
import tuwien.sbctu.models.Table;
import tuwien.sbctu.models.Table.TableStatus;
import tuwien.sbctu.models.Waiter;

public interface IPizzeriaRMI extends Remote{
	
	
	public void guestGroupEnters(GuestGroup group, IGuestGroupRMI subscriber) throws RemoteException;
	public GuestGroup getFromEntry() throws RemoteException;
	public GuestGroup getGuestGroup(Long id) throws RemoteException;
	public void makeOrder(Order order) throws RemoteException;
	public void guestNotifier(String message, Long id)throws RemoteException;
	public void requestBill(Long groupId)throws RemoteException;
	public void payBillNLeave(Long groupId) throws RemoteException;
	
	public void sitdownGuestGroup(GuestGroup group, Table tab) throws RemoteException;
	
	public void waiterEnteres(Waiter w, IWaiterRMI iw) throws RemoteException;
	public void waiterLeaves() throws RemoteException;
	public void freeTable(Table table) throws RemoteException;
	public void returnToEntry(GuestGroup group) throws RemoteException;
	public void putTableBack(Table tab) throws RemoteException;
	public Table getTableWithStatus(TableStatus tb) throws RemoteException;
	public Table getUsedTableWithStatus(TableStatus tb) throws RemoteException;
	public Table getTableWithId(Long tb) throws RemoteException;
	public Table isBillRequested() throws RemoteException;
	public Order isOrderReady() throws RemoteException;
	public Long getTableForGroupID(Long id) throws RemoteException;
	public void putNewOrderToBar(Order order) throws RemoteException;
	public Order isOrderWaiting() throws RemoteException;
	public void putTableBill(Table tab) throws RemoteException;
	public boolean todoEntry() throws RemoteException;
	public boolean todoBill() throws RemoteException;
	public boolean todoBar() throws RemoteException;
	
	public void cookEnters(Cook cook, ICookRMI cookrmi) throws RemoteException;
	public Pizza cookPizza() throws RemoteException;		
	public void pizzaCooked(Pizza pizza) throws RemoteException;
	public void putFinishedOrderToBar(Order order)throws RemoteException;
	
	public void subscribeGUI(ILoggingRMI logrmi) throws RemoteException;
}
