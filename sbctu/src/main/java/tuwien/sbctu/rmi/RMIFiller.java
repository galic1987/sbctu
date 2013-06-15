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
import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
import tuwien.sbctu.models.Order;
import tuwien.sbctu.models.Pizza;
import tuwien.sbctu.rmi.interfaces.IPizzeria;

/**
 *
 * @author Adnan
 */
public class RMIFiller {
    
    public static void main(String[] args) throws RemoteException, InterruptedException{
        ArrayList<Order> orders1 = new ArrayList<Order>();
        ArrayList<Order> orders2 = new ArrayList<Order>();
        ArrayList<Order> orders3 = new ArrayList<Order>();
        
        Queue<Pizza> deliveryPizzas1 = new ConcurrentLinkedQueue<>();
        Queue<Pizza> deliveryPizzas2 = new ConcurrentLinkedQueue<>();
        Queue<Pizza> deliveryPizzas3 = new ConcurrentLinkedQueue<>();
        
        Pizza pizza1 = new Pizza("MARGARITA", 2.99, 3);
        pizza1.setStatus(Pizza.PizzaStatus.ORDERED);
        Pizza pizza2 = new Pizza("SALAMI", 2.99, 7);
        pizza2.setStatus(Pizza.PizzaStatus.ORDERED);
        Pizza pizza3 = new Pizza("CARDINALE", 2.99, 5);
        pizza3.setStatus(Pizza.PizzaStatus.ORDERED);
        
        
        
        for(int i = 0; i < 10; i++){
            Order or1 = new Order(new Long(71000+i));
            or1.setOrderstatus(Order.OrderStatus.DELIVERYNEW);
            
            or1.addPizzaToOrder(pizza1);
            deliveryPizzas1.add(pizza1);
            
            or1.addPizzaToOrder(pizza1);
            deliveryPizzas1.add(pizza1);
            
            or1.addPizzaToOrder(pizza2);
            deliveryPizzas1.add(pizza2);
            
            or1.addPizzaToOrder(pizza3);
            deliveryPizzas1.add(pizza3);
            orders1.add(or1);
        }
        
        for(int i = 0; i < 101; i++){
            Order or2 = new Order(new Long(72000+i));
            or2.setOrderstatus(Order.OrderStatus.DELIVERYNEW);
            or2.addPizzaToOrder(pizza1);
            deliveryPizzas2.add(pizza1);
            
            or2.addPizzaToOrder(pizza2);
            deliveryPizzas2.add(pizza2);
            
            or2.addPizzaToOrder(pizza3);
            deliveryPizzas2.add(pizza3);
            
            orders2.add(or2);
        }
        
        for(int i = 0; i < 101; i++){
            Order or3 = new Order(new Long(73000+i));
            or3.setOrderstatus(Order.OrderStatus.DELIVERYNEW);
            or3.addPizzaToOrder(pizza1);
            deliveryPizzas3.add(pizza1);
            or3.addPizzaToOrder(pizza2);
            deliveryPizzas3.add(pizza2);
            
            orders3.add(or3);
        }
        IPizzeria ip1 = enterPizzeria(10879, "pizzeria");
        IPizzeria ip2 = enterPizzeria(10880, "pizzeria2");
        
        ip1.benchmarkOrders(orders1, deliveryPizzas1);
        ip2.benchmarkOrders(orders2, deliveryPizzas2);
        
        Thread.sleep(2000);
        Scanner sc = new Scanner(System.in);
        System.out.println("PressEnter to start working...");
        int val = sc.nextInt();
        if(val == 1)
            System.out.println("Working started.");
        
        ip1.benchmarkStart();
        ip2.benchmarkStart();
        
        Thread.sleep(2000);
        
        ip2.benchmarkOrders(orders3, deliveryPizzas3);

    }
    
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
