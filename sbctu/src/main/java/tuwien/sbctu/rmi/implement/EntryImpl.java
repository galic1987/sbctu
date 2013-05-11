package tuwien.sbctu.rmi.implement;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import tuwien.sbctu.models.GuestGroup;
import tuwien.sbctu.rmi.interfaces.IEntryRMI;
import tuwien.sbctu.rmi.interfaces.IGuestGroupRMI;
import tuwien.sbctu.rmi.interfaces.IWaiterRMI;

public class EntryImpl extends UnicastRemoteObject implements IEntryRMI{

	public EntryImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	Queue<GuestGroup> guestQueue = new ConcurrentLinkedQueue<GuestGroup>();
	Queue<IGuestGroupRMI> igg = new ConcurrentLinkedQueue<IGuestGroupRMI>();
	Queue<IWaiterRMI> iw = new ConcurrentLinkedQueue<IWaiterRMI>();
	
	
	@Override
	public GuestGroup getGuestGroup() throws RemoteException {
		igg.poll().tableNotify();
		return guestQueue.poll();
	}
	
	public void addSingleGuestGroup(GuestGroup group){
		System.out.println("*** ENTRY RMI **** Group entered.");
		this.guestQueue.add(group);
		
		for(IWaiterRMI iwr: iw){
			try {
				iwr.entryNotify();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void waiterSubscribeCallback(IWaiterRMI iw) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("*** ENTRY RMI **** Waiter subscribed.");
		this.iw.add(iw);
	}

	@Override
	public void waiterUnsubscribeCallback() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void groupSubscribeCallback(IGuestGroupRMI igg) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("*** ENTRY RMI **** Group subscribed.");
		this.igg.add(igg);
		
	}

	@Override
	public void groupUnsubscribeCallback() throws RemoteException {
		// TODO Auto-generated method stub
		this.igg.poll();
	}

}
