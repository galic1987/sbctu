package tuwien.sbctu;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

import org.mozartspaces.capi3.Coordinator;
import org.mozartspaces.capi3.FifoCoordinator;
import org.mozartspaces.capi3.KeyCoordinator;
import org.mozartspaces.core.Capi;
import org.mozartspaces.core.ContainerReference;
import org.mozartspaces.core.DefaultMzsCore;
import org.mozartspaces.core.Entry;
import org.mozartspaces.core.MzsCore;
import org.mozartspaces.core.TransactionReference;

import tuwien.sbctu.conf.PizzeriaConfiguration;
import tuwien.sbctu.models.Cook;
import tuwien.sbctu.models.Order;
import tuwien.sbctu.models.GuestGroup.GroupStatus;
import tuwien.sbctu.models.Pizza;
import tuwien.sbctu.runtime.RunDeliveryGuestSBC;

public class Simulation1 {
	
	protected static  ContainerReference delivery;
	protected static long id;
	protected static MzsCore core;
	protected static Capi capi;
	protected static URI space;
	
	protected static int timeOut;
	protected static String spaceAddress;
	
	
	public static void main( String[] args )
	{
		
		
		try{
		// connect to filiale put 100 pizza inside 
		int port = Integer.valueOf(args[0]);
		id = (long)Integer.valueOf(args[1]);
		spaceAddress = args[2];
		
		
		core = DefaultMzsCore.newInstance(port);
		capi = new Capi(core);
		timeOut = 4000;
		
		
		//put order on delivery 
		space = new URI(spaceAddress);

		
	     
        

		delivery = capi.lookupContainer(PizzeriaConfiguration.CONTAINER_NAME_DELIVERY, space, 112000, null);


		ArrayList<Pizza> p = new ArrayList<Pizza>();
		p.add(new Pizza("Margarite", 5.0f, 3));
		p.add(new Pizza("Margarite", 5.0f, 3));
        p.add(new Pizza("Salami", 5.5f, 7));
        p.add(new Pizza("Cardinale", 6.0f, 5));	
        
        System.out.println(id);

		for (int i = 16000; i < 16100; i++){
		//Order o = new Order(id+i);
        RunDeliveryGuestSBC rb = new RunDeliveryGuestSBC(i, i, spaceAddress, p);
        
      //  System.out.println(id+i);
//        Entry entry = new Entry(o, Arrays.asList(KeyCoordinator.newCoordinationData(String.valueOf(o.getId()))));
//		capi.write(delivery, entry);

		}
		
		}catch (Exception e){
			e.printStackTrace();
		}
		
	  //Entry entry2 = new Entry(g, Arrays.asList(KeyCoordinator.newCoordinationData(String.valueOf(g.getId()+22)), QueryCoordinator.newCoordinationData()));

		
		
		//test shizzle
		//capi.write(entrance, entry); 
		
	}

}
