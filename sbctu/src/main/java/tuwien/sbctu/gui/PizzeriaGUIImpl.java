/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuwien.sbctu.gui;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import tuwien.sbctu.models.GuestDelivery;
import tuwien.sbctu.models.GuestGroup;
import tuwien.sbctu.models.Order;
import tuwien.sbctu.models.Table;

/**
 *
 * @author Adnan
 */
public class PizzeriaGUIImpl implements IPizzeriaGUI{
    
    Queue<GuestGroup> guestGroupQueue = new ConcurrentLinkedQueue<>();
    Queue<GuestDelivery> guestDeliveryQueue = new ConcurrentLinkedQueue<>();
    Queue<Table> tableQueue = new ConcurrentLinkedQueue<>();
    Queue<Order> orderQueue = new ConcurrentLinkedQueue<>();

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
    
    Queue<Order> archiveQueue = new ConcurrentLinkedQueue<>();

    @Override
    public void setArchiveInfo(Order order) {
        archiveQueue.add(order);
    }

    @Override
    public Order getArchiveInfo() {
        return archiveQueue.poll();
    }

}
