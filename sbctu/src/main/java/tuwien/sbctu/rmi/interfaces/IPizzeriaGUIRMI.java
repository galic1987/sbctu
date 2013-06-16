/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuwien.sbctu.rmi.interfaces;

import tuwien.sbctu.models.GuestDelivery;
import tuwien.sbctu.models.GuestGroup;
import tuwien.sbctu.models.Order;
import tuwien.sbctu.models.Table;

/**
 *
 * @author Adnan
 */
public interface IPizzeriaGUIRMI {
    
    public Table getTableInfo();
    public Order getOrderInfo();
    public void setTableInfo(Table table);
    public void setOrderInfo(Order order);
    
    public GuestGroup getGuestGroupInfo();
    public GuestDelivery getGuestDeliveryInfo();
    public void setGuestGroupInfo(GuestGroup gg);
    public void setGuestGroupDeliveryInfo(GuestDelivery gd);
    
}
