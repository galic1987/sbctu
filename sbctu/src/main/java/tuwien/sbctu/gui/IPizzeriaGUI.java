/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuwien.sbctu.gui;

import tuwien.sbctu.models.GuestDelivery;
import tuwien.sbctu.models.GuestGroup;
import tuwien.sbctu.models.Order;
import tuwien.sbctu.models.Table;

/**
 *
 * @author Adnan
 */
public interface IPizzeriaGUI {
    
    public Table getTableInfo();
    public Order getOrderInfo();
    public void setTableInfo(Table table);
    public void setOrderInfo(Order order);
    
    public GuestGroup getGuestGroupInfo();
    public GuestDelivery getGuestDeliveryInfo();
    public void setGuestGroupInfo(GuestGroup gg);
    public void setGuestGroupDeliveryInfo(GuestDelivery gd);
    
    public void setArchiveInfo(Order order);
    public Order getArchiveInfo();
    
}
