package tuwien.sbctu.rmi.implement;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import tuwien.sbctu.models.Order;
import tuwien.sbctu.models.Table;
import tuwien.sbctu.models.Waiter.WaiterStatus;
import tuwien.sbctu.rmi.interfaces.ICookRMI;
import tuwien.sbctu.rmi.interfaces.IGuestGroupRMI;
import tuwien.sbctu.rmi.interfaces.IPizzeriaRMI;
import tuwien.sbctu.rmi.interfaces.IWaiterRMI;

public class PizzeriaImpl extends UnicastRemoteObject implements IPizzeriaRMI{

	
	Queue<IGuestGroupRMI> waitingEntry = new ConcurrentLinkedQueue<IGuestGroupRMI>();
	
	List<IWaiterRMI> iw = new ArrayList<IWaiterRMI>();
	List<ICookRMI> ic = new ArrayList<ICookRMI>();
	List<IGuestGroupRMI> guests = new ArrayList<IGuestGroupRMI>();
	
	List<Table> tableList = new ArrayList<Table>();
	List<Order> orderList = new ArrayList<Order>();

	private static final long serialVersionUID = 1L;

	public PizzeriaImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public IGuestGroupRMI getGuestGroup() throws RemoteException {
		return waitingEntry.poll();
	}

	public void guestGroupEnters(IGuestGroupRMI group) {
		System.out.println("*** ENTRY RMI **** Group entered.");
		this.waitingEntry.add(group);
		
		//inform free waiters for new entry
		newEntryNotify();
	}

	@Override
	public void waiterEnteres(IWaiterRMI iw) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("*** ENTRY RMI **** Waiter subscribed.");
		iw.beginWork();
		this.iw.add(iw);
	}

	@Override
	public void waiterLeaves() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	public void newEntryNotify(){
		int counter = 0;
		
		for(IWaiterRMI iwr: iw){
			try {
				if(iwr.waiterStatus() == WaiterStatus.WAITING){
					iwr.entryNotify();
					counter++;
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//TODO what happens if all waiters are busy?
	}

	
	@Override
	public void makeOrder(IGuestGroupRMI group) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void isTableFree() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void isBillRequested() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
