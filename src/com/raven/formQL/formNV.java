package com.raven.formQL;

import cart.Cart;
import com.raven.Connection.DatabaseConnection;
import com.raven.componert.Message;
import com.raven.datechooser.SelectedDate;
import com.raven.model.ModelNhanVien;
import com.raven.model.Model_Cart;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import com.raven.service.ServiceNV;
import com.raven.swing.ScrollBar;
import java.text.DecimalFormat;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class formNV extends javax.swing.JPanel {

    private MigLayout layout;

    private ModelNhanVien mdb;
    private ServiceNV service;
    final String header[] = {"Mã nhân viên", "Tên nhân viên", "Số điện thoại", "Ngày vào làm",
        "Chức vụ", "Lương"};
    DefaultTableModel tb = new DefaultTableModel(header, 0);
    int q, i;
    DatabaseConnection cn = new DatabaseConnection();
    Connection connection;

    public formNV() {
        initComponents();
        service = new ServiceNV();
        mdb = new ModelNhanVien();
        loadBang();
        intt();
        jScrollPane1.setVerticalScrollBar(new ScrollBar());
        updateTongNhanVien();
        lblIDNV.setEnabled(false);
        lblTK.setHint("Tìm kiếm theo mã nhân viên...");
    }

    private void intt() {
        layout = new MigLayout("fill, insets 0");
        bg.setLayout(layout);
    }

    private void updateTongNhanVien() {
        String nameToCount = "Le tan";
        int totalMatchingEmployees = countRowsByName(nameToCount);
        String nameToCount2 = "Dau bep";
        int totalMatchingEmployees2 = countRowsByName(nameToCount2);
        String nameToCount3 = "Phuc vu";
        int totalMatchingEmployees3 = countRowsByName(nameToCount3);
        String nameToCount4 = "Nhan vien ve sinh";
        int totalMatchingEmployees4 = countRowsByName(nameToCount4);
        String nameToCount5 = "Bao ve";
        int totalMatchingEmployees5 = countRowsByName(nameToCount5);

        tongNV.setData(new Model_Cart(new ImageIcon(getClass().getResource("/Icons/customer (1).png")), "Tổng nhân viên", "" + getTongNhanVien()));
        tongLeTan.setData(new Model_Cart(new ImageIcon(getClass().getResource("/Icons/profile2.jpg")), "Tổng NV Lễ tân", "" + String.valueOf(totalMatchingEmployees)));
        tongDauBep.setData(new Model_Cart(new ImageIcon(getClass().getResource("/Icons/bell-boy.png")), "Tổng NV Đầu bếp", "" + String.valueOf(totalMatchingEmployees2)));
        tongPhucVu.setData(new Model_Cart(new ImageIcon(getClass().getResource("/Icons/customer (1).png")), "Tổng NV Phục vụ", "" + String.valueOf(totalMatchingEmployees3)));
        tongNVVS.setData(new Model_Cart(new ImageIcon(getClass().getResource("/Icons/profile2.jpg")), "Tổng NV vệ sinh", "" + String.valueOf(totalMatchingEmployees4)));
        tongBV.setData(new Model_Cart(new ImageIcon(getClass().getResource("/Icons/profile1.jpg")), "Tổng NV Bảo vệ", "" + String.valueOf(totalMatchingEmployees5)));
    }

    private int countRowsByName(String CV) {
        int count = 0;
        for (int i = 0; i < dgwNV.getRowCount(); i++) {
            String rowName = dgwNV.getValueAt(i, 4).toString();
            if (rowName.equalsIgnoreCase(CV)) {
                count++;
            }
        }
        return count;
    }

    private String getTongNhanVien() {
        int totalEmployees = dgwNV.getRowCount();
        return String.valueOf(totalEmployees);
    }

    //phương thức tìm kiếm
    public void timKiem(JTable table, int cot) {
        DefaultTableModel ob = (DefaultTableModel) table.getModel();//lấy mô hình dữ liệu của bảng
        TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(ob);//tạo bộ lọc hàng
        table.setRowSorter(obj);//thiết lập bộ lọc hàng cho bảng
        obj.setRowFilter(RowFilter.regexFilter(lblTK.getText(), cot));//hiển thị kết quả
    }

    public void loadBang() {
        try {
            connection = cn.getConnection();
            int number;
            Vector row;
            String sql = "select * from qlnhanvien2";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            ResultSetMetaData metadata = rs.getMetaData();
            number = metadata.getColumnCount();
            tb.setRowCount(0);
            while (rs.next()) {
                row = new Vector();
                row.addElement(rs.getString(1)); // Mã nhân viên
                row.addElement(rs.getString(2)); // Tên nhân viên
                row.addElement(rs.getString(3)); // Số điện thoại
                row.addElement(rs.getString(4)); // Ngày vào làm
                row.addElement(rs.getString(5)); // Chức vụ

                // Lấy lương dưới dạng số và định dạng thành chuỗi mà không có dấu phẩy
                float luong = rs.getFloat(6); // Giả sử cột lương là cột thứ 6
                row.addElement(String.format("%.2f", luong)); // Định dạng lương
                tb.addRow(row);
//                for (int i = 1; i <= number; i++) {
//                    row.addElement(rs.getString(i));
//                }
//                tb.addRow(row);
            }
            dgwNV.setModel(tb);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetForm() {
        loadBang();
        lblIDNV.setText("");
        lblNameNV.setText("");
        lblSDT.setText("");
        cbCV.setSelectedItem("");
        lblDate.setText("");
        lblLuong.setText("");
        lblIDNV.setEnabled(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateChooser1 = new com.raven.datechooser.DateChooser();
        background1 = new com.raven.swing.Background();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnDate = new qlnh.swing.Button();
        btnInsert = new qlnh.swing.Button();
        btnUpdate = new qlnh.swing.Button();
        btnDelete = new qlnh.swing.Button();
        btnLM = new qlnh.swing.Button();
        jLabel8 = new javax.swing.JLabel();
        btnTK = new qlnh.swing.Button();
        bg = new javax.swing.JLayeredPane();
        cbCV = new javax.swing.JComboBox<>();
        lblTK = new qlnh.swing.MyTextField();
        lblIDNV = new qlnh.swing.MyTextField();
        lblNameNV = new qlnh.swing.MyTextField();
        lblSDT = new qlnh.swing.MyTextField();
        lblDate = new qlnh.swing.MyTextField();
        lblLuong = new qlnh.swing.MyTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        dgwNV = new javax.swing.JTable();
        sp = new javax.swing.JLayeredPane();
        tongNV = new com.raven.componert.Cart();
        tongLeTan = new com.raven.componert.Cart();
        tongDauBep = new com.raven.componert.Cart();
        tongPhucVu = new com.raven.componert.Cart();
        tongNVVS = new com.raven.componert.Cart();
        tongBV = new com.raven.componert.Cart();

        dateChooser1.setTextRefernce(lblDate);

        setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setText("Lương:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Quản lý nhân viên");

        jLabel1.setText("Mã nhân viên:");

        jLabel3.setText("Tên nhân viên: ");

        jLabel4.setText("Số điện thoại: ");

        jLabel5.setText("Ngày vào làm: ");

        jLabel6.setText("Chức vụ: ");

        btnDate.setBackground(new java.awt.Color(204, 183, 253));
        btnDate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/inventory (1).png"))); // NOI18N
        btnDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDateActionPerformed(evt);
            }
        });

        btnInsert.setBackground(new java.awt.Color(204, 183, 253));
        btnInsert.setForeground(new java.awt.Color(51, 51, 51));
        btnInsert.setText("Thêm");
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(204, 183, 253));
        btnUpdate.setForeground(new java.awt.Color(51, 51, 51));
        btnUpdate.setText("Sửa");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(204, 183, 253));
        btnDelete.setForeground(new java.awt.Color(51, 51, 51));
        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnLM.setBackground(new java.awt.Color(204, 183, 253));
        btnLM.setForeground(new java.awt.Color(51, 51, 51));
        btnLM.setText("Làm mới");
        btnLM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLMActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Tìm kiếm: ");

        btnTK.setBackground(new java.awt.Color(204, 183, 253));
        btnTK.setForeground(new java.awt.Color(51, 51, 51));
        btnTK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/search.png"))); // NOI18N
        btnTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTKActionPerformed(evt);
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
            .addGap(0, 53, Short.MAX_VALUE)
        );

        cbCV.setBackground(new java.awt.Color(83, 105, 118));
        cbCV.setForeground(new java.awt.Color(255, 255, 255));
        cbCV.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Le tan", "Dau bep", "Phuc vu", "Nhan vien ve sinh", "Bao ve" }));
        cbCV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCVActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bg))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel7)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(lblTK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblIDNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(lblDate, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnDate, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblSDT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbCV, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblNameNV, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblLuong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, 0))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnLM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(lblTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnTK, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblIDNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(lblNameNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(cbCV, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(lblSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(lblDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(247, 247, 247))
        );

        dgwNV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã nhân viên", "Tên nhân viên", "Số điện thoại", "Ngày vào làm", "Chức vụ", "Lương"
            }
        ));
        dgwNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dgwNVMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(dgwNV);

        sp.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        tongNV.setColor1(new java.awt.Color(142, 142, 255));
        tongNV.setColor2(new java.awt.Color(123, 123, 245));
        sp.add(tongNV);

        tongLeTan.setColor1(new java.awt.Color(186, 123, 247));
        tongLeTan.setColor2(new java.awt.Color(167, 94, 236));
        sp.add(tongLeTan);

        tongDauBep.setColor1(new java.awt.Color(154, 245, 214));
        tongDauBep.setColor2(new java.awt.Color(130, 150, 227));
        sp.add(tongDauBep);

        tongPhucVu.setColor1(new java.awt.Color(61, 246, 224));
        tongPhucVu.setColor2(new java.awt.Color(0, 195, 243));
        sp.add(tongPhucVu);

        tongNVVS.setColor1(new java.awt.Color(255, 102, 102));
        tongNVVS.setColor2(new java.awt.Color(255, 102, 102));
        sp.add(tongNVVS);

        tongBV.setColor1(new java.awt.Color(192, 255, 204));
        tongBV.setColor2(new java.awt.Color(102, 255, 102));
        sp.add(tongBV);

        javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background1);
        background1.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(background1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1))
                    .addComponent(sp, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        background1Layout.setVerticalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 464, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sp, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(background1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLMActionPerformed
        resetForm();
    }//GEN-LAST:event_btnLMActionPerformed

    private void btnDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDateActionPerformed
        dateChooser1.showPopup(this, (getWidth() - dateChooser1.getPreferredSize().width) / 2, (getHeight() - dateChooser1.getPreferredSize().height) / 2);

    }//GEN-LAST:event_btnDateActionPerformed

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        try {
            // Kiểm tra xem form đã được điền đầy đủ hay chưa
            if (!checkValidate()) {
                showMessage(Message.MessageType.ERROR, "Vui lòng điền đầy đủ thông tin.");
            } else {
                String idNV = service.gtriIDNV(); // lay idnv lon nhat trong bang
                if (idNV == null || idNV.isEmpty()) {
                    idNV = "NV001"; //idnv lon nhat neu trong bang chua co idnv nao 
                } else {
                    int k = Integer.parseInt(idNV.substring(2));//ep kieu chuoi con lay tu chuoi idNV, chuoi con bat dau tu ki tu thu 2(Dem tu 0)
                    int tangidHD = k + 1;//tang idnv len 1
                    idNV = "NV" + String.format("%03d", tangidHD); // tao idnv moi
                }
                // các thông tin thêm
                mdb.setMaNV(idNV);
                mdb.setTenNV(lblNameNV.getText());
                mdb.setSDT(lblSDT.getText());
                mdb.setNgayVL(lblDate.getText());
                mdb.setChucVu(cbCV.getSelectedItem().toString());
                float luong = Float.parseFloat(lblLuong.getText().replace(",", "")); // Xóa dấu phẩy nếu có
                mdb.setLuong(luong);
                // Kiểm tra trùng lặp và thực hiện thêm
                try {
                    if (service.checkDuplicateNV(lblIDNV.getText())) {
                        showMessage(Message.MessageType.ERROR, "Mã nhân viên đã tồn tại. ");
                    } else {
                        service.insertNV(mdb);
                        showMessage(Message.MessageType.SUCCESS, "Thêm nhân viên thành công.");
                        resetForm();
                        updateTongNhanVien();
                    }
                } catch (Exception e) {
                    showMessage(Message.MessageType.ERROR, "Lỗi khi thêm.");
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            showMessage(Message.MessageType.ERROR, "Đã xảy ra lỗi không xác định.");
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnInsertActionPerformed

    private void dgwNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dgwNVMouseClicked
        int x = dgwNV.getSelectedRow();
        if (x >= 0) {
            lblIDNV.setText(dgwNV.getValueAt(dgwNV.getSelectedRow(), 0) + "");
            lblNameNV.setText(dgwNV.getValueAt(dgwNV.getSelectedRow(), 1) + "");
            lblSDT.setText(dgwNV.getValueAt(dgwNV.getSelectedRow(), 2) + "");
            lblDate.setText(dgwNV.getValueAt(dgwNV.getSelectedRow(), 3) + "");
            cbCV.setSelectedItem(dgwNV.getValueAt(dgwNV.getSelectedRow(), 4) + "");
            lblLuong.setText(dgwNV.getValueAt(dgwNV.getSelectedRow(), 5) + "");
            lblIDNV.setEnabled(false);
        }
    }//GEN-LAST:event_dgwNVMouseClicked

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        try {
            // Kiểm tra xem form đã được điền đầy đủ hay chưa
            if (!checkValidate()) {
                showMessage(Message.MessageType.ERROR, "Vui lòng điền đầy đủ thông tin.");
            } else {
                try {
                    // các thông tin sửa
                    mdb.setMaNV(lblIDNV.getText());
                    mdb.setTenNV(lblNameNV.getText());
                    mdb.setSDT(lblSDT.getText());
                    mdb.setNgayVL(lblDate.getText());
                    mdb.setChucVu(cbCV.getSelectedItem().toString());

                    float luong = Float.parseFloat(lblLuong.getText().replace(",", "")); // Remove commas for parsing
                    mdb.setLuong(luong);
                    service.UpdateNV(mdb);
                    showMessage(Message.MessageType.SUCCESS, "Sửa nhân viên thành công.");
                    resetForm();
                    updateTongNhanVien();
                } catch (Exception e) {
                    showMessage(Message.MessageType.ERROR, "Lỗi khi sửa.");
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            showMessage(Message.MessageType.ERROR, "Đã xảy ra lỗi không xác định.");
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    //xóa nhân viên 
    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
// Kiểm tra xem có dòng nào được chọn trong bảng hay không
        if (dgwNV.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần xoá");
            return;
        }
        //connection = cn.getConnection();
        try {
            if (lblIDNV.getText().isEmpty()) {//kiểm tra ô nhập mã nhân viên, nếu trống thì thông báo lỗi
                showMessage(Message.MessageType.ERROR, "Vui lòng điền mã nhân viên cần xóa.");
            } else {
                if (lblIDNV.getText().equals("NV001")) {//kiểm tra nếu mã là mã của quản lý thì thông báo lỗi 
                    showMessage(Message.MessageType.ERROR, "Không thể xóa quản lý.");
                } else {
                    //String sql = "Delete qlnhanvien2 where idnv ='"+lblIDNV.getText()+"'";
                    //Statement st = connection.createStatement();
                    mdb.setMaNV(lblIDNV.getText());//set dữ liệu cần cho phương thức 
                    //tạo form thông báo xác nhận xóa nhân viên
                    int ck = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn xóa nhân viên này?", "Thông báo", JOptionPane.YES_NO_OPTION);
                    if (ck == JOptionPane.YES_OPTION) {//nếu chọn có thì thực hiện xóa nhân viên
                        service.deleteNV(mdb);
                        //st.executeUpdate(sql);
                        resetForm();//làm mới lại form
                        updateTongNhanVien();
                        showMessage(Message.MessageType.SUCCESS, "Xóa nhân viên thành công.");//thong báo thành công 
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTKActionPerformed
        timKiem(dgwNV, 0);//tim kiem theo ma
    }//GEN-LAST:event_btnTKActionPerformed

    private void cbCVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbCVActionPerformed
    public boolean checkValidate() {
        if (lblNameNV.getText().isEmpty()
                || cbCV.getSelectedItem().toString().isEmpty()
                || lblDate.getText().isEmpty()
                || lblLuong.getText().isEmpty()
                || lblSDT.getText().isEmpty()) {
            return false;
        }

        return true;
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.swing.Background background1;
    private javax.swing.JLayeredPane bg;
    private qlnh.swing.Button btnDate;
    private qlnh.swing.Button btnDelete;
    private qlnh.swing.Button btnInsert;
    private qlnh.swing.Button btnLM;
    private qlnh.swing.Button btnTK;
    private qlnh.swing.Button btnUpdate;
    private javax.swing.JComboBox<String> cbCV;
    private com.raven.datechooser.DateChooser dateChooser1;
    private javax.swing.JTable dgwNV;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private qlnh.swing.MyTextField lblDate;
    private qlnh.swing.MyTextField lblIDNV;
    private qlnh.swing.MyTextField lblLuong;
    private qlnh.swing.MyTextField lblNameNV;
    private qlnh.swing.MyTextField lblSDT;
    private qlnh.swing.MyTextField lblTK;
    private javax.swing.JLayeredPane sp;
    private com.raven.componert.Cart tongBV;
    private com.raven.componert.Cart tongDauBep;
    private com.raven.componert.Cart tongLeTan;
    private com.raven.componert.Cart tongNV;
    private com.raven.componert.Cart tongNVVS;
    private com.raven.componert.Cart tongPhucVu;
    // End of variables declaration//GEN-END:variables
}
/*
try {
            connection = cn.getConnection();
            int number;
            Vector row;
            String sql = "select * from qlnhanvien2";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            ResultSetMetaData metadata = rs.getMetaData();
            number = metadata.getColumnCount();
            tb.setRowCount(0);
            while (rs.next()) {
                row = new Vector();
                for( int i = 1; i<= number; i++){
                    row.addElement(rs.getString(i));
                }
                tb.addRow(row);
            }
            dgwNV.setModel(tb);
            updateTongNhanVien();
        } catch (Exception e) {
             e.printStackTrace(); 
        }
 */
