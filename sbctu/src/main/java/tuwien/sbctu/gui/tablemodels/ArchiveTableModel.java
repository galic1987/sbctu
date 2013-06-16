package tuwien.sbctu.gui.tablemodels;

import tuwien.sbctu.models.Order;

public class ArchiveTableModel extends
        ListBasedTableModel<Order> {
    
    private static final long serialVersionUID = -8747034718177177762L;
    
    private static final String[] COLUMN_NAMES = { "ID", "Table ID", "Group ID",
        "Status", "Pizzas", "Took Order", "Served ord.", "Bill ord."};
    
    public ArchiveTableModel() {
        super(COLUMN_NAMES);
    }
    
    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        final Order order = data.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return order.getId();
            case 1:
                return order.getTableID();
            case 2:
                return order.getGroupID();
            case 3:
                return order.getOrderstatus();
            case 4:
                return order.pizzaList();
            case 5:
                return order.getWaiterOrderTookId();
            case 6:
                return order.getWaiterServedId();
            case 7:
                return order.getWaiterBillProcessedId();
            default:
                return "?";
        }
        
    }
}