
package com.raven.formQL;

import com.raven.Connection.DatabaseConnection;
import com.raven.componert.Message;
import com.raven.datechooser.SelectedDate;
import com.raven.model.ModelNhanVien;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import com.raven.service.ServiceNV;
import com.raven.swing.ScrollBar;
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
    final  String header[] = {"Mã nhân viên", "Tên nhân viên", "Số điện thoại", "Ngày vào làm",
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
        //lblIDNV.setEnabled(false);
    }
   
    private void intt(){
        layout = new MigLayout("fill, insets 0");
        bg.setLayout(layout);
    }
    private void updateTongNhanVien() {
        int totalEmployees = dgwNV.getRowCount();
        lblTongNV.setText(""+totalEmployees);
    }
    public void timKiem(JTable table, int cot){
        DefaultTableModel ob = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(ob);
        table.setRowSorter(obj);
        obj.setRowFilter(RowFilter.regexFilter(lblTK.getText(), cot));
    }

    public void loadBang(){
        try {
            connection = cn.getConnection();
            String sql = "Select * from qlnhanvien2";
            int number;
            Vector row;
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();
            number =  metaData.getColumnCount();;
            tb.setRowCount(0);
            while(rs.next()){
                row = new Vector();
                for(int i = 1; i < number; i++){
                    row.addElement(rs.getString(i));
                }
                tb.addRow(row);
            }
            dgwNV.setModel(tb);
            updateTongNhanVien();
            
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
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lblDate = new javax.swing.JTextField();
        lblLuong = new javax.swing.JTextField();
        lblSDT = new javax.swing.JTextField();
        lblNameNV = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblIDNV = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnDate = new qlnh.swing.Button();
        btnInsert = new qlnh.swing.Button();
        btnUpdate = new qlnh.swing.Button();
        btnDelete = new qlnh.swing.Button();
        btnLM = new qlnh.swing.Button();
        btnOK = new qlnh.swing.Button();
        jLabel8 = new javax.swing.JLabel();
        lblTK = new javax.swing.JTextField();
        btnTK = new qlnh.swing.Button();
        bg = new javax.swing.JLayeredPane();
        jLabel9 = new javax.swing.JLabel();
        lblTongNV = new javax.swing.JLabel();
        cbCV = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        dgwNV = new javax.swing.JTable();

        jLabel7.setText("Lương:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
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

        btnOK.setBackground(new java.awt.Color(204, 183, 253));
        btnOK.setForeground(new java.awt.Color(51, 51, 51));
        btnOK.setText("OK");
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Tìm kiếm: ");

        lblTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblTKActionPerformed(evt);
            }
        });
        lblTK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lblTKKeyReleased(evt);
            }
        });

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
            .addGap(0, 361, Short.MAX_VALUE)
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 97, Short.MAX_VALUE)
        );

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Tổng nhân viên: ");

        lblTongNV.setBackground(new java.awt.Color(204, 204, 255));
        lblTongNV.setText("jLabel10");

        cbCV.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lễ tân", "Đầu bếp", "Thu ngân", "Nhân viên vệ sinh", "Bảo vệ" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(bg))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnLM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(lblDate, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnOK, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                                        .addComponent(lblNameNV, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblIDNV, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblLuong, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                            .addComponent(lblTK, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(25, 25, 25)
                                            .addComponent(btnTK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblTongNV, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(110, 110, 110))
                            .addComponent(cbCV, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnTK, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(lblTK))
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblIDNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblNameNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cbCV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5))
                    .addComponent(btnOK, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lblTongNV))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bg)
                .addContainerGap())
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void lblTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblTKActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblTKActionPerformed

    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOKActionPerformed
        
        SelectedDate selectedDate = dateChooser1.getSelectedDate();

        if (selectedDate != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date(selectedDate.getYear() - 1900, selectedDate.getMonth() - 1, selectedDate.getDay());
            String formattedDate = dateFormat.format(date);
            lblDate.setText(formattedDate);
        }
    }//GEN-LAST:event_btnOKActionPerformed

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
                mdb.setLuong(Float.parseFloat(lblLuong.getText()));
                // Kiểm tra trùng lặp và thực hiện thêm
                try {
                    if(service.checkDuplicateNV(mdb.getMaNV())){
                        showMessage(Message.MessageType.ERROR, "Mã nhân viên đã tồn tại. ");
                    }else{
                        service.insertNV(mdb);
                        showMessage(Message.MessageType.SUCCESS, "Thêm nhân viên thành công.");
                        resetForm();
                    }
                } catch (Exception e) {
                    showMessage(Message.MessageType.ERROR, "Lỗi khi thêm.");
                    e.printStackTrace();
                }
            }
            }catch (Exception e) {
            showMessage(Message.MessageType.ERROR, "Đã xảy ra lỗi không xác định.");
            e.printStackTrace();
        } 
    }//GEN-LAST:event_btnInsertActionPerformed

    private void dgwNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dgwNVMouseClicked
        int x = dgwNV.getSelectedRow();
        if(x >= 0){
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
                    mdb.setLuong(Float.parseFloat(lblLuong.getText()));
                    service.UpdateNV(mdb);
                    showMessage(Message.MessageType.SUCCESS, "Sửa nhân viên thành công.");
                    resetForm();
                } catch (Exception e) {
                    showMessage(Message.MessageType.ERROR, "Lỗi khi sửa.");
                    e.printStackTrace();
                }
            }
            }catch (Exception e) {
            showMessage(Message.MessageType.ERROR, "Đã xảy ra lỗi không xác định.");
            e.printStackTrace();
        } 
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        
        //connection = cn.getConnection();
        try {
            if(lblIDNV.getText().isEmpty()){
                showMessage(Message.MessageType.ERROR, "Vui lòng điền mã nhân viên cần xóa.");
            }else{
                if(lblIDNV.getText().equals("NV001")){
                    showMessage(Message.MessageType.ERROR, "Không thể xóa quản lý.");
                }
                else{
                    //String sql = "Delete qlnhanvien2 where idnv ='"+lblIDNV.getText()+"'";
                    //Statement st = connection.createStatement();
                    mdb.setMaNV(lblIDNV.getText());
                    int ck = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn xóa nhân viên này?", "Thông báo", JOptionPane.YES_NO_OPTION);
                    if(ck == JOptionPane.YES_OPTION){
                        service.deleteNV(mdb);
                        //st.executeUpdate(sql);
                        resetForm();
                        showMessage(Message.MessageType.SUCCESS, "Xóa nhân viên thành công.");
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

    private void lblTKKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lblTKKeyReleased
        timKiem(dgwNV, 1);//tim kiem theo ten
    }//GEN-LAST:event_lblTKKeyReleased
    public boolean checkValidate(){
        if(lblNameNV.getText().isEmpty()
                || cbCV.getSelectedItem().toString().isEmpty()
                || lblDate.getText().isEmpty()
                || lblLuong.getText().isEmpty()
                || lblSDT.getText().isEmpty()){
            return false;
        }
        
        
        return true;
    }
  
    
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
    private javax.swing.JLayeredPane bg;
    private qlnh.swing.Button btnDate;
    private qlnh.swing.Button btnDelete;
    private qlnh.swing.Button btnInsert;
    private qlnh.swing.Button btnLM;
    private qlnh.swing.Button btnOK;
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
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField lblDate;
    private javax.swing.JTextField lblIDNV;
    private javax.swing.JTextField lblLuong;
    private javax.swing.JTextField lblNameNV;
    private javax.swing.JTextField lblSDT;
    private javax.swing.JTextField lblTK;
    private javax.swing.JLabel lblTongNV;
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