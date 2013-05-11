package tuwien.sbctu.rmi.server;

import tuwien.sbctu.rmi.RunWaiter;

public class StartWaiter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Long id = new Long (Long.valueOf(args[0]));
		Integer port = Integer.valueOf(args[1]);
		
		Thread group = new Thread(new RunWaiter( id, port, args[2]));
		group.start();
	}

}
