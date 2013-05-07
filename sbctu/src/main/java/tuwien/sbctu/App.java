package tuwien.sbctu;


import org.mozartspaces.core.Capi;
import org.mozartspaces.core.MzsCoreException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" + args );
        
        
        try {
			Pizzeria p = new Pizzeria();
		} catch (MzsCoreException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
