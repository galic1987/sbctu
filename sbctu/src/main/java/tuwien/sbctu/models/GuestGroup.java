package tuwien.sbctu.models;

import java.util.ArrayList;

public class GuestGroup extends Person{

	
	
	public GuestGroup(Long id) {
		super(id);
		// TODO Auto-generated constructor stub
		gStatus = GroupStatus.WELCOME;
	}

	private static final long serialVersionUID = 1L;
	
	private int groupSize;
	private GroupStatus gStatus;

	public Long getId() {
		// TODO Auto-generated method stub
		return super.getId();
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
	
	public void setStatus(GroupStatus status){
		this.gStatus = status;
	}
	public GroupStatus getStatus(){
		return gStatus;
	}
	
	public int getGroupSize() {
		return groupSize;
	}

	public void setGroupSize(int groupSize) {
		this.groupSize = groupSize;
	}

	public enum GroupStatus {
<<<<<<< HEAD
		WELCOME, ENTERED, SITTING, ORDERED, EATING, BRINGMEBILL, FINISHED
=======
		WELCOME, ENTERED, SITTING, ORDERED, EATING, FINISHED, BILL
>>>>>>> 46af9cfced2282833e54e13e941eb813840b4c98
	}
}
