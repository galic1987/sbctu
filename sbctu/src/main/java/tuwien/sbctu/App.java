package tuwien.sbctu;


import org.mozartspaces.core.Capi;
import org.mozartspaces.core.MzsCoreException;

import tuwien.sbctu.models.Order;

/**
 * Hello world!
 *
 */
public class App 
{
	private static Pizzeria[] p;

	public static void main( String[] args )
	{
		//System.out.println( "Hello World!" + args );
		
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

		
		// more than 1 pizzeria, start loadbalancing
		if(args.length>1){
			algo();
		}


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
			
			System.out.println("Loadbalancer calculating!");

			
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

				if(everythingOK) Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
	}

}
