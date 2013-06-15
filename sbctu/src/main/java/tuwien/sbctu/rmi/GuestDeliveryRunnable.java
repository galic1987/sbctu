package tuwien.sbctu.rmi;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Logger;

import tuwien.sbctu.models.GuestDelivery;
import tuwien.sbctu.models.GuestDelivery.DeliveryStatus;
import tuwien.sbctu.rmi.implement.GuestDeliveryImpl;
import tuwien.sbctu.rmi.interfaces.IGuestDelivery;
import tuwien.sbctu.rmi.interfaces.IPizzeria;

public class GuestDeliveryRunnable implements Runnable{
    
    private boolean isActive;
    private IPizzeria iPizzeria;
    private IGuestDelivery iGuestDelivery;
    
    private static final Logger log = Logger.getLogger("RunnableGuestDelivery");
    
    public GuestDeliveryRunnable(GuestDelivery guestGroup, Integer port, String bindingName){
        isActive = true;
        
        try {
            iGuestDelivery = new GuestDeliveryImpl(guestGroup);
            log.info(String.format("GuestDelivery started \n\tID: %s \n\tsize: %s", guestGroup.getId(), iGuestDelivery.getGroup().getGroupSize() ));
            
        } catch (RemoteException e) {
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
            iPizzeria.callPizzeria(iGuestDelivery.getGroup(), iGuestDelivery);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (AccessException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        
    }
    
    public void work() throws RemoteException{
        DeliveryStatus gs = iGuestDelivery.getGroup().getStatus();
        
        switch(gs){
            
            case ORDERED:
//                System.out.println("ORDERED");
//                iGuestDelivery.getGroup().setStatus(GuestDeliveryStatus.DELIVERED);
                break;
            case DELIVERED:
                System.out.println("Delivered");
                iGuestDelivery.getGroup().setStatus(DeliveryStatus.PAYED);
                break;
            case PAYED:
                System.out.println("PAyed");
                //			iGuestDelivery.getGroup().setStatus(GuestDeliveryStatus.);
                isActive = false;
                break;
                
            default:
                
                break;
                
        }
    }
    
}

