package tuwien.sbctu.models;

import org.mozartspaces.capi3.Queryable;

@Queryable(autoindex = true)
public class GuestDelivery extends Person {

    public GuestDelivery(Long id) {
        super(id);
        gStatus = DeliveryStatus.DELIVERY;
    }
    private static final long serialVersionUID = 1L;
    private int groupSize;
    private DeliveryStatus gStatus;
    private String currentStatus;
    private Order order;

    @Override
    public Long getId() {
        return super.getId();
    }

    public void setStatus(DeliveryStatus status) {
        this.gStatus = status;
        this.currentStatus = status.toString();
    }

    public DeliveryStatus getStatus() {
        return gStatus;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public enum DeliveryStatus {

        DELIVERY, PROCESSED, PAYED, TRANSFERRED;
    }
}
