package com.raven.formNVKho;

import com.raven.Connection.DatabaseConnection;
import com.raven.model.ChiTietPhieuNhap;
import com.raven.model.ModelNguyenLieu;
import com.raven.model.ModelPhieuNhap;
import com.raven.model.ModelUser;
import com.raven.service.ChiTietPhieuNhapDAO;
import com.raven.service.PhieuNhapDAO;
import com.raven.service.ServiceNguyenLieu;
import com.raven.swing.ScrollBar;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.*;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class formNhapNL extends javax.swing.JPanel {

    private ModelNguyenLieu mdb;
    private ModelUser user;
    private ServiceNguyenLieu service;
    final String header[] = {"Mã nguyên liệu", "Tên nguyên liệu", "Đơn vị tính",
        "Nhà cung cấp", "Giá", "Hạn sử dụng"};
    DefaultTableModel tb = new DefaultTableModel(header, 0);
    int q, i;
    DatabaseConnection cn = new DatabaseConnection();
    Connection connection;
    private String MaPhieu;
    DecimalFormat formatter = new DecimalFormat("###,###,###");

    public formNhapNL(ModelUser user) {
        mdb = new ModelNguyenLieu();
        this.user = user;
        service = new ServiceNguyenLieu();
        initComponents();
        loadBang();
        txtNguoiTao.setText("" + user.getUserID());
        txtMaPhieu.setText("" + idPN());
        txtMaPhieu.setEnabled(false);
        txtNguoiTao.setEnabled(false);
        jScrollPane1.setVerticalScrollBar(new ScrollBar());
        jScrollPane2.setVerticalScrollBar(new ScrollBar());
    }

    public int idPN() {
        int id = 1;  // Giá trị mặc định nếu không có dữ liệu

        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            // Lấy ID phiếu nhập mới nhất từ cơ sở dữ liệu
            String idNL = service.getLatestPhieuNhap();

            if (idNL != null && !idNL.isEmpty()) {
                try {
                    // Chuyển đổi ID từ String sang int và cộng 1
                    id = Integer.parseInt(idNL) + 1;
                } catch (NumberFormatException e) {
                    Logger.getLogger(formNhapNL.class.getName()).log(Level.SEVERE, "Lỗi chuyển đổi ID", e);
                }
            }
        } catch (SQLException ex) {
            // Nếu có lỗi khi kết nối CSDL, log lỗi và giữ giá trị mặc định cho ID
            Logger.getLogger(formNhapNL.class.getName()).log(Level.SEVERE, "Lỗi kết nối cơ sở dữ liệu", ex);
        }

        return id;
    }

    public void loadBang() {
        try {
            connection = cn.getConnection();
            int number;
            Vector row;
            String sql = "select * from nguyenlieu where TrangThai = 'Hoat dong'";
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
            tblSanPham.setModel(tb);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double tinhTongTien() {
        double tt = 0;

        try {
            // Kết nối với cơ sở dữ liệu
            connection = cn.getConnection();

            // Truy vấn lấy thông tin số lượng và giá của các nguyên liệu trong tblNhapHang
            DefaultTableModel modelNhapHang = (DefaultTableModel) tblNhapHang.getModel();

            // Duyệt qua tất cả các dòng trong bảng tblNhapHang
            for (int i = 0; i < modelNhapHang.getRowCount(); i++) {
                String maNL = modelNhapHang.getValueAt(i, 1).toString();  // Mã nguyên liệu
                int soLuong = Integer.parseInt(modelNhapHang.getValueAt(i, 3).toString());  // Số lượng
                double donGia = Double.parseDouble(modelNhapHang.getValueAt(i, 6).toString().replace(",", "").trim());  // Đơn giá

                // Kiểm tra nếu số lượng hoặc đơn giá không hợp lệ
                if (soLuong <= 0 || donGia <= 0) {
                    continue; // Bỏ qua nếu số lượng hoặc đơn giá không hợp lệ
                }

                // Truy vấn để lấy thông tin giá và số lượng từ bảng NguyenLieu theo mã nguyên liệu
                String sql = "SELECT Gia FROM NguyenLieu WHERE MaNL = ?";
                PreparedStatement pst = connection.prepareStatement(sql);
                pst.setString(1, maNL);
                ResultSet rs = pst.executeQuery();

                // Nếu tìm thấy nguyên liệu trong cơ sở dữ liệu, tính tiền cho nguyên liệu đó
                if (rs.next()) {
                    donGia = rs.getDouble("Gia"); // Cập nhật giá lấy từ cơ sở dữ liệu
                }

                // Tính tiền cho từng nguyên liệu và cộng vào tổng tiền
                tt += soLuong * donGia;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi khi tính tổng tiền.");
        }

        // Trả về tổng tiền đã tính
        return tt;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateChooser1 = new com.raven.datechooser.DateChooser();
        background1 = new com.raven.swing.Background();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        addProduct = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        btnReset = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtMaPhieu = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNguoiTao = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhapHang = new javax.swing.JTable();
        btnNhapHang = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        textTongTien = new javax.swing.JLabel();
        deleteProduct = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        txtNgayNhap = new qlnh.swing.MyTextField();
        buttonOutLine1 = new qlnh.swing.ButtonOutLine();

        dateChooser1.setDateFormat("yyyy-MM-dd");
        dateChooser1.setTextRefernce(txtNgayNhap);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã nguyên liệu", "Tên nguyên liệu", "Số lượng", "Đơn vị tính", "Nhà cung cấp", "Đơn giá", "Hạn sử dụng"
            }
        ));
        jScrollPane2.setViewportView(tblSanPham);

        jLabel4.setText("Số lượng");

        txtSoLuong.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSoLuong.setText("1");

        addProduct.setBackground(new java.awt.Color(0, 153, 51));
        addProduct.setForeground(new java.awt.Color(255, 255, 255));
        addProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_add_25px_5.png"))); // NOI18N
        addProduct.setText("Thêm");
        addProduct.setBorder(null);
        addProduct.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProductActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm"));

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_reset_25px_1.png"))); // NOI18N
        btnReset.setText("Làm mới");
        btnReset.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(80, 80, 80))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(jLabel4)
                .addGap(27, 27, 27)
                .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(addProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2))
                .addGap(78, 78, 78))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(addProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Mã phiếu nhập");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        txtMaPhieu.setEditable(false);
        txtMaPhieu.setEnabled(false);
        txtMaPhieu.setFocusable(false);
        jPanel2.add(txtMaPhieu, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, 290, 36));

        jLabel2.setText("Ngày nhập");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, -1));

        jLabel3.setText("Người tạo phiếu");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, -1, -1));

        txtNguoiTao.setEditable(false);
        jPanel2.add(txtNguoiTao, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 120, 290, 36));

        tblNhapHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã nguyên liệu", "Tên nguyên liệu", "Số lượng", "Đơn vi tính", "Nhà cung cấp", "Đơn giá", "Hạn sử dụng"
            }
        ));
        jScrollPane1.setViewportView(tblNhapHang);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 470, 350));

        btnNhapHang.setBackground(new java.awt.Color(0, 153, 51));
        btnNhapHang.setForeground(new java.awt.Color(255, 255, 255));
        btnNhapHang.setText("Nhập hàng");
        btnNhapHang.setBorder(null);
        btnNhapHang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNhapHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhapHangActionPerformed(evt);
            }
        });
        jPanel2.add(btnNhapHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 600, 123, 37));

        jLabel5.setFont(new java.awt.Font("SF Pro Display", 1, 18)); // NOI18N
        jLabel5.setText("Tổng tiền:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 600, 120, 30));

        textTongTien.setFont(new java.awt.Font("SF Pro Display", 1, 18)); // NOI18N
        textTongTien.setForeground(new java.awt.Color(255, 0, 0));
        textTongTien.setText("0đ");
        jPanel2.add(textTongTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 600, 170, 30));

        deleteProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_delete_25px_1.png"))); // NOI18N
        deleteProduct.setText("Xoá sản phẩm ");
        deleteProduct.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteProductActionPerformed(evt);
            }
        });
        jPanel2.add(deleteProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 550, 160, 40));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_edit_25px.png"))); // NOI18N
        jButton1.setText("Sửa số lượng");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 550, -1, 40));

        txtNgayNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgayNhapActionPerformed(evt);
            }
        });
        jPanel2.add(txtNgayNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, 250, -1));

        buttonOutLine1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar.png"))); // NOI18N
        buttonOutLine1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOutLine1ActionPerformed(evt);
            }
        });
        jPanel2.add(buttonOutLine1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 70, 30, 30));

        javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background1);
        background1.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 14, Short.MAX_VALUE))
        );
        background1Layout.setVerticalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 647, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(background1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProductActionPerformed
        // Lấy chỉ số hàng được chọn từ bảng tblSanPham
        int selectedRow = tblSanPham.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nguyên liệu để thêm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lấy dữ liệu từ hàng được chọn
        DefaultTableModel modelSanPham = (DefaultTableModel) tblSanPham.getModel();
        String maNL = modelSanPham.getValueAt(selectedRow, 0).toString();
        String tenNL = modelSanPham.getValueAt(selectedRow, 1).toString();
        String donViTinh = modelSanPham.getValueAt(selectedRow, 2).toString();
        String nhaCungCap = modelSanPham.getValueAt(selectedRow, 3).toString();
        double donGia = Double.parseDouble(modelSanPham.getValueAt(selectedRow, 4).toString());
        String hanSuDung = modelSanPham.getValueAt(selectedRow, 5).toString();

        // Lấy số lượng từ txtSoLuong
        int soLuong;
        try {
            soLuong = Integer.parseInt(txtSoLuong.getText());
            if (soLuong <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng. Số lượng phải là số nguyên dương!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiểm tra nếu nguyên liệu đã tồn tại trong bảng tblNhapHang
        DefaultTableModel modelNhapHang = (DefaultTableModel) tblNhapHang.getModel();
        boolean isExist = false;
        for (int i = 0; i < modelNhapHang.getRowCount(); i++) {
            if (maNL.equals(modelNhapHang.getValueAt(i, 1))) {
                // Nếu tồn tại, cập nhật số lượng
                int currentSoLuong = Integer.parseInt(modelNhapHang.getValueAt(i, 3).toString());
                modelNhapHang.setValueAt(currentSoLuong + soLuong, i, 3);
                isExist = true;
                break;
            }
        }

        // Nếu chưa tồn tại, thêm nguyên liệu mới vào tblNhapHang
        if (!isExist) {
            modelNhapHang.addRow(new Object[]{
                modelNhapHang.getRowCount() + 1, // STT
                maNL,
                tenNL,
                soLuong,
                donViTinh,
                nhaCungCap,
                formatter.format(donGia),
                hanSuDung
            });
        }

        // Cập nhật tổng tiền
        double tongTien = tinhTongTien();// Lấy tổng tiền sau khi đã cập nhật bảng
        textTongTien.setText(formatter.format(tongTien) + " VND");
    }//GEN-LAST:event_addProductActionPerformed

     //phương thức tìm kiếm
    public void timKiem(JTable table, int cot) {
        DefaultTableModel ob = (DefaultTableModel) table.getModel();//lấy mô hình dữ liệu của bảng
        TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(ob);//tạo bộ lọc hàng
        table.setRowSorter(obj);//thiết lập bộ lọc hàng cho bảng
        obj.setRowFilter(RowFilter.regexFilter(txtSearch.getText(), cot));//hiển thị kết quả
    }
    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        timKiem(tblSanPham, 1);
    }//GEN-LAST:event_txtSearchKeyReleased

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        txtSearch.setText("");
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnNhapHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhapHangActionPerformed

        // Kiểm tra xem có dòng nào được chọn trong bảng hay không
        if (tblNhapHang.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng thêm nguyên liệu muốn nhập hàng");
            return;
        }
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            // Lấy ID phiếu nhập vừa tạo
            String idNL = service.getLatestPhieuNhap();
            int id;
            if (idNL == null || idNL.isEmpty()) {
                id = 1;
            } else {
                int k = Integer.parseInt(idNL);
                id = k + 1;
            }

            // Lấy các giá trị từ các trường nhập liệu
            String ngayNhap = txtNgayNhap.getText().trim();
            int idND = user.getUserID();
            String tongTienStr = textTongTien.getText().trim();

            // Kiểm tra dữ liệu hợp lệ
            if (ngayNhap.isEmpty() || tongTienStr.equals("0 VND") || tongTienStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Xử lý tổng tiền, loại bỏ các ký tự không cần thiết
            tongTienStr = tongTienStr.replace(" VND", "").trim();
            tongTienStr = tongTienStr.replace(",", "").trim();

            // Kiểm tra xem chuỗi tổng tiền có phải là một số hợp lệ không
            float tongTien = 0;
            try {
                tongTien = Float.parseFloat(tongTienStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Tổng tiền không hợp lệ. Vui lòng nhập lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Chuyển đổi ngày nhập từ String sang Date (theo định dạng "yyyy-MM-dd")
            Date dateNgayNhap = Date.valueOf(ngayNhap);

            // Tạo đối tượng ModelPhieuNhap với thông tin đã nhập
            ModelPhieuNhap phieuNhap = new ModelPhieuNhap(id, dateNgayNhap, idND, tongTien);

            // Thêm phiếu nhập vào cơ sở dữ liệu thông qua DAO
            PhieuNhapDAO phieuNhapDAO = PhieuNhapDAO.getInstance();
            int result = phieuNhapDAO.insert(phieuNhap);

            // Kiểm tra kết quả thêm
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Nhập hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                // Lấy ID của phiếu nhập vừa thêm vào cơ sở dữ liệu
                int maPN = phieuNhap.getMaPN();

                // Lấy dữ liệu từ bảng tblNhapHang
                DefaultTableModel modelNhapHang = (DefaultTableModel) tblNhapHang.getModel();

                // Bắt đầu thực hiện chèn chi tiết phiếu nhập vào cơ sở dữ liệu
                String sqlInsert = "INSERT INTO ChiTietPhieuNhap (MaPN, MaNL, SoLuong, DonGia) VALUES (?, ?, ?, ?)";

                try (PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
                    for (int i = 0; i < modelNhapHang.getRowCount(); i++) {
                        // Lấy các giá trị từ bảng
                        int maNL = Integer.parseInt(modelNhapHang.getValueAt(i, 1).toString());  // Mã nguyên liệu
                        float soLuong = Float.parseFloat(modelNhapHang.getValueAt(i, 3).toString());  // Số lượng
                        // Lấy giá trị đơn giá từ bảng (với dấu phẩy)
                        String donGiaStr = modelNhapHang.getValueAt(i, 6).toString();

                        // Loại bỏ dấu phẩy nếu có
                        donGiaStr = donGiaStr.replace(",", "").trim();

                        // Chuyển đổi chuỗi thành số float
                        float donGia = Float.parseFloat(donGiaStr);
                        // Loại bỏ dấu phẩy trong chuỗi đơn giá
                        donGia = Float.parseFloat(modelNhapHang.getValueAt(i, 6).toString().replace(",", "").trim());

                        // Thiết lập tham số cho PreparedStatement
                        ps.setInt(1, maPN);
                        ps.setInt(2, maNL);
                        ps.setFloat(3, soLuong);
                        ps.setFloat(4, donGia);

                        // Thực hiện chèn vào bảng ChiTietPhieuNhap
                        ps.addBatch();  // Thêm vào batch
                    }

                    // Thực hiện batch insert
                    ps.executeBatch();

                    // Cập nhật lại bảng hoặc giao diện sau khi thêm
                    // Ví dụ: làm sạch các trường nhập liệu, hoặc reload danh sách phiếu nhập
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi khi lưu chi tiết phiếu nhập. Vui lòng thử lại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi nhập hàng. Vui lòng thử lại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi. Vui lòng kiểm tra lại thông tin nhập.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnNhapHangActionPerformed

    private void deleteProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteProductActionPerformed
        // Lấy chỉ số dòng được chọn trong bảng tblNhapHang
        int selectedRow = tblNhapHang.getSelectedRow();

        // Kiểm tra nếu không có dòng nào được chọn
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Xóa dòng đã chọn trong bảng tblNhapHang
        DefaultTableModel modelNhapHang = (DefaultTableModel) tblNhapHang.getModel();
        modelNhapHang.removeRow(selectedRow);

        // Cập nhật lại tổng tiền sau khi xóa
        double tongTien = tinhTongTien(); // Gọi hàm tính tổng tiền đã cập nhật
        textTongTien.setText(formatter.format(tongTien) + " VND"); // Cập nhật tổng tiền vào JTextField
    }//GEN-LAST:event_deleteProductActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // Lấy chỉ số dòng được chọn trong bảng tblNhapHang
        int selectedRow = tblNhapHang.getSelectedRow();

        // Kiểm tra nếu không có dòng nào được chọn
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để thay đổi số lượng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        } else {
            // Lấy số lượng cũ từ bảng
            String currentQuantityStr = tblNhapHang.getValueAt(selectedRow, 3).toString();

            // Yêu cầu nhập số lượng mới từ người dùng
            String newQuantityStr = JOptionPane.showInputDialog(this, "Nhập số lượng cần thay đổi",
                    "Thay đổi số lượng", JOptionPane.QUESTION_MESSAGE);
            if (newQuantityStr != null && !newQuantityStr.trim().isEmpty()) {
                try {
                    int newQuantity = Integer.parseInt(newQuantityStr);

                    // Kiểm tra số lượng phải lớn hơn 0
                    if (newQuantity > 0) {
                        // Cập nhật lại số lượng trong bảng
                        tblNhapHang.setValueAt(newQuantity, selectedRow, 3); // Cột 3 là số lượng

                        // Cập nhật lại tổng tiền sau khi thay đổi số lượng
                        textTongTien.setText(formatter.format(tinhTongTien()) + " VND"); // Tính lại tổng tiền và hiển thị
                    } else {
                        JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng lớn hơn 0", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtNgayNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayNhapActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayNhapActionPerformed

    private void buttonOutLine1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOutLine1ActionPerformed
        dateChooser1.showPopup(this, (getWidth() - dateChooser1.getPreferredSize().width) / 2, (getHeight() - dateChooser1.getPreferredSize().height) / 2);

    }//GEN-LAST:event_buttonOutLine1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addProduct;
    private com.raven.swing.Background background1;
    private javax.swing.JButton btnNhapHang;
    private javax.swing.JButton btnReset;
    private qlnh.swing.ButtonOutLine buttonOutLine1;
    private com.raven.datechooser.DateChooser dateChooser1;
    private javax.swing.JButton deleteProduct;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblNhapHang;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JLabel textTongTien;
    private javax.swing.JTextField txtMaPhieu;
    private qlnh.swing.MyTextField txtNgayNhap;
    private javax.swing.JTextField txtNguoiTao;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSoLuong;
    // End of variables declaration//GEN-END:variables
}
