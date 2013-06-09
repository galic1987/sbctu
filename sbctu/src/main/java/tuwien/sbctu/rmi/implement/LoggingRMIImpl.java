package tuwien.sbctu.rmi.implement;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import tuwien.sbctu.models.GuestDelivery;
import tuwien.sbctu.models.GuestGroup;

import tuwien.sbctu.models.LoggingRecorder;
import tuwien.sbctu.models.Order;
import tuwien.sbctu.models.Table;
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

    @Override
    public List<GuestDelivery> getGd() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setGd(List<GuestDelivery> gd) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<GuestGroup> getGg() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setGg(List<GuestGroup> gg) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Table> getTables() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setTables(List<Table> gd) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Order> getOrders() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setOrders(List<Order> gg) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
