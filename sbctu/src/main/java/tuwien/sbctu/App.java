package tuwien.sbctu;


import org.mozartspaces.core.Capi;
import org.mozartspaces.core.MzsCoreException;

/**
 * Hello world!
 *
 */
public class App 
{
	private static Pizzeria[] p = new Pizzeria[10];

	public static void main( String[] args )
	{
		//System.out.println( "Hello World!" + args );
		for (int i = 0; i < args.length; i++) { 
			try {
				int port = Integer.valueOf(args[i]);
				p[i] = new Pizzeria(port);

			} catch (MzsCoreException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
		// more than 1 pizzeria, start loadbalancing
		if(args.length>1){
			algo();
		}


	}

	public static void algo(){
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
				if(minLoad.calculatePizzeriaLoad() > pizz.getLoad())
					minLoad = pizz;

				if(maxLoad.calculatePizzeriaLoad() < pizz.getLoad())
					maxLoad = pizz;



			}


			// check if there is something to do , number of newDeliveries orders
			if(maxLoad.getLoad()-minLoad.getLoad() > 1.5){
				// transfer order and immediately do recalculation
				minLoad.putDeliveryOrder(maxLoad.takeOneDeliveryOrder());
				everythingOK = false;
			}else{
				// do the sleeping
				everythingOK = true;
			}



			try {
				if(everythingOK) Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
