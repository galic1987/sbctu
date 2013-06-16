package tuwien.sbctu.rmi;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import tuwien.sbctu.rmi.implement.RMIGuestGUIImpl;
import tuwien.sbctu.rmi.interfaces.IGuestGUIRMI;
import tuwien.sbctu.rmi.interfaces.IPizzeria;

public class StartRMIGuestGUI {
    private static IPizzeria iPizzeria;
    private static IGuestGUIRMI guiInterface;
    /**
     * @param args
     */
    public static void main(String[] args)
            throws InterruptedException, RemoteException {
        
        guiInterface = new RMIGuestGUIImpl();
        enterPizzeria(Integer.valueOf(args[1]), args[0]);
        
        GUIGuestsRMI  guiGuests = new GUIGuestsRMI();
        guiGuests.setGuestTableInformationInterface(guiInterface);
        guiGuests.setVisible(true);
        guiGuests.setPizzeriaInterface(iPizzeria);
        
        Thread.sleep(10000);
        
        guiGuests.activateThread();
    }
    
    /**
     *
     * @param port
     * @param bindingName
     * @return
     */
    private static void enterPizzeria(Integer port, String bindingName){
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry(port);
            
            iPizzeria =(IPizzeria) registry.lookup(bindingName);
            iPizzeria.registerGuestGUI(guiInterface);
            
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (AccessException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        
    }
}
