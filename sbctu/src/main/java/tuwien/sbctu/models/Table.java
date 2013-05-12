package tuwien.sbctu.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Table implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Table(Long id){
		setTabStat(TableStatus.FREE);
		this.id = id;
		orders = new ArrayList<Order>();
	}
	
	private Long id;
	private TableStatus tabStat;
	private Long groupID;
	private ArrayList<Order> orders;
	private double bill;
	private GuestGroup group;
	private boolean requestBill;
	
	public boolean leaveTable(){
		
		setOrders(null);
		setBill(0);
		setGroup(null);
		requestBill = false;
		
		
		return true;
	}
	
	public boolean requestBill(){
		//TODO
		requestBill = true;
		return requestBill;
	}	

	public TableStatus getTabStat() {
		return tabStat;
		
	}

	public void setTabStat(TableStatus tabStat) {
		this.tabStat = tabStat;
	}

	public ArrayList<Order> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}
	
	public void addOrders(Order orders) {
		this.orders.add(orders);
	}

	public Long getGroupID() {
		return groupID;
	}

	public void setGroupID(Long groupID) {
		this.groupID = groupID;
	}

	public double getBill() {
		return bill;
	}

	public void setBill(double bill) {
		this.bill = bill;
	}

	public GuestGroup getGroup() {
		return group;
	}

	public void setGroup(GuestGroup group) {
		this.group = group;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void reset(){
		
		tabStat = TableStatus.FREE;
		groupID = null;
		orders = new ArrayList<Order>();
		bill = 0.00;
		group = null;
		requestBill = false;
		
	}

	public enum TableStatus {
	FREE, USED, PAY;
	
	public String toString(){
		return super.toString().toLowerCase();
	}
}
}
