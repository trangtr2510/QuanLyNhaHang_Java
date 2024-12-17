
package login;

import com.raven.formQL.formBan;
import com.raven.formQL.formHD;
import com.raven.formQL.formKH;
import com.raven.formQL.formMenu;
import com.raven.formQL.formNV;
import com.raven.formQL.formUser;
import com.raven.model.ModelUser;

public class MainQL extends javax.swing.JFrame {

    ModelUser user;
    formNV a = new formNV();
    formKH b = new formKH();
    formMenu c = new formMenu();
    formBan d = new formBan();
    formHD e = new formHD();
    formUser f = new formUser();
    public MainQL(ModelUser user) {
        initComponents();
        main.add(a);
        main.add(b);
        main.add(c);
        main.add(d);
        main.add(e);
        main.add(f);
        a.setVisible(false);
        b.setVisible(false);
        c.setVisible(false);
        d.setVisible(false);
        e.setVisible(false);
        f.setVisible(false);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnDX = new javax.swing.JButton();
        btnNV = new javax.swing.JButton();
        btnKH = new javax.swing.JButton();
        btnMenu = new javax.swing.JButton();
        btnBan = new javax.swing.JButton();
        btnHD = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        main = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1300, 650));

        jPanel1.setBackground(new java.awt.Color(204, 183, 253));
        jPanel1.setPreferredSize(new java.awt.Dimension(170, 495));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/businessman.png"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        btnDX.setBackground(new java.awt.Color(204, 183, 253));
        btnDX.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnDX.setForeground(new java.awt.Color(51, 51, 51));
        btnDX.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/10.png"))); // NOI18N
        btnDX.setText("Đăng xuất");
        btnDX.setBorderPainted(false);
        btnDX.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDXActionPerformed(evt);
            }
        });

        btnNV.setBackground(new java.awt.Color(204, 183, 253));
        btnNV.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnNV.setForeground(new java.awt.Color(51, 51, 51));
        btnNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bell-boy (1).png"))); // NOI18N
        btnNV.setText("Nhân viên");
        btnNV.setBorderPainted(false);
        btnNV.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNVActionPerformed(evt);
            }
        });

        btnKH.setBackground(new java.awt.Color(204, 183, 253));
        btnKH.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnKH.setForeground(new java.awt.Color(51, 51, 51));
        btnKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/customer.png"))); // NOI18N
        btnKH.setText("Khách hàng");
        btnKH.setBorderPainted(false);
        btnKH.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKHActionPerformed(evt);
            }
        });

        btnMenu.setBackground(new java.awt.Color(204, 183, 253));
        btnMenu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnMenu.setForeground(new java.awt.Color(51, 51, 51));
        btnMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/menu (2).png"))); // NOI18N
        btnMenu.setText("Menu");
        btnMenu.setBorderPainted(false);
        btnMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuActionPerformed(evt);
            }
        });

        btnBan.setBackground(new java.awt.Color(204, 183, 253));
        btnBan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnBan.setForeground(new java.awt.Color(51, 51, 51));
        btnBan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/receipt (1).png"))); // NOI18N
        btnBan.setText("Bàn");
        btnBan.setBorderPainted(false);
        btnBan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBanActionPerformed(evt);
            }
        });

        btnHD.setBackground(new java.awt.Color(204, 183, 253));
        btnHD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnHD.setForeground(new java.awt.Color(51, 51, 51));
        btnHD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/analytics.png"))); // NOI18N
        btnHD.setText("Hóa đơn");
        btnHD.setBorderPainted(false);
        btnHD.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHDActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(204, 183, 253));
        jButton1.setForeground(new java.awt.Color(51, 51, 51));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/user (2).png"))); // NOI18N
        jButton1.setText("QL Người dùng");
        jButton1.setBorderPainted(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnDX, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnNV, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnKH, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnMenu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnBan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnHD, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jLabel1)
                .addContainerGap(53, Short.MAX_VALUE))
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1)
                .addGap(21, 21, 21)
                .addComponent(btnNV)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnKH)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMenu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnHD)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 157, Short.MAX_VALUE)
                .addComponent(btnDX)
                .addGap(32, 32, 32))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.LINE_START);

        main.setPreferredSize(new java.awt.Dimension(1200, 495));
        main.setLayout(new java.awt.CardLayout());
        getContentPane().add(main, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnDXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDXActionPerformed
        MainLogin login = new MainLogin();
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnDXActionPerformed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
       
    }//GEN-LAST:event_jLabel1MouseClicked

    private void btnNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNVActionPerformed
        a.setVisible(true);
        b.setVisible(false);
        c.setVisible(false);
        d.setVisible(false);
        e.setVisible(false);
        f.setVisible(false);
    }//GEN-LAST:event_btnNVActionPerformed

    private void btnKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKHActionPerformed
        a.setVisible(false);
        b.setVisible(true);
        c.setVisible(false);
        d.setVisible(false);
        e.setVisible(false);
        f.setVisible(false);
    }//GEN-LAST:event_btnKHActionPerformed

    private void btnMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuActionPerformed
        a.setVisible(false);
        b.setVisible(false);
        c.setVisible(true);
        d.setVisible(false);
        e.setVisible(false);
        f.setVisible(false);
    }//GEN-LAST:event_btnMenuActionPerformed

    private void btnBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBanActionPerformed
       a.setVisible(false);
        b.setVisible(false);
        c.setVisible(false);
        d.setVisible(true);
        e.setVisible(false);
        f.setVisible(false);
        d.loadBang();
    }//GEN-LAST:event_btnBanActionPerformed

    private void btnHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHDActionPerformed
        a.setVisible(false);
        b.setVisible(false);
        c.setVisible(false);
        d.setVisible(false);
        e.setVisible(true);
        f.setVisible(false);
        
    }//GEN-LAST:event_btnHDActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        a.setVisible(false);
        b.setVisible(false);
        c.setVisible(false);
        d.setVisible(false);
        e.setVisible(false);
        f.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    
    public static void main(ModelUser user) {
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
            java.util.logging.Logger.getLogger(MainQL.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainQL.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainQL.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainQL.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainQL(user).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBan;
    private javax.swing.JButton btnDX;
    private javax.swing.JButton btnHD;
    private javax.swing.JButton btnKH;
    private javax.swing.JButton btnMenu;
    private javax.swing.JButton btnNV;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLayeredPane main;
    // End of variables declaration//GEN-END:variables
}
