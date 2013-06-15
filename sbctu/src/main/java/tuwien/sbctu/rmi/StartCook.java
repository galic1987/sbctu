package tuwien.sbctu.rmi;

import tuwien.sbctu.models.Cook;

public class StartCook {
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        
        String bindingName = args[0];
        Integer port = Integer.valueOf(args[1]);
        
        for(int i = 0; i < 2; i++){
            Cook cook = new Cook(new Long(222+i));
            Thread t = new Thread (new CookRunnable(cook, port, bindingName));
            t.start();
        }
       
    }
    
}
