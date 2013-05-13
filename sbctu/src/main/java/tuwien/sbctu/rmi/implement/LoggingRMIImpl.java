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
		System.out.println(message);
		return message;
	}

	@Override
	public String getGuest() throws RemoteException {
		// TODO Auto-generated method stub
		return logrec.getGuestInfo();
	}

	@Override
	public String getWaiter() throws RemoteException {
		// TODO Auto-generated method stub
		return logrec.getWaiterInfo();
	}

	@Override
	public String getCook() throws RemoteException {
		// TODO Auto-generated method stub
		return logrec.getCookInfo();
	}

	@Override
	public String getTable() throws RemoteException {
		// TODO Auto-generated method stub
		return logrec.getTableInfo();
	}

	@Override
	public String getPizza() throws RemoteException {
		// TODO Auto-generated method stub
		return logrec.getPizzaInfo();
	}

	@Override
	public String getOrder() throws RemoteException {
		// TODO Auto-generated method stub
		return logrec.getOrderInfo();
	}

	@Override
	public String getBill() throws RemoteException {
		// TODO Auto-generated method stub
		return logrec.getBillInfo();
	}

	@Override
	public void testMe() throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("SERVER: HIIIIII");
	}

}
