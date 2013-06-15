package tuwien.sbctu.rmi;

import tuwien.sbctu.models.GuestGroup;
import tuwien.sbctu.models.Order;
import tuwien.sbctu.models.Pizza;
import tuwien.sbctu.models.GuestGroup.GroupStatus;

public class StartGG {
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Pizza pizza1 = new Pizza("MARGARITA", 2.99, 30);
        Pizza pizza2 = new Pizza("SALAMI", 2.99, 30);
        
        
        
        for(int i = 0; i < 7; i++){
            Order or = new Order(new Long(8000+10*i));
            or.addPizzaToOrder(pizza1);
            or.addPizzaToOrder(pizza2);
            or.setOrderstatus(Order.OrderStatus.NEW);
            
            GuestGroup gg = new GuestGroup(new Long(1000+i));
            or.setGroupID(gg.getId());
            gg.setGroupSize(2);
            gg.setOrder(or);
            gg.setStatus(GroupStatus.ENTERED);
            Thread t = new Thread (new GuestGroupRunnable(gg, 10879, "pizzeria"));
            t.start();
        }
        
        
    }
    
}
