package tuwien.sbctu.gui.tablemodels;

import tuwien.sbctu.models.GuestGroup;

public class GroupsTableModel extends
		ListBasedTableModel<GuestGroup> {

	private static final long serialVersionUID = -8747034718177177762L;

	private static final String[] COLUMN_NAMES = { "ID", "Size", "Order",
			"Status"};

	public GroupsTableModel() {
		super(COLUMN_NAMES);
	}

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		final GuestGroup group = data.get(rowIndex);

		switch (columnIndex) {
		case 0:
			return group.getId();
		case 1:
			return group.getGroupSize();
		case 2:
			return group.getOrder().pizzaList();
                case 3: 
			return group.getStatus();
		default:
			return "?";
		}
	}

}