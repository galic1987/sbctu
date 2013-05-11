package tuwien.sbctu;

import java.util.ArrayList;

import org.mozartspaces.capi3.FifoCoordinator;
import org.mozartspaces.core.Capi;
import org.mozartspaces.core.ContainerReference;
import org.mozartspaces.core.DefaultMzsCore;
import org.mozartspaces.core.Entry;
import org.mozartspaces.core.MzsCore;
import org.mozartspaces.core.MzsCoreException;

public class App2 {

	/**
	 * @param args
	 * @throws MzsCoreException 
	 */
	public static void main(String[] args) throws MzsCoreException {
		// TODO Auto-generated method stub
       // System.out.println( "Hello World!" + args.toString() + args[0]);
        System.out.println("MozartSpaces: simple 'Hello, space!' with synchronous core interface");
        		// create an embedded space and construct a Capi instance for it
        		MzsCore core = DefaultMzsCore.newInstance();
        		Capi capi = new Capi(core);
        		// create a container
        		ContainerReference cref = capi.createContainer(null, null, 5, null, 
        				new FifoCoordinator());
        		
        		
        		
        		
        		// fill the queue with the numbers 1 to 5
        		for (int i = 1; i <= 5; i++) {
        		// FifoCoordinator is implicit, no coordination data necessary
        		Entry entry = new Entry (i);
        		capi.write(cref, entry);
        		}
        		// read one entry (default selector count of 1)
        		ArrayList<Integer> readEntries = capi.read(cref, FifoCoordinator.newSelector(), 0, null);
        		// prints 1
        		System.out.println(readEntries.get(0));
        		// read two entries with 10 ms timeout
        		readEntries = capi.read(cref, FifoCoordinator.newSelector(3), 10, null);
        		// prints 1
        		System.out.println(readEntries.get(0));
        		// prints 2
        		System.out.println(readEntries.get(1));
        		System.out.println(readEntries.get(2));
        		
        		
        		
        		
        		
        		
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
