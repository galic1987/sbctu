package tuwien.sbctu.gui.tablemodels;

import tuwien.sbctu.models.GuestDelivery;

public class DeliveryTableModel extends
		ListBasedTableModel<GuestDelivery> {

	private static final long serialVersionUID = -8747034718177177762L;

	private static final String[] COLUMN_NAMES = { "ID", "Order",
			"Status"};

	public DeliveryTableModel() {
		super(COLUMN_NAMES);
	}

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		final GuestDelivery group = data.get(rowIndex);

		switch (columnIndex) {
		case 0:
			return group.getId();
//		case 1:
//			return group.getGroupSize();
		case 1:
			return group.getOrder().pizzaList();
                case 2: 
			return group.getStatus();
		default:
			return "?";
		}
	}

}