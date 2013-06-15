package tuwien.sbctu.rmi;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import tuwien.sbctu.models.Cook;
import tuwien.sbctu.rmi.implement.CookImpl;
import tuwien.sbctu.rmi.interfaces.ICook;

import tuwien.sbctu.rmi.interfaces.IPizzeria;

public class CookRunnable implements Runnable{
    private static final Logger log = Logger.getLogger("RunnableCook");
    
    private boolean isActive;
    private IPizzeria iPizzeria;
    private ICook interfaceCook;
    
    public CookRunnable(Cook cook, Integer port, String bindingName){
        isActive = true;
        
        try {
            this.interfaceCook = new CookImpl(cook);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        
        enterPizzeria(port, bindingName);
        
        log.info(String.format("Cook started \n\tID: %s ", cook.getId()));
    }
    
    @Override
    public void run() {
        
        while(isActive){
            
            try {
                
                work();
                Thread.sleep(500);
                
            } catch (InterruptedException | RemoteException e) {
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
    private void enterPizzeria(Integer port, String bindingName){
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry(port);
            
            iPizzeria =(IPizzeria) registry.lookup(bindingName);
            iPizzeria.beginWork(interfaceCook.getCook(), interfaceCook);
            
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (AccessException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        
    }
    
    private void work() throws RemoteException, InterruptedException{
        int status = interfaceCook.getStatus();
//        System.out.println("Actual Status: "+status);
        
        switch(status){
            case 0:
                Thread.sleep(300);
                break;
            case 1:
                
                cookOrder();
                break;
            case 2:
                
                cookDelivery();
                break;
            default:
                break;
                
        }
        
    }
    
    private void cookOrder(){
        try {
//            TODO
            iPizzeria.cookGroupPizza();
            lookUpWork();          
        } catch (RemoteException ex) {
            Logger.getLogger(CookRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void cookDelivery(){
        try {
//            TODO
            System.out.println("delivery to do");
            iPizzeria.cookDeliveryPizza();            
            lookUpWork();
        } catch (RemoteException ex) {
            Logger.getLogger(CookRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void lookUpWork() throws RemoteException{
        
          if(interfaceCook.getDeliveryOrder() != null)
                interfaceCook.setStatus(2);
            else if(interfaceCook.getGroupOrder() != null)
                interfaceCook.setStatus(1);
            else
                interfaceCook.setStatus(0);
    }
    
}
