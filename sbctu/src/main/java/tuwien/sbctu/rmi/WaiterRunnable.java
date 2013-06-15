package tuwien.sbctu.rmi;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Logger;

import tuwien.sbctu.models.GuestDelivery;
import tuwien.sbctu.models.GuestGroup;
import tuwien.sbctu.models.Order;
import tuwien.sbctu.models.Table;
import tuwien.sbctu.models.Waiter;
import tuwien.sbctu.models.Waiter.WaiterStatus;
import tuwien.sbctu.rmi.implement.WaiterImpl;
import tuwien.sbctu.rmi.interfaces.IPizzeria;
import tuwien.sbctu.rmi.interfaces.IWaiter;

public class WaiterRunnable implements Runnable{
    private static final Logger log = Logger.getLogger("RunnableWaiter");
    
    private boolean isActive;
    
    private IPizzeria iPizzeria;
    private IWaiter interfaceWaiter;
    
    public WaiterRunnable(Waiter waiter, Integer port, String bindingName){
        isActive = true;
        
        try {
            this.interfaceWaiter = new WaiterImpl(waiter);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        
        enterPizzeria(port, bindingName);
        
        log.info(String.format("Waiter started \n\tID: %s ", waiter.getId()));
    }
    @Override
    public void run() {
        
        while(isActive){
            
            try {
                
                work();
                Thread.sleep(500);
                
            } catch (InterruptedException | RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
    }
    
    /**
     *
     * @param port
     * @param bindingName
     * @return
     */
    private void enterPizzeria(Integer port, String bindingName){
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry(port);
            
            iPizzeria =(IPizzeria) registry.lookup(bindingName);
            iPizzeria.beginWork(interfaceWaiter.getWaiter(), interfaceWaiter);
            
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (AccessException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        
    }
    
    public void work() throws RemoteException{
        
        //looksUp if calls are waiting
        WaiterStatus todo = null;
        WaiterStatus call = interfaceWaiter.lookupCalls();
        
        //if calls are found then answer else do some other work
        if(call != null)
            todo = call;
        else
            todo = interfaceWaiter.lookupTodo();
        
        if(todo == null)
            return;
        
        switch(todo){
            
            case BILLING:
                break;
            case CALL:
                answerCall();
                break;
            case SERVING:
                serveOrder();
                break;
            case SITDOWN:
                sitDownGroup();
                break;
            case WAITING:
                break;
            case WELCOME:
                break;
            case WORKING:
                break;
            case GETORDER:
                getOrder();
                break;
            default:
                break;
        }
    }
    
    //try to find a place for waiting group
    private void sitDownGroup() throws RemoteException{
        interfaceWaiter.getWaiter().setWaiterStatus(WaiterStatus.WORKING);
        GuestGroup gg = null;
        
        //find free table
        
        Table freeTable = iPizzeria.getFreeTable();
        
        //bring guest to table
        
        if(freeTable != null){
            gg = iPizzeria.getEntry();
            
            if(gg != null)
                iPizzeria.sitdownGroup(freeTable, gg);
            else{
                System.out.println("No GuestGroup left to sit down.");
                iPizzeria.returnFreeTable(freeTable);
            }
        }
        
        
        interfaceWaiter.getWaiter().setWaiterStatus(WaiterStatus.WAITING);
    }
    
    private void answerCall() throws RemoteException{
        interfaceWaiter.getWaiter().setWaiterStatus(WaiterStatus.WORKING);
        GuestDelivery gd = null;
        
        gd = iPizzeria.answerCall();
        
        if(gd == null)
            System.out.println("No call left.");
        else
        {
            System.out.println(String.format("Waiter:%s - Calling guest:%s .... \n Order:%s", interfaceWaiter.getWaiter().getId(), gd.getId(), gd.getOrder().getId()));
            
            gd.getOrder().setWaiterOrderTookId(this.interfaceWaiter.getWaiter().getId());
            gd.getOrder().setOrderstatus(Order.OrderStatus.DELIVERYNEW);
            
            iPizzeria.placeDeliveryOrder(gd.getOrder());
            iPizzeria.notifyDelivery(gd.getId(), "!ordered");
        }
        interfaceWaiter.getWaiter().setWaiterStatus(WaiterStatus.WAITING);
    }
    
    private void getOrder() throws RemoteException{
        Order or = iPizzeria.getNewOrder();
        
        if(or!= null){
            System.out.println(String.format("Waiter:%s - Got order:%s from group:%s", interfaceWaiter.getWaiter().getId(),or.getId(), or.getGroupID() ) );
            or.setWaiterOrderTookId(this.interfaceWaiter.getWaiter().getId());
            
            iPizzeria.placeGroupOrder(or);
        }
        else
            System.out.println("No new order waiting.");
        
        interfaceWaiter.getWaiter().setWaiterStatus(WaiterStatus.WAITING);
    }
    
    private void serveOrder() throws RemoteException{
    
    }
    
}
