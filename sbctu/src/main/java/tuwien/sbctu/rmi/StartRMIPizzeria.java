package tuwien.sbctu.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.logging.Logger;

import tuwien.sbctu.rmi.implement.PizzeriaImpl;
import tuwien.sbctu.rmi.interfaces.IPizzeria;

public class StartRMIPizzeria {
    
    private static final Logger log = Logger.getLogger("RMIPizzeria");
    
    
    /**
     * @param args
     */
    public static void main(String[] args) throws RemoteException {
        
        String host = args[0];
        int RMIPort = Integer.valueOf(args[1]);
        String bindingName = args[2];
        int tables = Integer.valueOf(args[3]);
        
        try {
            startRMI(host, RMIPort, bindingName, tables);
        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
        
        System.out.println(String.format("Server started on \n\thost: %s \n\tlistening on port: %s \n\tbindingName: %s", host, RMIPort, bindingName ));
        
        
    }
    
    private static IPizzeria startRMI(String host, Integer RMIPort,
            String bindingName, Integer tables)
            throws RemoteException, MalformedURLException{
        
        IPizzeria entryInterface = null;
        entryInterface = new PizzeriaImpl(tables, bindingName);
        LocateRegistry.createRegistry(RMIPort);
        Naming.rebind("rmi://"
                +host + ":"
                +RMIPort+  "/"
                +bindingName,
                entryInterface);
        
        return entryInterface;
        
    }
    
}
