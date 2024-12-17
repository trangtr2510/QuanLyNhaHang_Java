
package com.raven.componert;

import com.raven.model.ModelLogin;
import com.raven.model.ModelUser;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import qlnh.swing.Button;
import qlnh.swing.MyPasswordField;
import qlnh.swing.MyTextField;

public class PanelLoginAndRegister extends javax.swing.JLayeredPane {

     public ModelLogin getDataLogin() {
        return dataLogin;
    }
    public ModelUser getUser() {
        return user;
    }
    
    private ModelUser user;
    private String name; //Tên Khách Hàng
    private ModelLogin dataLogin; //Model thông tin đăng nhập
    private Icon hide;
    private Icon show;
    private char def;
    public PanelLoginAndRegister(ActionListener eventRegister, ActionListener eventLongin) {
        initComponents();
        hide = new ImageIcon(getClass().getResource("/Icons/hide.png"));
        show = new ImageIcon(getClass().getResource("/Icons/view.png"));
        initRegister(eventRegister);
        initLogin(eventLongin);
        login.setVisible(false);
        register.setVisible(true);
    }
    private void initRegister(ActionListener eventRegister){
        register.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]25[]push"));
        JLabel label = new JLabel("Đăng ký tài khoản");
        label.setFont(new Font("sansserif", 1, 30));
        label.setForeground(new Color(183, 174, 242));
        register.add(label);
        //ten tai khoan
        MyTextField txtUser = new MyTextField();
        txtUser.setPrefixIcon(new ImageIcon(getClass().getResource("/Icons/user (2).png")));
        txtUser.setHint("Tên Khách Hàng ...");
        register.add(txtUser, "w 60%");
        //email
        MyTextField txtEmail = new MyTextField();
        txtEmail.setPrefixIcon(new ImageIcon(getClass().getResource("/Icons/mail.png")));
        txtEmail.setHint("Email ...");
        register.add(txtEmail, "w 60%");
        //mat khau
        MyPasswordField txtPassword = new MyPasswordField();
        def = txtPassword.getEchoChar();
        txtPassword.setPrefixIcon(new ImageIcon(getClass().getResource("/Icons/pass.png")));
        txtPassword.setHint("Mật khẩu ...");
        txtPassword.setSuffixIcon(show);
        register.add(txtPassword, "w 60%");
        txtPassword.addMouseListener(new MouseAdapter() {
         
            public void mouseClicked(MouseEvent e) {
                if (txtPassword.getSuffixIcon().equals(hide)) {
                    txtPassword.setSuffixIcon(show);
                    txtPassword.setEchoChar((char) 0);

                } else {
                    txtPassword.setSuffixIcon(hide);
                    txtPassword.setEchoChar(def);
                }
            }

        });
        Button cmd = new Button();
        cmd.setBackground(new Color(242, 145, 163));
        cmd.setForeground(new Color(250, 250, 250));
        cmd.addActionListener(eventRegister);
        cmd.setText("Đăng ký");
        register.add(cmd, "w 40%, h 40");
        cmd.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                name = txtUser.getText().trim();
                String email = txtEmail.getText().trim();
                String password = String.valueOf(txtPassword.getPassword());
                user = new ModelUser(0, email, password, "Khach Hang");
            }
            
        });
        }
    
    private void initLogin(ActionListener eventLongin){
        login.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]25[]push"));
        JLabel label = new JLabel("Đăng nhập");
        label.setFont(new Font("sansserif", 1, 30));
        label.setForeground(new Color(183, 174, 242));
        login.add(label);
        //ten tai khoan
        MyTextField txtEmail = new MyTextField();
        txtEmail.setPrefixIcon(new ImageIcon(getClass().getResource("/Icons/user (2).png")));
        txtEmail.setHint("Email ...");
        login.add(txtEmail, "w 60%");
        //mat khau
        MyPasswordField txtPassword = new MyPasswordField();
        txtPassword.setPrefixIcon(new ImageIcon(getClass().getResource("/Icons/pass.png")));
        txtPassword.setHint("Mật khẩu ...");
        txtPassword.setSuffixIcon(show);
        login.add(txtPassword, "w 60%");
        txtPassword.addMouseListener(new MouseAdapter() {
         
            public void mouseClicked(MouseEvent e) {
                if (txtPassword.getSuffixIcon().equals(hide)) {
                    txtPassword.setSuffixIcon(show);
                    txtPassword.setEchoChar((char) 0);

                } else {
                    txtPassword.setSuffixIcon(hide);
                    txtPassword.setEchoChar(def);
                }
            }

        });
        JButton cmdForget = new JButton("Quên mật khẩu ?");
        cmdForget.setForeground(new Color(100, 100, 100));
        cmdForget.setFont(new Font("sansserif", 1, 12));
        cmdForget.setContentAreaFilled(false);
        cmdForget.setCursor(new Cursor(Cursor.HAND_CURSOR));
        login.add(cmdForget);
        Button cmd = new Button();
        cmd.setBackground(new Color(242, 145, 163));
        cmd.setForeground(new Color(250, 250, 250));
        cmd.addActionListener(eventLongin);
        cmd.setText("Đăng nhập");
        login.add(cmd, "w 40%, h 40");
        cmd.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = txtEmail.getText().trim();
                String password = String.valueOf(txtPassword.getPassword());
                dataLogin = new ModelLogin(email, password);
            }
            
        });
    }
    
    public void showRedister( boolean show) {
         if(show){
            register.setVisible(true);
            login.setVisible(false);
         }
         else{
            register.setVisible(false);
            login.setVisible(true);
         }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        login = new javax.swing.JPanel();
        register = new javax.swing.JPanel();

        setLayout(new java.awt.CardLayout());

        login.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout loginLayout = new javax.swing.GroupLayout(login);
        login.setLayout(loginLayout);
        loginLayout.setHorizontalGroup(
            loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 306, Short.MAX_VALUE)
        );
        loginLayout.setVerticalGroup(
            loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        add(login, "card3");

        register.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout registerLayout = new javax.swing.GroupLayout(register);
        register.setLayout(registerLayout);
        registerLayout.setHorizontalGroup(
            registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 306, Short.MAX_VALUE)
        );
        registerLayout.setVerticalGroup(
            registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        add(register, "card2");
    }// </editor-fold>//GEN-END:initComponents

   
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel login;
    private javax.swing.JPanel register;
    // End of variables declaration//GEN-END:variables

    

}
