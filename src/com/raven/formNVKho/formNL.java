package com.raven.formNVKho;

import com.raven.Connection.DatabaseConnection;
import com.raven.componert.Message;
import com.raven.model.ModelNguyenLieu;
import com.raven.model.ModelNhanVien;
import com.raven.service.ServiceNV;
import com.raven.service.ServiceNguyenLieu;
import com.raven.swing.ScrollBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class formNL extends javax.swing.JPanel {

    private MigLayout layout;

    private ModelNguyenLieu mdb;
    private ServiceNguyenLieu service;
    final String header[] = {"Mã nguyên liệu", "Tên nguyên liệu", "Đơn vị tính",
        "Nhà cung cấp", "Giá", "Hạn sử dụng", "Trạng thái"};
    DefaultTableModel tb = new DefaultTableModel(header, 0);
    int q, i;
    DatabaseConnection cn = new DatabaseConnection();
    Connection connection;

    public formNL() {
        initComponents();
        service = new ServiceNguyenLieu();
        mdb = new ModelNguyenLieu();
        intt();
        jScrollPane1.setVerticalScrollBar(new ScrollBar());
        loadBang();
        populateSupplierComboBox(cbNCC);
    }

    private void intt() {
        layout = new MigLayout("fill, insets 0");
        bg.setLayout(layout);
    }

    //phương thức tìm kiếm
    public void timKiem(JTable table, int cot) {
        DefaultTableModel ob = (DefaultTableModel) table.getModel();//lấy mô hình dữ liệu của bảng
        TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(ob);//tạo bộ lọc hàng
        table.setRowSorter(obj);//thiết lập bộ lọc hàng cho bảng
        obj.setRowFilter(RowFilter.regexFilter(txtTK.getText(), cot));//hiển thị kết quả
    }

    public void populateSupplierComboBox(JComboBox comboBox) {
        try {
            List<String> suppliers = service.getActiveSuppliers();
            comboBox.removeAllItems();

            for (String supplier : suppliers) {
                comboBox.addItem(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải danh sách nhà cung cấp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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

    public void loadBang() {
        try {
            connection = cn.getConnection();
            int number;
            Vector row;
            String sql = "select * from nguyenlieu";
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

    public void resetForm() {
        loadBang();
        txtID.setText("");
        txtName.setText("");
        txtGia.setText("");
        cbDVT.setSelectedItem("");
        cbTT.setSelectedItem("");
        cbNCC.setSelectedItem("");
        txtHSD.setText("");
        txtID.setEnabled(true);
    }

    public boolean checkValidate() {
        if (txtName.getText().isEmpty()
                || cbDVT.getSelectedItem().toString().isEmpty()
                || cbNCC.getSelectedItem().toString().isEmpty()
                || txtHSD.getText().isEmpty()
                || txtGia.getText().isEmpty()) {
            return false;
        }

        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateChooser1 = new com.raven.datechooser.DateChooser();
        background1 = new com.raven.swing.Background();
        jToolBar1 = new javax.swing.JToolBar();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnEdit1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        txtTK = new qlnh.swing.MyTextField();
        buttonOutLine1 = new qlnh.swing.ButtonOutLine();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtID = new qlnh.swing.MyTextField();
        txtName = new qlnh.swing.MyTextField();
        txtGia = new qlnh.swing.MyTextField();
        cbDVT = new javax.swing.JComboBox<>();
        cbTT = new javax.swing.JComboBox<>();
        txtHSD = new qlnh.swing.MyTextField();
        cbNCC = new javax.swing.JComboBox<>();
        bg = new javax.swing.JLayeredPane();
        buttonOutLine2 = new qlnh.swing.ButtonOutLine();
        btnThemNCC = new qlnh.swing.ButtonOutLine();

        dateChooser1.setDateFormat("yyyy-MM-dd");
        dateChooser1.setTextRefernce(txtHSD);

        setLayout(new java.awt.BorderLayout());

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
        btnEdit.setText("Sửa");
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

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 153, 255));
        jLabel1.setText("Quản lý nguyên liệu");

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPham);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm"));
        jPanel1.setToolTipText("");
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtTK.setHint("Tìm kiếm theo tên nguyên liệu");
        jPanel1.add(txtTK, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 420, -1));

        buttonOutLine1.setBackground(new java.awt.Color(0, 204, 204));
        buttonOutLine1.setText("Tìm kiếm");
        buttonOutLine1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOutLine1ActionPerformed(evt);
            }
        });
        jPanel1.add(buttonOutLine1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 40, 120, 40));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setText("Mã nguyên liệu:");

        jLabel3.setText("Tên nguyên liệu:");

        jLabel5.setText("Đơn vị tính:");

        jLabel6.setText("Nhà cung cấp:");

        jLabel7.setText("Giá:");

        jLabel8.setText("Hạn sử dụng:");

        jLabel9.setText("Trạng thái:");

        cbDVT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "kg", "g", "l", "ml", "thung" }));

        cbTT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hoat dong", "Het han", "Tam dung" }));

        cbNCC.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hai San Tuoi Song Hoa Binh", "Trang Trai Rau Sach Viet", "Cong ty Thuc Pham ABC", "Hop Tac Xa Nong San Xanh", "Cong ty Bia Sai Gon" }));

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 286, Short.MAX_VALUE)
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        buttonOutLine2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar.png"))); // NOI18N
        buttonOutLine2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOutLine2ActionPerformed(evt);
            }
        });

        btnThemNCC.setBackground(new java.awt.Color(153, 153, 255));
        btnThemNCC.setText("Thêm nhà cung cấp mới");
        btnThemNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemNCCActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(43, 43, 43)
                        .addComponent(cbTT, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 43, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbDVT, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(73, 73, 73)
                        .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtHSD, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(buttonOutLine2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(bg))
                        .addContainerGap(47, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnThemNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbDVT, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel5)
                        .addComponent(jLabel8)
                        .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtHSD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(buttonOutLine2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThemNCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbTT, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(bg))
                .addContainerGap())
        );

        javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background1);
        background1.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(background1Layout.createSequentialGroup()
                        .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                        .addGap(59, 59, 59)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE))
                    .addGroup(background1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        background1Layout.setVerticalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(background1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        try {
            // Kiểm tra tính hợp lệ của form
            if (!checkValidate()) {
                showMessage(Message.MessageType.ERROR, "Vui lòng điền đầy đủ thông tin.");
            } else {
                try {
                    // Kiểm tra mã nguyên liệu và tạo mã mới nếu chưa có
                    String idNL = service.getLatestNguyenLieu();
                    int id;// Lấy ID nguyên liệu lớn nhất trong bảng
                    if (idNL == null || idNL.isEmpty()) {
                        id = 1; // Nếu chưa có ID nguyên liệu nào, tạo ID mới bắt đầu từ "NL001"
                    } else {
                        int k = Integer.parseInt(idNL); // Lấy số từ ID nguyên liệu 
                        id = k + 1; // Tăng giá trị ID nguyên liệu lên 1
                    }
                    mdb.setMaNL(id); // Set giá trị cho mã nguyên liệu

                    // Lấy tên nguyên liệu từ trường nhập liệu
                    mdb.setTenNL(txtName.getText());

                    // Lấy giá nguyên liệu từ trường nhập liệu
                    float gia = Float.parseFloat(txtGia.getText());
                    mdb.setGia(gia);

                    // Lấy đơn vị tính từ comboBox
                    mdb.setDonViTinh(cbDVT.getSelectedItem().toString());

                    // Lấy trạng thái từ comboBox
                    mdb.setTrangThai(cbTT.getSelectedItem().toString());

                    // Lấy nhà cung cấp từ comboBox
                    mdb.setNhaCungCap(cbNCC.getSelectedItem().toString());

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    // Lấy hạn sử dụng từ trường nhập liệu
                    LocalDate hsdLocal = LocalDate.parse(txtHSD.getText(), formatter);
                    java.sql.Date hsd = java.sql.Date.valueOf(hsdLocal);
                    mdb.setHanSuDung(hsd);

                    // Kiểm tra trùng lặp và thực hiện thêm
                    try {
                        if (service.checkDuplicateNL(mdb.getTenNL())) {
                            showMessage(Message.MessageType.ERROR, "Nguyên liệu đã tồn tại. ");
                        } else {
                            // Gọi phương thức thêm nguyên liệu vào cơ sở dữ liệu
                            service.insertNguyenLieu(mdb);

                            // Reset form sau khi thêm thành công
                            resetForm();

                            // Hiển thị thông báo thành công
                            showMessage(Message.MessageType.SUCCESS, "Thêm nguyên liệu thành công.");

                        }
                    } catch (Exception e) {
                        showMessage(Message.MessageType.ERROR, "Lỗi khi thêm.");
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    // Hiển thị thông báo lỗi nếu có ngoại lệ xảy ra
                    showMessage(Message.MessageType.ERROR, "Lỗi khi thêm nguyên liệu.");
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            // Hiển thị thông báo lỗi nếu có lỗi không xác định
            showMessage(Message.MessageType.ERROR, "Đã xảy ra lỗi không xác định.");
            e.printStackTrace();
        }

    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        try {
            if (txtID.getText().isEmpty()) {
                showMessage(Message.MessageType.ERROR, "Vui lòng điền mã nguyên liệu cần xóa.");
            } else if (service.isMaNLInChiTietPhieuNhap(txtID.getText())) {
                JOptionPane.showMessageDialog(this, "Nguyên liệu đang tồn tại trong bảng chi tiết phiếu. Không thể xóa.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            } else {
                int ck = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn xóa nguyên liệu này?", "Thông báo", JOptionPane.YES_NO_OPTION);
                if (ck == JOptionPane.YES_OPTION) {//nếu chọn có thì thực hiện xóa nhân viên
                    service.deleteNguyenLieu(txtID.getText());
                    //st.executeUpdate(sql);
                    resetForm();//làm mới lại form
                    showMessage(Message.MessageType.SUCCESS, "Xóa nguyên liệu thành công.");//thong báo thành công 
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnDeleteActionPerformed


    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        try {
            if (txtID.getText().isEmpty()) {
                showMessage(Message.MessageType.ERROR, "Vui lòng điền Mã nguyn lieu cần chỉnh sửa.");
            } else {
                try {
                    // Kiểm tra mã nguyên liệu và tạo mã mới nếu chưa có
                    int id = Integer.parseInt(txtID.getText());
                    mdb.setMaNL(id); // Set giá trị cho mã nguyên liệu

                    // Lấy tên nguyên liệu từ trường nhập liệu
                    mdb.setTenNL(txtName.getText());

                    // Lấy giá nguyên liệu từ trường nhập liệu
                    float gia = Float.parseFloat(txtGia.getText());
                    mdb.setGia(gia);

                    // Lấy đơn vị tính từ comboBox
                    mdb.setDonViTinh(cbDVT.getSelectedItem().toString());

                    // Lấy trạng thái từ comboBox
                    mdb.setTrangThai(cbTT.getSelectedItem().toString());

                    // Lấy nhà cung cấp từ comboBox
                    mdb.setNhaCungCap(cbNCC.getSelectedItem().toString());
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    // Lấy hạn sử dụng từ trường nhập liệu
                    LocalDate hsdLocal = LocalDate.parse(txtHSD.getText(), formatter);
                    java.sql.Date hsd = java.sql.Date.valueOf(hsdLocal);
                    mdb.setHanSuDung(hsd);

                    service.updateNguyenLieu(mdb);
                    resetForm();
                    showMessage(Message.MessageType.SUCCESS, "Sửa thành công.");

                } catch (Exception e) {
                    showMessage(Message.MessageType.ERROR, "Lỗi khi sửa.");
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            showMessage(Message.MessageType.ERROR, "Đã xảy ra lỗi không xác định.");
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnEdit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEdit1ActionPerformed
        resetForm();
    }//GEN-LAST:event_btnEdit1ActionPerformed

    private void buttonOutLine1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOutLine1ActionPerformed
        timKiem(tblSanPham, 1);
    }//GEN-LAST:event_buttonOutLine1ActionPerformed

    private void buttonOutLine2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOutLine2ActionPerformed
        dateChooser1.showPopup(this, (getWidth() - dateChooser1.getPreferredSize().width) / 2, (getHeight() - dateChooser1.getPreferredSize().height) / 2);

    }//GEN-LAST:event_buttonOutLine2ActionPerformed

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        int x = tblSanPham.getSelectedRow();
        if (x >= 0) {
            txtID.setText(tblSanPham.getValueAt(tblSanPham.getSelectedRow(), 0) + "");
            txtName.setText(tblSanPham.getValueAt(tblSanPham.getSelectedRow(), 1) + "");
            cbDVT.setSelectedItem(tblSanPham.getValueAt(tblSanPham.getSelectedRow(), 2) + "");
            cbNCC.setSelectedItem(tblSanPham.getValueAt(tblSanPham.getSelectedRow(), 3) + "");
            cbTT.setSelectedItem(tblSanPham.getValueAt(tblSanPham.getSelectedRow(), 6) + "");
            txtGia.setText(tblSanPham.getValueAt(tblSanPham.getSelectedRow(), 4) + "");
            txtHSD.setText(tblSanPham.getValueAt(tblSanPham.getSelectedRow(), 5) + "");
            txtID.setEnabled(false);
        }
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void btnThemNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemNCCActionPerformed
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            formThemNCC formNhap = new formThemNCC();

            // Cấu hình JDialog
            JDialog dialog = new JDialog();
            dialog.setTitle("Thêm Nhà cung cấp mới");
            dialog.setSize(1025, 700);
            dialog.setLocationRelativeTo(null);
            dialog.setModal(true);
            dialog.add(formNhap);

            dialog.setVisible(true);
            loadBang();
        } catch (SQLException ex) {
            Logger.getLogger(formPhieuNhap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnThemNCCActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.swing.Background background1;
    private javax.swing.JLayeredPane bg;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnEdit1;
    private qlnh.swing.ButtonOutLine btnThemNCC;
    private qlnh.swing.ButtonOutLine buttonOutLine1;
    private qlnh.swing.ButtonOutLine buttonOutLine2;
    private javax.swing.JComboBox<String> cbDVT;
    private javax.swing.JComboBox<String> cbNCC;
    private javax.swing.JComboBox<String> cbTT;
    private com.raven.datechooser.DateChooser dateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTable tblSanPham;
    private qlnh.swing.MyTextField txtGia;
    private qlnh.swing.MyTextField txtHSD;
    private qlnh.swing.MyTextField txtID;
    private qlnh.swing.MyTextField txtName;
    private qlnh.swing.MyTextField txtTK;
    // End of variables declaration//GEN-END:variables
}
