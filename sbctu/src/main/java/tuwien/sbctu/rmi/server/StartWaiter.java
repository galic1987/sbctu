package tuwien.sbctu.rmi.server;

import tuwien.sbctu.rmi.RunWaiter;

public class StartWaiter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Long count = new Long (Long.valueOf(args[0]));
		Integer port = Integer.valueOf(args[1]);
		
		
			
			Thread group = new Thread(new RunWaiter( count , port, args[2]));
			group.start();
	
	}

}
