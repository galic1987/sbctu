package tuwien.sbctu.rmi;

import tuwien.sbctu.models.Driver;

public class StartDriver {
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String bindingName = args[0];
        Integer port = Integer.valueOf(args[1]);
        
        for(int i = 0; i < 2; i++){
            Driver driver = new Driver(new Long(5500+i));
            Thread t = new Thread (new DriverRunnable(driver, port, bindingName));
            t.start();
        }
       
    }
    
}
