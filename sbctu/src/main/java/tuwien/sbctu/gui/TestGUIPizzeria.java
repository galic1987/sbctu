package tuwien.sbctu.gui;

import tuwien.sbctu.models.GuestGroup;
import tuwien.sbctu.models.GuestGroup.GroupStatus;
import tuwien.sbctu.models.Order;
import tuwien.sbctu.models.Pizza;
import tuwien.sbctu.models.Table;

public class TestGUIPizzeria {
    
    /**
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        IPizzeriaGUI guiInterface = new PizzeriaGUIImpl();
        
        GUIPizzeria  gui = new GUIPizzeria();
        gui.setPizzeriaInformationInterface(guiInterface);
        gui.setVisible(true);
        
//        Pizza p1 = new Pizza("MARGARITA", 2.50, 3);
//        Pizza p2 = new Pizza("SALAMI", 2.50, 3);
//        
//        Order ord1 = new Order(new Long(51));
//        ord1.addPizzaToOrder(p1);
//        ord1.addPizzaToOrder(p2);
//        
//        Order ord2 = new Order(new Long(52));
//        ord2.addPizzaToOrder(p2);
//        ord2.addPizzaToOrder(p2);
//        ord2.addPizzaToOrder(p2);
//        ord2.addPizzaToOrder(p1);
//        
//        Order ord3 = new Order(new Long(53));
//        ord3.addPizzaToOrder(p1);
//        
//        Table tab1 = new Table(new Long(201));
//        tab1.setTabStat(Table.TableStatus.FREE);
//        
//        Table tab2 = new Table(new Long(202));
//        tab2.setTabStat(Table.TableStatus.FREE);
//        
//        Table tab3 = new Table(new Long(203));
//        tab3.setTabStat(Table.TableStatus.FREE);
//        
//        GuestGroup tgg1 = new GuestGroup(new Long(101));
//        tgg1.setGroupSize(2);
//        ord1.setGroupID(tgg1.getId());
//        
//        GuestGroup tgg2 = new GuestGroup(new Long(102));
//        tgg2.setGroupSize(4);
//        ord2.setGroupID(tgg2.getId());
//        
//        GuestGroup tgg3 = new GuestGroup(new Long(103));
//        tgg3.setGroupSize(1);
//        ord3.setGroupID(tgg3.getId());
//        
//        GuestGroup tgg4 = new GuestGroup(new Long(104));
//        tgg4.setGroupSize(2);
//        GuestGroup tgg5 = new GuestGroup(new Long(105));
//        tgg5.setGroupSize(2);
//        
//        Thread.sleep(3000);
//        
//        
//        gui.activateThread();
//        System.out.println("Thread on");
//        
//        guiInterface.setGuestGroupInfo(tgg1);
//        guiInterface.setGuestGroupInfo(tgg2);
//        guiInterface.setGuestGroupInfo(tgg3);
//        guiInterface.setGuestGroupInfo(tgg4);
//        guiInterface.setGuestGroupInfo(tgg5);
//        
//        guiInterface.setTableInfo(tab1);
//        guiInterface.setTableInfo(tab2);
//        guiInterface.setTableInfo(tab3);
//        
//        Thread.sleep(3000);
//        
//        tgg1.setStatus(GroupStatus.SITTING);
//        tab1.setTabStat(Table.TableStatus.USED);
//        tab1.setGroup(tgg1);
//        tab1.setGroupID(tgg1.getId());
//        
//        
//        tgg2.setStatus(GroupStatus.SITTING);
//        tab2.setTabStat(Table.TableStatus.USED);
//        tab2.setGroup(tgg2);
//        tab2.setGroupID(tgg2.getId());
//        
//        tgg3.setStatus(GroupStatus.SITTING);
//        tab3.setTabStat(Table.TableStatus.USED);
//        tab3.setGroup(tgg3);
//        tab3.setGroupID(tgg3.getId());
//        
//        guiInterface.setTableInfo(tab1);
//        guiInterface.setTableInfo(tab2);
//        guiInterface.setTableInfo(tab3);
//        
//        Thread.sleep(3000);
//        
//        guiInterface.setOrderInfo(ord1);
//        guiInterface.setOrderInfo(ord2);
//        guiInterface.setOrderInfo(ord3);
        
                
    }
    
}
