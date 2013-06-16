package tuwien.sbctu.rmi.implement;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import tuwien.sbctu.models.Cook;
import tuwien.sbctu.models.Driver;
import tuwien.sbctu.models.GuestDelivery;
import tuwien.sbctu.models.GuestGroup;
import tuwien.sbctu.models.Order;
import tuwien.sbctu.models.Order.OrderStatus;
import tuwien.sbctu.models.Pizza;
import tuwien.sbctu.models.Pizza.PizzaStatus;
import tuwien.sbctu.models.Table;
import tuwien.sbctu.models.Table.TableStatus;
import tuwien.sbctu.models.Waiter;
import tuwien.sbctu.rmi.interfaces.ICook;
import tuwien.sbctu.rmi.interfaces.IDriver;
import tuwien.sbctu.rmi.interfaces.IGuestDelivery;
import tuwien.sbctu.rmi.interfaces.IGuestGUIRMI;
import tuwien.sbctu.rmi.interfaces.IGuestGroup;
import tuwien.sbctu.rmi.interfaces.IPizzeria;
import tuwien.sbctu.rmi.interfaces.IPizzeriaGUIRMI;
import tuwien.sbctu.rmi.interfaces.IWaiter;

@SuppressWarnings("serial")
public class PizzeriaImpl extends UnicastRemoteObject implements IPizzeria{
    String pizzeriaInfo;
    IPizzeriaGUIRMI guiPizzeriaInterface;
    IGuestGUIRMI guiGuestInterface;
    Double load;

    Queue<GuestGroup> waitingEntry = new ConcurrentLinkedQueue<>();
    Queue<GuestDelivery> waitingCall = new ConcurrentLinkedQueue<>();
    List<GuestGroup> guests = Collections.synchronizedList(new ArrayList<GuestGroup>());
    
    List<Order> groupOrders = Collections.synchronizedList(new ArrayList<Order>());
    List<Order> deliveryOrders = Collections.synchronizedList(new ArrayList<Order>());
    List<Table> tables = Collections.synchronizedList(new ArrayList<Table>());
    
    Queue<Pizza> groupPizzas = new ConcurrentLinkedQueue<>();
    Queue<Pizza> deliveryPizzas = new ConcurrentLinkedQueue<>();
    
    List<IGuestDelivery> guestDeliveries = Collections.synchronizedList(new ArrayList<IGuestDelivery>());
    List<IGuestGroup> guestGroups = Collections.synchronizedList(new ArrayList<IGuestGroup>());
    List<IWaiter> waiters = Collections.synchronizedList(new ArrayList<IWaiter>());
    List<ICook> cooks = Collections.synchronizedList(new ArrayList<ICook>());
    List<IDriver> drivers = Collections.synchronizedList(new ArrayList<IDriver>());
    
    Queue<Order> toDeliver = new ConcurrentLinkedQueue<>();
    
    Queue<String> remainingNotifiers = new ConcurrentLinkedQueue<>();
    
    /**
     *
     * @param numberOfTables
     * @throws RemoteException
     */
    public PizzeriaImpl(Integer numberOfTables, String pizzeriaInfo) throws RemoteException {
        super();
        prepareTables(numberOfTables);
        load = 0.0;
        this.pizzeriaInfo = pizzeriaInfo;
    }
    
    private void prepareTables(Integer size){
        
        for (int i = 0; i < size; i++){
            Table table = new Table(new Long(10+i));
            tables.add(table);
//            guiPizzeriaInterface.setTableInfo(table);
        }
    }
    
    @Override
    public synchronized void entryPizzeria(GuestGroup guestGroup,
    IGuestGroup guestGroupInterface) throws RemoteException {
        System.out.println("GuestGroup entered: "+guestGroup.getId());
        
        waitingEntry.add(guestGroup);
        guestGroups.add(guestGroupInterface);
        
        guestGroupInterface.notification("!welcome");
        
        if(waiters != null)
            notifyWaiter("!waitingGuests");
        else
            remainingNotifiers.add("!waitingGuests");
    }
    
    @Override
    public synchronized void callPizzeria(GuestDelivery guestDelivery,
    IGuestDelivery guestDeliveryInterface) throws RemoteException {
        waitingCall.add(guestDelivery);
        guestDeliveries.add(guestDeliveryInterface);
        
        guestDeliveryInterface.notification("!hello");
        notifyWaiter("!phoneCall");
        
        guiGuestInterface.setDeliveryInfo(guestDelivery);
//        guiPizzeriaInterface
    }
    
    @Override
    public synchronized void beginWork(Waiter waiter, IWaiter interfaceWaiter)
            throws RemoteException {
        waiters.add(interfaceWaiter);
        
        System.out.println("Waiter entered: "+waiter.getId());
        
        interfaceWaiter.notification("!hello");
    }
    
