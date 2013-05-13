package tuwien.sbctu.models;

import java.io.Serializable;

import org.mozartspaces.capi3.Queryable;


@Queryable(autoindex=true)
public class Table implements Serializable{

	@Override
	public String toString() {
		return "Table [id=" + id  + ", order=" + order + ", bill=" + bill + ", group="
				+ group + ", requestBill=" + requestBill + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Table(Long id){
		setTabStat(TableStatus.FREE);
		this.id = id;
		order = null;
	}
	
	private Long id;
	private TableStatus tabStat;
	private Long groupID;
	private Order order;
	private double bill;
	private GuestGroup group;
	private boolean requestBill;
	
	public boolean leaveTable(){
		
		setOrder(null);
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

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public enum TableStatus {
	FREE, USED, PAY;
	
	public String toString(){
		return super.toString().toLowerCase();
	}
}
}
