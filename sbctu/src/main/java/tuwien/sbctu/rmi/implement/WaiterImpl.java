package tuwien.sbctu.rmi.implement;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import tuwien.sbctu.models.Waiter;
import tuwien.sbctu.models.Waiter.WaiterStatus;
import tuwien.sbctu.rmi.interfaces.IWaiter;

@SuppressWarnings("serial")
public class WaiterImpl extends UnicastRemoteObject implements IWaiter{
    
    Waiter waiter;
    Queue<WaiterStatus> todos = new ConcurrentLinkedQueue<>();
    Queue<WaiterStatus> calls = new ConcurrentLinkedQueue<>();
    
    public WaiterImpl(Waiter waiter) throws RemoteException{
        this.waiter = waiter;
    }
    
    @Override
    public synchronized void notification(String message) throws RemoteException {
        System.out.println(message);
        
        if(message.contains("!waitingGuests"))
            todos.add(WaiterStatus.SITDOWN);
        else if(message.contains("!phoneCall"))
            calls.add(WaiterStatus.CALL);
        else if(message.contains("!orderWaiting"))
            todos.add(WaiterStatus.GETORDER);
        else if(message.contains("!cookedOrder"))
            todos.add(WaiterStatus.SERVING);
        
    }
    
    @Override
    public void setStatus(WaiterStatus waiterStatus) throws RemoteException {
        waiter.setWaiterStatus(waiterStatus);
    }
    
    @Override
    public Waiter getWaiter() throws RemoteException {
        return waiter;
    }
    
    @Override
    public synchronized WaiterStatus lookupTodo() throws RemoteException {
        return todos.poll();
    }
    
    @Override
    public WaiterStatus lookupCalls() throws RemoteException {
        return calls.poll();
    }
}
