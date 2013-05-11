package tuwien.sbctu;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.mozartspaces.capi3.Coordinator;
import org.mozartspaces.capi3.FifoCoordinator;
import org.mozartspaces.capi3.KeyCoordinator;
import org.mozartspaces.core.Capi;
import org.mozartspaces.core.ContainerReference;
import org.mozartspaces.core.DefaultMzsCore;
import org.mozartspaces.core.Entry;
import org.mozartspaces.core.MzsCore;
import org.mozartspaces.core.MzsCoreException;
import org.mozartspaces.core.TransactionReference;
import org.mozartspaces.notifications.Notification;
import org.mozartspaces.notifications.NotificationListener;
import org.mozartspaces.notifications.NotificationManager;
import org.mozartspaces.notifications.Operation;
import tuwien.sbctu.conf.*;

import tuwien.sbctu.container.*;
import tuwien.sbctu.models.*;

public class Pizzeria {
// TODO
	/*
	 * @ inputs list of guests - FIFO queue
	 * @ inputs list of waiters
	 * @ inputs list of cooks
	 * @ inputs list of tables
	 * 
	 */
	
	
	
	

	private final MzsCore core;
	private final Capi capi;
	private final URI space;
	private final NotificationManager manager;
	
	
	private ContainerReference entrance;
	private ContainerReference tables;
	
	// 3 containers
//	private final FifoContainerXvsm<GuestGroup> entranceContainer;
//	private final IdContainerXvsm tableContainer;
//	private final IdContainerXvsm barContainer;

	
	
	public Pizzeria() throws MzsCoreException,
	InterruptedException {
		
		
		core = DefaultMzsCore.newInstance();
		capi = new Capi(core);

		try {
			space = new URI(PizzeriaConfiguration.LOCAL_SPACE_URI);
		} catch (final URISyntaxException e) {
			throw new IllegalStateException(
					"Unexpected state: Unparseable space URI="
							+ PizzeriaConfiguration.LOCAL_SPACE_URI);
		}

		manager = new NotificationManager(core);
		
		ArrayList<Coordinator> obligatoryCoords = new ArrayList<Coordinator>();
        obligatoryCoords.add(new FifoCoordinator());
        obligatoryCoords.add(new KeyCoordinator());
		
	    entrance = capi.createContainer(PizzeriaConfiguration.CONTAINER_NAME_ENTRANCE, space, 100, obligatoryCoords, 
	    		null,null);
	    
	    tables = capi.createContainer(PizzeriaConfiguration.CONTAINER_NAME_TABLES, space, 100, obligatoryCoords, 
	    		null,null);

		
		
		
//		entranceContainer = new FifoContainerXvsm<>(capi, space,
//				PizzeriaConfiguration.CONTAINER_NAME_ENTRANCE);
//		tableContainer = new IdContainerXvsm(capi, space, PizzeriaConfiguration.CONTAINER_NAME_TABLES);
//		barContainer = new IdContainerXvsm(capi, space, PizzeriaConfiguration.CONTAINER_NAME_BAR);
	}
	
	
	

	
	
	
	
	
}
