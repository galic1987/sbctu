package tuwien.sbctu.rmi.implement;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import tuwien.sbctu.models.Cook.CookStatus;
import tuwien.sbctu.rmi.interfaces.ICookRMI;

public class CookImpl extends UnicastRemoteObject implements ICookRMI{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private CookStatus ws;
	public CookImpl(Long id) throws RemoteException{
		this.id = id;
		ws = CookStatus.WAITING;
	}
	@Override
	public void notification(String message) throws RemoteException {
		System.out.println(message);
		if(ws.equals(CookStatus.WAITING))
			processNotification(message);	
	}

	@Override
	public void setStatus(CookStatus cs) throws RemoteException {
		ws = cs;		
	}

	@Override
	public CookStatus getStatus() throws RemoteException {
		return ws;
	}

	@Override
	public Long getId() throws RemoteException {
		return id;
	}

	public void processNotification(String message){
		if(message.contains("Todo"))
			ws = CookStatus.WORKING;
	}
}
