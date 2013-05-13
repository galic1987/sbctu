package tuwien.sbctu.rmi.implement;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import tuwien.sbctu.models.LoggingRecorder;
import tuwien.sbctu.rmi.interfaces.ILoggingRMI;

public class LoggingRMIImpl extends UnicastRemoteObject implements ILoggingRMI{
	
	LoggingRecorder logrec = new LoggingRecorder();

	protected LoggingRMIImpl() throws RemoteException {
		super();
	}

	@Override
	public void guestInfo(String message) throws RemoteException {
		logrec.insertGuestInfo(message);
		
	}

	@Override
	public void waiterInfo(String message) throws RemoteException {
		logrec.insertWaiterInfo(message);		
	}

	@Override
	public void cookInfo(String message) throws RemoteException {
		logrec.insertCookInfo(message);
		
	}

	@Override
	public void tableInfo(String message) throws RemoteException {
		logrec.insertTableInfo(message);
		
	}

	@Override
	public void pizzaInfo(String message) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void orderInfo(String message) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void billInfo(String message) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	
	public void processMessage(String message){
		
	}

}
