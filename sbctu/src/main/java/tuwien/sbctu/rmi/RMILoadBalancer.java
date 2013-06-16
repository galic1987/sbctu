/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuwien.sbctu.rmi;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import tuwien.sbctu.models.Order;
import tuwien.sbctu.rmi.interfaces.IPizzeria;

/**
 *
 * @author Adnan
 */
public class RMILoadBalancer {
    private static IPizzeria[] p = new IPizzeria[2];
    
    /**
     *
     * @param args host1;port1;bindingName1 host2;port2;bindingName2
     */
    public static void main(String[] args){
        
        //        for(int i = 1; i < 3; i ++){
        //            enterPizzeria();
        //        }
        //        localhost 10880 pizzeria2 7
        
        p[0] = enterPizzeria(10879, "pizzeria");
        p[1] = enterPizzeria(10880, "pizzeria2");
        
        try {
            algo();
        } catch (RemoteException ex) {
            Logger.getLogger(RMILoadBalancer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void algo() throws RemoteException{
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(RMILoadBalancer.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean everythingOK = true;
        while(true){
            
            IPizzeria maxLoad = null;
            IPizzeria minLoad = null;
            
            for (int i = 0; i < p.length; i++) {
                IPizzeria pizz = p[i];
                
                pizz.calculatePizzeriaLoad();
                
                // init
                if(minLoad == null) minLoad = pizz;
                if(maxLoad == null) maxLoad = pizz;
                
                // differences
                if(minLoad.calculatePizzeriaLoad() > pizz.getLoad())
                    minLoad = pizz;
                
                if(maxLoad.calculatePizzeriaLoad() < pizz.getLoad())
                    maxLoad = pizz;
                
                
                
            }
            
            System.out.println("MAX Load:"+maxLoad.getLoad()+" - MIN Load:"+minLoad.getLoad());
            
            int difference = (int) ((int) maxLoad.getLoad()-minLoad.getLoad());
            // check if there is something to do , number of newDeliveries orders
            if(difference >= 2){
                // transfer order and immediately do recalculation
                int howManyOrders = difference/2;
                
                System.out.println("Balancing "+howManyOrders+ " orders from pizzeria " +maxLoad.getAddress() +" to pizzeria " + minLoad.getAddress() );
                
                for (int i = 0; i < howManyOrders; i++) {
                    Order o = maxLoad.takeOneDeliveryOrder();
                    
                    if(o != null){
                        // try to put order on min load
                        if(minLoad.putDeliveryOrder(o)){
                            // it is ok
                        }else{
                            // custom rollback
                            maxLoad.putDeliveryOrder(o);
                        }
                    }
                }
                
                everythingOK = false;
            }else{
                // do the sleeping
                everythingOK = true;
            }
            
            
            
            try {
                if(everythingOK) Thread.sleep(3000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    /**
     *
     * @param port
     * @param bindingName
     * @return
     */
    private static IPizzeria enterPizzeria(Integer port, String bindingName){
        IPizzeria iPizzeria = null;
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry(port);
            
            iPizzeria =(IPizzeria) registry.lookup(bindingName);
            
            
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (AccessException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return iPizzeria;
    }
}
