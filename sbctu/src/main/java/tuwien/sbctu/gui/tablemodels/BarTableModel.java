package tuwien.sbctu.gui.tablemodels;

import tuwien.sbctu.models.Order;

public class BarTableModel extends
		ListBasedTableModel<Order> {

	private static final long serialVersionUID = -8747034718177177762L;

	private static final String[] COLUMN_NAMES = { "ID", "Pizzas", "Table ID", "Group ID",
			"Order Status", "Job, WaiterID"};

	public BarTableModel() {
		super(COLUMN_NAMES);
	}

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		final Order order = data.get(rowIndex);

		switch (columnIndex) {
		case 0:
			return order.getId();
		case 1:
			return order.pizzaList();
		case 2:
			return order.getTableID();
		case 3: 
			return order.getGroupID();
                case 4: 
			return order.getOrderstatus();
                case 5: 
			return "Taken by: "+order.getWaiterOrderTookId()+", served by: "+order.getWaiterServedId()+", bill by: "+order.getWaiterBillProcessedId();
		default:
			return "?";
		}
	}

}