package tuwien.sbctu.rmi.implement;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import tuwien.sbctu.models.GuestDelivery;
import tuwien.sbctu.models.GuestDelivery.DeliveryStatus;
import tuwien.sbctu.rmi.interfaces.IGuestDelivery;

public class GuestDeliveryImpl extends UnicastRemoteObject implements IGuestDelivery{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	GuestDelivery guestGroup;
	
	public GuestDeliveryImpl(GuestDelivery gg) throws RemoteException{
		this.guestGroup = gg;
	}

	public GuestDelivery getGg() {
		return guestGroup;
	}

	public void setGg(GuestDelivery gg) {
		this.guestGroup = gg;
	}

	@Override
	public void notification(String message) throws RemoteException {
		System.out.println(message);
		processNotification(message);		
	}

	@Override
	public GuestDelivery getGroup() throws RemoteException {
		return guestGroup;
	}
	
	@Override
	public void setStatus(DeliveryStatus groupStatus) throws RemoteException{
		guestGroup.setStatus(groupStatus);
	}
	
	public void processNotification(String message){
		if(message.contains("!hello"))
			guestGroup.setStatus(DeliveryStatus.WELCOME);
		if(message.contains("!ordered"))
			guestGroup.setStatus(DeliveryStatus.ORDERED);
		else if(message.contains("!dingdong"))
			guestGroup.setStatus(DeliveryStatus.DELIVERED);
		else if (message.contains("!bill"))
			guestGroup.setStatus(DeliveryStatus.PAYED);
	}

}