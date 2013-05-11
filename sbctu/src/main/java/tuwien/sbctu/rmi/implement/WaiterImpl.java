package tuwien.sbctu.rmi.implement;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import tuwien.sbctu.models.Waiter;
import tuwien.sbctu.models.Waiter.WaiterStatus;
import tuwien.sbctu.rmi.interfaces.IGuestGroupRMI;
import tuwien.sbctu.rmi.interfaces.IPizzeriaRMI;
import tuwien.sbctu.rmi.interfaces.IWaiterRMI;

public class WaiterImpl extends UnicastRemoteObject  implements IWaiterRMI{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Waiter waiter;
	private IPizzeriaRMI entry;
	
	public IPizzeriaRMI getEntry() {
		return entry;
	}

	public void setEntry(IPizzeriaRMI entry) {
		this.entry = entry;
	}

	public WaiterImpl(Long id) throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
		setWaiter(new Waiter(id));
//		this.entry = entry;
	}
	
	@Override
	public void beginWork() throws RemoteException {
		waiter.setWaiterStatus(WaiterStatus.WAITING);
	}

	@Override
	public void entryNotify() throws RemoteException {
		System.out.println("RMIServer: GUESTS ARE WAITING");
		
		if(waiter.getWaiterStatus() == WaiterStatus.WAITING)
			bringGuestToTable();
		else
			System.out.println("I'm working!!");
		
	}

	@Override
	public void orderNotify() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void billNotifiy() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void finishedOrderNotify() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	public Waiter getWaiter() {
		return waiter;
	}

	public void setWaiter(Waiter waiter) {
		this.waiter = waiter;
	}

	public void bringGuestToTable() throws RemoteException{
		//TODO LOOK if tables are free

		//TODO get table and assign group to table
		IGuestGroupRMI igg = entry.getGuestGroup();

		if(igg != null){
			waiter.setWaiterStatus(WaiterStatus.WORKING);
			igg.tableNotify();
			try {
				Thread.sleep(12000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Handled group table request.");
			}
		else
			System.out.println("No group waiting.");
		
		waiter.setWaiterStatus(WaiterStatus.WAITING);
	}

	@Override
	public WaiterStatus waiterStatus() throws RemoteException {
		return waiter.getWaiterStatus();
	}
}
