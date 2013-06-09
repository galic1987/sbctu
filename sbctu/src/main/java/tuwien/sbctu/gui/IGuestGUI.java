/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuwien.sbctu.gui;

import tuwien.sbctu.models.GuestDelivery;
import tuwien.sbctu.models.GuestGroup;

/**
 *
 * @author Adnan
 */
public interface IGuestGUI {
    /**
     * 
     * implement a queue from where the groupInfo can be taken 
     */
    public GuestGroup getGroupInfo();
    public GuestDelivery getDeliveryInfo(); 
    
    /**
     * 
     * insert guestinformation to a queue
     */
    public void setGroupInfo(GuestGroup gg);
    public void setDeliveryInfo(GuestDelivery gd);
}
