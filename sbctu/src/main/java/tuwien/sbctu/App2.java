package tuwien.sbctu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import org.mozartspaces.capi3.Coordinator;
import org.mozartspaces.capi3.FifoCoordinator;
import org.mozartspaces.capi3.KeyCoordinator;
import org.mozartspaces.capi3.Property;
import org.mozartspaces.capi3.Query;
import org.mozartspaces.capi3.QueryCoordinator;
import org.mozartspaces.core.Capi;
import org.mozartspaces.core.ContainerReference;
import org.mozartspaces.core.DefaultMzsCore;
import org.mozartspaces.core.Entry;
import org.mozartspaces.core.MzsCore;
import org.mozartspaces.core.MzsCoreException;
import org.mozartspaces.core.MzsTimeoutException;
import org.mozartspaces.core.TransactionReference;
import org.mozartspaces.core.MzsConstants.RequestTimeout;
import org.mozartspaces.util.parser.sql.javacc.ParseException;

import tuwien.sbctu.conf.PizzeriaConfiguration;
import tuwien.sbctu.models.GuestGroup;
import tuwien.sbctu.models.Table;

public class App2 {

	/**
	 * @param args
	 * @throws MzsCoreException 
	 * @throws URISyntaxException 
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws MzsCoreException, URISyntaxException, IOException, InterruptedException {
		// TODO Auto-generated method stub
       // System.out.println( "Hello World!" + args.toString() + args[0]);
        System.out.println("MozartSpaces: simple 'Hello, space!' with synchronous core interface");
        		// create an embedded space and construct a Capi instance for it
//        		MzsCore core = DefaultMzsCore.newInstance();
//        		Capi capi = new Capi(core);
//        		// create a container
//        		ContainerReference cref = capi.createContainer(null, null, 5, null, 
//        				new FifoCoordinator());
//        		
//        		
//        		
//        		
//        		// fill the queue with the numbers 1 to 5
//        		for (int i = 1; i <= 5; i++) {
//        		// FifoCoordinator is implicit, no coordination data necessary
//        		Entry entry = new Entry (i);
//        		capi.write(cref, entry);
//        		}
//        		// read one entry (default selector count of 1)
//        		ArrayList<Integer> readEntries = capi.read(cref, FifoCoordinator.newSelector(), 0, null);
//        		// prints 1
//        		System.out.println(readEntries.get(0));
//        		// read two entries with 10 ms timeout
//        		readEntries = capi.read(cref, FifoCoordinator.newSelector(3), 10, null);
//        		// prints 1
//        		System.out.println(readEntries.get(0));
//        		// prints 2
//        		System.out.println(readEntries.get(1));
//        		System.out.println(readEntries.get(2));
        		
        		
        		//MzsCore core = Def
         
       
            // display new properties
            //System.getProperties().list(System.out);

            
            URI space = new URI(PizzeriaConfiguration.LOCAL_SPACE_URI);
            Capi capi = new Capi(DefaultMzsCore.newInstance(1200));
		
	
		
		
        ArrayList<GuestGroup> entries = new ArrayList<GuestGroup>();

        ArrayList<Table> tables = new ArrayList<Table>();

		
		for (;;) {
            // explicit TX to prevent possible loss of taken message when consumer is offline
            TransactionReference tx = capi.createTransaction(10000, space);
           
    		ContainerReference cref = capi.lookupContainer(PizzeriaConfiguration.CONTAINER_NAME_ENTRANCE, space, 0, tx);
    		ContainerReference creftab = capi.lookupContainer(PizzeriaConfiguration.CONTAINER_NAME_TABLES, space, 0, tx);

			// Take one entry
            try {
            	
            	Query q = new Query().sql("currentStatus = 'ENTERED' AND groupSize > 2 LIMIT 1 ");
            	//Query q = new Query().sql("groupSize >= 3");
            	
            	ArrayList<Coordinator> tableCoords = new ArrayList<Coordinator>();
            	tableCoords.add(new FifoCoordinator());
            	tableCoords.add(new KeyCoordinator());
            	tableCoords.add(new QueryCoordinator());
                
                
                entries = capi.read(cref, Arrays.asList(FifoCoordinator.newSelector()) , RequestTimeout.INFINITE, tx);
                
                tables = capi.read(creftab, Arrays.asList(FifoCoordinator.newSelector()) , RequestTimeout.INFINITE, tx);
                
                
                
                
                
                
                
            } catch (MzsTimeoutException | ParseException ex) {
                System.out.println("transaction timeout. retry.");
                continue;
            }
            GuestGroup message = entries.get(0);          
            Table t = tables.get(0);


            // output
            System.out.println(message.getId());
            System.out.println(message.getStatus());
          

            capi.commitTransaction(tx);
            Thread.sleep(1000);
        }
        		
        		
        		
        		// write an entry to the container
//        		capi.write(container, new Entry("Hello, space1!"));
//        		capi.write(container, new Entry("Hello, space!2"));
//        		capi.write(container, new Entry("Hello, space!2333"));
//        		System.out.println("Entry written");
//        		// read an entry from the container
//        		ArrayList<String> resultEntries = capi.read(container);
//        		System.out.println("Entry read: " + resultEntries.get(0));
//        		System.out.println("Entry read: " + resultEntries.get(1));
        		// destroy the container
        		//capi.destroyContainer(container, null);
        		// shutdown the core
        		//core.shutdown(true);
	}

}
