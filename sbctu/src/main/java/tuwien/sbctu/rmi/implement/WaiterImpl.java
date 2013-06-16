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
    
    Queue<WaiterStatus> calls = new ConcurrentLinkedQueue<>();
    Queue<WaiterStatus> entry = new ConcurrentLinkedQueue<>();
    Queue<WaiterStatus> order = new ConcurrentLinkedQueue<>();
    Queue<WaiterStatus> serve = new ConcurrentLinkedQueue<>();
    Queue<WaiterStatus> bill = new ConcurrentLinkedQueue<>();
    
    public WaiterImpl(Waiter waiter) throws RemoteException{
        this.waiter = waiter;
    }
    
    @Override
    public synchronized void notification(String message) throws RemoteException {
        System.out.println(message);
        
        if(message.contains("!bill"))
            bill.add(WaiterStatus.BILLING);
        else if(message.contains("!waitingGuests"))
            entry.add(WaiterStatus.SITDOWN);
        else if(message.contains("!phoneCall"))
            calls.add(WaiterStatus.CALL);
        else if(message.contains("!orderWaiting"))
            order.add(WaiterStatus.GETORDER);
        else if(message.contains("!cookedOrder"))
            serve.add(WaiterStatus.SERVING);
        
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
        WaiterStatus ws = null;
        
        if(!bill.isEmpty())
            ws = bill.poll();        
        else if(!serve.isEmpty())
            ws = serve.poll();
        else if(!order.isEmpty())
            ws = order.poll();
        else if(!entry.isEmpty())
            ws = entry.poll();
        
        return ws;
    }
    
    @Override
    public WaiterStatus lookupCalls() throws RemoteException {
        return calls.poll();
    }
}
