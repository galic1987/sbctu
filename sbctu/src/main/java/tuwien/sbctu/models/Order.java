package tuwien.sbctu.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Pizza> pizzaList;
	
	public boolean addPizzaToOrder(Pizza pizza){
		if(pizzaList == null)
			pizzaList = new ArrayList<Pizza>();

		pizzaList.add(pizza);
		return true;
	}

	public ArrayList<Pizza> getPizzaList() {
		return pizzaList;
	}
	
}