    @Override
    public synchronized void beginWork(Cook cook, ICook cookInterface)
            throws RemoteException {
        cooks.add(cookInterface);
        System.out.println("Cook entered: "+cook.getId());
        
        cookInterface.notification("!hello");
    }
    @Override
    public synchronized void beginWork(Driver driver, IDriver interfaceDriver)
            throws RemoteException {
        drivers.add(interfaceDriver);
        System.out.println("Driver entered: "+driver.getId());
        
        interfaceDriver.notification("!hello");
    }
    
    //interface.notification should return boolean value to determine if message could be sent
    public synchronized void notifyWaiter(String msg){
        try {
            for(IWaiter iw : waiters){
                iw.notification(msg);
            }
            
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    
    public synchronized void notifyCook(String msg){
        
        try {
            for(ICook ic : cooks){
                ic.notification(msg);
            }
            
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    
    public synchronized void notifyDriver(String msg){
        try {
            for(IDriver id : drivers){
                id.notification(msg);
            }
            
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public synchronized void notifyGroup(Long id, String msg) throws RemoteException{
        try {
            for(IGuestGroup iguest : guestGroups){
                if(iguest.getGroup().getId().equals(id))
                    iguest.notification(msg);
            }
            
        } catch (RemoteException e) {
            System.out.println("Group is not available anymore.");
        }
    }
    
    @Override
    public synchronized void notifyDelivery(Long id, String msg) throws RemoteException{
        try {
            for(IGuestDelivery iguest : guestDeliveries){
                if(iguest.getGroup().getId().equals(id))
                    iguest.notification(msg);
            }
            
        } catch (RemoteException e) {
            System.out.println("Caller is not available anymore.");
        }
    }
    
    @Override
    public GuestDelivery answerCall() throws RemoteException {
        return waitingCall.poll();
    }
    
    @Override
    public GuestGroup getEntry() throws RemoteException {
        return waitingEntry.poll();
    }
    
    @Override
    public synchronized Table getFreeTable() throws RemoteException {
        Table result = null;
        Iterator<Table> it = tables.iterator();
        
        while(it.hasNext()){
            Table tab = it.next();
            if(tab.getTabStat().equals(TableStatus.FREE)){
                result = tab;
                it.remove();
                break;
            }
        }
        
        return result;
    }
    
    @Override
    public synchronized void sitdownGroup(Table table, GuestGroup group) throws RemoteException {
        
        table.setGroup(group);
        table.setGroupID(group.getId());
        table.setTabStat(TableStatus.USED);
        table.setOrder(group.getOrder());
        
        tables.add(table);
        notifyGroup(group.getId(), String.format("!table Sitting on table:%s.", table.getId()));
        
        System.out.println(String.format("TABLE:%s is %s",table.getId(), table.getTabStat().toString()));
        
        //TODO GUI NOTIFY TABLE ID CHANGED STATUS
        //TODO GUI NOTIFY GROUP ID SITTING
        if(guiGuestInterface != null)
            guiGuestInterface.setGroupInfo(group);
        if(guiPizzeriaInterface != null)
            guiPizzeriaInterface.setTableInfo(table);
    }
    
    @Override
    public synchronized void returnFreeTable(Table table) throws RemoteException {
        tables.add(table);
    }
    
    @Override
    public synchronized void placeDeliveryOrder(Order order) throws RemoteException{
        deliveryOrders.add(order);
        for(Pizza p : order.getPizzaList()){
            deliveryPizzas.add(p);
        }
        System.out.println("Delivery order:"+order.getId()+" waiting to be cooked.");
        //TODO GUI information
        if(guiPizzeriaInterface != null)
            guiPizzeriaInterface.setOrderInfo(order);
        notifyCook("!delivery");
    }
    
    @Override
    public synchronized void placeGroupOrder(Order order) throws RemoteException {
        
        //        double bill = 0.0;
        for(Pizza p : order.getPizzaList()){
            //            bill += p.getPrice();
            groupPizzas.add(p);
        }
        
        for(Order o : groupOrders){
            if(o.getId().equals(order.getId())){
                o.setOrderstatus(Order.OrderStatus.ORDERED);
                o.setWaiterOrderTookId(order.getWaiterOrderTookId());
            }
        }
        
        notifyGroup(order.getGroupID(), "!order");
        System.out.println("Group order:"+order.getId()+" waiting to be cooked.");
        notifyCook("!inHouse");
        
        //TODO GUI
//        if(guiGuestInterface != null)
//            guiGuestInterface.setGroupInfo(group);
        if(guiPizzeriaInterface != null)
            guiPizzeriaInterface.setOrderInfo(order);
    }
    
    private synchronized GuestGroup findGroupInterface(Long groupID){
        GuestGroup result = null;
        try {
            for(IGuestGroup iguest : guestGroups){
                if(iguest.getGroup().getId().equals(groupID)){
                    result = iguest.getGroup();
                    break;
                }
                
            }
            
        } catch (RemoteException e) {
            System.out.println("Group is not available anymore.");
        }
        return result;
    }
    
    @Override
    public synchronized void makeOrder(Order order) throws RemoteException {
        
        System.out.println("Group "+ order.getGroupID()+ " wants to order.");
        groupOrders.add(order);
        
        notifyWaiter("!orderWaiting");
        
        //TODO GUI INFO
    }
    
    @Override
    public synchronized Order getNewOrder() throws RemoteException {
        Iterator it = groupOrders.iterator();
        Order newOrder = null;
        
        while ( it.hasNext() && newOrder == null){
            Order or = (Order) it.next();
            
            if(or.getOrderstatus().equals(Order.OrderStatus.NEW)){
                newOrder = or;
                or.setOrderstatus(Order.OrderStatus.ORDERED);
            }
            
        }
        
        //TODO inform GUI for new order
        
        return newOrder;
    }
    
    @Override
    public synchronized void cookGroupPizza() throws RemoteException {
        Order ord = null;
        for(Order o : groupOrders){
            
            if(o.getOrderstatus().equals(OrderStatus.ORDERED)){
                o.setOrderstatus(OrderStatus.COOKED);
                System.out.println("Order:"+o.getId()+"is cooked by cook.");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PizzeriaImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                //                o.setOrderstatus(OrderStatus.COOKED);
                ord = o;
                break;
            }
        }
        
        if(ord!=null){
            notifyWaiter("!cookedOrder Order id:"+ord.getId()+" is ready for serving.");
        
        if(guiPizzeriaInterface != null)
            guiPizzeriaInterface.setOrderInfo(ord);
        }
    }
    
    @Override
    public synchronized void cookDeliveryPizza() throws RemoteException {
        
        Pizza p = deliveryPizzas.poll();
        
        
        try {
            System.out.println("Cooking "+p.getName()+" takes "+p.getPrepareTime());
            Thread.sleep(new Long(p.getPrepareTime()*1000));
        } catch (InterruptedException ex) {
            Logger.getLogger(PizzeriaImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Order ord = null;
        
        for(Order o : deliveryOrders){
            boolean pizzaFound = false;
            //            System.out.println("Begin cooking deliveryOrder");
            int finished = 0;
            int toMake = 0;
            
            if(o.getOrderstatus().equals(OrderStatus.DELIVERYNEW) || o.getOrderstatus().equals(OrderStatus.DELIVERYTRANSFERRED)){
                toMake = o.getPizzaList().size();
                //                System.out.println("TO MAKE:"+toMake);
                for(Pizza pizza : o.getPizzaList()){
                    if(pizza.getStatus().equals(PizzaStatus.ORDERED)){
                        if( pizza.getName().equals(p.getName()) && !pizzaFound){
                            System.out.println("FOUND ORDER FOR PIZZA");
                            pizza.setStatus(PizzaStatus.FINISHED);
                            pizzaFound = true;
                            finished++;
                        }
                    }
                    else if(pizza.getStatus().equals(PizzaStatus.FINISHED))
                        finished++;
                }
                //                System.out.println("TO MAKE:"+toMake+", FINISHED:"+finished);
                if(finished == toMake){
                    o.setOrderstatus(OrderStatus.DELIVERYFINISHED);
                    toDeliver.add(o);
                    notifyDriver("!delivery order id:"+o.getId()+" finished");
                    finished = 0;
                    toMake = 0;
                }
                ord = o;
                
                if(pizzaFound)
                    break;
            }
            
        }
    }
    
    @Override
    public synchronized Order getCookedOrder() throws RemoteException {
        Order order = null;
        
        for (Order o : groupOrders){
            if(o.getOrderstatus().equals(OrderStatus.COOKED)){
                order = o;
                break;
            }
        }
        
        return order;
    }
    
    @Override
    public synchronized void serveOrder(Order order) throws RemoteException {
        for (Order o : groupOrders){
            if(o.getId().equals(order.getId())){
                o.setOrderstatus(order.getOrderstatus());
                o.setWaiterServedId(order.getWaiterServedId());
                break;
            }
        }
//        if(guiGuestInterface != null)
//            guiGuestInterface.setGroupInfo(group);
        if(guiPizzeriaInterface != null)
            guiPizzeriaInterface.setOrderInfo(order);
        notifyGroup(order.getGroupID(), "!eat");
    }
    
    @Override
    public synchronized void deliverOrder(Order order) throws RemoteException {
        for (Order o : deliveryOrders){
            if(o.getId().equals(order.getId())){
                o.setOrderstatus(OrderStatus.DELIVERYFINISHED);
                o.setWaiterServedId(order.getWaiterServedId());
//                System.out.println("Delivered or thrown away delivery order:"+o.getId());
                notifyDelivery(order.getGroupID(), "!dingdong");
                break;
            }
        }
        
    }
    
    @Override
    public synchronized Order getDeliveryOrder() throws RemoteException {
        return toDeliver.poll();
    }
    
    @Override
    public synchronized double calculatePizzeriaLoad() throws RemoteException {
        int count = 0;
        for(Order order : deliveryOrders){
            if(order.getOrderstatus().equals(OrderStatus.DELIVERYNEW))
                count++;
        }
        load = Double.valueOf(count);
        return load;
    }
    
    @Override
    public double getLoad() throws RemoteException {
        return load;
    }
    
    @Override
    public synchronized Order takeOneDeliveryOrder() throws RemoteException {
        Order order = null;
        
        for(Order o : deliveryOrders){
            if(o.getOrderstatus().equals(OrderStatus.DELIVERYNEW)){
                order = o;
                o.setOrderstatus(OrderStatus.TRANSFERRED);
                System.out.println("Transferred order:"+o.getId());
                break;
            }
            
        }
        
        if(order != null)
            order.setOrderstatus(OrderStatus.DELIVERYTRANSFERRED);
        
        return order;
    }
    
    @Override
    public synchronized boolean putDeliveryOrder(Order o) throws RemoteException {
        System.out.println("GOT Transferred order:"+o.getId());
        deliveryOrders.add(o);
        
        return true;
    }
    
    @Override
    public String getAddress() throws RemoteException {
        return pizzeriaInfo;
    }
    
    @Override
    public void benchmarkOrders(ArrayList<Order> orderList, Queue<Pizza> pizzas) throws RemoteException {
        System.out.println("ADDED "+orderList.size()+" orders manually.");
        deliveryOrders = orderList;
        deliveryPizzas = pizzas;
        
    }
    
    @Override
    public void benchmarkStart() throws RemoteException {
        System.out.println("Benchmark started: size "+deliveryOrders.size());
        for(int i = 0; i < deliveryOrders.size(); i++){
            notifyCook("!delivery");
        }
    }
    
    @Override
    public synchronized void requestBill(GuestGroup guestGroup) throws RemoteException {
        Long id = guestGroup.getId();
        
        for(Table tab : tables){
            if(tab.getGroupID() != null){
                if(tab.getGroupID().equals(id)){
                    tab.setTabStat(TableStatus.PAY);
                    tab.setBill(calculateBill(guestGroup.getOrder()));
                    break;
                }
            }
            
        }
        notifyWaiter("!bill"+" GuestGroup:"+id+" wants to pay.");
    }
    
    public double calculateBill(Order order){
        double result = 0.0;
        
        for(Pizza p: order.getPizzaList())
            result += p.getPrice();
        
        return result;
    }
    
    @Override
    public synchronized Table prepareBill() throws RemoteException {
        Table result = null;
        
        for(Table t : tables){
            if(t.getTabStat().equals(TableStatus.PAY)){
                result = t;
                t.setTabStat(TableStatus.DOBILL);
                break;}
        }
        return result;
    }
    
    @Override
    public synchronized void sendBill(Table table) throws RemoteException{
        for(Table t : tables){
            if(t.getId().equals(table.getId())){
                t.getOrder().setWaiterBillProcessedId(table.getOrder().getWaiterBillProcessedId());
                t.getOrder().setOrderstatus(OrderStatus.PAID);
                t.setTabStat(TableStatus.PAID);
                break;
            }            
        }
        notifyGroup(table.getGroupID(), "!bill "+ table.getBill());
    }
    
    @Override
    public synchronized void payBillNLeave(Long groupID) throws RemoteException{
        for(Table t : tables){
            if(t.getGroupID().equals(groupID)){
                t.leaveTable();
                break;
            }
            
        }
    }

    @Override
    public void registerGuestGUI(IGuestGUIRMI guiInterface) throws RemoteException {
        this.guiGuestInterface = guiInterface;
        System.out.println("GUEST GUI registered.");
    }

    @Override
    public void registerPizzeriaGUI(IPizzeriaGUIRMI guiInterface) throws RemoteException {
        this.guiPizzeriaInterface = guiInterface;
        System.out.println("PIZZERIA GUI registered.");
    }
}
