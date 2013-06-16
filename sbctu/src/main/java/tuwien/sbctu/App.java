package tuwien.sbctu;


import org.mozartspaces.core.Capi;
import org.mozartspaces.core.MzsCoreException;

import tuwien.sbctu.gui.GUIPizzeria;
import tuwien.sbctu.gui.IPizzeriaGUI;
import tuwien.sbctu.gui.PizzeriaGUIImpl;
import tuwien.sbctu.models.Order;

/**
 * Hello world!
 *
 */
public class App 
{



	private static IPizzeriaGUI guiInterface = null;
	private static Pizzeria[] p; 
	private static GUIPizzeria  gui = null;
	private static int transferedNr = 0;
	private static boolean active;

	public static void main( String[] args )
	{
		//System.out.println( "Hello World!" + args );

active = false;

		p = new Pizzeria[args.length];
		for (int i = 0; i < args.length; i++) { 
			try {
				int port = Integer.valueOf(args[i]);
				p[i] = new Pizzeria(port);

			} catch (MzsCoreException | InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}



//		guiInterface = new PizzeriaGUIImpl();
//
//	   gui = new GUIPizzeria();
//		gui.setPizzeriaInformationInterface(guiInterface);
//		gui.setVisible(true);

		// more than 1 pizzeria, start loadbalancing
		if(args.length>1){
			algo();
		}



		//  pizz.subscribeToSBC();
		// pizz.updateFields();


	}

	public static void algo(){
		System.out.println("Loadbalancer started!");
		boolean everythingOK = true;
		while(true){

			Pizzeria maxLoad = null;
			Pizzeria minLoad = null;

			for (int i = 0; i < p.length; i++) { 
				Pizzeria pizz = p[i];
				pizz.calculatePizzeriaLoad();

				// init
				if(minLoad == null) minLoad = pizz;
				if(maxLoad == null) maxLoad = pizz;

				// differences
				if(minLoad.getLoad() > pizz.getLoad())
					minLoad = pizz;

				if(maxLoad.getLoad() < pizz.getLoad())
					maxLoad = pizz;



			}



			int difference = (int) ((int) maxLoad.getLoad()-minLoad.getLoad());

			System.out.println("Loadbalancer calculating! maxmin " + maxLoad.getLoad() + " >= " + minLoad.getLoad());


			// check if there is something to do , number of newDeliveries orders
			if(difference >= 2){
				// transfer order and immediately do recalculation
				int howManyOrders = difference/2;

				System.out.println("Balancing "+howManyOrders+ " orders from pizzeria " +maxLoad.getAddress() +" to pizzeria " + minLoad.getAddress() );

				for (int i = 0; i < howManyOrders; i++) {  
					Order o = maxLoad.takeOneDeliveryOrder();

					if(o != null){
						// try to put order on min load
						if(minLoad.putDeliveryOrder(o)){
							// it is ok
							transferedNr +=  1;

						}else{
							// custom rollback
							maxLoad.putDeliveryOrder(o);
						}
					}
				}

				everythingOK = false;
			}else{
				// do the sleeping
				everythingOK = true;
			}



			try {	
				System.out.println("Transfered # " + transferedNr);
				updategui();

				if(everythingOK) Thread.sleep(3000);


			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
	}


	public static void updategui(){
		int deliveryGood = 0;
		int deliveryFailed = 0;
		int deliveryCooked = 0;
		int deliveryOpen = 0;

		for (int i = 0; i < p.length; i++) { 
			Pizzeria pizz = p[i];
			
			try {	
				
			
				deliveryGood += pizz.goodDeliveries();
				deliveryFailed += pizz.failDeliveries();
				
				deliveryCooked += pizz.finishedDeliveries();
				deliveryOpen += pizz.openDeliveries();
				
				//System.out.println(pizz.getArchive().size());
//			for(Order o :pizz.getArchive() ) guiInterface.setArchiveInfo(o);
			//for(Order o :pizz.getArchive() ) System.out.println(o.toString());

//			if(!active){
//				gui.activateThread();
//			active = true;
//			}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			


		}
		
		System.out.println("Good deliveries " + deliveryGood);
		System.out.println("Bad deliveries " + deliveryFailed);
		System.out.println("Cooked deliveries " + deliveryCooked);
		System.out.println("Open deliveries " + deliveryOpen);
		
	}

}
