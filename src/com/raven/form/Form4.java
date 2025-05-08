package com.raven.form;

import com.raven.Connection.DatabaseConnection;
import com.raven.model.ModelUser;
import com.raven.transitions.TransitionsForm;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Form4 extends TransitionsForm {

    private DatabaseConnection cnConnection = new DatabaseConnection();
    private ModelUser user;

    public Form4(ModelUser user) {
        initComponents();
        this.user = user; // Khởi tạo người dùng
        loadData(); // Gọi phương thức để tải dữ liệu
    }

    private void loadData() {
        final String header[] = {"Mã hóa đơn", "Bàn", "Tầng", "Ngày thanh toán", "Giá", "Trạng thái"};
        DefaultTableModel model = new DefaultTableModel(header, 0);

        try {
            Connection connection = cnConnection.getConnection();
            Statement st = connection.createStatement();

            // Lấy idkh của khách hàng
            String queryKhachHang = "SELECT idkh FROM qlkhachhang WHERE ID_ND = '" + user.getUserID() + "'";
            ResultSet rsKhachHang = st.executeQuery(queryKhachHang);
            String sdt = null;
            if (rsKhachHang.next()) {
                sdt = rsKhachHang.getString("idkh");
            }

            // Lấy danh sách bàn đã đặt dựa trên số điện thoại
            String queryDatBan = "SELECT * FROM qlhoadon WHERE idkh = '" + sdt + "'";
            ResultSet rsDatBan = st.executeQuery(queryDatBan);
            while (rsDatBan.next()) {
                String maBan = rsDatBan.getString("idhd");
                String tang = rsDatBan.getString("idban");
                String ten = rsDatBan.getString("tang");
                String Sdt = rsDatBan.getString("ngaytt");
                int soLuongNguoi = rsDatBan.getInt("gia");
                String trangthai = rsDatBan.getString("trangthai");

                model.addRow(new Object[]{maBan, tang, ten, Sdt, soLuongNguoi, trangthai});
            }

            tbLS.setModel(model); // Đặt mô hình cho bảng

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background1 = new com.raven.swing.Background();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbLS = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 153, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Lịch sử hoá đơn");

        tbLS.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        tbLS.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tbLS);

        javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background1);
        background1.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 921, Short.MAX_VALUE)
            .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(background1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 853, Short.MAX_VALUE))
                    .addContainerGap()))
        );
        background1Layout.setVerticalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 483, Short.MAX_VALUE)
            .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(background1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane1)
                    .addContainerGap()))
        );

        add(background1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.swing.Background background1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbLS;
    // End of variables declaration//GEN-END:variables
}
