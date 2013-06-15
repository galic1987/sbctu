/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuwien.sbctu.rmi.implement;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import tuwien.sbctu.models.Cook;
import tuwien.sbctu.rmi.interfaces.ICook;

/**
 *
 * @author Adnan
 */
public class CookImpl extends UnicastRemoteObject implements ICook{
    
    Cook cook ;
    int status = 0;
    
    Queue<String> groups = new ConcurrentLinkedQueue<>();
    Queue<String> deliveries = new ConcurrentLinkedQueue<>();
    
    public CookImpl(Cook cook) throws RemoteException{
        this.cook = cook;
    }
    
    @Override
    public void notification(String message) throws RemoteException {
//        System.out.println(message);
        if(status == 0){
            if(message.contains("!inHouse"))
                status = 1;
            else if(message.contains("!delivery"))
                status = 2;
        }
        else
            if(message.contains("!inHouse"))
                groups.add(message);
            else if(message.contains("!delivery"))
                deliveries.add(message);
    }
    
    @Override
    public void setStatus(int status) throws RemoteException {
        this.status = status;
    }
    
    @Override
    public Cook getCook() throws RemoteException {
        return cook;
    }

    @Override
    public int getStatus() throws RemoteException {
        return status;
    }

    @Override
    public String getGroupOrder() throws RemoteException {
       return groups.poll();
    }

    @Override
    public String getDeliveryOrder() throws RemoteException {
        return deliveries.poll();
    }    
}
