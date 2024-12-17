package com.raven.formNVKho;

import com.raven.Connection.DatabaseConnection;
import com.raven.model.ModelNguyenLieu;
import com.raven.model.ModelUser;
import com.raven.service.ServiceNguyenLieu;
import com.raven.swing.ScrollBar;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import net.miginfocom.swing.MigLayout;

public class formPhieuXuat extends javax.swing.JPanel {

    private DefaultTableModel tblModel;
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/YYYY HH:mm");
    private MigLayout layout;
    private ModelUser user;

    private ModelNguyenLieu mdb;
    private ServiceNguyenLieu service;
    final String header[] = {"Mã phiếu xuất", "Ngày xuất", "Mã người xuất(Người dùng)", "Tổng tiền"};
    DefaultTableModel tb = new DefaultTableModel(header, 0);
    int q, i;
    DatabaseConnection cn = new DatabaseConnection();
    private Connection connection;

    public formPhieuXuat(ModelUser user) {
        connection = cn.getConnection();
        this.user = user;
        service = new ServiceNguyenLieu();
        mdb = new ModelNguyenLieu();
        initComponents();
        loadBang();
        connectToDatabase();
        jScrollPane1.setVerticalScrollBar(new ScrollBar());
        // Lắng nghe thay đổi trên các ô nhập giá
        txtTKMa.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                timKiem(tblPhieuNhap, 0);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                timKiem(tblPhieuNhap, 0);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                timKiem(tblPhieuNhap, 0);
            }
        });
        
        // Lắng nghe thay đổi trên các ô nhập giá
        giaTu.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                handlePriceInput();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handlePriceInput();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                handlePriceInput();
            }
        });

        giaDen.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                handlePriceInput();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handlePriceInput();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                handlePriceInput();
            }
        });
    }

    private void handlePriceInput() {
        String minPrice = giaTu.getText().trim();
        String maxPrice = giaDen.getText().trim();

        // Nếu một trong hai ô trống, tải lại bảng
        if (minPrice.isEmpty() || maxPrice.isEmpty()) {
            loadBang();
        }
    }

    // Method to ensure the connection is always open
    private void connectToDatabase() {
        if (connection == null || isConnectionClosed()) {
            connection = DatabaseConnection.getInstance().getConnection();
        }
    }

    public void searchByDateRange() {

        String fromDate = txtTu.getText(); // Lấy ngày từ ô nhập
        String toDate = txtDen.getText();    // Lấy ngày đến từ ô nhập

        // Kiểm tra dữ liệu nhập vào
        if (fromDate.isEmpty() || toDate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ ngày bắt đầu và ngày kết thúc");
            return;
        }

        // Định dạng ngày trong SQL (giả định cột ngày trong cơ sở dữ liệu ở định dạng yyyy-MM-dd)
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String fromDateFormatted = sqlFormat.format(inputFormat.parse(fromDate));
            String toDateFormatted = sqlFormat.format(inputFormat.parse(toDate));

            // Câu truy vấn SQL
            String sql = "SELECT * FROM phieuxuat WHERE ngayXuat BETWEEN '" + fromDateFormatted + "' AND '" + toDateFormatted + "'";

            // Kết nối cơ sở dữ liệu và thực thi truy vấn
            connection = cn.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            ResultSetMetaData metadata = rs.getMetaData();
            int number = metadata.getColumnCount();
            Vector row;
            tb.setRowCount(0); // Xóa dữ liệu cũ trong bảng

            // Thêm dữ liệu mới vào bảng
            while (rs.next()) {
                row = new Vector();
                for (int i = 1; i <= number; i++) {
                    row.addElement(rs.getString(i));
                }
                tb.addRow(row);
            }
            tblPhieuNhap.setModel(tb);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi trong quá trình tìm kiếm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
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

    public void filterByPriceRange() {
        String minPriceText = giaTu.getText(); // Lấy giá trị từ ô nhập giá tối thiểu
        String maxPriceText = giaDen.getText(); // Lấy giá trị từ ô nhập giá tối đa

        // Kiểm tra nếu ô nhập trống
        if (minPriceText.isEmpty() || maxPriceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ giá tối thiểu và giá tối đa");
            return;
        }

        // Chuyển đổi giá trị sang kiểu số
        try {
            double minPrice = Double.parseDouble(minPriceText);
            double maxPrice = Double.parseDouble(maxPriceText);

            // Kiểm tra nếu minPrice > maxPrice
            if (minPrice > maxPrice) {
                JOptionPane.showMessageDialog(this, "Giá tối thiểu không được lớn hơn giá tối đa");
                return;
            }

            // Câu truy vấn SQL
            String sql = "SELECT * FROM phieuxuat WHERE tongTien BETWEEN " + minPrice + " AND " + maxPrice;

            // Kết nối cơ sở dữ liệu và thực thi truy vấn
            connection = cn.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            ResultSetMetaData metadata = rs.getMetaData();
            int number = metadata.getColumnCount();
            Vector row;
            tb.setRowCount(0); // Xóa dữ liệu cũ trong bảng

            // Thêm dữ liệu mới vào bảng
            while (rs.next()) {
                row = new Vector();
                for (int i = 1; i <= number; i++) {
                    row.addElement(rs.getString(i));
                }
                tb.addRow(row);
            }
            tblPhieuNhap.setModel(tb);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ cho giá trị", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi trong quá trình lọc: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public void loadBang() {
        try {
            connection = cn.getConnection();
            int number;
            Vector row;
            String sql = "select * from phieuxuat";
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
            tblPhieuNhap.setModel(tb);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateChooser1 = new com.raven.datechooser.DateChooser();
        dateChooser2 = new com.raven.datechooser.DateChooser();
        background1 = new com.raven.swing.Background();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        giaTu = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        giaDen = new javax.swing.JTextField();
        btnTKGia = new qlnh.swing.ButtonOutLine();
        jToolBar1 = new javax.swing.JToolBar();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnDetail = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        txtTKMa = new qlnh.swing.MyTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPhieuNhap = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtDen = new qlnh.swing.MyTextField();
        buttonOutLine2 = new qlnh.swing.ButtonOutLine();
        txtTu = new qlnh.swing.MyTextField();
        buttonOutLine3 = new qlnh.swing.ButtonOutLine();
        btnTKNgay = new qlnh.swing.ButtonOutLine();

        dateChooser1.setDateFormat("yyyy-MM-dd");
        dateChooser1.setTextRefernce(txtTu);

        dateChooser2.setDateFormat("yyyy-MM-dd");
        dateChooser2.setTextRefernce(txtDen);

        setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Lọc theo giá"));

        jLabel3.setText("Từ");

        giaTu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                giaTuActionPerformed(evt);
            }
        });
        giaTu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                giaTuKeyReleased(evt);
            }
        });

        jLabel4.setText("Đến");

        giaDen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                giaDenActionPerformed(evt);
            }
        });
        giaDen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                giaDenKeyReleased(evt);
            }
        });

        btnTKGia.setBackground(new java.awt.Color(0, 204, 204));
        btnTKGia.setText("Tìm kiếm");
        btnTKGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTKGiaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(giaTu, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(giaDen)
                .addGap(18, 18, 18)
                .addComponent(btnTKGia, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(giaTu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(giaDen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTKGia, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

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

        btnDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_eye_40px.png"))); // NOI18N
        btnDetail.setText("Xem chi tiết");
        btnDetail.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDetail.setFocusable(false);
        btnDetail.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDetail.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDetail);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm"));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_reset_25px_1.png"))); // NOI18N
        jButton7.setText("Làm mới");
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 30, 140, 40));

        txtTKMa.setHint("Tìm kiếm theo mã phiếu");
        txtTKMa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTKMaKeyPressed(evt);
            }
        });
        jPanel3.add(txtTKMa, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 420, -1));

        tblPhieuNhap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblPhieuNhap);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Lọc theo ngày"));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Đến");
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, 30, 20));

        jLabel5.setFont(new java.awt.Font("SF Pro Display", 0, 14)); // NOI18N
        jLabel5.setText("Từ");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 20, 20));
        jPanel4.add(txtDen, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 30, 150, -1));

        buttonOutLine2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar.png"))); // NOI18N
        buttonOutLine2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOutLine2ActionPerformed(evt);
            }
        });
        jPanel4.add(buttonOutLine2, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 30, -1, -1));
        jPanel4.add(txtTu, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 150, -1));

        buttonOutLine3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar.png"))); // NOI18N
        buttonOutLine3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOutLine3ActionPerformed(evt);
            }
        });
        jPanel4.add(buttonOutLine3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 30, -1, -1));

        btnTKNgay.setBackground(new java.awt.Color(0, 204, 204));
        btnTKNgay.setText("Tìm kiếm");
        btnTKNgay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTKNgayActionPerformed(evt);
            }
        });
        jPanel4.add(btnTKNgay, new org.netbeans.lib.awtextra.AbsoluteConstraints(469, 30, 70, 30));

        javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background1);
        background1.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(background1Layout.createSequentialGroup()
                        .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 732, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, background1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        background1Layout.setVerticalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(13, 13, 13)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(background1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            // Tạo một đối tượng JDialog để hiển thị form nhập nguyên liệu
            formXuatNL formXuat = new formXuatNL(user);  // Truyền đối tượng user vào form

            // Cấu hình JDialog
            JDialog dialog = new JDialog();
            dialog.setTitle("Nhập Nguyên Liệu");
            dialog.setSize(1025, 700);  // Đặt kích thước cho cửa sổ
            dialog.setLocationRelativeTo(null);  // Đặt cửa sổ ở giữa màn hình
            dialog.setModal(true);  // Cửa sổ này sẽ bắt buộc phải đóng để trở lại giao diện chính

            // Thêm JPanel vào JDialog
            dialog.add(formXuat);

            // Hiển thị cửa sổ
            dialog.setVisible(true);
            loadBang();
        } catch (SQLException ex) {
            Logger.getLogger(formPhieuXuat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // Kiểm tra xem có dòng nào được chọn trong bảng hay không
        if (tblPhieuNhap.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu cần xoá");
            return;
        }

        // Lấy mã phiếu nhập (maPN) từ dòng được chọn
        int selectedRow = tblPhieuNhap.getSelectedRow();
        String maPNString = (String) tblPhieuNhap.getValueAt(selectedRow, 0);
        int maPN;
        try {
            maPN = Integer.parseInt(maPNString); // Chuyển đổi chuỗi sang số nguyên
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Mã phiếu nhập không hợp lệ: " + maPNString, "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Xác nhận xoá
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xoá phiếu nhập này?", "Xác nhận xoá", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Gọi phương thức xoá phiếu nhập
                service.deletePhieuNhap(maPN);
                JOptionPane.showMessageDialog(this, "Xoá phiếu nhập thành công");
                loadBang();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xoá phiếu nhập: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailActionPerformed
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            if (tblPhieuNhap.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu !");
            } else {
                // Lấy mã phiếu nhập từ bảng (giả sử mã phiếu nằm ở cột đầu tiên)
                int selectedRow = tblPhieuNhap.getSelectedRow();
                String maPN = tblPhieuNhap.getValueAt(selectedRow, 0).toString(); // Lấy mã phiếu nhập từ bảng
                int idpn = Integer.parseInt(maPN); // Chuyển mã phiếu nhập thành số nguyên

                // Tạo dialog CTPhieuNhap với constructor mới
                CTPhieuXuat detailDialog = new CTPhieuXuat(idpn);

                // Hiển thị dialog
                detailDialog.setVisible(true);
                loadBang();
            }
        } catch (SQLException ex) {
            Logger.getLogger(formPhieuXuat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDetailActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        txtTKMa.setText("");
        txtTu.setText("");
        txtDen.setText("");
        giaTu.setText("");
        giaDen.setText("");
        loadBang();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void buttonOutLine2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOutLine2ActionPerformed
        dateChooser2.showPopup(this, (getWidth() - dateChooser2.getPreferredSize().width) / 2, (getHeight() - dateChooser2.getPreferredSize().height) / 2);
    }//GEN-LAST:event_buttonOutLine2ActionPerformed

    private void buttonOutLine3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOutLine3ActionPerformed
        dateChooser1.showPopup(this, (getWidth() - dateChooser1.getPreferredSize().width) / 2, (getHeight() - dateChooser1.getPreferredSize().height) / 2);

    }//GEN-LAST:event_buttonOutLine3ActionPerformed

    private void giaTuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_giaTuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_giaTuActionPerformed

    private void giaTuKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_giaTuKeyReleased

    }//GEN-LAST:event_giaTuKeyReleased

    private void giaDenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_giaDenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_giaDenActionPerformed

    private void giaDenKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_giaDenKeyReleased

    }//GEN-LAST:event_giaDenKeyReleased

    private void btnTKNgayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTKNgayActionPerformed

        searchByDateRange();
    }//GEN-LAST:event_btnTKNgayActionPerformed

    private void btnTKGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTKGiaActionPerformed

        filterByPriceRange();
    }//GEN-LAST:event_btnTKGiaActionPerformed

    private void txtTKMaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTKMaKeyPressed
//        timKiem(tblPhieuNhap, 0);
    }//GEN-LAST:event_txtTKMaKeyPressed

    public void timKiem(JTable table, int cot) {
        DefaultTableModel ob = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(ob);
        table.setRowSorter(obj);
        obj.setRowFilter(RowFilter.regexFilter(txtTKMa.getText(), cot));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.swing.Background background1;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDetail;
    private qlnh.swing.ButtonOutLine btnTKGia;
    private qlnh.swing.ButtonOutLine btnTKNgay;
    private qlnh.swing.ButtonOutLine buttonOutLine2;
    private qlnh.swing.ButtonOutLine buttonOutLine3;
    private com.raven.datechooser.DateChooser dateChooser1;
    private com.raven.datechooser.DateChooser dateChooser2;
    private javax.swing.JTextField giaDen;
    private javax.swing.JTextField giaTu;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTable tblPhieuNhap;
    private qlnh.swing.MyTextField txtDen;
    private qlnh.swing.MyTextField txtTKMa;
    private qlnh.swing.MyTextField txtTu;
    // End of variables declaration//GEN-END:variables
}
