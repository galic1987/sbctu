package tuwien.sbctu.models;

public class Cook extends Person{

	public Cook(Long id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	
	public Order checkIfOrdersAvailable(){
		return null;
	}
	
	public boolean cookPizzasFromOrder(Order order){
		//TODO
		return false;
	}
	
	public boolean putOrderOnTheke(Order order){
		//TODO
		return true;

	}

}
