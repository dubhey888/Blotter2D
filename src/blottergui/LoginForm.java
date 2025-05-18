/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blottergui;



import admin.admindashboard;
import config.Session;
import config.dbConnector;
import config.passwordHasher;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import javax.swing.JOptionPane;
import user.usersDashboard;

/**
 *
 * @author You
 */
public class LoginForm extends javax.swing.JFrame {

    /**
     * Creates new form LoginForm
     */
    public LoginForm() {
        initComponents();      
        this.setResizable(false);

    }
    static String status;
    static String type;
    
    public static boolean loginAcc(String username, String password){
        dbConnector connector = new dbConnector();
        try{
            String query = "SELECT * FROM tbl_users  WHERE u_username = '" + username + "'";
            ResultSet resultSet = connector.getData(query);
            if(resultSet.next()){     
   
                String hashedPass = resultSet.getString("u_password");
                String rehashedPass = passwordHasher.hashPassword(password);
                
                if(hashedPass.equals(rehashedPass)){        
                status = resultSet.getString("u_status");   
                type = resultSet.getString("u_type");
                Session sess = Session.getInstance();
                sess.setUid(resultSet.getInt("u_id"));
                sess.setFname(resultSet.getString("u_fname"));
                sess.setLname(resultSet.getString("u_lname"));
                sess.setEmail(resultSet.getString("u_email"));
                sess.setName(resultSet.getString("u_username"));
                sess.setType(resultSet.getString("u_type"));
                sess.setStatus(resultSet.getString("u_status"));
                return true;   
                }else{
                return false;
                }
        }else{
            return false;
        }          
        }catch (SQLException | NoSuchAlgorithmException ex) {
            return false;
        }

    }
    
    public String getUserId(String username) {
       
        dbConnector dbc = new dbConnector();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String userId = null;

        try {
         
            String sql = "SELECT u_id FROM tbl_users WHERE u_username = ?";
            pstmt = dbc.connect.prepareStatement(sql);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                userId = rs.getString("u_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
           
       
        }
        return userId;
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


     public String getUserTypeFromDatabase(String username) {
    String type = "";
    String query = "SELECT u_type FROM tbl_users WHERE LOWER(u_username) = LOWER(?)";
    
    // Use an instance of dbConnector to get the connection
    dbConnector connector = new dbConnector();  // Create instance of dbConnector
    try (Connection con = connector.getConnection(); 
         PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            type = rs.getString("u_type");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return type;
}
      public String getStatusFromDatabase(String username) {
    String status = "";
    String query = "SELECT log_status FROM tbl_log WHERE LOWER(u_username) = LOWER(?) ORDER BY login_time DESC LIMIT 1";
    
    // Use an instance of dbConnector to get the connection
    dbConnector connector = new dbConnector();  // Create instance of dbConnector
    try (Connection con = connector.getConnection(); 
         PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            status = rs.getString("log_status");
            System.out.println("status: "+status);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return status;
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
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        user = new javax.swing.JTextField();
        pass = new javax.swing.JPasswordField();
        jPanel4 = new javax.swing.JPanel();
        cancel = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        Login = new javax.swing.JButton();
        Regis = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 204, 204));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setText("         WELCOME TO BLOTTER SYSTEM !!!");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 400, 60));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 850, 110));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/blottergui/image/s.png"))); // NOI18N
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 360, 360));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 510));

        jPanel2.setBackground(new java.awt.Color(153, 153, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel2.setText("LOGIN FORM");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 90, 170, 100));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel3.setText("Username:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 209, -1, 40));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel4.setText("Password:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 269, -1, 40));

        user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userActionPerformed(evt);
            }
        });
        jPanel2.add(user, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 210, 170, 40));
        jPanel2.add(pass, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 270, 170, 40));

        jPanel4.setBackground(new java.awt.Color(153, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cancel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        cancel.setText("Cancel");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });
        jPanel4.add(cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 40));

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 360, 120, 40));

        jPanel5.setBackground(new java.awt.Color(153, 255, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Login.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        Login.setText("Login");
        Login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginActionPerformed(evt);
            }
        });
        jPanel5.add(Login, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 101, 40));

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 360, 100, 40));

        Regis.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        Regis.setText("Are you a new User? Click here to Register!");
        Regis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RegisMouseClicked(evt);
            }
        });
        jPanel2.add(Regis, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 450, 390, 30));

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Forgot Password?");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 320, 150, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 0, 440, 510));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_userActionPerformed

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        user.setText("");
        pass.setText("");    }//GEN-LAST:event_cancelActionPerformed

    private void RegisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RegisMouseClicked
        new Register().setVisible(true);
        this.setVisible(false);
        this.dispose();        this.dispose();    }//GEN-LAST:event_RegisMouseClicked

    private void LoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginActionPerformed
    String username = user.getText();  // Get username input
String password = pass.getText();  // Get password input

dbConnector connector = new dbConnector();
String status = null;
String type = null;

if (loginAcc(username, password)) {
    // Get session info
    Session sess = Session.getInstance();
    int userId = sess.getUid();

    try {
        String query = "SELECT * FROM tbl_users WHERE u_username = ?";
        PreparedStatement pstmt = connector.getConnection().prepareStatement(query);
        pstmt.setString(1, username);

        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            status = rs.getString("u_status");
            type = rs.getString("u_type");
        } else {
            JOptionPane.showMessageDialog(null, "User not found.");
            return;
        }
    } catch (SQLException e) {
        System.out.println("SQL Exception: " + e.getMessage());
        return;
    }

    if (!"Active".equalsIgnoreCase(status)) {
        JOptionPane.showMessageDialog(null, "Inactive Account, Contact the Admin!");
        logEvent(userId, username, "Failed - Inactive Account");
    } else {
        // Separate redirection logic
        switch (type) {
            case "Admin":
                JOptionPane.showMessageDialog(null, "Login Success! Welcome Admin.");
                admindashboard adminFrame = new admindashboard();
                adminFrame.setVisible(true);
                logEvent(userId, username, "Success - Admin Login");
                break;
            case "Police":
                JOptionPane.showMessageDialog(null, "Login Success! Welcome Police.");
                admindashboard policeFrame = new admindashboard(); // You need to create this frame
                policeFrame.setVisible(true);
                logEvent(userId, username, "Success - Police Login");
                break;
            case "User":
                JOptionPane.showMessageDialog(null, "Login Success! Welcome User.");
                usersDashboard userFrame = new usersDashboard();
                userFrame.setVisible(true);
                logEvent(userId, username, "Success - User Login");
                break;
            default:
                JOptionPane.showMessageDialog(null, "Unknown user type: " + type);
                break;
        }
        this.dispose();
    }
} else {
    JOptionPane.showMessageDialog(null, "Invalid Username or Password!");
    logEvent(-1, username, "Failed - Invalid Login");


    }    }//GEN-LAST:event_LoginActionPerformed

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
            new ForgetPassword().setVisible(true); // Open ForgotPassword form
          dispose();         // TODO add your handling code here:
    }//GEN-LAST:event_jLabel6MouseClicked
    
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
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Login;
    private javax.swing.JLabel Regis;
    private javax.swing.JButton cancel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPasswordField pass;
    private javax.swing.JTextField user;
    // End of variables declaration//GEN-END:variables


    
}
