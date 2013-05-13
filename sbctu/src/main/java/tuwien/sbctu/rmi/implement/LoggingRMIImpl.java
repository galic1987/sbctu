package tuwien.sbctu.rmi.implement;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import tuwien.sbctu.models.LoggingRecorder;
import tuwien.sbctu.rmi.interfaces.ILoggingRMI;

public class LoggingRMIImpl extends UnicastRemoteObject implements ILoggingRMI{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	LoggingRecorder logrec;

	public LoggingRMIImpl() throws RemoteException {
		super();
		logrec = new LoggingRecorder();
	}

	@Override
	public void guestInfo(String message) throws RemoteException {
		logrec.insertGuestInfo(processMessage(message));
		
	}

	@Override
	public void waiterInfo(String message) throws RemoteException {
		logrec.insertWaiterInfo(processMessage(message));		
	}

	@Override
	public void cookInfo(String message) throws RemoteException {
		logrec.insertCookInfo(processMessage(message));
		
	}

	@Override
	public void tableInfo(String message) throws RemoteException {
		logrec.insertTableInfo(processMessage(message));
		
	}

	@Override
	public void pizzaInfo(String message) throws RemoteException {
		logrec.insertPizzaInfo(processMessage(message));
		
	}

	@Override
	public void orderInfo(String message) throws RemoteException {
		logrec.insertOrderInfo(processMessage(message));
		
	}

	@Override
	public void billInfo(String message) throws RemoteException {
		logrec.insertBillInfo(processMessage(message));
		
	}
	
	public String processMessage(String message){
		return message;
	}

}
