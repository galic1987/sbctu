package tuwien.sbctu.rmi.implement;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import tuwien.sbctu.rmi.interfaces.IWaiterRMI;

public class WaiterImpl extends UnicastRemoteObject  implements IWaiterRMI{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private int ws;
	
	public WaiterImpl(Long id) throws RemoteException {
		super();
		
		this.id = id;
		ws = 0;
	}
	
	@Override
	public Long getId(){
		return id;
	}

	@Override
	public void notification(String message) throws RemoteException {
		System.out.println(message);
		if(ws == 0)
			processNotification(message);		
	}
	
	public synchronized void setStatus(int ws) throws RemoteException {
		this.ws = ws;
	}

	public synchronized int getStatus() throws RemoteException {
		return ws;
	}
	
	public void processNotification(String message){
		if(message.contains("Entry"))// && ws.equals(WaiterStatus.WAITING))
			ws = 1;		//WaiterStatus.WORKING;
		else if(message.contains("Done"))
			ws = 0;	//WaiterStatus.WAITING;
		else if(message.contains("Order") && message.contains("waiting"))
			ws = 2;
		else if(message.contains("Order") && message.contains("ready"))
			ws = 3;
		else if(message.contains("Bill"))
			ws = 4;
	}
}
