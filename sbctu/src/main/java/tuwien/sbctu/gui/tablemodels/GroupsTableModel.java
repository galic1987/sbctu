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
			return "ID";
		case 1:
			return "hello";
		case 2:
			return "u";
		case 3: 
			return "ok";
		default:
			return "?";
		}
	}

}