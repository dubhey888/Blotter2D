/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import admin.*;
import blottergui.LoginForm;
import config.Session;
import config.dbConnector;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Bentastic
 */
public class usersDashboard extends javax.swing.JFrame {

    /**
     * Creates new form adminDashboard
     */
    public usersDashboard() {
        initComponents();
        loadUserProfile();
        loadReportsData();
    }

    Color navcolor = new Color(255,255,255);
    Color hovercolor = new Color(153,204,255);
    
     private void loadUserProfile() {
    dbConnector dbc = new dbConnector();
    Session sess = Session.getInstance();

    String query = "SELECT u_image FROM tbl_users WHERE u_id = ?";

    try (Connection conn = dbc.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        pstmt.setInt(1, sess.getUid());
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            String imagePath = rs.getString("u_image");

            if (imagePath != null && !imagePath.isEmpty()) {
                ImageIcon icon = new ImageIcon(imagePath);
                u_image.setIcon(icon);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Log the error
        JOptionPane.showMessageDialog(this, "Error loading profile image: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}   
     
       private void logoutUser(String username) {
    dbConnector connector = new dbConnector();
    try (Connection con = connector.getConnection()) {

        String updateQuery = "UPDATE tbl_log SET log_status = 'Inactive', logout_time = NOW() " +
                             "WHERE LOWER(u_username) = LOWER(?) AND log_status = 'Active'";

        try (PreparedStatement stmt = con.prepareStatement(updateQuery)) {
            System.out.println("Logging out user: " + username); // Debug
            stmt.setString(1, username);
            int updatedRows = stmt.executeUpdate();

            if (updatedRows > 0) {
                JOptionPane.showMessageDialog(null, "User " + username + " has logged out successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "No active session found for " + username);
            }
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error logging out: " + ex.getMessage());
    }
}
   public void displayReportData() {
    try {
        dbConnector dbc = new dbConnector();
        Session sess = Session.getInstance();
        int currentUserId = sess.getUid();

        String query = "SELECT report_id, full_name, incident_type, description, location, " +
                       "CONCAT(date_of_incident, ' ', time_of_incident) AS datetime, status " +
                       "FROM reports WHERE u_id = ?";

        PreparedStatement pst = dbc.getConnection().prepareStatement(query);
        pst.setInt(1, currentUserId);

        ResultSet rs = pst.executeQuery();
        tblblotter.setModel(DbUtils.resultSetToTableModel(rs));

        rs.close();
        pst.close();
    } catch (SQLException ex) {
        System.out.println("Errors: " + ex.getMessage());
    }
}




  

    
 public void tableChanged(TableModelEvent e) {
    if (e.getType() == TableModelEvent.UPDATE) {
        int row = e.getFirstRow();
        int column = e.getColumn();

        if (row == -1 || column == -1) {
            return;
        }

        updateReportDatabase(row, column);
    }

    DefaultTableModel model = (DefaultTableModel) tblblotter.getModel();
    model.setRowCount(0);

    String sql = "SELECT report_id, full_name, incident_type, description, location, date_of_incident, time_of_incident, status FROM reports WHERE u_id = ?";

    try (Connection connect = new dbConnector().getConnection();
         PreparedStatement pst = connect.prepareStatement(sql)) {

        Session sess = Session.getInstance();
        int userId = sess.getUid();

        pst.setInt(1, userId);

        try (ResultSet rs = pst.executeQuery()) {
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
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}



    
private void updateReportDatabase(int row, int column) {
    DefaultTableModel model = (DefaultTableModel) tblblotter.getModel(); 
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
    DefaultTableModel model = (DefaultTableModel) tblblotter.getModel();
    model.setRowCount(0);

    String sql = "SELECT report_id, full_name, incident_type, description, location, date_of_incident, time_of_incident, status FROM reports WHERE u_id = ?";

    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/blotter", "root", "");
         PreparedStatement pst = con.prepareStatement(sql)) {

        Session sess = Session.getInstance();
        int userId = sess.getUid();

        pst.setInt(1, userId);

        try (ResultSet rs = pst.executeQuery()) {
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
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error loading report data: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    }
}




// Example method to get logged-in user's full name

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        acc_name = new javax.swing.JLabel();
        cars1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        u_name = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        u_image = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblblotter = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 204, 204));
        jLabel1.setText("USERS DASHBOARD");

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 204, 204));
        jLabel2.setText("LOGOUT");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 365, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(26, 26, 26))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addContainerGap(20, Short.MAX_VALUE))
        );

<<<<<<< HEAD
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel2.setBackground(new java.awt.Color(255, 204, 204));
=======
        jPanel2.setBackground(new java.awt.Color(51, 51, 51));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
>>>>>>> 1a8cf6a27ba0aad50f90578defa03417c9459a3a
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        acc_name.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        acc_name.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        acc_name.setText("User");
        jPanel2.add(acc_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 170, 25));

