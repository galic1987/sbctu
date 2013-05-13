package tuwien.sbctu.models;

import java.io.Serializable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LoggingRecorder implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Queue<String> guestInfo = new ConcurrentLinkedQueue<String>();
	Queue<String> waiterInfo = new ConcurrentLinkedQueue<String>();
	Queue<String> cookInfo = new ConcurrentLinkedQueue<String>();
	Queue<String> orderInfo = new ConcurrentLinkedQueue<String>();
	Queue<String> pizzaInfo = new ConcurrentLinkedQueue<String>();
	Queue<String> billInfo = new ConcurrentLinkedQueue<String>();
	Queue<String> tableInfo = new ConcurrentLinkedQueue<String>();
	
	public LoggingRecorder (){
		insertGuestInfo("GUEST1, 9");
		insertGuestInfo("GUEST2");
		insertGuestInfo("GUEST3");
		
		insertWaiterInfo("WAITER1");
		insertWaiterInfo("WAITER2");
		insertWaiterInfo("WAITER3");
		
		insertCookInfo("COOK1");
		insertCookInfo("COOK2");
		insertCookInfo("COOK3");
		
		insertOrderInfo("ORDER1");
		insertOrderInfo("ORDER2");
		insertOrderInfo("ORDER3");
		
		insertPizzaInfo("PIZZA1");
		insertPizzaInfo("PIZZA2");
		insertPizzaInfo("PIZZA3");
		
		insertBillInfo("BILL1");
		insertBillInfo("BILL2");
		insertBillInfo("BILL3");
		
		insertTableInfo("Table1");
		insertTableInfo("Table2");
		insertTableInfo("Table3");

	}
	
	public void insertGuestInfo(String info){
		guestInfo.add(info);
	}
	public void insertWaiterInfo(String info){
		waiterInfo.add(info);
	}
	public void insertCookInfo(String info){
		cookInfo.add(info);
	}
	public void insertOrderInfo(String info){
		orderInfo.add(info);
	}
	public void insertPizzaInfo(String info){
		pizzaInfo.add(info);
	}
	public void insertBillInfo(String info){
		billInfo.add(info);
	}
	public void insertTableInfo(String info){
		tableInfo.add(info);
	}
	
	public String getGuestInfo(){
		return guestInfo.poll();
	}
	public String getWaiterInfo(){
		return waiterInfo.poll();
	}
	public String getCookInfo(){
		return cookInfo.poll();
	}
	public String getOrderInfo(){
		return orderInfo.poll();
	}
	public String getPizzaInfo(){
		return pizzaInfo.poll();
	}
	public String getBillInfo(){
		return billInfo.poll();
	}
	public String getTableInfo(){
		return tableInfo.poll();
	}
	
}
