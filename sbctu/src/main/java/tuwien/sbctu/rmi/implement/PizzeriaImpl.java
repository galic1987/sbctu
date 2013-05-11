package tuwien.sbctu.rmi.implement;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import tuwien.sbctu.models.Table;
import tuwien.sbctu.rmi.interfaces.IGuestGroupRMI;
import tuwien.sbctu.rmi.interfaces.IPizzeriaRMI;
import tuwien.sbctu.rmi.interfaces.IWaiterRMI;

public class PizzeriaImpl extends UnicastRemoteObject implements IPizzeriaRMI{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PizzeriaImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	Queue<IGuestGroupRMI> igg = new ConcurrentLinkedQueue<IGuestGroupRMI>();
	Queue<IWaiterRMI> iw = new ConcurrentLinkedQueue<IWaiterRMI>();
	
	List<Table> tableList = new ArrayList<Table>();
	
	@Override
	public IGuestGroupRMI getGuestGroup() throws RemoteException {
		return igg.poll();
	}
	
	public void addSingleGuestGroup(IGuestGroupRMI group){
		System.out.println("*** ENTRY RMI **** Group entered.");
		this.igg.add(group);
		
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
		iw.beginWork();
		this.iw.add(iw);
	}

	@Override
	public void waiterUnsubscribeCallback() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
