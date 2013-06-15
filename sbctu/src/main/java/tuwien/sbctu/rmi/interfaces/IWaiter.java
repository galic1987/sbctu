package tuwien.sbctu.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import tuwien.sbctu.models.Waiter;
import tuwien.sbctu.models.Waiter.WaiterStatus;

public interface IWaiter extends Remote{
    
    public void notification(String message) throws RemoteException;
    public void setStatus(WaiterStatus waiterStatus) throws RemoteException;
    public Waiter getWaiter() throws RemoteException;
    
    public WaiterStatus lookupTodo() throws RemoteException;
    public WaiterStatus lookupCalls() throws RemoteException;
    
}
