package tuwien.sbctu.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Table implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Order> orders;
	private double bill;
	private GuestGroup group;
	private boolean requestBill;
	public boolean leaveTable(){
		
		orders = null;
		bill = 0;
		group = null;
		requestBill = false;
		
		return true;
	}
	
	public boolean requestBill(){
		//TODO
		requestBill = true;
		return requestBill;
	}	

}
