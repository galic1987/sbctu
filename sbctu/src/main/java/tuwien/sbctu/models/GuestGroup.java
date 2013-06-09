package tuwien.sbctu.models;

import java.util.ArrayList;

import org.mozartspaces.capi3.Queryable;

@Queryable(autoindex=true)
public class GuestGroup extends Person{

	
	
	public GuestGroup(Long id) {
		super(id);
		// TODO Auto-generated constructor stub
		gStatus = GroupStatus.WELCOME;
	}

	private static final long serialVersionUID = 1L;
	
	private int groupSize;
	private GroupStatus gStatus;
	private String currentStatus;
        private Order order;

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
		//System.out.println(status.toString());
		this.currentStatus = status.toString();
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

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
        
        public Order getOrder() {
            return order;
        }
        
        public void setOrder(Order order) {
            this.order = order;
        }

	public enum GroupStatus {

		WELCOME, ENTERED, SITTING, ORDERED, ORDERONBAR, EATING, FINISHED, BILL
	}
}
