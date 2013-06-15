package tuwien.sbctu.models;

public class Waiter extends Person{
	
//	public enum WaiterStatus {
//		IDLE, SERVING,;
//		
//		public String toString(){
//			return super.toString().toLowerCase();
//		}
//	}
	private WaiterStatus waiterStatus;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Waiter(Long id) {
		super(id);
		// TODO Auto-generated constructor stub
		waiterStatus = WaiterStatus.WELCOME;
	}
	
	public Long getId() {
		// TODO Auto-generated method stub
		return super.getId();
	}
	
	public Table areFreeTablesAvailable(){
		// todo logic call interface method
		
		return null;
	}
	
	public GuestGroup areGroupsWaiting(){
		//TODO
		return null;
	}
	
	public Table isBillRequestedFromTable(){
		//TODO
		return null;
	}
	
	public Order areFinishedOrdersWaitingToBeServed(){
		//TODO
		return null;
	}
	
	/*
	 * additional functions 
	 */
	
	public boolean bringGroupToTable(GuestGroup group, Table table){
		//TODO
		return true;
	}
	
	public boolean takeOrderFromTable(Table table){
		//TODO
		return true;
	}
	
	public boolean bringOrderToTable(Table table, Order order){
		//TODO
		return true;
	}
	
	public boolean doBillingForTable(Table table){
		table.leaveTable();
		return true;
	}

	public synchronized WaiterStatus getWaiterStatus() {
		return waiterStatus;
	}

	public void setWaiterStatus(WaiterStatus waiterStatus) {
		this.waiterStatus = waiterStatus;
	}
	
	public enum WaiterStatus {
		WELCOME, WAITING, WORKING,
                BILLING, CALL, SERVING, SITDOWN, GETORDER;
	}

}
