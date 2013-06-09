package tuwien.sbctu;

import java.util.logging.Level;
import java.util.logging.Logger;
import tuwien.sbctu.gui.GUIGuests;
import tuwien.sbctu.gui.GuestGUIImpl;
import tuwien.sbctu.gui.IGuestGUI;
import tuwien.sbctu.models.GuestGroup;
import tuwien.sbctu.models.GuestGroup.GroupStatus;

public class TestGuest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws InterruptedException {
            IGuestGUI guiInterface = new GuestGUIImpl();
            
            GUIGuests  guiGuests = new GUIGuests();
            guiGuests.setGuestTableInformationInterface(guiInterface);
            guiGuests.setVisible(true);
            
            Thread.sleep(10000);
            
            guiGuests.activateThread();
           
             GuestGroup tgg = new GuestGroup(new Long(100));
            tgg.setStatus(GroupStatus.EATING);
            guiInterface.setGroupInfo(tgg);
            System.out.println("gg 100: update status to eating");
             
                    Thread.sleep(6000);
              
            tgg.setStatus(GroupStatus.FINISHED);
            guiInterface.setGroupInfo(tgg);
            System.out.println("gg 100: update status to finished");
                    
                    Thread.sleep(3000);
                    
                   GuestGroup tgg2 = new GuestGroup(new Long(101));
                   
                   guiInterface.setGroupInfo(tgg2);
                   
                   Thread.sleep(50000);
                   tgg2.setStatus(GroupStatus.SITTING);
                   guiInterface.setGroupInfo(tgg2);
            
            
            
        }
       
}
