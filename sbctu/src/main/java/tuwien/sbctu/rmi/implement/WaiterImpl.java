package tuwien.sbctu.rmi.implement;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import tuwien.sbctu.rmi.interfaces.IWaiterRMI;

public class WaiterImpl extends UnicastRemoteObject  implements IWaiterRMI{

	public WaiterImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void entryNotify() throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("GUESTS ARE WAITING - TESTE");
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

}
