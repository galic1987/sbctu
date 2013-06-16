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
import tuwien.sbctu.rmi.interfaces.IGuestGUIRMI;

/**
 *
 * @author Adnan
 */
public class RMIGuestGUIImpl extends UnicastRemoteObject implements IGuestGUIRMI{
    
    public RMIGuestGUIImpl() throws RemoteException{}
    
    Queue<GuestGroup> ggQueue = new ConcurrentLinkedQueue<>();
    Queue<GuestDelivery> gdQueue = new ConcurrentLinkedQueue<>();

    @Override
    public GuestGroup getGroupInfo() throws RemoteException{
        return ggQueue.poll();
    }

    @Override
    public GuestDelivery getDeliveryInfo() throws RemoteException{
        return gdQueue.poll();
    }

    @Override
    public void setGroupInfo(GuestGroup gg) throws RemoteException{
        ggQueue.add(gg);
    }

    @Override
    public void setDeliveryInfo(GuestDelivery gd) throws RemoteException{
        gdQueue.add(gd);
    }    

}
