package tuwien.sbctu.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Queue;

import tuwien.sbctu.models.Cook;
import tuwien.sbctu.models.Driver;
import tuwien.sbctu.models.GuestDelivery;
import tuwien.sbctu.models.GuestGroup;
import tuwien.sbctu.models.Order;
import tuwien.sbctu.models.Order.OrderStatus;
import tuwien.sbctu.models.Pizza;
import tuwien.sbctu.models.Table;
import tuwien.sbctu.models.Waiter;

public interface IPizzeria extends Remote{
    
    public void entryPizzeria(GuestGroup guestGroup, IGuestGroup guestGroupInterface) throws RemoteException;
    public void callPizzeria(GuestDelivery guestDelivery, IGuestDelivery guestDeliveryInterface) throws RemoteException;
    public void beginWork(Waiter waiter, IWaiter interfaceWaiter) throws RemoteException;
    public void beginWork(Cook cook, ICook cookInterface)throws RemoteException;
    public void beginWork(Driver driver, IDriver driverInterface)throws RemoteException;
    
    public GuestDelivery answerCall() throws RemoteException;
    public GuestGroup getEntry() throws RemoteException;
    
    public Table getFreeTable() throws RemoteException;
    public void sitdownGroup(Table tab, GuestGroup gg) throws RemoteException;
    public void returnFreeTable(Table table) throws RemoteException;
    
    public void makeOrder(Order order)throws RemoteException;
    public void placeDeliveryOrder(Order order) throws RemoteException;
    public void placeGroupOrder(Order order) throws RemoteException;
    public Order getNewOrder() throws RemoteException;
    public Order getCookedOrder(OrderStatus status) throws RemoteException;
    public void notifyGroup(Long id, String msg) throws RemoteException;
    public void notifyDelivery(Long id, String msg) throws RemoteException;
    
    public void cookGroupPizza() throws RemoteException;
    public void cookDeliveryPizza() throws RemoteException;
    
    public void serveOrder(Order order) throws RemoteException;
    public void deliverOrder(Order order) throws RemoteException;
    
    public Order getDeliveryOrder()throws RemoteException;
    
    public double calculatePizzeriaLoad() throws RemoteException;
    public double getLoad() throws RemoteException;    
    
    public Order takeOneDeliveryOrder() throws RemoteException;
    public boolean putDeliveryOrder(Order o) throws RemoteException;
    
    public String getAddress() throws RemoteException;
    
    public void benchmarkOrders(ArrayList<Order> orderList, Queue<Pizza> pizzas) throws RemoteException;
    public void benchmarkStart() throws RemoteException;
    
}
