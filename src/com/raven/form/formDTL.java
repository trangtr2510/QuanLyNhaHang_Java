package com.raven.form;

import com.raven.Connection.DatabaseConnection;
import com.raven.componert.Message;
import com.raven.model.ModelUser;
import com.raven.model.ModelVoucher;
import com.raven.service.ServiceBan;
import com.raven.service.ServiceVoucher;
import com.raven.transitions.TransitionsForm;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class formDTL extends TransitionsForm {

    private MigLayout layout;
    private ModelVoucher mdb;
    private ServiceVoucher service;
    private ServiceBan serviceBan;
    final String header[] = {"Mã khuyến mại", "Tên mã khuyến mại", "Mô tả", "Giảm giá", "Ngày bắt đầu", "Ngày kết thúc", "Trạng thái", "Điểm tích lũy"};
    DefaultTableModel tb = new DefaultTableModel(header, 0);
    int q, i;
    DatabaseConnection cn = new DatabaseConnection();
    Connection connection;
    private ModelUser user;

    public formDTL(ModelUser user) {
        this.user = user;
        service = new ServiceVoucher();
        mdb = new ModelVoucher();
        serviceBan = new ServiceBan();
        initComponents();
        loadBang();
        cbbox.addActionListener(e -> updateTableBasedOnComboBoxSelection());

        // Hiển thị kết quả lên JTextField
        txtDTL.setText(getDiemTichLuy(user));
        txtDTL.setEnabled(false);
        intt();
    }

    private void intt() {
        layout = new MigLayout("fill, insets 0");
        bg.setLayout(layout);
    }

    public String getDiemTichLuy(ModelUser user) {
        String result = "0";  // Giá trị mặc định khi có lỗi xảy ra
        try {
            // Lấy idkh từ user thông qua serviceBan
            String idkhStr = serviceBan.getIdKhByIdNd(user.getUserID());
            // Chuyển idkh từ String sang int
            int idkhBan = Integer.parseInt(idkhStr);
            // Tính số lần đặt bàn
            int soLanDatBan = service.demSoLanDB(idkhBan);
            if (soLanDatBan < 10) {
                int diemTL = soLanDatBan * 5;
                // Tạo chuỗi kết quả để trả về
                result = "" + diemTL;
            }
            if (soLanDatBan >= 10) {
                // Tính điểm tích lũy dựa trên số lần đặt bàn (mỗi lần đặt bàn = 5 điểm)
                int diemTL = soLanDatBan * 10;
                // Tạo chuỗi kết quả để trả về
                result = "" + diemTL;
            }

        } catch (SQLException ex) {
            Logger.getLogger(formDTL.class.getName()).log(Level.SEVERE, null, ex);
            result = "Lỗi khi truy xuất cơ sở dữ liệu";
        } catch (NumberFormatException ex) {
            Logger.getLogger(formDTL.class.getName()).log(Level.SEVERE, "Lỗi chuyển đổi ID khách hàng", ex);
            result = "Lỗi dữ liệu khách hàng";
        }

        // Trả về chuỗi kết quả
        return result;
    }

    // Phương thức lọc dữ liệu theo lựa chọn của ComboBox
    private List<Integer> filterDiemTLList(List<Integer> diemTLList, String filter) {
        List<Integer> filteredList = new ArrayList<>();

        switch (filter) {
            case "Dưới 300 điểm":
                for (Integer diem : diemTLList) {
                    if (diem < 300) {
                        filteredList.add(diem);
                    }
                }
                break;

            case "Từ 300 đến 500 điểm":
                for (Integer diem : diemTLList) {
                    if (diem >= 300 && diem <= 500) {
                        filteredList.add(diem);
                    }
                }
                break;

            case "Trên 500 điểm":
                for (Integer diem : diemTLList) {
                    if (diem > 500) {
                        filteredList.add(diem);
                    }
                }
                break;

            case "Tất cả":
            default:
                filteredList = diemTLList; // Không lọc, giữ nguyên danh sách gốc
                break;
        }

        return filteredList;
    }

    // Phương thức này sẽ được gọi khi chọn một mục trong ComboBox
    private void updateTableBasedOnComboBoxSelection() {
        // Lấy tất cả DiemTL từ cơ sở dữ liệu
        List<Integer> diemTLList = service.getDTLByIdKH();

        // Lấy lựa chọn hiện tại trong ComboBox
        String selectedFilter = (String) cbbox.getSelectedItem();

        // Lọc danh sách theo lựa chọn
        List<Integer> filteredList = filterDiemTLList(diemTLList, selectedFilter);

        // Hiển thị dữ liệu vào bảng dựa trên danh sách đã lọc
        displayResultsInTable(filteredList);
    }

    // Phương thức để hiển thị kết quả lọc vào bảng
    private void displayResultsInTable(List<Integer> filteredList) {
        tb.setRowCount(0); // Xóa dữ liệu cũ trong bảng
        List<ModelVoucher> allVouchers = service.getAllVouchers();

        for (ModelVoucher voucher : allVouchers) {
            if (filteredList.contains(voucher.getDiemTL())) {
                tb.addRow(new Object[]{
                    voucher.getMaVoucher(),
                    voucher.getTenVoucher(),
                    voucher.getMoTa(),
                    voucher.getGiamGia(),
                    voucher.getNgayBatDau(),
                    voucher.getNgayKetThuc(),
                    voucher.getTrangThai(),
                    voucher.getDiemTL()
                });
            }
        }
    }

    public void loadBang() {
        try {
            connection = cn.getConnection();
            int number;
            Vector row;
            String sql = "select * from Voucher where Trangthai = 'Hoat dong'";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            ResultSetMetaData metadata = rs.getMetaData();
            number = metadata.getColumnCount();
            tb.setRowCount(0);
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            while (rs.next()) {
                row = new Vector();
                for (int i = 1; i <= number; i++) {
                    if (i == 4) {
                        float discount = rs.getFloat(i);
                        row.addElement(decimalFormat.format(discount));
                    } else {
                        row.addElement(rs.getString(i));
                    }
                }
                tb.addRow(row);
            }
            dgwVoucher.setModel(tb);
            //updateTongNhanVien();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background1 = new com.raven.swing.Background();
        lbTitle = new javax.swing.JLabel();
        lbdTL = new javax.swing.JLabel();
        txtDTL = new qlnh.swing.MyTextField();
        lbVoucher = new javax.swing.JLabel();
        myTextField1 = new qlnh.swing.MyTextField();
        cbbox = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        dgwVoucher = new javax.swing.JTable();
        buttonOutLine1 = new qlnh.swing.ButtonOutLine();
        txtID = new qlnh.swing.MyTextField();
        bg = new javax.swing.JLayeredPane();

        setLayout(new java.awt.BorderLayout());

        lbTitle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbTitle.setForeground(new java.awt.Color(108, 91, 123));
        lbTitle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/voucher.png"))); // NOI18N
        lbTitle.setText("Điểm tích lũy\n");
        lbTitle.setIconTextGap(10);

        lbdTL.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbdTL.setForeground(new java.awt.Color(89, 89, 89));
        lbdTL.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbdTL.setText("Điểm tích lũy của bạn");

        lbVoucher.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbVoucher.setForeground(new java.awt.Color(89, 89, 89));
        lbVoucher.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbVoucher.setText("Đổi Voucher");

        myTextField1.setHint("Tìm kiếm Voucher");
        myTextField1.setPrefixIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/loupe (1).png"))); // NOI18N
        myTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myTextField1ActionPerformed(evt);
            }
        });

        cbbox.setEditable(true);
        cbbox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbbox.setForeground(new java.awt.Color(108, 91, 123));
        cbbox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Dưới 300 điểm", "Từ 300 đến 500 điểm", "Trên 500 điểm" }));
        cbbox.setToolTipText("Sắp xếp");
        cbbox.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(164, 145, 145), 2));
        cbbox.setFocusable(false);
        cbbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbboxActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(108, 91, 123));
        jLabel1.setText("Hiển thị theo");

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        dgwVoucher.setModel(new javax.swing.table.DefaultTableModel(
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
        dgwVoucher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dgwVoucherMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(dgwVoucher);

        buttonOutLine1.setBackground(new java.awt.Color(51, 255, 51));
        buttonOutLine1.setText("Đổi");
        buttonOutLine1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOutLine1ActionPerformed(evt);
            }
        });

        txtID.setHint("Nhập mã muốn đổi");

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background1);
        background1.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, background1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(background1Layout.createSequentialGroup()
                        .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbTitle)
                            .addGroup(background1Layout.createSequentialGroup()
                                .addComponent(lbdTL)
                                .addGap(18, 18, 18)
                                .addComponent(txtDTL, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(background1Layout.createSequentialGroup()
                                .addComponent(lbVoucher)
                                .addGap(27, 27, 27)
                                .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(buttonOutLine1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(myTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 238, Short.MAX_VALUE)
                        .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(background1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(cbbox, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(bg)))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        background1Layout.setVerticalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(background1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bg)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbbox, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(background1Layout.createSequentialGroup()
                        .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbdTL, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDTL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonOutLine1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(myTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(background1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void myTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_myTextField1ActionPerformed

    private void cbboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbboxActionPerformed
//        initMenuVoucherbyPoint(cbbox.getSelectedItem().toString());
    }//GEN-LAST:event_cbboxActionPerformed

    private void dgwVoucherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dgwVoucherMouseClicked
        int x = dgwVoucher.getSelectedRow();
        if (x >= 0) {
            txtID.setText(dgwVoucher.getValueAt(dgwVoucher.getSelectedRow(), 0) + "");
            txtID.setEnabled(false);
        }
    }//GEN-LAST:event_dgwVoucherMouseClicked

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

    private void buttonOutLine1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOutLine1ActionPerformed
        // Kiểm tra xem có dòng nào được chọn trong bảng hay không
        if (txtID.getText().isEmpty()) {
            if (dgwVoucher.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn Voucher muốn đổi");
                return;
            }
        } else {
            try {
                // Lấy điểm tích lũy từ txtDTL
                String diem = txtDTL.getText();
                int dTL = 0;

                // Kiểm tra xem điểm tích lũy có hợp lệ không
                try {
                    dTL = Integer.parseInt(diem);
                } catch (NumberFormatException ex) {
                    showMessage(Message.MessageType.ERROR, "Điểm tích lũy không hợp lệ.");
                    return;  // Dừng xử lý nếu điểm tích lũy không hợp lệ
                }

                // Lấy mã voucher từ txtID (chỗ nhập ID Voucher)
                String idVoucher = txtID.getText();

                // Kiểm tra nếu điểm tích lũy nhỏ hơn yêu cầu
                if (dTL < service.getDiemTLByMaVoucher(idVoucher)) {
                    showMessage(Message.MessageType.ERROR, "Điểm tích lũy không đủ.");
                } else {
                    try {
                        // Lấy ID khách hàng từ serviceBan
                        String idkhStr = serviceBan.getIdKhByIdNd(user.getUserID());
                        int idkhBan = Integer.parseInt(idkhStr);

                        // Set thông tin voucher vào đối tượng mdb
                        mdb.setMaVoucher(idVoucher);
                        mdb.setMoTa(service.getMoTaByMaVoucher(idVoucher));
                        mdb.setGiamGia(service.getGiamGiaByMaVoucher(idVoucher));
                        mdb.setTrangThai(service.getTrangThaiByMaVoucher(idVoucher));

                        // Insert voucher vào hệ thống cho khách hàng
                        service.insertVoucherKH(mdb, idkhBan);

                        // Trừ điểm tích lũy trong txtDTL
                        int remainingDiem = dTL - service.getDiemTLByMaVoucher(idVoucher);
                        txtDTL.setText(String.valueOf(remainingDiem));

                        // Lưu lại ngày đổi voucher và cập nhật số lần đặt bàn bắt đầu từ ngày này
                        service.updateNgayBatDauDemSoLanDB(idkhBan);

                        showMessage(Message.MessageType.SUCCESS, "Đổi thành công.");
                    } catch (Exception e) {
                        showMessage(Message.MessageType.ERROR, "Lỗi khi đổi.");
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                showMessage(Message.MessageType.ERROR, "Đã xảy ra lỗi không xác định.");
                e.printStackTrace();
            }
        }

    }//GEN-LAST:event_buttonOutLine1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.swing.Background background1;
    private javax.swing.JLayeredPane bg;
    private qlnh.swing.ButtonOutLine buttonOutLine1;
    private javax.swing.JComboBox<String> cbbox;
    private javax.swing.JTable dgwVoucher;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JLabel lbVoucher;
    private javax.swing.JLabel lbdTL;
    private qlnh.swing.MyTextField myTextField1;
    private qlnh.swing.MyTextField txtDTL;
    private qlnh.swing.MyTextField txtID;
    // End of variables declaration//GEN-END:variables
}
