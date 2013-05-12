package tuwien.sbctu.rmi.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import tuwien.sbctu.rmi.implement.PizzeriaImpl;
import tuwien.sbctu.rmi.interfaces.IPizzeriaRMI;

public class StartRMI {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String host = "localhost";	//args[0];
		int RMIPort = 10879;		//args[1];
		
		startRMI(host, RMIPort);
		System.out.println("========= SERVER started =========");
		
		//TODO start MULTIPLE WAITER
//		Thread waiter = new Thread(new RunWaiter(new Long(10), 10879, "entry"));
//		waiter.start();
		
		//TODO start MULTIPLE COOKS
	}
	
	private static void startRMI(String host, Integer RMIPort){
		IPizzeriaRMI entryInterface = null;
		
		try {

			entryInterface = new PizzeriaImpl(4);
			LocateRegistry.createRegistry(RMIPort);
			Naming.rebind("rmi://" + host + ":" + RMIPort+  "/"+"pizzeria", entryInterface);

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
}
