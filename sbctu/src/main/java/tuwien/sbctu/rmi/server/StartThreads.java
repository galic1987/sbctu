package tuwien.sbctu.rmi.server;

import tuwien.sbctu.rmi.RunGuestGroup;
import tuwien.sbctu.rmi.RunWaiter;

public class StartThreads {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread group = new Thread(new RunGuestGroup(new Long(1), 10879, "entry"));
		group.start();
	}

}
