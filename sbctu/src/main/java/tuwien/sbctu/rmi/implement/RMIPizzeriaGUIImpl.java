/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuwien.sbctu.rmi.implement;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import tuwien.sbctu.models.GuestDelivery;
import tuwien.sbctu.models.GuestGroup;
import tuwien.sbctu.models.Order;
import tuwien.sbctu.models.Table;
import tuwien.sbctu.rmi.interfaces.IPizzeriaGUIRMI;

/**
 *
 * @author Adnan
 */
public class RMIPizzeriaGUIImpl extends UnicastRemoteObject implements IPizzeriaGUIRMI{
    
    Queue<GuestGroup> guestGroupQueue = new ConcurrentLinkedQueue<>();
    Queue<GuestDelivery> guestDeliveryQueue = new ConcurrentLinkedQueue<>();
    Queue<Table> tableQueue = new ConcurrentLinkedQueue<>();
    Queue<Order> orderQueue = new ConcurrentLinkedQueue<>();

    public RMIPizzeriaGUIImpl() throws RemoteException{}
    
    @Override
    public Table getTableInfo() {
        return tableQueue.poll();
    }

    @Override
    public Order getOrderInfo() {
        return orderQueue.poll();
    }

    @Override
    public void setTableInfo(Table table) {
        tableQueue.add(table);
    }

    @Override
    public void setOrderInfo(Order order) {
        if(order!=null)
            orderQueue.add(order);
    }
    
    @Override
    public GuestGroup getGuestGroupInfo() {
        return guestGroupQueue.poll();
    }

    @Override
    public GuestDelivery getGuestDeliveryInfo() {
        return guestDeliveryQueue.poll();
    }
    
    @Override
    public void setGuestGroupInfo(GuestGroup gg) {
        guestGroupQueue.add(gg);
    }

    @Override
    public void setGuestGroupDeliveryInfo(GuestDelivery gd) {
        guestDeliveryQueue.add(gd);
    }

}
