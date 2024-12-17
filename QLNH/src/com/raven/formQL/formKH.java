
package com.raven.formQL;

import com.raven.Connection.DatabaseConnection;
import com.raven.componert.Message;
import com.raven.model.ModelKhachHang;
import com.raven.service.ServiceKH;
import com.raven.swing.ScrollBar;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Vector;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

public class formKH extends javax.swing.JPanel {

    private MigLayout layout;
    private ModelKhachHang mdb;
    private ServiceKH service;
    final  String header[] = {"Mã khách hàng", "Tên khách hàng","Giới tính", "Số điện thoại", "Địa chỉ",
     "Mã người dùng", "Ngày tham gia"};//tieu de cua bang
    DefaultTableModel tb = new DefaultTableModel(header, 0);//them tieu de vao hang dau cua bang
    int q, i;
    DatabaseConnection cn = new DatabaseConnection();//class ket noi voi database
    Connection connection;
    public formKH() {
        initComponents();
        //txtND.setEnabled(false);
        service = new ServiceKH();//goi doi tuong kh
        mdb = new ModelKhachHang();//goi class ServiceKH de thuc thi cac lenh them sua xoa
        loadBang();//thuc hien load bang
        intt();
        jScrollPane1.setVerticalScrollBar(new ScrollBar());
    }
    private void intt(){
        layout = new MigLayout("fill, insets 0");// set layout cho form hien thong bao
        bg.setLayout(layout);
    }
    private void updateTongKH() {
        int totalEmployees = dgwKH.getRowCount();//dem cac dong co trong bang bat dau tu dong 1
        lblTongKH.setText(""+totalEmployees);//in ra label
    }
    public void timKiem(JTable table, JTextField text, int cot){
        DefaultTableModel ob = (DefaultTableModel) table.getModel();                     //lay mo hinh du lieu cua bang 
        TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(ob);           //them bo sap xep hang vao trong bang
        table.setRowSorter(obj);                                                    //thiet lap bo sap sep hang vao bang 
        obj.setRowFilter(RowFilter.regexFilter(text.getText(), cot));   //loc cac hang co chuoi can tim thuoc cot 
    }

    public void loadBang(){
        try {
            connection = cn.getConnection();//ket noi toi database
            int number;
            Vector row;//tao 1 vector chua cac hang
            String sql = "select * from qlkhachhang";//cau truy van
            Statement st = connection.createStatement();//cung cap phuong thuc de thucc thi truy van voi database
            ResultSet rs = st.executeQuery(sql);//con tro den hang dau cua bang ket qua cua cau sql select 
            ResultSetMetaData metadata = rs.getMetaData();// lay cac thong tin tu ResultSet
            number = metadata.getColumnCount();
            tb.setRowCount(0);
            while (rs.next()) {//con tro duy chuyen den hang tiep theo
                row = new Vector();
                for( int i = 1; i<= number; i++){
                    row.addElement(rs.getString(i));//them thong tin vao tung hang trong bang
                }
                tb.addRow(row);//them hang vao bang
            }
            dgwKH.setModel(tb);
            updateTongKH();//cap nhat tong so luong khach hang
        } catch (Exception e) {//xu ly ngoai le
             e.printStackTrace(); 
        }
    }
    public void resetForm(){//lam moi cac o text
        txtName.setText("");
        txtDC.setText("");
        txtSDT.setText("");
        txtID.setText("");
        cbGT.setSelectedItem("");
        txtND.setText("");
        txtID.setEnabled(true);
        txtND.setEnabled(true);
        loadBang();
    }

