package tuwien.sbctu.runtime;

import java.io.Serializable;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.mozartspaces.capi3.Coordinator;
import org.mozartspaces.capi3.FifoCoordinator;
import org.mozartspaces.capi3.KeyCoordinator;
import org.mozartspaces.capi3.QueryCoordinator;
import org.mozartspaces.capi3.RandomCoordinator;
import org.mozartspaces.core.Capi;
import org.mozartspaces.core.ContainerReference;
import org.mozartspaces.core.DefaultMzsCore;
import org.mozartspaces.core.Entry;
import org.mozartspaces.core.MzsCore;
import org.mozartspaces.core.MzsCoreException;
import org.mozartspaces.core.TransactionReference;
import org.mozartspaces.core.MzsConstants.RequestTimeout;
import org.mozartspaces.notifications.Notification;
import org.mozartspaces.notifications.NotificationListener;
import org.mozartspaces.notifications.NotificationManager;
import org.mozartspaces.notifications.Operation;

import tuwien.sbctu.conf.PizzeriaConfiguration;
import tuwien.sbctu.models.DeliveryAddress;
import tuwien.sbctu.models.GuestGroup;
import tuwien.sbctu.models.Order;
import tuwien.sbctu.models.Pizza;
import tuwien.sbctu.models.Table;
import tuwien.sbctu.models.GuestGroup.GroupStatus;

public class RunDeliveryGuestSBC implements NotificationListener,Runnable {

	/**
	 * @param args[0] - int port unique 
	 * args[1] - int id unique
	 * 
	 */
	
	protected  long id;
	protected  MzsCore core;
	protected  Capi capi;
	protected  URI space;
	protected  ContainerReference delivery;
	protected  ContainerReference deliveryContainer;

	
	protected  AtomicBoolean working;
	protected  int timeOut;
	protected  String spaceAddress;
	protected  DeliveryAddress deliveryAddress;
	
	//
	  
	private  SecureRandom random = new SecureRandom();
	private  String [] indirectArgs = null;
	private  ArrayList<Pizza> pizz = null;
	
	 
	public RunDeliveryGuestSBC(int porta,long ida, String spaceAddressa){

		// TODO Auto-generated method stub
		
        try {

		int port = porta;
	    id = ida;
		spaceAddress = spaceAddressa;
		
		deliveryAddress = new DeliveryAddress(spaceAddress, nextSessionId());
		
		 core = DefaultMzsCore.newInstance(port);
         capi = new Capi(core);
         
         timeOut = 1000;
         
         working = new AtomicBoolean();
         

			space = new URI(spaceAddress);

			
			
	        
            TransactionReference tx = capi.createTransaction(timeOut, space);

			delivery = capi.lookupContainer(PizzeriaConfiguration.CONTAINER_NAME_DELIVERY, space, 1000, tx);
			
			capi.createContainer(deliveryAddress.getContainerName(), space, 10, tx );
			
			Order o = new Order();
			o.setId(id);
			o.setPizzaList(pizz);
			
			
		    Entry entry = new Entry(o, Arrays.asList(KeyCoordinator.newCoordinationData(String.valueOf(o.getId()))));
		  //Entry entry2 = new Entry(g, Arrays.asList(KeyCoordinator.newCoordinationData(String.valueOf(g.getId()+22)), QueryCoordinator.newCoordinationData()));

			
			
			//test shizzle
			//capi.write(entrance, entry); 
			capi.write(delivery, entry);
			
			capi.commitTransaction(tx);
			//capi.write(entrance, entry2);
			
			// Create notification
	        NotificationManager notifManager = new NotificationManager(core);
	        Set<Operation> operations = new HashSet<Operation>();
	        operations.add(Operation.WRITE);
	        notifManager.createNotification(deliveryContainer, this, operations, null, null);


			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			//capi.rollbackTransaction(tx);
			e.printStackTrace();
			
		}
         
        
		
		
		
	
	
	
		
	}

	@Override
	public void entryOperationFinished(Notification arg0, Operation arg1,
			List<? extends Serializable> arg2) {
        System.out.println(id + " > Got Pizza delivered : ID" +arg2.toString() + " " + arg1.toString());

      
		
	}
	
	
	

	


	@Override
	public void run() {
		// TODO Auto-generated method stub
		//main(indirectArgs);
	}
	
	public String[] getIndirectArgs() {
		return indirectArgs;
	}


	public void setIndirectArgs(String[] indirectArgs) {
		this.indirectArgs = indirectArgs;
	}
	

	public  String nextSessionId()
	  {
	    return new BigInteger(130, random).toString(32);
	  }


	public  ArrayList<Pizza> getPizz() {
		return pizz;
	}


	public  void setPizz(ArrayList<Pizza> pizz) {
		this.pizz = pizz;
	}


}
