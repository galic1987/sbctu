/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuwien.sbctu.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import tuwien.sbctu.models.GuestDelivery;
import tuwien.sbctu.models.GuestGroup;
import tuwien.sbctu.models.Order;
import tuwien.sbctu.models.Table;

/**
 *
 * @author Adnan
 */
public interface IPizzeriaGUIRMI extends Remote{
    
    public Table getTableInfo() throws RemoteException;
    public Order getOrderInfo() throws RemoteException;
    public void setTableInfo(Table table) throws RemoteException;
    public void setOrderInfo(Order order) throws RemoteException;
    
    public GuestGroup getGuestGroupInfo() throws RemoteException;
    public GuestDelivery getGuestDeliveryInfo() throws RemoteException;
    public void setGuestGroupInfo(GuestGroup gg) throws RemoteException;
    public void setGuestGroupDeliveryInfo(GuestDelivery gd) throws RemoteException;
    
}
