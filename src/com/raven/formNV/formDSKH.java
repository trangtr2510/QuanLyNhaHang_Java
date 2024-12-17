package com.raven.formNV;

import com.raven.formQL.*;
import com.raven.Connection.DatabaseConnection;
import com.raven.componert.Message;
import com.raven.model.ModelKhachHang;
import com.raven.model.Model_Cart;
import com.raven.service.ServiceKH;
import com.raven.swing.ScrollBar;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Vector;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class formDSKH extends javax.swing.JPanel {

    private MigLayout layout;
    private ModelKhachHang mdb;
    private ServiceKH service;
    final String header[] = {"Mã khách hàng", "Tên khách hàng", "Giới tính", "Số điện thoại", "Địa chỉ",
        "Mã người dùng", "Ngày tham gia"};
    DefaultTableModel tb = new DefaultTableModel(header, 0);
    DefaultTableModel tb1 = new DefaultTableModel(header, 0);
    int q, i;
    DatabaseConnection cn = new DatabaseConnection();
    Connection connection;

    public formDSKH() {
        initComponents();
        //txtND.setEnabled(false);
        service = new ServiceKH();
        mdb = new ModelKhachHang();
        loadBang();
        loadBang2();
        jScrollPane1.setVerticalScrollBar(new ScrollBar());
        jScrollPane3.setVerticalScrollBar(new ScrollBar());
        txtID.setHint("Tìm kiếm theo mã Khách hàng");
    }


    private void updateTongKH() {
        int totalEmployees = dgwKH.getRowCount();
        int totalEmployees2 = dgwTT.getRowCount();
        cart1.setData(new Model_Cart(new ImageIcon(getClass().getResource("/Icons/customer (1).png")), "Tổng khách hàng", "" + totalEmployees));
        cart2.setData(new Model_Cart(new ImageIcon(getClass().getResource("/Icons/customer (1).png")), "Tổng khách hàng thân thiết", "" + totalEmployees2));

    }

    //phuong thuc tim kiem
    public void timKiem(JTable table, JTextField text, int cot) {
        DefaultTableModel ob = (DefaultTableModel) table.getModel();                     //lay mo hinh du lieu cua bang 
        TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(ob);           //them bo sap xep hang vao trong bang
        table.setRowSorter(obj);                                                    //thiet lap bo sap sep hang vao bang 
        obj.setRowFilter(RowFilter.regexFilter(text.getText(), cot));   //loc cac hang co chuoi can tim thuoc cot 
    }

    public void loadBang2() {
        try {
            connection = cn.getConnection();
            int number;
            Vector row;
            String sql = "SELECT kh.idkh, kh.TenKH, kh.Gioitinh, kh.Sdt, kh.Diachi, kh.ID_ND, kh.Ngaythamgia "
                    + "FROM qlkhachhang kh "
                    + "JOIN qlhoadon hd ON kh.idkh = hd.idkh "
                    + "WHERE kh.idkh != 0 "
                    + "GROUP BY kh.idkh, kh.TenKH, kh.Gioitinh, kh.Sdt, kh.Diachi, kh.ID_ND, kh.Ngaythamgia "
                    + "HAVING COUNT(hd.idhd) > 10";

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
            dgwTT.setModel(tb1);
            updateTongKH();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadBang() {
        try {
            connection = cn.getConnection();
            int number;
            Vector row;
            String sql = "select * from qlkhachhang";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            ResultSetMetaData metadata = rs.getMetaData();
            number = metadata.getColumnCount();
            tb.setRowCount(0);
            while (rs.next()) {
                row = new Vector();
                for (int i = 1; i <= number; i++) {
                    row.addElement(rs.getString(i));
                }
                tb.addRow(row);
            }
            dgwKH.setModel(tb);
            updateTongKH();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background1 = new com.raven.swing.Background();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnTK = new qlnh.swing.Button();
        txtID = new qlnh.swing.MyTextField();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        cart1 = new com.raven.componert.Cart();
        cart2 = new com.raven.componert.Cart();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        dgwTT = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        dgwKH = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Danh Sách Khách Hàng");

        jLabel6.setText("Tìm kiếm: ");

        btnTK.setBackground(new java.awt.Color(204, 183, 253));
        btnTK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/search.png"))); // NOI18N
        btnTK.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        btnTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTKActionPerformed(evt);
            }
        });

        txtID.setHint("Tìm kiếm theo tên khách hàng");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(31, 31, 31)
                .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTK, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(599, Short.MAX_VALUE))
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jLayeredPane1.setLayout(new java.awt.GridLayout(1, 0, 30, 0));

        cart1.setColor1(new java.awt.Color(255, 204, 204));
        cart1.setColor2(new java.awt.Color(204, 51, 255));
        jLayeredPane1.add(cart1);

        cart2.setColor1(new java.awt.Color(153, 255, 204));
        cart2.setColor2(new java.awt.Color(0, 153, 153));
        jLayeredPane1.add(cart2);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane3MouseClicked(evt);
            }
        });

        dgwTT.setModel(new javax.swing.table.DefaultTableModel(
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
        dgwTT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dgwTTMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(dgwTT);

        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseClicked(evt);
            }
        });

        dgwKH.setModel(new javax.swing.table.DefaultTableModel(
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
        dgwKH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dgwKHMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(dgwKH);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel9.setText("Khách hàng thân thiết:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background1);
        background1.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 938, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, background1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(background1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        background1Layout.setVerticalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, background1Layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(background1Layout.createSequentialGroup()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 568, Short.MAX_VALUE)))
        );

        add(background1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jScrollPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseClicked

    }//GEN-LAST:event_jScrollPane1MouseClicked

    private void dgwKHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dgwKHMouseClicked

    }//GEN-LAST:event_dgwKHMouseClicked

    private void btnTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTKActionPerformed
        timKiem(dgwKH, txtID, 0);
    }//GEN-LAST:event_btnTKActionPerformed

    private void dgwTTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dgwTTMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_dgwTTMouseClicked

    private void jScrollPane3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane3MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.swing.Background background1;
    private qlnh.swing.Button btnTK;
    private com.raven.componert.Cart cart1;
    private com.raven.componert.Cart cart2;
    private javax.swing.JTable dgwKH;
    private javax.swing.JTable dgwTT;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private qlnh.swing.MyTextField txtID;
    // End of variables declaration//GEN-END:variables
}
