package tuwien.sbctu.models;

import java.io.Serializable;
import java.util.ArrayList;

public class GuestGroup extends Person{
public GuestGroup(Long id) {
		super(id);
		// TODO Auto-generated constructor stub
	}


/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int groupSize;

	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean enterPizzeria(){
		//TODO
		return true;
	}
	
	public ArrayList<Pizza> placeOrder(){
		//TODO
		return null;
	}
	
	public boolean eatPizzaNotDishes(){
		//TODO
		// wait random time between 3 and 5 sec;
		int time = 3000 + (int)(Math.random() * ((5000 - 3000) + 1));
		
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			// cannot sleep mfk;
			e.printStackTrace();
		
		}
		return true;
	}
	
	public boolean finishedEating(){
		return true;
	}
	
	
	public boolean leaveTheTableAndThePizzeriaAndGoWOderPFeffer(){
		//TODO
		return true;
	}
	
}
