/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuwien.sbctu.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import tuwien.sbctu.models.GuestDelivery;
import tuwien.sbctu.models.GuestGroup;

/**
 *
 * @author Adnan
 */
public interface IGuestGUIRMI extends Remote{
    /**
     *
     * implement a queue from where the groupInfo can be taken
     */
    public GuestGroup getGroupInfo()throws RemoteException;
    public GuestDelivery getDeliveryInfo()throws RemoteException;
    
    /**
     *
     * insert guestinformation to a queue
     */
    public void setGroupInfo(GuestGroup gg)throws RemoteException;
    public void setDeliveryInfo(GuestDelivery gd)throws RemoteException;
    
}
