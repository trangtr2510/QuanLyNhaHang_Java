package com.raven.formNVKho;

import com.raven.Connection.DatabaseConnection;
import com.raven.model.ChiTietPhieuNhap;
import com.raven.model.ModelNguyenLieu;
import com.raven.model.ModelPhieuNhap;
import com.raven.model.ModelUser;
import com.raven.service.ChiTietPhieuNhapDAO;
import com.raven.service.PhieuNhapDAO;
import com.raven.service.ServiceNguyenLieu;
import java.sql.Connection;
import java.sql.*;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CTPhieuNhap extends javax.swing.JDialog {

    ModelPhieuNhap phieuNhap;
    ChiTietPhieuNhapDAO dao;
    private ModelNguyenLieu mdb;
    private ModelUser user;
    private ServiceNguyenLieu service;
    final String header[] = {"STT", "Mã phiếu nhập", "Mã nguyên liệu", "Tên nguyên liệu", "Nhà cung cấp", "Đơn vị tính", "Hạn sử dụng",
        "Số lượng", "Giá"};
    DefaultTableModel tb = new DefaultTableModel(header, 0);
    int q, i;
    DatabaseConnection cn = new DatabaseConnection();
    private Connection connection;

    public CTPhieuNhap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        connectToDatabase();
    }

    // Method to ensure the connection is always open
    private void connectToDatabase() {
        if (connection == null || isConnectionClosed()) {
            connection = DatabaseConnection.getInstance().getConnection();
        }
    }

    // Check if the connection is closed
    private boolean isConnectionClosed() {
        try {
            return connection == null || connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return true; // Return true if the connection is closed or null
        }
    }

    public CTPhieuNhap(int mapn) throws SQLException {
        System.out.println("Connection open: " + (connection != null && !connection.isClosed()));
        connectToDatabase();
        phieuNhap = new ModelPhieuNhap();
        service = new ServiceNguyenLieu();
        initComponents();
        loadBang(mapn);
        String id = String.valueOf(mapn);
        hienThiThongTinPhieuNhap(id);
    }

    public void loadBang(int mapn) {
        try (Connection connection = cn.getConnection(); // Kết nối cơ sở dữ liệu
                 PreparedStatement pst = connection.prepareStatement(
                        "SELECT NL.TenNL, NL.NhaCungCap, NL.DonViTinh, NL.HanSuDung, "
                        + "CT.MaPN, CT.MaNL, CT.SoLuong, CT.DonGia "
                        + "FROM ChiTietPhieuNhap CT "
                        + "JOIN NguyenLieu NL ON CT.MaNL = NL.MaNL "
                        + "WHERE CT.MaPN = ?")) {

            Vector row; // Dùng để lưu một hàng dữ liệu
            pst.setInt(1, mapn);
            ResultSet rs = pst.executeQuery();

            // Xóa dữ liệu cũ trong model
            tb.setRowCount(0);
            int stt = 1; // Bắt đầu STT từ 1

            // Duyệt qua kết quả truy vấn
            while (rs.next()) {
                row = new Vector();

                // Thêm cột STT
                row.add(stt++);

                // Thêm các cột dữ liệu từ cơ sở dữ liệu
                row.add(rs.getString("MaPN")); // Mã phiếu nhập
                row.add(rs.getString("MaNL")); // Mã nguyên liệu
                row.add(rs.getString("TenNL")); // Tên nguyên liệu
                row.add(rs.getString("NhaCungCap")); // Nhà cung cấp
                row.add(rs.getString("DonViTinh")); // Đơn vị tính
                row.add(rs.getDate("HanSuDung")); // Hạn sử dụng
                row.add(rs.getInt("SoLuong")); // Số lượng
                row.add(rs.getFloat("DonGia")); // Giá

                // Thêm hàng vào model
                tb.addRow(row);
            }

            // Gán model cho bảng
            tblChiTietPhieu.setModel(tb);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi khi tải dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void hienThiThongTinPhieuNhap(String idPN) {
        try (Connection connection = cn.getConnection()) {
            // Gọi hàm selectById để lấy thông tin phiếu nhập
            phieuNhap = service.selectById(idPN);

            // Kiểm tra kết quả trả về
            if (phieuNhap != null) {
                // Hiển thị thông tin lên các JLabel
                labelThoiGianTao.setText(phieuNhap.getNgayNhap().toString());
                labelNguoiTao.setText(String.valueOf(phieuNhap.getIdND())); // Hiển thị ID người dùng
                labelTongTien.setText(String.format("%,.2f VND", phieuNhap.getTongTien())); // Định dạng tiền tệ
                labelMaPhieu.setText(String.valueOf(phieuNhap.getMaPN()));
            } else {
                // Nếu không tìm thấy phiếu nhập, hiển thị thông báo
                JOptionPane.showMessageDialog(null, "Không tìm thấy thông tin phiếu nhập!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi khi lấy thông tin phiếu nhập!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblChiTietPhieu = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        labelTongTien = new javax.swing.JLabel();
        labelMaPhieu = new javax.swing.JLabel();
        labelNguoiTao = new javax.swing.JLabel();
        labelThoiGianTao = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(0, 204, 102));

        jLabel1.setFont(new java.awt.Font("SF Pro Display", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("CHI TIẾT PHIẾU NHẬP");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(280, 280, 280)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel2.setText("Mã phiếu: ");

        tblChiTietPhieu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã phiếu nhập", "Mã nguyên liệu", "Số lượng", "Đơn giá"
            }
        ));
        jScrollPane1.setViewportView(tblChiTietPhieu);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel4.setText("Mã người dùng tạo:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel5.setText("Thời gian tạo:");

        labelTongTien.setFont(new java.awt.Font("SF Pro Display", 1, 18)); // NOI18N
        labelTongTien.setText("...đ");

        labelMaPhieu.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelMaPhieu.setText("jLabel7");

        labelNguoiTao.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelNguoiTao.setText("jLabel7");

        labelThoiGianTao.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelThoiGianTao.setText("jLabel7");

        jLabel7.setFont(new java.awt.Font("SF Pro Display", 1, 18)); // NOI18N
        jLabel7.setText("TỔNG TIỀN:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(labelTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelMaPhieu, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelNguoiTao, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelThoiGianTao, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(labelMaPhieu)
                    .addComponent(labelNguoiTao)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(labelThoiGianTao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(labelTongTien))
                .addGap(39, 39, 39))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(CTPhieuNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CTPhieuNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CTPhieuNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CTPhieuNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CTPhieuNhap dialog = new CTPhieuNhap(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelMaPhieu;
    private javax.swing.JLabel labelNguoiTao;
    private javax.swing.JLabel labelThoiGianTao;
    private javax.swing.JLabel labelTongTien;
    private javax.swing.JTable tblChiTietPhieu;
    // End of variables declaration//GEN-END:variables
}
