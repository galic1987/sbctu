package tuwien.sbctu.models;

public class Pizza {

	private String name;
	private double price;
	private int prepareTime;
	private PizzaStatus status;
	private Long cookId;
	
	public enum PizzaStatus {
		ORDERED, INMAKING,FINISHED;
		
		public String toString(){
			return super.toString().toLowerCase();
		}
	} 
	
	
	public Pizza(String name, double price, int time){
		this.name = name;
		this.price = price;
		this.prepareTime = time;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getPrepareTime() {
		return prepareTime;
	}
	public void setPrepareTime(int prepareTime) {
		this.prepareTime = prepareTime;
	}

	public PizzaStatus getStatus() {
		return status;
	}

	public void setStatus(PizzaStatus status) {
		this.status = status;
	}

	public Long getCookId() {
		return cookId;
	}

	public void setCookId(Long cookId) {
		this.cookId = cookId;
	}


	

}
