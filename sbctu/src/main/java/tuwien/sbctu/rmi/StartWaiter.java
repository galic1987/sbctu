package tuwien.sbctu.rmi;

import tuwien.sbctu.models.Waiter;

public class StartWaiter {
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String bindingName = args[0];
        Integer port = Integer.valueOf(args[1]);
        
        for(int i = 0; i < 2; i++){
            Waiter gg = new Waiter(new Long(990+i));
            Thread t = new Thread (new WaiterRunnable(gg, port, bindingName));
            t.start();
        }
        
    }
    
}
