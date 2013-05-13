package tuwien.sbctu.models;

import tuwien.sbctu.models.Order.OrderStatus;

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
	
	public boolean cookPizzasFromOrder(Order o) throws InterruptedException{
		//TODO
		
		for (Pizza p : o.getPizzaList()) {
			System.out.println("Cooking the pizza for sec " +p.getPrepareTime());
			Thread.sleep(p.getPrepareTime()*1000);
		}
      
      	o.setOrderstatus(OrderStatus.COOKED);
		return true;
	}
	
	public boolean putOrderOnTheke(Order order){
		//TODO
		return true;

	}

}
