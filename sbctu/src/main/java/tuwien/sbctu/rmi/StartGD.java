package tuwien.sbctu.rmi;

import tuwien.sbctu.models.GuestDelivery;
import tuwien.sbctu.models.GuestDelivery.DeliveryStatus;
import tuwien.sbctu.models.Order;
import tuwien.sbctu.models.Pizza;

public class StartGD {
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Pizza pizza1 = new Pizza("MARGARITA", 2.99, 3);
        Pizza pizza2 = new Pizza("SALAMI", 2.99, 3);
        
        
        
        for(int i = 0; i < 100; i++){
            Order or = new Order(new Long(7000+10*i));
            or.setOrderstatus(Order.OrderStatus.NEW);
            or.addPizzaToOrder(pizza1);
            or.addPizzaToOrder(pizza2);
            
            GuestDelivery gg = new GuestDelivery(new Long(5000+i));
            gg.setGroupSize(2);
            gg.setOrder(or);
            gg.setStatus(DeliveryStatus.WELCOME);
            Thread t = new Thread (new GuestDeliveryRunnable(gg, 10880, "pizzeria2"));
            t.start();
        }
        
        
    }
    
}
