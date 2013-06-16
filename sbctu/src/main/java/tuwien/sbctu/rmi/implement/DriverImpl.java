/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuwien.sbctu.rmi.implement;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import tuwien.sbctu.models.Driver;
import tuwien.sbctu.models.Driver.DriverStatus;
import tuwien.sbctu.rmi.interfaces.IDriver;

/**
 *
 * @author Adnan
 */
public class DriverImpl extends UnicastRemoteObject implements IDriver{
    
    Driver driver ;
    
    Queue<String> deliveries = new ConcurrentLinkedQueue<>();
    
    public DriverImpl(Driver driver) throws RemoteException{
        this.driver = driver;
    }
    
    @Override
    public void notification(String message) throws RemoteException {
        System.out.println(message);
        if(driver.getDriverStatus().equals(DriverStatus.WAITING)){
            if(message.contains("!delivery"))
                driver.setDriverStatus(DriverStatus.DRIVING);
        }
        else
            deliveries.add(message);
    }

    @Override
    public void setStatus(DriverStatus driverStatus) throws RemoteException {
        driver.setDriverStatus(driverStatus);
    }

    @Override
    public DriverStatus getStatus() throws RemoteException {
        return driver.getDriverStatus();
    }

    @Override
    public Driver getDriver() throws RemoteException {
        return driver;
    }

    @Override
    public String toDeliver() throws RemoteException {
        String result = deliveries.poll();
        if(result != null){
            
            if(driver.getDriverStatus().equals(DriverStatus.WAITING)){
            if(result.contains("!delivery"))
                driver.setDriverStatus(DriverStatus.DRIVING);
        }
        else
            deliveries.add(result);
        }
        
        
        return result;
    }
    
    

}