    public boolean checkValidate(){//kiem tra xem cac o text da duoc dien day du chua
        if(txtName.getText().isEmpty()
          ||txtID.getText().isEmpty()
          ||txtDC.getText().isEmpty()
          ||txtSDT.getText().isEmpty()
          ||cbGT.getSelectedItem().toString().isEmpty()){
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        cbGT = new javax.swing.JComboBox<>();
        txtDC = new javax.swing.JTextField();
        bg = new javax.swing.JLayeredPane();
        btnSua = new qlnh.swing.Button();
        btnXoa = new qlnh.swing.Button();
        btnLM = new qlnh.swing.Button();
        btnTK = new qlnh.swing.Button();
        jLabel7 = new javax.swing.JLabel();
        lblTongKH = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtND = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        dgwKH = new javax.swing.JTable();

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Quản Lý Khách Hàng");

        jLabel1.setText("Tên KH:");

        jLabel3.setText("SDT:");

        jLabel4.setText("Giới tính:");

        jLabel5.setText("Địa chỉ:");

        jLabel6.setText("Mã KH:");

        cbGT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        btnSua.setBackground(new java.awt.Color(204, 183, 253));
        btnSua.setForeground(new java.awt.Color(51, 51, 51));
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(204, 183, 253));
        btnXoa.setForeground(new java.awt.Color(51, 51, 51));
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
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

        btnTK.setBackground(new java.awt.Color(204, 183, 253));
        btnTK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/search.png"))); // NOI18N
        btnTK.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        btnTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTKActionPerformed(evt);
            }
        });

        jLabel7.setText("Tổng số khách hàng:");

        lblTongKH.setText("jLabel8");

        jLabel8.setText("Mã ND:");

        txtND.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNDKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLM, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7)
                        .addGap(62, 62, 62)
                        .addComponent(lblTongKH))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtName)
                            .addComponent(txtSDT)
                            .addComponent(cbGT, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtDC)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTK, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                            .addComponent(txtND))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTK, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cbGT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtDC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtND, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblTongKH))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(bg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        try {
            // kiem tra xem cac o text da duoc dien day du chua
            if (!checkValidate()) {
                showMessage(Message.MessageType.ERROR, "Vui lòng điền đầy đủ thông tin.");
            } else {
                try {
                    //cap nhat cac thong tin
                    mdb.setIDKH(Integer.parseInt(txtID.getText()));
                    mdb.setName(txtName.getText());
                    mdb.setSdt(txtSDT.getText());
                    mdb.setDiaChi(txtDC.getText()); 
                    mdb.setGioiTinh(cbGT.getSelectedItem().toString());
                    service.UpdateKH(mdb);//thuc hien sua thong tin
                    showMessage(Message.MessageType.SUCCESS, "Sửa KH thành công.");
                    resetForm();//lam moi lai form
                    updateTongKH();//cap nhat tong khach hang
                } catch (Exception e) {
                    showMessage(Message.MessageType.ERROR, "Lỗi khi sửa.");
                    e.printStackTrace();
                }
            }
        }catch (Exception e) {
            showMessage(Message.MessageType.ERROR, "Đã xảy ra lỗi không xác định.");
            e.printStackTrace();
        } 
    }//GEN-LAST:event_btnSuaActionPerformed

    private void jScrollPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseClicked
        
    }//GEN-LAST:event_jScrollPane1MouseClicked

    private void dgwKHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dgwKHMouseClicked
        //do du lieu vao cac o textbox
        int x = dgwKH.getSelectedRow();//dem hang trong bang
        if(x >= 0){//neu bang co tu 1 hang tro len, ta do du lieu cua hang vao o textbox
            txtID.setText(dgwKH.getValueAt(dgwKH.getSelectedRow(), 0) + "");
            txtName.setText(dgwKH.getValueAt(dgwKH.getSelectedRow(), 1) + "");
            txtSDT.setText(dgwKH.getValueAt(dgwKH.getSelectedRow(), 2) + "");
            cbGT.setSelectedItem(dgwKH.getValueAt(dgwKH.getSelectedRow(), 3) + "");
            txtDC.setText(dgwKH.getValueAt(dgwKH.getSelectedRow(), 4) + "");
            txtND.setText(dgwKH.getValueAt(dgwKH.getSelectedRow(), 5) + "");
            txtID.setEnabled(false);//khoa o idkh vi day la o khoa chinh cua bang khachhang
            txtND.setEnabled(false);//khoa o idkh vi day la o khoa chinh cua bang nguoidung
        }
    }//GEN-LAST:event_dgwKHMouseClicked

    private void btnLMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLMActionPerformed
        resetForm();//lam moi form
    }//GEN-LAST:event_btnLMActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        connection = cn.getConnection();//ket noi database
        try {
            if(txtND.getText().isEmpty()){
                showMessage(Message.MessageType.ERROR, "Vui lòng điền mã người dùng của KH cần xóa.");
            }else{
                int id = Integer.parseInt(txtND.getText());//ep kieu chuoi thanh int
                String sql1 = "DELETE FROM qlkhachhang WHERE id_nd = ?";//cau lenh truy van delete
                String sql2 = "DELETE FROM nguoidung WHERE id_nd = ?";
                Statement st = connection.createStatement();//cung cap phuong thuc de thucc thi truy van voi database
                int ck = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn xóa KH này?", "Thông báo", JOptionPane.YES_NO_OPTION);
                if(ck == JOptionPane.YES_OPTION){
                    try (PreparedStatement pstmt1 = connection.prepareStatement(sql1);
                        PreparedStatement pstmt2 = connection.prepareStatement(sql2)) //thuc hien truy van tham so
                    {
                        pstmt1.setInt(1, id);//tham so 1 ung voi id_nd
                        pstmt2.setInt(1, id);//tham so 1 ung voi id_nd
                        
                        pstmt1.executeUpdate();//thuc thi cau lenh delete
                        pstmt2.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    resetForm();//lam moi form
                    showMessage(Message.MessageType.SUCCESS, "Xóa KH thành công.");
                }
            }
                
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTKActionPerformed
        timKiem(dgwKH, txtID, 0);
    }//GEN-LAST:event_btnTKActionPerformed

    private void txtNDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNDKeyReleased
        timKiem(dgwKH, txtID, 5);
    }//GEN-LAST:event_txtNDKeyReleased

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
    private qlnh.swing.Button btnLM;
    private qlnh.swing.Button btnSua;
    private qlnh.swing.Button btnTK;
    private qlnh.swing.Button btnXoa;
    private javax.swing.JComboBox<String> cbGT;
    private javax.swing.JTable dgwKH;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTongKH;
    private javax.swing.JTextField txtDC;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtND;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtSDT;
    // End of variables declaration//GEN-END:variables
}
