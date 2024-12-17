
package com.raven.formNV;

import com.raven.Connection.DatabaseConnection;
import com.raven.componert.Message;
import com.raven.formQL.formBan;
import com.raven.model.ModelDatBan;
import com.raven.model.ModelHD;
import com.raven.model.ModelUser;
import com.raven.model.Model_Cart;
import com.raven.service.ServiceBan;
import com.raven.service.ServiesHD;
import com.raven.swing.ScrollBar;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class formCNHoaDon extends javax.swing.JPanel {

    private MigLayout layout;
    private ModelHD mdb;
    private ServiesHD service;
    private ServiceBan serviceDB;
    private ModelDatBan mdbDB;
    formBan formBan;
    private ModelUser user;
    final String header[] = {"Mã hóa đơn", "Bàn", "Tầng", "Ngày thanh toán", "Giá", "Mã khách hàng", "Trạng thái"};
    DefaultTableModel tb = new DefaultTableModel(header, 0);
    DefaultTableModel tb1 = new DefaultTableModel(header, 0);
    int q, i;
    DatabaseConnection cn = new DatabaseConnection();
    Connection connection;
    public formCNHoaDon() {
        initComponents();
        service = new ServiesHD();
        mdb = new ModelHD();
        serviceBan = new ServiceBan();
        mdbB = new ModelDatBan();
        formBan = new formBan();
        txtID.setEnabled(false);
        txtGia.setEnabled(false);
        txtTK.setHint("Tìm kiếm theo bàn....");
        loadBang2();
        intt();
        updateTongHD();
        jScrollPane2.setVerticalScrollBar(new ScrollBar());
        cbBan.setEnabled(false);
        cbTang.setEnabled(false);
    }
    private void intt() {
        layout = new MigLayout("fill, insets 0");
        bg.setLayout(layout);
    }
    
    //hien thong bao
    private void showMessage(Message.MessageType messageType, String message) {
        Message ms = new Message();
        ms.showMessage(messageType, message);
        TimingTarget target = new TimingTargetAdapter() {
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


    public void loadBang2() {
        try {
            connection = cn.getConnection();
            int number;
            Vector row;
            String sql = "select * from qlhoadon where trangthai= 'Chua thanh toan'";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            ResultSetMetaData metadata = rs.getMetaData();
            number = metadata.getColumnCount();
            tb1.setRowCount(0);
            while (rs.next()) {
                row = new Vector();
                for (int i = 1; i <= number; i++) {
                    row.addElement(rs.getString(i));
                }
                tb1.addRow(row);
            }
            dgwCTT.setModel(tb1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void resetForm() {
        txtID.setText("");
        txtGia.setText("");
        txtDate.setText("");
        cbBan.setSelectedItem("");
        cbTang.setSelectedItem("");
        loadBang2();
        updateTongHD();
    }
    
    public boolean checkValidateForm2() {
        if (txtDate.getText().isEmpty()) {
            return false;
        }
        return true;
    }
    public void updateTongHD() {
        int totalEmployees = dgwCTT.getRowCount();
        cart1.setData(new Model_Cart(new ImageIcon(getClass().getResource("/Icons/inventory (1).png")), "Số hóa đơn chưa thanh toán", "" + totalEmployees));

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateChooser1 = new com.raven.datechooser.DateChooser();
        background1 = new com.raven.swing.Background();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cbBan = new javax.swing.JComboBox<>();
        cbTang = new javax.swing.JComboBox<>();
        button1 = new qlnh.swing.Button();
        button3 = new qlnh.swing.Button();
        btnSua = new qlnh.swing.Button();
        bg = new javax.swing.JLayeredPane();
        jLabel43 = new javax.swing.JLabel();
        txtID = new qlnh.swing.MyTextField();
        txtDate = new qlnh.swing.MyTextField();
        txtGia = new qlnh.swing.MyTextField();
        txtTK = new qlnh.swing.MyTextField();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        cart1 = new com.raven.componert.Cart();
        jLabel31 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        dgwCTT = new javax.swing.JTable();

        dateChooser1.setTextRefernce(txtDate);

        setLayout(new java.awt.BorderLayout());

        background1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Quản Lý Hóa Đơn");

        jLabel1.setText("Mã hóa đơn:");

        jLabel3.setText("Mã bàn: ");

        jLabel4.setText("Tầng");

        jLabel5.setText("Ngày thanh toán:");

        jLabel6.setText("Giá:");

        cbBan.setBackground(new java.awt.Color(83, 105, 118));
        cbBan.setForeground(new java.awt.Color(204, 204, 204));
        cbBan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ban 1", "Ban 2", "Ban 3", "Ban 4" }));

        cbTang.setBackground(new java.awt.Color(83, 105, 118));
        cbTang.setForeground(new java.awt.Color(204, 204, 204));
        cbTang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tang 1", "Tang 2", "Tang 3", "Tang 4" }));
        cbTang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTangActionPerformed(evt);
            }
        });

        button1.setBackground(new java.awt.Color(204, 183, 253));
        button1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/inventory (1).png"))); // NOI18N
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        button3.setBackground(new java.awt.Color(204, 183, 253));
        button3.setForeground(new java.awt.Color(51, 51, 51));
        button3.setText("Làm mới");
        button3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button3ActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(204, 183, 253));
        btnSua.setForeground(new java.awt.Color(51, 51, 51));
        btnSua.setText("Cập nhật hóa đơn");
        btnSua.setPreferredSize(new java.awt.Dimension(57, 26));
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
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
            .addGap(0, 120, Short.MAX_VALUE)
        );

        jLabel43.setText("Tìm kiếm:");

        txtID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtIDKeyPressed(evt);
            }
        });

        txtGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaActionPerformed(evt);
            }
        });

        txtTK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTKKeyReleased(evt);
            }
        });

        jLayeredPane1.setLayout(new java.awt.CardLayout());

        cart1.setColor1(new java.awt.Color(153, 255, 255));
        cart1.setColor2(new java.awt.Color(0, 204, 204));
        jLayeredPane1.add(cart1, "card2");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bg)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(90, 90, 90)
                                .addComponent(jLabel2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addGap(37, 37, 37)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbBan, javax.swing.GroupLayout.Alignment.TRAILING, 0, 216, Short.MAX_VALUE)
                                    .addComponent(cbTang, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(7, 7, 7))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel43))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtGia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtTK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(12, 12, 12))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(25, 25, 25)))))
                .addGap(495, 495, 495))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(495, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbBan, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cbTang, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(txtTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(254, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(500, Short.MAX_VALUE)
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        jLabel31.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel31.setText("Chưa thanh toán:");

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));

        dgwCTT.setModel(new javax.swing.table.DefaultTableModel(
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
        dgwCTT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dgwCTTMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(dgwCTT);

        javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background1);
        background1.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background1Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(background1Layout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE))
                .addContainerGap())
        );
        background1Layout.setVerticalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(background1Layout.createSequentialGroup()
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        add(background1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void cbTangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbTangActionPerformed

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        dateChooser1.showPopup(this, (getWidth() - dateChooser1.getPreferredSize().width) / 2, (getHeight() - dateChooser1.getPreferredSize().height) / 2);
    }//GEN-LAST:event_button1ActionPerformed

    private void button3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button3ActionPerformed
        resetForm();
    }//GEN-LAST:event_button3ActionPerformed

    ModelDatBan mdbB;
    ServiceBan serviceBan;
    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // Kiểm tra xem có dòng nào được chọn trong bảng hay không
        if (dgwCTT.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần thanh toán");
            return;
        }
        try {
            // Kiểm tra xem form đã được điền đầy đủ hay chưa
            if (!checkValidateForm2()) {
                showMessage(Message.MessageType.ERROR, "Vui lòng điền đầy đủ thông tin.");
            } else {
                mdb.setIdHD(txtID.getText());
                mdb.setNgayTT(txtDate.getText());
                String a = cbTang.getSelectedItem().toString();
                int index = a.lastIndexOf(" ") + 1;
                String numberPart = a.substring(index);
                String idb = cbBan.getSelectedItem().toString() + "T"+numberPart;
                mdbB.setIDBan(idb);
                mdbB.setTang(cbTang.getSelectedItem().toString());
                try {
                    service.UpdateHD(mdb);
                    serviceBan.HUYBan(mdbB);
                    resetForm();
                    showMessage(Message.MessageType.SUCCESS, "Cap nhat HD thành công.");

                } catch (Exception e) {
                    showMessage(Message.MessageType.ERROR, "Lỗi khi cap nhat.");
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            showMessage(Message.MessageType.ERROR, "Đã xảy ra lỗi không xác định.");
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void txtGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaActionPerformed

    //phương thức tìm kiếm
    public void timKiem(JTable table, int cot) {
        DefaultTableModel ob = (DefaultTableModel) table.getModel();//lấy mô hình dữ liệu của bảng
        TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(ob);//tạo bộ lọc hàng
        table.setRowSorter(obj);//thiết lập bộ lọc hàng cho bảng
        obj.setRowFilter(RowFilter.regexFilter(txtID.getText(), cot));//hiển thị kết quả
    }
    private void txtTKKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTKKeyReleased
        timKiem(dgwCTT, 1);
    }//GEN-LAST:event_txtTKKeyReleased

    private void dgwCTTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dgwCTTMouseClicked
        int x = dgwCTT.getSelectedRow();
        if (x >= 0) {
            txtID.setText(dgwCTT.getValueAt(dgwCTT.getSelectedRow(), 0) + "");
            String banData = dgwCTT.getValueAt(dgwCTT.getSelectedRow(), 1) + "";
            if (banData.startsWith("Ban") && banData.contains("T")) {
                // Lấy phần số giữa "Ban" và "T"
                String[] parts = banData.split("T");
                String banNumber = parts[0].replace("Ban", "").trim();
                cbBan.setSelectedItem("Ban " + banNumber);
            } else {
                // Nếu dữ liệu không hợp lệ, đặt giá trị mặc định
                cbBan.setSelectedItem("N/A");
            }
            cbTang.setSelectedItem(dgwCTT.getValueAt(dgwCTT.getSelectedRow(), 2) + "");
            txtDate.setText(dgwCTT.getValueAt(dgwCTT.getSelectedRow(), 3) + "");
            txtGia.setText(dgwCTT.getValueAt(dgwCTT.getSelectedRow(), 4) + "");

            txtID.setEnabled(false);
        }
    }//GEN-LAST:event_dgwCTTMouseClicked

    private void txtIDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIDKeyPressed
        
    }//GEN-LAST:event_txtIDKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.swing.Background background1;
    private javax.swing.JLayeredPane bg;
    private qlnh.swing.Button btnSua;
    private qlnh.swing.Button button1;
    private qlnh.swing.Button button3;
    private com.raven.componert.Cart cart1;
    private javax.swing.JComboBox<String> cbBan;
    private javax.swing.JComboBox<String> cbTang;
    private com.raven.datechooser.DateChooser dateChooser1;
    private javax.swing.JTable dgwCTT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private qlnh.swing.MyTextField txtDate;
    private qlnh.swing.MyTextField txtGia;
    private qlnh.swing.MyTextField txtID;
    private qlnh.swing.MyTextField txtTK;
    // End of variables declaration//GEN-END:variables
}
