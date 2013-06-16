package tuwien.sbctu;
 

import tuwien.sbctu.runtime.*; 

public class SimulationComplete {

	/**
	 * @param args
	 * 
	 * git://github.com/galic1987/sbctu.git

	 * 
	 * 
	 * 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//tuwien.sbctu.Simulation1.main("".split(" "));
		//tuwien.sbctu.Simulation2.main("".split(" "));

		
		System.out.println("start cook");
		String[] cook = new String[4];
		cook[0] = "6161 89 xvsm://localhost:5100";
		cook[1] = "6162 88 xvsm://localhost:5101";
		cook[2] = "6163 87 xvsm://localhost:5100";
		cook[3] = "6164 86 xvsm://localhost:5101";
		
		for(int i = 0; i<cook.length;i++){
			Thread t = new Thread(new RunCookThread(cook[i].split(" ")));
			t.start();
		}
		
		// RunCookSBC.main(cook[0].split(" "));
		
		
		System.out.println("start delivery");

		String[] del = new String[4];
		del[0] = "6171 79 xvsm://localhost:5100";
		del[1] = "6172 78 xvsm://localhost:5101";
		del[2] = "6173 77 xvsm://localhost:5100";
		del[3] = "6174 76 xvsm://localhost:5101";
		
		for(int i = 0; i<del.length;i++){
			Thread t = new Thread(new RunDriverThread(del[i].split(" ")));
			t.start();
		}
		
		System.out.println("start waiter");

		String [] wait = new String[4];
		wait[0] = "6151 99 xvsm://localhost:5100";
		wait[1] = "6152 98 xvsm://localhost:5101";
		wait[2] = "6153 97 xvsm://localhost:5100";
		wait[3] = "6154 96 xvsm://localhost:5101";

		for(int i = 0; i<wait.length;i++){
			Thread t = new Thread(new RunWaiterThread(wait[i].split(" ")));
			t.start();
		}
		
		

	}
	
	
	public static class RunCookThread implements Runnable {
		public String [] argss;
		public RunCookThread(String [] arg){
			argss = arg;
		}
		
	    public void run() {
			  RunCookSBC.main(argss);	
	    }
	}
	
	public static class RunWaiterThread implements Runnable {
		public String [] argss;
		public RunWaiterThread(String [] arg){
			argss = arg;
		}
	    public void run() {
				  RunWaiterSBC.main(argss);	
	    }
	}
	
	
	public static class RunDriverThread implements Runnable {
		public String [] argss;
		public RunDriverThread(String [] arg){
			argss = arg;
		}
	    public void run() {
				  RunDriverSBC.main(argss);	
	    }
	}
	
	

}
