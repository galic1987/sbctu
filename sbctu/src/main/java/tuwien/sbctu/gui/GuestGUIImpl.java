/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuwien.sbctu.gui;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import tuwien.sbctu.models.GuestDelivery;
import tuwien.sbctu.models.GuestGroup;

/**
 *
 * @author Adnan
 */
public class GuestGUIImpl implements IGuestGUI{
    
    Queue<GuestGroup> ggQueue = new ConcurrentLinkedQueue<>();
    Queue<GuestDelivery> gdQueue = new ConcurrentLinkedQueue<>();

    @Override
    public GuestGroup getGroupInfo() {
        return ggQueue.poll();
    }

    @Override
    public GuestDelivery getDeliveryInfo() {
        return gdQueue.poll();
    }

    @Override
    public void setGroupInfo(GuestGroup gg) {
        ggQueue.add(gg);
    }

    @Override
    public void setDeliveryInfo(GuestDelivery gd) {
        gdQueue.add(gd);
    }
    
}
