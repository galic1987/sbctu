package tuwien.sbctu.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import org.mozartspaces.capi3.Queryable;

@Queryable(autoindex=true)
public class Order implements Serializable{

	@Override
	public String toString() {
		return "Order [id=" + id + ", pizzaList=" + pizzaList
				+ ", orderstatus=" + orderstatus + ", status=" + status + "]";
	}
        
        public String pizzaList(){
            String result= "";
            
            Iterator<Pizza> it = pizzaList.iterator();
            while(it.hasNext()){
                Pizza pz = it.next();
                result += pz.getName() + " \n";
            }
//            System.out.println(result);
            return result;
        }

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long groupID;
	private Long tableID;
	
	private ArrayList<Pizza> pizzaList;
	public void setPizzaList(ArrayList<Pizza> pizzaList) {
		this.pizzaList = pizzaList;
	}

	private OrderStatus orderstatus;
	private Long waiterTableAssigmentId;
	private Long waiterOrderTookId;
	private Long waiterServedId;
	private Long waiterBillProcessedId;
	private Long driverId;

	
	private DeliveryAddress deliveryAddress;
	
	private String status;
	
	
	public Order(Long id) {
		super();
		this.id = id;
	}
	
	public Order() {
		super();
	}

	public enum OrderStatus {
		ORDERED, PROCESSING, COOKED, SERVING, PAID, DELIVERYNEW, DELIVERYTRANSFERRED, DELIVERYCOOKING, DELIVERYFINISHED, DELIVERYCOOKED, DELIVERYFAILED,
                NEW, TRANSFERRED;
                
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
	
	
	public double writeBill(){
		double price = 0.0;
		for(Pizza p : pizzaList){
			price += p.getPrice();
		}
		
	return price;
	}

	public ArrayList<Pizza> getPizzaList() {
		return pizzaList;
	}

	public OrderStatus getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(OrderStatus orderstatus) {
		this.status = orderstatus.toString().toUpperCase();
		this.orderstatus = orderstatus;
	}

	public Long getGroupID() {
		return groupID;
	}

	public void setGroupID(Long groupID) {
		this.groupID = groupID;
	}

	public Long getTableID() {
		return tableID;
	}

	public void setTableID(Long tableID) {
		this.tableID = tableID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public DeliveryAddress getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public Long getDriverId() {
		return driverId;
	}

	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}
	
}
