package tuwien.sbctu.rmi;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Logger;

import tuwien.sbctu.models.GuestGroup;
import tuwien.sbctu.models.GuestGroup.GroupStatus;
import tuwien.sbctu.rmi.implement.GuestGroupImpl;
import tuwien.sbctu.rmi.interfaces.IGuestGroup;
import tuwien.sbctu.rmi.interfaces.IPizzeria;

public class GuestGroupRunnable implements Runnable{
    
    private boolean isActive;
    private IPizzeria iPizzeria;
    private IGuestGroup iGuestGroup;
    
    private static final Logger log = Logger.getLogger("RunnableGuestGroup");
    
    public GuestGroupRunnable(GuestGroup guestGroup, Integer port, String bindingName){
        isActive = true;
        
        try {
            this.iGuestGroup = new GuestGroupImpl(guestGroup);
            //			log.info(String.format("GuestGroup started \n\tID: %s \n\tsize: %s", guestGroup.getId(), iGuestGroup.getGroup().getGroupSize() ));
            
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        enterPizzeria(port, bindingName);
        
    }
    
    @Override
    public void run() {
        while(isActive){
            try {
                work();
                Thread.sleep(500);
            } catch (InterruptedException | RemoteException e) {
                e.printStackTrace();
            }
        }
        log.info("Leaving Pizzeria.");
        
    }
    
    /**
     *
     * @param port
     * @param bindingName
     * @return
     */
    private void enterPizzeria(Integer port, String bindingName){
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry(port);
            
            iPizzeria =(IPizzeria) registry.lookup(bindingName);
            iPizzeria.entryPizzeria(iGuestGroup.getGroup(), iGuestGroup);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (AccessException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        
    }
    
    public void work() throws RemoteException{
        GroupStatus gs = iGuestGroup.getGroup().getStatus();
        
        switch(gs){
            
            case ENTERED:
                break;
            case SITTING:
                iPizzeria.makeOrder(iGuestGroup.getGroup().getOrder());
                iGuestGroup.setStatus(GroupStatus.ORDERED);
                break;
            case ORDERED:
                break;
            case EATING:
                int random = (1 + (int)(Math.random() * ((5 - 1) + 1))) -1;
                System.out.println(String.format("Started eating. It takes %s seconds", random));
                
                try {
                    Thread.sleep(random*1000);
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                GuestGroup gg = iGuestGroup.getGroup();
                iPizzeria.requestBill(gg);
                iGuestGroup.setStatus(GroupStatus.FINISHED);
                break;
            case FINISHED:
                break;
            case BILL:
                System.out.println("Thanks for the delicious meal.");
                iPizzeria.payBillNLeave(iGuestGroup.getGroup().getId());
                isActive = false;
                break;
                
            default:
                try {
                    Thread.sleep(1000);
                    //				enterPizzeria(pizzeriaRMI);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                break;
                
        }
    }
    
}

