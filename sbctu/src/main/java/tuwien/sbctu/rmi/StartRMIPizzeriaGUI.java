/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuwien.sbctu.rmi;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import tuwien.sbctu.rmi.implement.RMIPizzeriaGUIImpl;
import tuwien.sbctu.rmi.interfaces.IPizzeria;
import tuwien.sbctu.rmi.interfaces.IPizzeriaGUIRMI;

/**
 *
 * @author Adnan
 */
public class StartRMIPizzeriaGUI {
    private static IPizzeria iPizzeria;
    private static IPizzeriaGUIRMI guiInterface;
    
    /**
     * @param args
     */
    public static void main(String[] args)
            throws InterruptedException, RemoteException {
        
        Integer port = Integer.valueOf(args[1]);
        String bindingName = args[0];
        
        guiInterface = new RMIPizzeriaGUIImpl();
        enterPizzeria(port, bindingName);
        
        GUIPizzeriaRMI  gui = new GUIPizzeriaRMI();
        gui.setPizzeriaInformationInterface(guiInterface);
        gui.setVisible(true);
        
        gui.activateThread();
    }
    
    private static void enterPizzeria(Integer port, String bindingName){
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry(port);
            
            iPizzeria =(IPizzeria) registry.lookup(bindingName);
            System.out.println("OK ---");
            iPizzeria.registerPizzeriaGUI(guiInterface);
            System.out.println("OK ---");
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (AccessException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        
    }
}
