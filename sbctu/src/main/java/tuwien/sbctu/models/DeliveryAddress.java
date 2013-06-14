package tuwien.sbctu.models;

public class DeliveryAddress {

	private String spaceAddress;
	private String containerName;
	
	
	public String getContainerName() {
		return containerName;
	}
	public void setContainerName(String containerName) {
		this.containerName = containerName;
	}
	public String getSpaceAddress() {
		return spaceAddress;
	}
	public void setSpaceAddress(String spaceAddress) {
		this.spaceAddress = spaceAddress;
	}
	
	
	public DeliveryAddress(String spaceAddress, String containerName) {
		super();
		this.spaceAddress = spaceAddress;
		this.containerName = containerName;
	}
	
	
	
	
}
