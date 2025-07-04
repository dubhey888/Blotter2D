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
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Bentastic
 */
public class accountDetails extends javax.swing.JFrame {

    /**
     * Creates new form adminDashboard
     */
    public accountDetails() {
        initComponents();
    }

     public boolean updateCheck(){
        
     dbConnector dbc = new dbConnector();
    Session sess = Session.getInstance();

    try {
        String query = "SELECT * FROM tbl_users WHERE (u_username = '" + acc_email.getText() 
                     + "' OR u_email = '" + acc_uname.getText() 
                     + "') AND u_id != '" + sess.getUid() + "'";

        ResultSet resultSet = dbc.getData(query);

        if (resultSet.next()) {
            String email = resultSet.getString("u_email"); 
            if (email.equals(acc_uname.getText())) { 
                JOptionPane.showMessageDialog(null, "Email is Already Used!");
                acc_uname.setText(""); 
            }

            String username = resultSet.getString("u_username");
            if (username.equals(acc_email.getText())) { 
                JOptionPane.showMessageDialog(null, "Username is Already Used!");
                acc_email.setText(""); 
            }

            return true;
        } else {
            return false;
        }
    } catch (SQLException ex) {
        System.out.println("" + ex);
        return false;
    }
        
    }
       
     private void uploadImage() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png"));

    int result = fileChooser.showOpenDialog(this);
    if (result == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        
        // Define destination path inside src/images/
        File destinationFolder = new File("src/images/");
        if (!destinationFolder.exists()) {
            destinationFolder.mkdirs(); // Create folder if it does not exist
        }

        File destinationFile = new File(destinationFolder, selectedFile.getName());

        try {
            // Copy file to the destination folder
            Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            
            // Store the image path
            selectedImagePath = "src/images/" + selectedFile.getName();

            // Update the image label
            image.setIcon(new javax.swing.ImageIcon(destinationFile.getAbsolutePath()));

            JOptionPane.showMessageDialog(this, "Image uploaded successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error uploading image: " + e.getMessage());
        }
    }
}
   
     private String selectedImagePath = "";
      



    
        public void logEvent(int userId, String username, String userType, String logDescription) {
    dbConnector dbc = new dbConnector();
    Connection con = dbc.getConnection();
    PreparedStatement pstmt = null;

    try {
        String sql = "INSERT INTO tbl_log (u_id, u_username, login_time, u_type, log_status, log_description) VALUES (?, ?, ?, ?, ?, ?)";
        pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, userId);
        pstmt.setString(2, username);
        pstmt.setTimestamp(3, new Timestamp(new Date().getTime()));
        pstmt.setString(4, userType); // This should be "Admin" or "User"
        pstmt.setString(5, "Active");
        pstmt.setString(6, logDescription); // Insert the log description

        pstmt.executeUpdate();
        System.out.println("Log recorded successfully.");
    } catch (SQLException e) {
        System.out.println("Error recording log: " + e.getMessage());
    } finally {
        try {
            if (pstmt != null) pstmt.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error closing resources: " + e.getMessage());
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        iddisplay = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        acc_fname = new javax.swing.JTextField();
        acc_lname = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        acc_email = new javax.swing.JTextField();
        acc_uname = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        ut = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        image = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        update = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(153, 153, 255));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 204, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ACCOUNT INFORMATION");

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 204, 204));
        jLabel2.setText("LOGOUT");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        iddisplay.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        iddisplay.setForeground(new java.awt.Color(204, 204, 204));
        iddisplay.setText("(UID)");
        iddisplay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iddisplayMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
<<<<<<< HEAD
                .addComponent(iddisplay)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 336, Short.MAX_VALUE)
=======
                .addComponent(iddisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
>>>>>>> 1a8cf6a27ba0aad50f90578defa03417c9459a3a
                .addComponent(jLabel2)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(iddisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
<<<<<<< HEAD
            .addGap(0, 461, Short.MAX_VALUE)
=======
            .addGap(0, 0, Short.MAX_VALUE)
>>>>>>> 1a8cf6a27ba0aad50f90578defa03417c9459a3a
        );

        jPanel3.setBackground(new java.awt.Color(153, 153, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel4.setText("First Name:");
<<<<<<< HEAD
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 15, -1, -1));

        acc_fname.addActionListener(new java.awt.event.ActionListener() {
=======
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 13, -1, -1));

        fn.setBackground(new java.awt.Color(204, 204, 255));
        fn.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fn.addActionListener(new java.awt.event.ActionListener() {
>>>>>>> 1a8cf6a27ba0aad50f90578defa03417c9459a3a
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acc_fnameActionPerformed(evt);
            }
        });
<<<<<<< HEAD
        jPanel3.add(acc_fname, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 41, 227, 30));
        jPanel3.add(acc_lname, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 117, 227, 30));
=======
        jPanel3.add(fn, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 39, 238, 30));
>>>>>>> 1a8cf6a27ba0aad50f90578defa03417c9459a3a

        ln.setBackground(new java.awt.Color(204, 204, 255));
        ln.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel3.add(ln, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 112, 238, 30));

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel7.setText("Last Name:");
<<<<<<< HEAD
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 87, -1, -1));
=======
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 83, -1, -1));
>>>>>>> 1a8cf6a27ba0aad50f90578defa03417c9459a3a

        jLabel8.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel8.setText("Email:");
<<<<<<< HEAD
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 160, -1, -1));
        jPanel3.add(acc_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 184, 227, 30));
        jPanel3.add(acc_uname, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 260, 227, 33));
