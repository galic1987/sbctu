package tuwien.sbctu.gui.tablemodels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public abstract class ListBasedTableModel<T> extends AbstractTableModel {
	private static final long serialVersionUID = 7844007477496232519L;

	protected final List<T> data = Collections
			.synchronizedList(new ArrayList<T>());

	private final String[] columnNames;

	public ListBasedTableModel(final String[] columnNames) {
		this.columnNames = columnNames;
	}

	public void add(final T element) {
		synchronized (data) {
			// if (!data.contains(element)) {
			data.add(element);
			// }
			fireTableDataChanged();
		}
	}

	public void remove(final T element) {
		synchronized (data) {
			data.remove(element);
			fireTableDataChanged();
		}
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(final int column) {
		return columnNames[column];
	}

	@Override
	public int getRowCount() {
		synchronized (data) {
			return data.size();
		}
	}
}
