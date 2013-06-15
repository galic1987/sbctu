package tuwien.sbctu.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import tuwien.sbctu.models.Driver;
import tuwien.sbctu.models.Driver.DriverStatus;
import tuwien.sbctu.models.Order;

public interface IDriver extends Remote{
    public void notification(String message) throws RemoteException;
    public void setStatus(DriverStatus driverStatus) throws RemoteException;
    public DriverStatus getStatus() throws RemoteException;
    public Driver getDriver() throws RemoteException;
    
    public String toDeliver() throws RemoteException;
    
}