=======
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 153, -1, -1));
>>>>>>> 1a8cf6a27ba0aad50f90578defa03417c9459a3a

        em.setBackground(new java.awt.Color(204, 204, 255));
        em.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel3.add(em, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 177, 238, 30));

        un.setBackground(new java.awt.Color(204, 204, 255));
        un.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel3.add(un, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 238, 30));

        jLabel11.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel11.setText("UserName:");
<<<<<<< HEAD
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 227, -1, -1));
=======
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 218, -1, -1));
>>>>>>> 1a8cf6a27ba0aad50f90578defa03417c9459a3a

        ut.setBackground(new java.awt.Color(153, 204, 255));
        ut.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ut.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "User" }));
<<<<<<< HEAD
        jPanel3.add(ut, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 310, 122, -1));
=======
        ut.setBorder(new javax.swing.border.MatteBorder(null));
        jPanel3.add(ut, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 290, 122, 30));
>>>>>>> 1a8cf6a27ba0aad50f90578defa03417c9459a3a

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel6.setText("Account Type:");
<<<<<<< HEAD
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, -1, -1));
=======
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 288, -1, 30));
>>>>>>> 1a8cf6a27ba0aad50f90578defa03417c9459a3a

        jButton1.setBackground(new java.awt.Color(204, 204, 255));
        jButton1.setText("CHANGE PASS");
        jButton1.setBorder(new javax.swing.border.MatteBorder(null));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
<<<<<<< HEAD
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 39, 100, 35));

        jPanel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel9MouseClicked(evt);
            }
        });
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        image.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/loupe.png"))); // NOI18N
        image.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                imageMouseClicked(evt);
            }
        });
        jPanel9.add(image, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 190, 130));

        jPanel3.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 100, 210, 180));

        jButton2.setText("Select");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 290, -1, 30));

        update.setBackground(new java.awt.Color(255, 255, 255));
        update.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        update.setText("Save ");
        update.setBorder(null);
        update.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updateMouseClicked(evt);
            }
        });
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });
        jPanel3.add(update, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 370, 90, 30));
=======
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(289, 37, 130, 35));
>>>>>>> 1a8cf6a27ba0aad50f90578defa03417c9459a3a

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        LoginForm ads = new LoginForm();
        JOptionPane.showMessageDialog(null, "Logout Success!");
        ads.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        Session sess = Session.getInstance();

        iddisplay.setText("USER ID: "+sess.getUid());
        acc_fname.setText(""+sess.getFname());    
        acc_lname.setText(""+sess.getLname());
        acc_email.setText(""+sess.getEmail());
        acc_uname.setText(""+sess.getName());
    }//GEN-LAST:event_formWindowActivated

    private void acc_fnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acc_fnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_acc_fnameActionPerformed

    private void iddisplayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iddisplayMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_iddisplayMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        changePass cp = new changePass();
        cp.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void imageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imageMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_imageMouseClicked

    private void jPanel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel9MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // Define destination path inside src/images/
            File destinationFolder = new File("src/images/");
            if (!destinationFolder.exists()) {
                destinationFolder.mkdirs(); // Create folder if it does not exist
            }

            File destinationFile = new File(destinationFolder, selectedFile.getName());

            try {
                // Copy file to the destination folder
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Store the image path
                selectedImagePath = "src/images/" + selectedFile.getName();

                // Update the image label
                image.setIcon(new javax.swing.ImageIcon(destinationFile.getAbsolutePath()));

                JOptionPane.showMessageDialog(this, "Image uploaded successfully!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error uploading image: " + e.getMessage());
            }
        }    // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void updateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateMouseClicked

    }//GEN-LAST:event_updateMouseClicked

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
        dbConnector dbc = new dbConnector();
        dbConnector connector = new dbConnector();
        Session sess = Session.getInstance();
        int userId = 0;
        String uname2 = null;

        // Check if username or email already exists
        if (updateCheck()) {
            return;
        }

        // Validate inputs
        if (acc_fname.getText().isEmpty() || acc_lname.getText().isEmpty() ||
            acc_email.getText().isEmpty() || acc_uname.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // SQL query to update the user profile
        String query = "UPDATE tbl_users SET u_fname=?, u_lname=?, u_email=?, u_username=?, u_image=? WHERE u_id=?";

        try (Connection conn = dbc.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, acc_fname.getText());
            pstmt.setString(2, acc_lname.getText());
            pstmt.setString(3, acc_email.getText());
            pstmt.setString(4, acc_uname.getText());
            pstmt.setString(5, selectedImagePath);  // make sure selectedImagePath is not null
            pstmt.setInt(6, sess.getUid()); // Ensure sess.getId() returns int

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Profile updated successfully!");

                // Retrieve updated user data
                try (PreparedStatement pstmt2 = connector.getConnection().prepareStatement(
                    "SELECT * FROM tbl_users WHERE u_id = ?")) {

                pstmt2.setInt(1, sess.getUid());
                ResultSet resultSet = pstmt2.executeQuery();

                if (resultSet.next()) {
                    userId = resultSet.getInt("u_id");
                    uname2 = resultSet.getString("u_username");
                }

            } catch (SQLException ex) {
                System.out.println("SQL Exception on fetch: " + ex);
            }

            logEvent(userId, uname2, sess.getType(), "User Changed Their Details");

        } else {
            JOptionPane.showMessageDialog(this, "Failed to update profile!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_updateActionPerformed

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
            java.util.logging.Logger.getLogger(accountDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(accountDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(accountDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(accountDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new accountDetails().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField acc_email;
    private javax.swing.JTextField acc_fname;
    private javax.swing.JTextField acc_lname;
    private javax.swing.JTextField acc_uname;
    private javax.swing.JLabel iddisplay;
    private javax.swing.JLabel image;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel9;
    public javax.swing.JButton update;
    private javax.swing.JComboBox<String> ut;
    // End of variables declaration//GEN-END:variables
}
