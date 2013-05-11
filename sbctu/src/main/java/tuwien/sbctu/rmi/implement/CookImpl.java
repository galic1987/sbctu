package tuwien.sbctu.rmi.implement;

import java.rmi.RemoteException;

import tuwien.sbctu.rmi.interfaces.ICookRMI;

public class CookImpl implements ICookRMI{

	@Override
	public void ordersWaiting() throws RemoteException {
		System.out.println("Orders are waiting at the bar.");
	}
}
