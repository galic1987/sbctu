package tuwien.sbctu.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Pizza> pizzaList;
	private OrderStatus orderstatus;
	private Long waiterTableAssigmentId;
	private Long waiterOrderTookId;
	private Long waiterServedId;
	private Long waiterBillProcessedId;
	
	
	public enum OrderStatus {
		ORDERED, SERVING,PAID;
		
		public String toString(){
			return super.toString().toLowerCase();
		}
	} 
	
	
	
	
	
	public Long getWaiterTableAssigmentId() {
		return waiterTableAssigmentId;
	}

	public void setWaiterTableAssigmentId(Long waiterTableAssigmentId) {
		this.waiterTableAssigmentId = waiterTableAssigmentId;
	}

	public Long getWaiterOrderTookId() {
		return waiterOrderTookId;
	}

	public void setWaiterOrderTookId(Long waiterOrderTookId) {
		this.waiterOrderTookId = waiterOrderTookId;
	}

	public Long getWaiterServedId() {
		return waiterServedId;
	}

	public void setWaiterServedId(Long waiterServedId) {
		this.waiterServedId = waiterServedId;
	}

	public Long getWaiterBillProcessedId() {
		return waiterBillProcessedId;
	}

	public void setWaiterBillProcessedId(Long waiterBillProcessedId) {
		this.waiterBillProcessedId = waiterBillProcessedId;
	}


	
	public boolean addPizzaToOrder(Pizza pizza){
		if(pizzaList == null)
			pizzaList = new ArrayList<Pizza>();

		pizzaList.add(pizza);
		return true;
	}

	public ArrayList<Pizza> getPizzaList() {
		return pizzaList;
	}

	public OrderStatus getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(OrderStatus orderstatus) {
		this.orderstatus = orderstatus;
	}
	
}
