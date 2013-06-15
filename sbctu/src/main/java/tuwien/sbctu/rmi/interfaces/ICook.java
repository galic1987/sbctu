package tuwien.sbctu.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import tuwien.sbctu.models.Cook;

public interface ICook extends Remote{
    public void notification(String message) throws RemoteException;
    public void setStatus(int cookStatus) throws RemoteException;
    public int getStatus() throws RemoteException;
    public Cook getCook() throws RemoteException;
    
    public String getGroupOrder() throws RemoteException;
    public String getDeliveryOrder() throws RemoteException;
}
