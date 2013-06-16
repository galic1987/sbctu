package tuwien.sbctu.rmi;

import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Logger;

import tuwien.sbctu.rmi.implement.PizzeriaImpl;
import tuwien.sbctu.rmi.implement.RMIPizzeriaGUIImpl;
import tuwien.sbctu.rmi.interfaces.IPizzeria;
import tuwien.sbctu.rmi.interfaces.IPizzeriaGUIRMI;

public class RMIPizzeria {
    
    private static final Logger log = Logger.getLogger("RMIPizzeria");
    private static IPizzeria iPizzeria;
    
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
        
        IPizzeriaGUIRMI guiInterface = new RMIPizzeriaGUIImpl();
        
        GUIPizzeriaRMI  gui = new GUIPizzeriaRMI();
        gui.setPizzeriaInformationInterface(guiInterface);
        gui.setVisible(true);
        gui.activateThread();
        
        while(true){
            
        }
    }
    
    private static void startRMI(String host, Integer RMIPort,
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
        
        iPizzeria = entryInterface;
        
    }
    
//    private static void enterPizzeria(Integer port, String bindingName){
//        Registry registry = null;
//        try {
//            registry = LocateRegistry.getRegistry(port);
//            
//            iPizzeria =(IPizzeria) registry.lookup(bindingName);
//            iPizzeria.registerGuestGUI(guiInterface);
//            
//        } catch (NotBoundException e) {
//            e.printStackTrace();
//        } catch (AccessException e) {
//            e.printStackTrace();
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//        
//    }
    
}
