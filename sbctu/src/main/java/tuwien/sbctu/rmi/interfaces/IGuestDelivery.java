package tuwien.sbctu.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import tuwien.sbctu.models.GuestDelivery;
import tuwien.sbctu.models.GuestDelivery.DeliveryStatus;

public interface IGuestDelivery extends Remote{

	public void notification(String message) throws RemoteException;
	public void setStatus(DeliveryStatus groupStatus) throws RemoteException;
	public GuestDelivery getGroup() throws RemoteException;
	
}
