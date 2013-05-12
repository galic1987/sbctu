package tuwien.sbctu.models;

public class Cook extends Person{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum CookStatus{
		WAITING, WORKING;
	}
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
