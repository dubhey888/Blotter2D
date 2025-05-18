/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import config.Session;
import config.dbConnector;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author milan
 */
public class ViewReports extends javax.swing.JFrame {

    /**
     * Creates new form ViewReports
     */
    public ViewReports() {
        initComponents();
        loadReportsData();
    }

    public void displayReportData() {
    try {
        dbConnector dbc = new dbConnector();

        // Query to fetch relevant columns from 'reports' table
        ResultSet rs = dbc.getData("SELECT report_id, full_name, incident_type, description, location, " +
                                   "CONCAT(date_of_incident, ' ', time_of_incident) AS datetime, status " +
                                   "FROM reports");

        // Assuming your JTable is named 'report_tbl'
        tblblotter.setModel(DbUtils.resultSetToTableModel(rs));

        rs.close();
    } catch (SQLException ex) {
        System.out.println("Errors: " + ex.getMessage());
    }
}
    
    DefaultTableModel model = new DefaultTableModel();
    
    public void tableChanged(TableModelEvent e) {
    if (e.getType() == TableModelEvent.UPDATE) {
        int row = e.getFirstRow();
        int column = e.getColumn();

        if (row == -1 || column == -1) {
            return;
        }

        updateReportDatabase(row, column); // Call your update function for 'reports'
    }

    // Set column headers for reports table
    String[] columnNames = {
        "report_id", "full_name", "incident_type", "description",
        "location", "date_of_incident", "time_of_incident", "status"
    };
    model.setColumnIdentifiers(columnNames);
    model.setRowCount(0); // Clear table

    String sql = "SELECT report_id, full_name, incident_type, description, location, date_of_incident, time_of_incident, status FROM reports";

    try (Connection connect = new dbConnector().getConnection();
         PreparedStatement pst = connect.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {

        while (rs.next()) {
            Object[] row = {
                rs.getInt("report_id"),
                rs.getString("full_name"),
                rs.getString("incident_type"),
                rs.getString("description"),
                rs.getString("location"),
                rs.getDate("date_of_incident"),
                rs.getTime("time_of_incident"),
                rs.getString("status")
            };
            model.addRow(row);
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    
private void updateReportDatabase(int row, int column) {
    try {
        int reportId = Integer.parseInt(model.getValueAt(row, 0).toString()); // Assuming ID is column 0
        String columnName = model.getColumnName(column);
        Object newValue = model.getValueAt(row, column);

        String sql = "UPDATE reports SET " + columnName + " = ? WHERE report_id = ?";
        try (Connection conn = new dbConnector().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setObject(1, newValue);
            pst.setInt(2, reportId);
            pst.executeUpdate();
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Update Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void loadReportsData() {
    DefaultTableModel model = (DefaultTableModel) tblblotter.getModel();  // Assuming 'report_tbl' is your JTable for reports
    model.setRowCount(0); // Clear the table before reloading

    // Include status in the SELECT query
    String sql = "SELECT report_id, full_name, incident_type, description, location, date_of_incident, time_of_incident, status FROM reports";

    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/blotter", "root", "");
         PreparedStatement pst = con.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {

        // Iterate through ResultSet and add rows to the table
        while (rs.next()) {
            model.addRow(new Object[] {
                rs.getInt("report_id"),
                rs.getString("full_name"),
                rs.getString("incident_type"),
                rs.getString("description"),
                rs.getString("location"),
                rs.getDate("date_of_incident"),
                rs.getTime("time_of_incident"),
                rs.getString("status")
            });
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error loading report data: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    }
}

 public void logEvent(int userId, String username, String userType) {
    dbConnector dbc = new dbConnector();
    Connection con = dbc.getConnection();
    PreparedStatement pstmt = null;
    String ut = "Active"; // Assuming the status is "Active" once logged in

    try {
        String sql = "INSERT INTO tbl_log (u_id, u_username, login_time, u_type, log_status) VALUES (?, ?, ?, ?, ?)";
        pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, userId);
        pstmt.setString(2, username);
        pstmt.setTimestamp(3, new Timestamp(new Date().getTime())); // Log the current time
        pstmt.setString(4, userType);
        pstmt.setString(5, ut);

        pstmt.executeUpdate();
        System.out.println("Login log recorded successfully.");
    } catch (SQLException e) {
        System.out.println("Error recording log: " + e.getMessage());
    } finally {
        try {
            if (pstmt != null) pstmt.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            System.out.println("Error closing resources: " + e.getMessage());
        }
    }
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblblotter = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 204, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblblotter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Full Name", "Incident Type", "Description", "Location", "date_of_incident", "time_of_incident", "Status"
            }
        ));
        jScrollPane1.setViewportView(tblblotter);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 690, 340));

        jButton1.setText("Select");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 80, -1));

        jButton3.setText("Back");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 10, -1, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("BLOTTER REPORTS");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, -1, -1));

        jButton2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton2.setText("Refresh");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 90, 20));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 720, 440));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
   Session sess = Session.getInstance();
    int selectedRow = tblblotter.getSelectedRow(); 
    int userId = sess.getUid(); // Get logged-in user ID
    String username = sess.getName(); // Make sure this exists in your Session class

    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select a report first.");
        return;
    }

    int reportId = Integer.parseInt(tblblotter.getValueAt(selectedRow, 0).toString()); 

    try {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/blotter", "root", "");
        String sql = "UPDATE reports SET status = 'Resolved' WHERE report_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, reportId);
        int updated = stmt.executeUpdate();

        if (updated > 0) {
            JOptionPane.showMessageDialog(this, "Report marked as resolved.");
            tblblotter.setValueAt("Resolved", selectedRow, 7); // assuming 'Status' is at column index 7
            logEvent(userId, username, "Resolved a blotter report (ID: " + reportId + ")");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update report status.");
        }

        conn.close();
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }      // TODO add your handling code here:
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
         loadReportsData();     // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
admindashboard adminDashboard = new admindashboard();
    adminDashboard.setVisible(true); // Show the AdminDashboard
    this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3MouseClicked

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
            java.util.logging.Logger.getLogger(ViewReports.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewReports.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewReports.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewReports.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewReports().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblblotter;
    // End of variables declaration//GEN-END:variables
}
