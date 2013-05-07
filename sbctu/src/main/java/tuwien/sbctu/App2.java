package tuwien.sbctu;

import java.util.ArrayList;

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
        		ContainerReference container = capi.createContainer();
        		// write an entry to the container
        		capi.write(container, new Entry("Hello, space!"));
        		System.out.println("Entry written");
        		// read an entry from the container
        		ArrayList<String> resultEntries = capi.read(container);
        		System.out.println("Entry read: " + resultEntries.get(0));
        		// destroy the container
        		//capi.destroyContainer(container, null);
        		// shutdown the core
        		//core.shutdown(true);
	}

}
