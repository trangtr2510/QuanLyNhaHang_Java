package com.raven.formQL;

import com.raven.Connection.DatabaseConnection;
import com.raven.componert.Message;
import com.raven.model.ModelUser;
import com.raven.service.ServiceUser;
import java.awt.Color;
import java.awt.Frame;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class formUser extends javax.swing.JPanel {

    final String header[] = {"Mã người dùng", "Email", "Mật khẩu", "Verifycode", "Trạng thái", "Vai trò"};//tieu de cua bang
    DefaultTableModel tb = new DefaultTableModel(header, 0);//them tieu de vao hang dau cua bang
    int q, i;
    DatabaseConnection cn = new DatabaseConnection();//class ket noi voi database
    Connection connection;
    private Icon ihide;
    private Icon ishow;
    private char def;
    private final Animator animator;
    private boolean show = true;
    private Frame frame;
    ModelUser mdb;
    ServiceUser service;
    private MigLayout layout;

    public formUser() {
        initComponents();
        loadBang();
        intt();
        checkPQ();
        dgwND.getSelectionModel().addListSelectionListener(e -> {
            checkPQ(); 
        });
        mdb = new ModelUser();
        service = new ServiceUser();
        animator = new Animator(200);
        animator.setResolution(0);
        animator.setAcceleration(0.5f);
        ihide = new ImageIcon(getClass().getResource("/Icons/hide.png"));
        ishow = new ImageIcon(getClass().getResource("/Icons/view.png"));
        def = txtPass.getEchoChar();
        txtPass.setSuffixIcon(ihide);
    }

    private void intt() {
        layout = new MigLayout("fill, insets 0");
        bg.setLayout(layout);
    }

    public void loadBang() {
        try {
            connection = cn.getConnection();//ket noi toi database
            int number;
            Vector row;//tao 1 vector chua cac hang
            String sql = "select * from nguoidung";//cau truy van
            Statement st = connection.createStatement();//cung cap phuong thuc de thucc thi truy van voi database
            ResultSet rs = st.executeQuery(sql);//con tro den hang dau cua bang ket qua cua cau sql select 
            ResultSetMetaData metadata = rs.getMetaData();// lay cac thong tin tu ResultSet
            number = metadata.getColumnCount();
            tb.setRowCount(0);
            while (rs.next()) {//con tro duy chuyen den hang tiep theo
                row = new Vector();
                for (int i = 1; i <= number; i++) {
                    row.addElement(rs.getString(i));//them thong tin vao tung hang trong bang
                }
                tb.addRow(row);//them hang vao bang
            }
            dgwND.setModel(tb);
        } catch (Exception e) {//xu ly ngoai le
            e.printStackTrace();
        }
    }

    //hien thong bao
    private void showMessage(Message.MessageType messageType, String message) {
        Message ms = new Message();
        ms.showMessage(messageType, message);
        TimingTarget target;
        target = new TimingTargetAdapter() {
            @Override
            public void begin() {
                if (!ms.isShow()) {
                    bg.add(ms, "pos 0.5al 0", 0); //Insert to bg first index 0
                    ms.setVisible(true);
                    bg.repaint();
                }
            }

            @Override
            public void timingEvent(float fraction) {
                float f;
                if (ms.isShow()) {
                    f = 40 * (1f - fraction);
                } else {
                    f = fraction * 40;
                }
                layout.setComponentConstraints(ms, "pos 0.5al " + (int) (f - 30));
                bg.repaint();
                bg.revalidate();
            }

            @Override
            public void end() {
                if (ms.isShow()) {
                    bg.remove(ms);
                    bg.repaint();
                    bg.revalidate();
                } else {
                    ms.setShow(true);
                }
            }
        };
        Animator animator = new Animator(300, target);
        animator.setResolution(0);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        animator.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    animator.start();
                } catch (InterruptedException e) {
                    System.err.println(e);
                }
            }
        }).start();
    }

    public void resetForm() {
        loadBang();
        txtEmail.setText("");
        txtPass.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background1 = new com.raven.swing.Background();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtTK = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnEdit1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtEmail = new qlnh.swing.MyTextField();
        txtPass = new qlnh.swing.MyPasswordField();
        bg = new javax.swing.JLayeredPane();
        jLabel5 = new javax.swing.JLabel();
        cbVT = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        dgwND = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 153, 255));
        jLabel1.setText("Danh sách người dùng");

        jLabel2.setText("Tìm kiếm theo mã người dùng:");

        txtTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTKActionPerformed(evt);
            }
        });
        txtTK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTKKeyReleased(evt);
            }
        });

        jLabel4.setText("Mật khẩu:");

        jToolBar1.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar1.setBorder(javax.swing.BorderFactory.createTitledBorder("Chức năng"));
        jToolBar1.setRollover(true);

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_add_40px.png"))); // NOI18N
        btnAdd.setText("Thêm");
        btnAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdd.setFocusable(false);
        btnAdd.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAdd.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAdd);

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_delete_40px.png"))); // NOI18N
        btnDelete.setText("Xoá");
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDelete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDelete);

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_edit_40px.png"))); // NOI18N
        btnEdit.setText("Phân quyền");
        btnEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEdit.setFocusable(false);
        btnEdit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEdit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        jToolBar1.add(btnEdit);

        btnEdit1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/change.png"))); // NOI18N
        btnEdit1.setText("Làm mới");
        btnEdit1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEdit1.setFocusable(false);
        btnEdit1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEdit1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEdit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEdit1ActionPerformed(evt);
            }
        });
        jToolBar1.add(btnEdit1);

        jLabel3.setText("Email:");

        txtPass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPassMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 45, Short.MAX_VALUE)
        );

        jLabel5.setText("Vai trò:");

        cbVT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nhan Vien", "Nhan Vien Kho" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(45, 45, 45)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbVT, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(bg)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(txtTK)))))
                .addContainerGap(248, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(cbVT, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(bg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 44, 44))))
        );

        dgwND.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(dgwND);

        javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background1);
        background1.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        background1Layout.setVerticalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(background1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void txtTKKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTKKeyReleased
        DefaultTableModel ob = (DefaultTableModel) dgwND.getModel();
        TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(ob);
        dgwND.setRowSorter(obj);
        obj.setRowFilter(RowFilter.regexFilter(txtTK.getText(), 0));
    }//GEN-LAST:event_txtTKKeyReleased

    public boolean checkValidate() {
        if (txtPass.getText().isEmpty()
                || txtEmail.getText().isEmpty()) {
            return false;
        }

        return true;
    }

    public void checkPQ() {
        int selectedRow = dgwND.getSelectedRow();
        // Kiểm tra nếu không có dòng nào được chọn
        if (selectedRow == -1) {
            cbVT.setEnabled(false);  // Vô hiệu hóa cbVT nếu không có dòng được chọn
        } else {
            cbVT.setEnabled(true);   // Kích hoạt cbVT nếu có dòng được chọn
        }
    }

    private void txtTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTKActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTKActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        try {
            if (!checkValidate()) {
                showMessage(Message.MessageType.ERROR, "Vui lòng điền đầy đủ thông tin.");
            } else {
                try {
                    mdb.setEmail(txtEmail.getText());
                    mdb.setPassword(txtPass.getText());
                    if (service.checkDuplicateEmail(mdb.getEmail())) {
                        showMessage(Message.MessageType.ERROR, "Email đã tồn tại.");
                    } else {
                        service.insertND(mdb);
                        resetForm();
                        showMessage(Message.MessageType.SUCCESS, "Thêm người dùng thành công.");
                    }
                } catch (Exception e) {
                    showMessage(Message.MessageType.ERROR, "Lỗi khi thêm người dùng.");
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            showMessage(Message.MessageType.ERROR, "Đã xảy ra lỗi không xác định.");
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int selectedRow = dgwND.getSelectedRow();
        try {
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn người dùng để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            } else {
                int ck = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn xóa người dùng này? Có thể người dùng này là 1 khách hàng hoặc nhân viên của quán?", "Thông báo", JOptionPane.YES_NO_OPTION);
                if (ck == JOptionPane.YES_OPTION) {
                    String maPNString = (String) dgwND.getValueAt(selectedRow, 0);
                    int maPN = Integer.parseInt(maPNString);
                    service.deleteUser(maPN);
                    resetForm();
                    showMessage(Message.MessageType.SUCCESS, "Xóa người dùng thành công.");//thong báo thành công
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        try {
            int selectedRow = dgwND.getSelectedRow();
            // Kiểm tra nếu không có dòng nào được chọn
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn người dùng để phân quyền!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            } else {
                try {
                    String maPNString = (String) dgwND.getValueAt(selectedRow, 0);
                    int maPN = Integer.parseInt(maPNString);
                    mdb.setUserID(maPN);
                    mdb.setEmail(txtEmail.getText());
                    mdb.setRole(cbVT.getSelectedItem().toString());
                    service.updateUserStatus(mdb.getUserID(), mdb.getRole());
                    resetForm();
                    showMessage(Message.MessageType.SUCCESS, "Phân quyền thành công.");
                } catch (Exception e) {
                    showMessage(Message.MessageType.ERROR, "Lỗi khi phân quyền.");
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            showMessage(Message.MessageType.ERROR, "Đã xảy ra lỗi không xác định.");
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnEdit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEdit1ActionPerformed
//        resetForm();
    }//GEN-LAST:event_btnEdit1ActionPerformed

    private void txtPassMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPassMouseClicked
        if (txtPass.getSuffixIcon().equals(ihide)) {
            txtPass.setSuffixIcon(ishow);
            txtPass.setEchoChar((char) 0);

        } else {
            txtPass.setSuffixIcon(ihide);
            txtPass.setEchoChar(def);
        }
    }//GEN-LAST:event_txtPassMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.swing.Background background1;
    private javax.swing.JLayeredPane bg;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnEdit1;
    private javax.swing.JComboBox<String> cbVT;
    private javax.swing.JTable dgwND;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private qlnh.swing.MyTextField txtEmail;
    private qlnh.swing.MyPasswordField txtPass;
    private javax.swing.JTextField txtTK;
    // End of variables declaration//GEN-END:variables
}
