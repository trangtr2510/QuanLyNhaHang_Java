
package com.raven.componert;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import net.miginfocom.swing.MigLayout;
import qlnh.swing.ButtonOutLine;
import qlnh.swing.ImageLogo;


public class PanelCover extends javax.swing.JPanel {
    private final DecimalFormat df = new DecimalFormat("##0.###");
    public ActionListener event;
    private MigLayout layout;
    private ImageLogo logo;
    private JLabel title;
    private JLabel subtitle;
    private JLabel description;
    private JLabel descriptionl;
    private ButtonOutLine button;
    private boolean isLogin;
    
    public PanelCover() {
        initComponents();
        setOpaque(false);
        layout = new MigLayout("wrap, fill", "[center]", "push[]40[]10[]25[]10[]25[]push");
        setLayout(layout);
        init();
    }

    private void init(){
        
        logo= new ImageLogo();
        logo.setPreferredSize(new Dimension(250,250));
        logo.setIcon(new ImageIcon(getClass().getResource("/Icons/logo2.jpg")));
        add(logo);
        title= new JLabel("Chào mừng đến");
        title.setFont(new Font("sansserif",1,18));
        title.setForeground(new Color(245,245,245));
        add(title);
        subtitle = new JLabel("Việt Phố");
        subtitle.setFont(new Font("sansserif",1,28));
        subtitle.setForeground(new Color(245,245,245));
        add(subtitle);
        description = new JLabel("Để giữ liên lạc với chúng tôi, xin vui lòng");
        description.setForeground(new Color(245, 245, 245));
        add(description);
        descriptionl = new JLabel("đăng nhập bằng thông tin cá nhân của bạn");
        descriptionl.setForeground(new Color(245, 245, 245));
        add(descriptionl);
        button = new ButtonOutLine();
        button.setBackground(new Color(255, 255, 255));
        button.setForeground(new Color(255, 255, 255));
        button.setText("Đăng nhập");
        button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                event.actionPerformed(ae);
            }
        });
        add(button, "w 60%, h 40");
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 306, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    public void addEvent(ActionListener event){
        this.event = event;
    }
    @Override
    protected void paintComponent(Graphics gphcs){
        Graphics2D g2 = (Graphics2D) gphcs;
        GradientPaint gra = new GradientPaint(0,0, new Color(173, 130, 217),0 , getHeight(), new Color(173, 162, 242)); //1: AD82D9 //2: ADA2F2
        g2.setPaint(gra);
        g2.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(gphcs);
    }
    
    
    //Repaint Panel Cover khi thay đổi giữa màn hình Đăng nhập/Đăng ký
    public void registerLeft(double v){
        v=Double.parseDouble(df.format(v));
        login(false);
        layout.setComponentConstraints(logo, "pad 0 -"+v+"% 0 0");
        layout.setComponentConstraints(title, "pad 0 -"+v+"% 0 0");
        layout.setComponentConstraints(subtitle, "pad 0 -"+v+"% 0 0");
        layout.setComponentConstraints(description, "pad 0 -"+v+"% 0 0");
        layout.setComponentConstraints(descriptionl, "pad 0 -"+v+"% 0 0");
    }
    //Repaint Panel Cover khi thay đổi giữa màn hình Đăng nhập/Đăng ký
    public void registerRight(double v){
        v=Double.parseDouble(df.format(v));
        login(false);
        layout.setComponentConstraints(logo, "pad 0 -"+v+"% 0 0");
        layout.setComponentConstraints(title, "pad 0 -"+v+"% 0 0");
        layout.setComponentConstraints(subtitle, "pad 0 -"+v+"% 0 0");
        layout.setComponentConstraints(description, "pad 0 -"+v+"% 0 0");
        layout.setComponentConstraints(descriptionl, "pad 0 -"+v+"% 0 0");
    }
     //Repaint Panel Cover khi thay đổi giữa màn hình Đăng nhập/Đăng ký
    public void loginLeft(double v){
        v=Double.parseDouble(df.format(v));
        login(true);
        layout.setComponentConstraints(logo, "pad 0 "+v+"% 0 "+v+"%");
        layout.setComponentConstraints(title, "pad 0 "+v+"% 0 "+v+"%");
        layout.setComponentConstraints(subtitle, "pad 0 "+v+"% 0 "+v+"%");
        layout.setComponentConstraints(description, "pad 0 "+v+"% 0 "+v+"%");
        layout.setComponentConstraints(descriptionl, "pad 0 "+v+"% 0 "+v+"%");
    }
     //Repaint Panel Cover khi thay đổi giữa màn hình Đăng nhập/Đăng ký
    public void loginRight(double v){
        v=Double.parseDouble(df.format(v));
        login(true);
        layout.setComponentConstraints(logo, "pad 0 "+v+"% 0 "+v+"%");
        layout.setComponentConstraints(title, "pad 0 "+v+"% 0 "+v+"%");
        layout.setComponentConstraints(subtitle, "pad 0 "+v+"% 0 "+v+"%");
        layout.setComponentConstraints(description, "pad 0 "+v+"% 0 "+v+"%");
        layout.setComponentConstraints(descriptionl, "pad 0 "+v+"% 0 "+v+"%");
    }
    private void login(boolean login){
        if(this.isLogin!=login){
            //Set Data cho Panel cover khi ở màn hình đăng nhập
            if(login){
                logo.setIcon(new ImageIcon(getClass().getResource("/Icons/logo1.png")));
                title.setText("Bạn chưa có tài khoản tại");
                description.setText("Đừng lo, tạo mới một tài khoản");
                descriptionl.setText("và bắt đầu trải nghiệm của bạn với nhà hàng chúng tôi");
                button.setText("Đăng ký  >>");
            }else{
                //Set Data cho Panel cover khi ở màn hình đăng ký
                logo.setIcon(new ImageIcon(getClass().getResource("/Icons/logo2.jpg")));
                title.setText("Chào mừng bạn đến với nhà hàng");
                description.setText("Để sử dụng dịch vụ tại đây vui lòng");
                descriptionl.setText("đăng nhập với tài khoản cá nhân của bạn");
                button.setText("<<  Đăng nhập");
            }
            this.isLogin=login;
        }
    }
}

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    


