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
		cook[0] = "11161 89 xvsm://localhost:5102";
		cook[1] = "11162 88 xvsm://localhost:5101";
		cook[2] = "11163 87 xvsm://localhost:5102";
		cook[3] = "11164 86 xvsm://localhost:5101";
 		for(int i = 0; i<cook.length;i++){
			Thread t = new Thread(new RunCookThread(cook[i].split(" ")));
			t.start();
		}
		
		// RunCookSBC.main(cook[0].split(" "));
		
		
		System.out.println("start delivery");

		String[] del = new String[4];
		del[0] = "8171 79 xvsm://localhost:5102";
		del[1] = "8172 78 xvsm://localhost:5102";
		del[2] = "8173 77 xvsm://localhost:5102";
		del[3] = "8174 76 xvsm://localhost:5102";
		 
		for(int i = 0; i<del.length;i++){
			
			Thread t = new Thread(new RunDriverThread(del[i].split(" ")));
			t.start();
		}
		
		System.out.println("start waiter");

		String [] wait = new String[4];
		wait[0] = "9151 99 xvsm://localhost:5101";
		wait[1] = "9152 98 xvsm://localhost:5101";
		wait[2] = "9153 97 xvsm://localhost:5102";
		wait[3] = "9154 96 xvsm://localhost:5102";

		for(int i = 0; i<wait.length;i++){
			Thread t = new Thread(new RunWaiterThread(wait[i].split(" ")));
			t.start();
		}
		
		

	}
	
	

	

}
