
package com.raven.form;

import com.raven.Connection.DatabaseConnection;
import com.raven.componert.Message;
import com.raven.model.ModelKhachHang;
import com.raven.model.ModelUser;
import com.raven.service.ServiceKH;
import com.raven.transitions.TransitionsForm;
import com.view.Dialog.MS_ChangePassword;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import login.MainKhachHang;
import login.MainLogin;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class Form5 extends TransitionsForm {

    private DatabaseConnection cn = new DatabaseConnection();

    private MigLayout layout;
    private ModelUser user;
    private ModelKhachHang mdb;
    private ServiceKH service;
    private Icon hide;
    private Icon show;
    private char def;
    private MS_ChangePassword changepass;
    JLabel lbid = new JLabel();

    public Form5() {
        initComponents();
        service = new ServiceKH();
        mdb = new ModelKhachHang();
        intt();
    }
    
    
    
    public Form5(ModelUser user) {
        this.user = user;
        service = new ServiceKH();
        mdb = new ModelKhachHang();
        initComponents();
        intt();
        displayUserInfo(user);
        init();
    }
    
    private void intt(){
        layout = new MigLayout("fill, insets 0");
        bg.setLayout(layout);
    }
    public void init() {
        service = new ServiceKH();
        changepass = new MS_ChangePassword(MainKhachHang.getFrames()[0], true);
        hide = new ImageIcon(getClass().getResource("/Icons/hide.png"));
        show = new ImageIcon(getClass().getResource("/Icons/view.png"));
        def = txtmatkhau.getEchoChar();
        initUser_information();
        //initCustomer_information();
        txtmatkhau.addMouseListener(new MouseAdapter() {
         
            public void mouseClicked(MouseEvent e) {
                if (txtmatkhau.getSuffixIcon().equals(hide)) {
                    txtmatkhau.setSuffixIcon(show);
                    txtmatkhau.setEchoChar((char) 0);

                } else {
                    txtmatkhau.setSuffixIcon(hide);
                    txtmatkhau.setEchoChar(def);
                }
            }

        });
    }
    
    public void initUser_information() {
        txtemail.setText(user.getEmail());
        txtmatkhau.setText(user.getPassword());
        txtrole.setText(user.getRole());
        txtmatkhau.setSuffixIcon(hide);
    }

//    public void initCustomer_information() {
//        try {
//            mdb = service.getCustomer(user.getUserID());
//            txtmaKH.setText(customer.getID_KH() + "");
//            txttenKH.setText(customer.getName());
//            txtngayTG.setText(customer.getDateJoin());
//            txtdso.setText(customer.getSales() + "đ");
//            txtdiemtl.setText(customer.getPoints() + " xu");
//        } catch (SQLException ex) {
//            Logger.getLogger(AccountC_Form.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    // Phương thức hiển thị thông tin khách hàng
    public void displayUserInfo(ModelUser user) {
        // Thay thế user.getUserID() bằng ID_ND để lấy thông tin từ bảng khachhang và nguoidung
        int userID = user.getUserID();  // ID_ND của khách hàng

        try {
            Connection conn = cn.getConnection();
            // Truy vấn để lấy thông tin khách hàng
            String sql = "SELECT kh.idkh, kh.tenKH, kh.GioiTinh, kh.SDT, kh.DiaChi "
                    + "FROM qlkhachhang kh "
                    + "JOIN nguoidung nd ON kh.ID_ND = nd.ID_ND "
                    + "WHERE nd.ID_ND = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userID);

            ResultSet rs = stmt.executeQuery();
            // Đặt dữ liệu vào các trường giao diện nếu tìm thấy khách hàng
            if (rs.next()) {
                lbid.setText(rs.getString("idkh"));
                txtName.setText(rs.getString("tenKH"));
                txtSdt.setText(rs.getString("SDT"));
                txtDC.setText(rs.getString("DiaChi"));
                // Thiết lập giá trị giới tính
                // Kiểm tra nếu giới tính không phải là null
                String gender = rs.getString("GioiTinh");
                if (gender != null) {
                    cbGT.setSelectedItem(gender.equalsIgnoreCase("Nam") ? "Nam" : "Nữ");
                } else {
                    cbGT.setSelectedItem("Nam"); // Hoặc giá trị mặc định tùy chọn
                }
            }

            // Đóng kết nối
            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể tải thông tin khách hàng.");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background1 = new com.raven.swing.Background();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        cbGT = new javax.swing.JComboBox<>();
        cmdDMK = new qlnh.swing.Button();
        lblUserName = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblRole = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        bg = new javax.swing.JLayeredPane();
        titleND = new javax.swing.JLabel();
        imageAvatar1 = new qlnh.swing.ImageAvatar();
        lbEmail = new javax.swing.JLabel();
        lbmk = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        lbRole = new javax.swing.JLabel();
        txtName = new qlnh.swing.MyTextField();
        txtSdt = new qlnh.swing.MyTextField();
        txtDC = new qlnh.swing.MyTextField();
        button2 = new qlnh.swing.Button();
        txtrole = new qlnh.swing.MyTextField();
        txtemail = new qlnh.swing.MyTextField();
        txtmatkhau = new qlnh.swing.MyPasswordField();
        jSeparator1 = new javax.swing.JSeparator();

        setName(""); // NOI18N

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 153, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Thông tin cá nhân");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        cbGT.setBackground(new java.awt.Color(175, 185, 203));
        cbGT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));

        cmdDMK.setBackground(new java.awt.Color(215, 221, 232));
        cmdDMK.setForeground(new java.awt.Color(89, 89, 89));
        cmdDMK.setText("Đổi mật khẩu");
        cmdDMK.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cmdDMK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdDMKActionPerformed(evt);
            }
        });

        lblUserName.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lblUserName.setText("Họ tên:");

        lblEmail.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lblEmail.setText("Số điện thoại:");

        lblRole.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lblRole.setText("Giới tính:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel2.setText("Địa chỉ: ");

        titleND.setBackground(new java.awt.Color(255, 255, 255));
        titleND.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        titleND.setForeground(new java.awt.Color(108, 91, 123));
        titleND.setText("Thông tin TÀI KHOẢN");

        bg.setLayer(titleND, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bgLayout.createSequentialGroup()
                .addContainerGap(82, Short.MAX_VALUE)
                .addComponent(titleND)
                .addGap(30, 30, 30))
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addComponent(titleND)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        imageAvatar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/profile.jpg"))); // NOI18N

        lbEmail.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbEmail.setForeground(new java.awt.Color(89, 89, 89));
        lbEmail.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbEmail.setText("Email");

        lbmk.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbmk.setForeground(new java.awt.Color(89, 89, 89));
        lbmk.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbmk.setText("Mật Khẩu");

        jSeparator2.setBackground(new java.awt.Color(76, 76, 76));

        lbRole.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbRole.setForeground(new java.awt.Color(89, 89, 89));
        lbRole.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbRole.setText("Vai trò");

        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });

        button2.setBackground(new java.awt.Color(215, 221, 232));
        button2.setForeground(new java.awt.Color(89, 89, 89));
        button2.setText("Cập nhật thông tin");
        button2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });

        txtrole.setBackground(new java.awt.Color(175, 185, 203));

        txtemail.setBackground(new java.awt.Color(175, 185, 203));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(57, 57, 57)
                                .addComponent(bg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(229, 229, 229))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(imageAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(203, 203, 203)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblUserName)
                                    .addComponent(lblEmail)
                                    .addComponent(lblRole)
                                    .addComponent(jLabel2))
                                .addGap(43, 43, 43)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(cbGT, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtDC, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbmk, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtemail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtmatkhau, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                        .addGap(56, 56, 56)
                        .addComponent(lbRole, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmdDMK, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(txtrole, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(140, 140, 140))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(132, 132, 132)
                    .addComponent(jSeparator2)
                    .addGap(132, 132, 132)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblUserName))
                        .addGap(37, 37, 37)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEmail)
                            .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblRole)
                            .addComponent(cbGT, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtDC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(imageAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(button2, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(bg))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lbEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmdDMK, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lbmk, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtmatkhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbRole, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtrole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(58, 58, 58)))
                .addContainerGap(78, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(374, 374, 374)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(202, Short.MAX_VALUE)))
        );

        jSeparator1.setBackground(new java.awt.Color(76, 76, 76));

        javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background1);
        background1.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background1Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(background1Layout.createSequentialGroup()
                    .addGap(135, 135, 135)
                    .addComponent(jSeparator1)
                    .addGap(135, 135, 135)))
        );
        background1Layout.setVerticalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, background1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(background1Layout.createSequentialGroup()
                    .addGap(59, 59, 59)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(587, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    public boolean checkValidate(){//kiem tra xem cac o text da duoc dien day du chua
        if(txtName.getText().isEmpty()
          ||txtDC.getText().isEmpty()
          ||txtSdt.getText().isEmpty()
          ||cbGT.getSelectedItem().toString().isEmpty()){
            return false;
        }
        return true;
    }
    private void cmdDMKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdDMKActionPerformed
        changepass.ChangePassword(user);
        txtmatkhau.setText(user.getPassword());
    }//GEN-LAST:event_cmdDMKActionPerformed

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        try {
            if (!checkValidate()) {
                showMessage(Message.MessageType.ERROR, "Vui lòng điền đầy đủ thông tin.");
            } else {
                try {
                    mdb.setIDKH(Integer.parseInt(lbid.getText()));
                    mdb.setName(txtName.getText());
                    mdb.setSdt(txtSdt.getText());
                    mdb.setDiaChi(txtDC.getText()); 
                    mdb.setGioiTinh(cbGT.getSelectedItem().toString());
                    service.UpdateKH(mdb);
                    showMessage(Message.MessageType.SUCCESS, "Sửa thông tin thành công.");
                } catch (Exception e) {
                    showMessage(Message.MessageType.ERROR, "Lỗi khi sửa.");
                    e.printStackTrace();
                }
            }
        }catch (Exception e) {
            showMessage(Message.MessageType.ERROR, "Đã xảy ra lỗi không xác định.");
            e.printStackTrace();
        } 
    }//GEN-LAST:event_button2ActionPerformed

     //hien thong bao
    private void showMessage(Message.MessageType messageType, String message){
        Message ms = new Message();
        ms.showMessage(messageType, message);
        TimingTarget target;
        target = new TimingTargetAdapter(){
            @Override
            public void begin() {
                if(!ms.isShow()){
                    bg.add(ms,"pos 0.5al 0",0); //Insert to bg first index 0
                    ms.setVisible(true);
                    bg.repaint();
                }
            }

            @Override
            public void timingEvent(float fraction) {
                float f;
                if(ms.isShow()){
                    f=40*(1f-fraction);
                }else{
                    f=fraction*40;
                }
                layout.setComponentConstraints(ms, "pos 0.5al "+(int)(f-30));
                bg.repaint();
                bg.revalidate();
            }

            @Override
            public void end() {
                if(ms.isShow()){
                    bg.remove(ms);
                    bg.repaint();
                    bg.revalidate();
                }else{
                    ms.setShow(true);
                }
            }
        };
        Animator animator = new Animator(300, target);
        animator.setResolution(0);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        animator.start();
        new Thread(new Runnable(){
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                    animator.start();
                }catch(InterruptedException e){
                    System.err.println(e);
                }
            }
        }).start();
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.swing.Background background1;
    private javax.swing.JLayeredPane bg;
    private qlnh.swing.Button button2;
    private javax.swing.JComboBox<String> cbGT;
    private qlnh.swing.Button cmdDMK;
    private qlnh.swing.ImageAvatar imageAvatar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lbEmail;
    private javax.swing.JLabel lbRole;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblRole;
    private javax.swing.JLabel lblUserName;
    private javax.swing.JLabel lbmk;
    private javax.swing.JLabel titleND;
    private qlnh.swing.MyTextField txtDC;
    private qlnh.swing.MyTextField txtName;
    private qlnh.swing.MyTextField txtSdt;
    private qlnh.swing.MyTextField txtemail;
    private qlnh.swing.MyPasswordField txtmatkhau;
    private qlnh.swing.MyTextField txtrole;
    // End of variables declaration//GEN-END:variables
}