        cars1.setBackground(new java.awt.Color(255, 255, 255));
        cars1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cars1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cars1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cars1MouseExited(evt);
            }
        });
        cars1.setLayout(null);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("BLOTTER");
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });
        cars1.add(jLabel8);
        jLabel8.setBounds(0, 0, 170, 40);

        jPanel2.add(cars1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 183, 190, 41));

        u_name.setBackground(new java.awt.Color(255, 255, 255));
        u_name.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                u_nameMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                u_nameMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                u_nameMouseExited(evt);
            }
        });
        u_name.setLayout(null);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("ACCOUNT");
        u_name.add(jLabel10);
        jLabel10.setBounds(50, -3, 80, 40);

        jPanel2.add(u_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 242, 180, 36));

<<<<<<< HEAD
        u_image.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        u_image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-user-50.png"))); // NOI18N
=======
        jLabel9.setBackground(new java.awt.Color(153, 153, 153));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-user-50.png"))); // NOI18N
>>>>>>> 1a8cf6a27ba0aad50f90578defa03417c9459a3a

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(u_image, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(u_image, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 19, -1, -1));

<<<<<<< HEAD
        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 181, 310));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
=======
        jPanel3.setBackground(new java.awt.Color(153, 153, 255));
>>>>>>> 1a8cf6a27ba0aad50f90578defa03417c9459a3a
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

<<<<<<< HEAD
        tblblotter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Full Name", "Incident Type", "Description", "Location", "Date of Incident", "Time of Incident", "Status"
            }
        ));
        jScrollPane1.setViewportView(tblblotter);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 500, 210));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(188, 90, 530, 310));
=======
        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
>>>>>>> 1a8cf6a27ba0aad50f90578defa03417c9459a3a

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        
            Session sess = Session.getInstance();
    if (sess.getUid() != 0) {
        logoutUser(sess.getName());  // Log out the current user // CLEAR
    }

    LoginForm loginFrame = new LoginForm();
    JOptionPane.showMessageDialog(null, "Log-out Success!");
    loginFrame.setVisible(true);
    this.dispose();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        Session sess = Session.getInstance();
        if(sess.getUid() == 0){
           JOptionPane.showMessageDialog(null, "No account, Login First!"); 
           LoginForm lf = new LoginForm();
           lf.setVisible(true);
           this.dispose();
        }
        acc_name.setText(""+sess.getFname());
    }//GEN-LAST:event_formWindowActivated

    private void u_nameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_u_nameMouseClicked
        accountDetails accd = new accountDetails();
        accd.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_u_nameMouseClicked

    private void u_nameMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_u_nameMouseEntered
        u_name.setBackground(hovercolor);
    }//GEN-LAST:event_u_nameMouseEntered

    private void u_nameMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_u_nameMouseExited
        u_name.setBackground(navcolor);
    }//GEN-LAST:event_u_nameMouseExited

    private void cars1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cars1MouseClicked
        
    }//GEN-LAST:event_cars1MouseClicked

    private void cars1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cars1MouseEntered
        cars1.setBackground(hovercolor);
    }//GEN-LAST:event_cars1MouseEntered

    private void cars1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cars1MouseExited
        cars1.setBackground(navcolor);
    }//GEN-LAST:event_cars1MouseExited

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
          UserReportForm urf = new UserReportForm();
        urf.setVisible(true);
        this.dispose();       // TODO add your handling code here:
    }//GEN-LAST:event_jLabel8MouseClicked

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
            java.util.logging.Logger.getLogger(usersDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(usersDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(usersDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(usersDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new usersDashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel acc_name;
    private javax.swing.JPanel cars1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblblotter;
    private javax.swing.JLabel u_image;
    private javax.swing.JPanel u_name;
    // End of variables declaration//GEN-END:variables
}
