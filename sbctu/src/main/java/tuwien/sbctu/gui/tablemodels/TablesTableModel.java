package tuwien.sbctu.gui.tablemodels;

import tuwien.sbctu.models.Table;

public class TablesTableModel extends
		ListBasedTableModel<Table> {

	private static final long serialVersionUID = -8747034718177177762L;

	private static final String[] COLUMN_NAMES = { "ID", "Status", "Group ID"};

	public TablesTableModel() {
		super(COLUMN_NAMES);
	}

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		final Table table = data.get(rowIndex);

		switch (columnIndex) {
		case 0:
			return table.getId();
		case 1:
			return table.getTabStat();
		case 2:
			return table.getGroupID();
		default:
			return "?";
		}
	}

}