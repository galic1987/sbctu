/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuwien.sbctu.rmi;

import tuwien.sbctu.gui.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import tuwien.sbctu.gui.tablemodels.BarTableModel;
import tuwien.sbctu.gui.tablemodels.EntryTableModel;
import tuwien.sbctu.gui.tablemodels.TablesTableModel;
import tuwien.sbctu.models.GuestDelivery;
import tuwien.sbctu.models.GuestGroup;
import tuwien.sbctu.models.GuestGroup.GroupStatus;
import tuwien.sbctu.models.Order;
import tuwien.sbctu.models.Table;
import tuwien.sbctu.rmi.interfaces.IPizzeriaGUIRMI;

/**
 *
 * @author Adnan
 */
public class GUIPizzeriaRMI extends javax.swing.JFrame {
    
    /**
     * Creates new form GUIPizzeria
     */
    public GUIPizzeriaRMI() {
        initComponents();
        
        isActive = true;
        
        guestGroupInfo = new ArrayList<>();
        guestDeliveryInfo = new ArrayList<>();
        tableInfo = new ArrayList<>();
        orderInfo = new ArrayList<>();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        entryTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablesTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        barTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel3.setText("Entry:");

        entryTable.setModel(new tuwien.sbctu.gui.tablemodels.EntryTableModel());
        jScrollPane1.setViewportView(entryTable);

        jLabel1.setText("Tables:");

        tablesTable.setModel(new tuwien.sbctu.gui.tablemodels.TablesTableModel());
        jScrollPane2.setViewportView(tablesTable);

        jLabel2.setText("Bar:");

        barTable.setModel(new tuwien.sbctu.gui.tablemodels.BarTableModel());
        jScrollPane3.setViewportView(barTable);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Actual", jPanel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 695, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 672, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Archive", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private boolean isActive;
    
    ArrayList<GuestGroup> guestGroupInfo;
    ArrayList<GuestDelivery> guestDeliveryInfo;
    ArrayList<Table> tableInfo;
    ArrayList<Order> orderInfo;
    
    private IPizzeriaGUIRMI pizzeriaInformationInterface;
    
    //initalize interace to get information for tables
    public void setPizzeriaInformationInterface(IPizzeriaGUIRMI pizzeriaGUI){
        pizzeriaInformationInterface = pizzeriaGUI;
    }
    /**
     * Thread which runs update tables
     */
    private class Updater extends Thread {
        @Override
        public void run() {
            while (isActive){
                try {
                    
                    //call method updateTables, then wait 1sec
                    updateTables();
                    Thread.sleep(1000);
                    
                } catch (InterruptedException ex) {
                    Logger.getLogger(GUIPizzeria.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    /**
     * activates the thread for the GUI
     */
    public void activateThread(){
        Updater up = new Updater();
        
        up.start();
    }
    
    /**
     *
     */
    public void updateTables(){
        
        GuestGroup ggi = pizzeriaInformationInterface.getGuestGroupInfo();
//        GuestDelivery gdi = pizzeriaInformationInterface.getGuestDeliveryInfo();
        Table ti = pizzeriaInformationInterface.getTableInfo();
        Order oi = pizzeriaInformationInterface.getOrderInfo();
        
        if(ggi != null)
            updateEntry(ggi);
        if(ti != null)
            updateTable(ti);
        if(oi != null)
            updateBar(oi);
    }

    private void updateEntry(GuestGroup ggi){
        boolean foundGG = false;
        EntryTableModel entrytm = new EntryTableModel();
        
        if(!guestGroupInfo.isEmpty()){
            for(GuestGroup gg : guestGroupInfo){
                
                if(gg.getId().equals(ggi.getId())){
                    gg.setStatus(ggi.getStatus());
                    foundGG = true;
                }
                if(gg.getStatus().equals(GroupStatus.WELCOME))
                    entrytm.add(gg);
            }
        }
        
        if (!foundGG)
            guestGroupInfo.add(ggi);
        
        entryTable.setModel(entrytm);
    }
    
    private void updateTable(Table tabi){
        boolean foundGG = false;
        
        TablesTableModel tabtm = new TablesTableModel();
        
        if(!tableInfo.isEmpty()){
            for(Table tab : tableInfo){
                
                if(tab.getId().equals(tabi.getId())){
                    tab.setTabStat(tabi.getTabStat());
                    foundGG = true;
                }
                tabtm.add(tab);
            }
        }
        
        if (!foundGG)
            tableInfo.add(tabi);
        
        tablesTable.setModel(tabtm);
    }
    
    private void updateBar(Order ordi){
    boolean foundGG = false;
        
        BarTableModel tabtm = new BarTableModel();
        
        if(!orderInfo.isEmpty()){
            for(Order o : orderInfo){
                
                if(o.getId().equals(ordi.getId())){
                    o.setOrderstatus(ordi.getOrderstatus());
                    o.setTableID(ordi.getTableID());
                    o.setWaiterBillProcessedId(ordi.getWaiterBillProcessedId());
                    o.setWaiterOrderTookId(ordi.getWaiterOrderTookId());
                    o.setWaiterServedId(ordi.getWaiterServedId());
                    o.setWaiterTableAssigmentId(ordi.getWaiterTableAssigmentId());
                    
                    foundGG = true;
                }
                tabtm.add(o);
            }
        }
        
        if (!foundGG)
            orderInfo.add(ordi);
        
        barTable.setModel(tabtm);
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUIPizzeria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUIPizzeria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUIPizzeria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUIPizzeria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUIPizzeria().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable barTable;
    private javax.swing.JTable entryTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tablesTable;
    // End of variables declaration//GEN-END:variables
}