package tuwien.sbctu.rmi;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Logger;
import tuwien.sbctu.models.Driver;
import tuwien.sbctu.models.Driver.DriverStatus;
import tuwien.sbctu.models.Order;
import tuwien.sbctu.rmi.implement.DriverImpl;
import tuwien.sbctu.rmi.interfaces.IDriver;

import tuwien.sbctu.rmi.interfaces.IPizzeria;

public class DriverRunnable implements Runnable{
    private static final Logger log = Logger.getLogger("RunnableCook");
    
    private boolean isActive;
    private IPizzeria iPizzeria;
    private IDriver interfaceDriver;
    
    public DriverRunnable(Driver driver, Integer port, String bindingName){
        isActive = true;
        
                try {
                    this.interfaceDriver = new DriverImpl(driver);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
        
        enterPizzeria(port, bindingName);
        
        log.info(String.format("Driver started \n\tID: %s ", driver.getId()));
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
            iPizzeria.beginWork(interfaceDriver.getDriver(), interfaceDriver);
            
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (AccessException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        
    }
    
    private void work() throws RemoteException, InterruptedException{
        DriverStatus status = interfaceDriver.getDriver().getDriverStatus(); //interfaceCook.getStatus();
//                System.out.println("Actual Status: "+status);
        
        switch(status){
            case WAITING:
                interfaceDriver.toDeliver();
                break;
            case DRIVING:
                //iPizzeria.
                deliverOrder();
                break;
            default:
                break;
                
        }
        
    }
    
    private void deliverOrder() throws RemoteException, InterruptedException{
        Order o = iPizzeria.getDeliveryOrder();
        
        if(o != null){
            System.out.println("Driving.");
            Thread.sleep(3000);
            System.out.println("");
            System.out.println("Finished: sent delivery.");
            iPizzeria.deliverOrder(o);
        }
        interfaceDriver.setStatus(DriverStatus.WAITING);
    }
    
}
