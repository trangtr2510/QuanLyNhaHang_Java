
package com.view.Dialog;

import com.raven.model.ModelUser;
import com.raven.service.ServiceUser;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;


public class MS_ChangePassword extends javax.swing.JDialog {

    private final Animator animator;
    private boolean show = true;
    private Frame frame;
    private ServiceUser service;
    private Icon ihide;
    private Icon ishow;
    private char def;

    public MS_ChangePassword(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        service = new ServiceUser();
        this.frame = parent;
        setOpacity(0f);
        getContentPane().setBackground(Color.WHITE);
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                if (show) {
                    setOpacity(fraction);
                } else {
                    setOpacity(1f - fraction);
                }
            }

            @Override
            public void end() {
                if (show == false) {
                    setVisible(false);
                }
            }

        };
        animator = new Animator(200, target);
        animator.setResolution(0);
        animator.setAcceleration(0.5f);
        ihide = new ImageIcon(getClass().getResource("/Icons/hide.png"));
        ishow = new ImageIcon(getClass().getResource("/Icons/view.png"));
        def = txtnewPass.getEchoChar();
        txtnewPass.setSuffixIcon(ihide);
        txtconfirm.setSuffixIcon(ihide);
    }

    public void ChangePassword(ModelUser user) {
        setLocationRelativeTo(frame);
        lbMessage.setVisible(false);
        animator.start();
        cmdOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String newPass = String.valueOf(txtnewPass.getPassword());
                    String confmPass = String.valueOf(txtconfirm.getPassword());
                    //Nếu mật khẩu mới và xác nhận mật khẩu giống nhau thực 
                    //hiện đổi mật khẩu và hiển thị thông báo đổi thành công
                    if (newPass.equals(confmPass)) {
                        lbMessage.setBackground(new Color(186, 225, 186));
                        lbMessage.setForeground(new Color(60, 117, 60));
                        lbMessage.setText("Mật khẩu của bạn đã được đổi thành công");
                        lbMessage.setVisible(true);
                        service.changePassword(user.getUserID(), newPass);
                        user.setPassword(newPass);
                    } //Nếu mật khẩu mới và xác nhận mật khẩu không giống nhau thì thông báo xác nhận mật khẩu không đúng
                    else {
                        lbMessage.setBackground(new Color(249, 181, 181));
                        lbMessage.setForeground(new Color(130, 38, 38));
                        lbMessage.setText("Xác nhận Mật Khẩu không khớp");
                        lbMessage.setVisible(true);
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(MS_ChangePassword.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
        setVisible(true);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelRound2 = new qlnh.swing.PanelRound();
        lbTitle = new javax.swing.JLabel();
        lbIcon = new javax.swing.JLabel();
        lbnewPass = new javax.swing.JLabel();
        lbConfirmPass = new javax.swing.JLabel();
        lbMessage = new javax.swing.JLabel();
        cmdClose = new javax.swing.JLabel();
        cmdCancel = new qlnh.swing.ButtonOutLine();
        cmdOK = new qlnh.swing.ButtonOutLine();
        txtnewPass = new qlnh.swing.MyPasswordField();
        txtconfirm = new qlnh.swing.MyPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        panelRound2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(134, 168, 231), 3));

        lbTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbTitle.setForeground(new java.awt.Color(108, 91, 123));
        lbTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitle.setText("ĐỔI MẬT KHẨU TÀI KHOẢN");

        lbIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/reset-password (1).png"))); // NOI18N

        lbnewPass.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbnewPass.setForeground(new java.awt.Color(89, 89, 89));
        lbnewPass.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbnewPass.setText("Mật Khẩu mới ");

        lbConfirmPass.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbConfirmPass.setForeground(new java.awt.Color(89, 89, 89));
        lbConfirmPass.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbConfirmPass.setText("Xác nhận Mật Khẩu mới");

        lbMessage.setBackground(new java.awt.Color(249, 181, 181));
        lbMessage.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbMessage.setForeground(new java.awt.Color(130, 38, 38));
        lbMessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbMessage.setText("Xác nhận Mật Khẩu không khớp");
        lbMessage.setOpaque(true);

        cmdClose.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cmdClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/close (2).png"))); // NOI18N
        cmdClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmdCloseMouseClicked(evt);
            }
        });

        cmdCancel.setBackground(new java.awt.Color(237, 33, 58));
        cmdCancel.setForeground(new java.awt.Color(108, 91, 123));
        cmdCancel.setText("Hủy");
        cmdCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmdCancelMouseClicked(evt);
            }
        });

        cmdOK.setBackground(new java.awt.Color(17, 153, 142));
        cmdOK.setForeground(new java.awt.Color(108, 91, 123));
        cmdOK.setText("Xác nhận");

        txtnewPass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtnewPassMouseClicked(evt);
            }
        });

        txtconfirm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtconfirmMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelRound2Layout = new javax.swing.GroupLayout(panelRound2);
        panelRound2.setLayout(panelRound2Layout);
        panelRound2Layout.setHorizontalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbMessage, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbTitle, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelRound2Layout.createSequentialGroup()
                        .addGroup(panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelRound2Layout.createSequentialGroup()
                                .addGroup(panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lbnewPass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(panelRound2Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(lbConfirmPass)))
                                .addGap(44, 44, 44))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(cmdOK, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)))
                        .addGroup(panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound2Layout.createSequentialGroup()
                                .addComponent(cmdCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(80, 80, 80))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound2Layout.createSequentialGroup()
                                .addGroup(panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtconfirm, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                    .addComponent(txtnewPass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(45, 45, 45)))))
                .addGap(30, 30, 30))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbIcon)
                .addGap(241, 241, 241))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(cmdClose, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelRound2Layout.setVerticalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound2Layout.createSequentialGroup()
                .addComponent(cmdClose, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(lbTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbnewPass, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtnewPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbConfirmPass, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(txtconfirm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdOK, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelRound2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelRound2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmdCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdCloseMouseClicked
        closeMenu();
    }//GEN-LAST:event_cmdCloseMouseClicked

    private void cmdCancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdCancelMouseClicked
        closeMenu();
    }//GEN-LAST:event_cmdCancelMouseClicked

    private void txtnewPassMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtnewPassMouseClicked
        if (txtnewPass.getSuffixIcon().equals(ihide)) {
            txtnewPass.setSuffixIcon(ishow);
            txtnewPass.setEchoChar((char) 0);

        } else {
            txtnewPass.setSuffixIcon(ihide);
            txtnewPass.setEchoChar(def);
        }
    }//GEN-LAST:event_txtnewPassMouseClicked

    private void txtconfirmMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtconfirmMouseClicked
        if (txtconfirm.getSuffixIcon().equals(ihide)) {
            txtconfirm.setSuffixIcon(ishow);
            txtconfirm.setEchoChar((char) 0);

        } else {
            txtconfirm.setSuffixIcon(ihide);
            txtconfirm.setEchoChar(def);
        }
    }//GEN-LAST:event_txtconfirmMouseClicked

    private void closeMenu() {
        if (animator.isRunning()) {
            animator.stop();
        }
        show = false;
        animator.start();
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gp = new GradientPaint(0, 0, Color.decode("#516395"), 0, getHeight(), Color.decode("#614385"));
        g2.setPaint(gp);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        g2.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponents(g);
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private qlnh.swing.ButtonOutLine cmdCancel;
    private javax.swing.JLabel cmdClose;
    private qlnh.swing.ButtonOutLine cmdOK;
    private javax.swing.JLabel lbConfirmPass;
    private javax.swing.JLabel lbIcon;
    private javax.swing.JLabel lbMessage;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JLabel lbnewPass;
    private qlnh.swing.PanelRound panelRound2;
    private qlnh.swing.MyPasswordField txtconfirm;
    private qlnh.swing.MyPasswordField txtnewPass;
    // End of variables declaration//GEN-END:variables
}
