/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuwien.sbctu.gui;

import java.util.ArrayList;
import tuwien.sbctu.models.Order;
import tuwien.sbctu.models.Table;

/**
 *
 * @author Adnan
 */
public interface IPizzeriaGUI {
    
    public ArrayList<Table> getTableInfo();
    public ArrayList<Order> getOrderInfo(); 
    
    public void setTableInfo(ArrayList<Table> gg);
    public void setOrderInfo(ArrayList<Order> gd);
}
