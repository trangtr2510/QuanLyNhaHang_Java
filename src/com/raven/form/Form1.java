package com.raven.form;

import com.raven.Connection.DatabaseConnection;
import com.raven.componert.Message;
import com.raven.datechooser.SelectedDate;
import com.raven.model.DSTangDAO;
import com.raven.model.DanhSachTang;
import com.raven.model.ModelDatBan;
import com.raven.model.ModelHD;
import com.raven.model.ModelUser;
import com.raven.model.ModelVoucher;
import com.raven.service.ServiceBan;
import com.raven.service.ServiceVoucher;
import com.raven.service.ServiesHD;
import com.raven.swing.ScrollBar;
import com.raven.transitions.TransitionsForm;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class Form1 extends TransitionsForm {

    DSTangDAO daoTang = new DSTangDAO();
    private MigLayout layout;
    private ModelDatBan mdb;
    private ServiceBan service;
    private JFrame jFame;
    private ModelUser user;
    private DatabaseConnection cn = new DatabaseConnection();
    Connection connection;

    public Form1(ModelUser user) {
        this.user = user;
        setLayout(new BorderLayout());
        initComponents();
        BanID.setEnabled(false);
        txtDate1.setEditable(false);
        txtTime.setEditable(false);
        intt();
        service = new ServiceBan();
        mdb = new ModelDatBan();
        serviceHD = new ServiesHD();
        mdbHD = new ModelHD();
        mdbVC = new ModelVoucher();
        serviceVC = new ServiceVoucher();
        jScrollPane2.setVerticalScrollBar(new ScrollBar());

        getDataCombobox();
        idTang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateLabels(); // Gọi phương thức để cập nhật nhãn
            }
        });

        JLabel[] labels = {jLabel15, jLabel17, jLabel19, jLabel21};
        for (JLabel label : labels) {
            label.addAncestorListener(new javax.swing.event.AncestorListener() {
                @Override
                public void ancestorAdded(javax.swing.event.AncestorEvent event) {
                    updateLabels();
                }

                @Override
                public void ancestorRemoved(javax.swing.event.AncestorEvent event) {

                }

                @Override
                public void ancestorMoved(javax.swing.event.AncestorEvent event) {

                }
            });
        }

        cbMa.addActionListener(e -> {
            Object selectedItem = cbMa.getSelectedItem();
            if (selectedItem != null) {
                updateMoTaLabel(selectedItem.toString());
            } else {
                // Handle the case when nothing is selected, if needed
                updateMoTaLabel(""); // or any appropriate default value
            }
        });

        checkAndAddVoucher(user.getUserID());
        loadVouchersToComboBox();
        // Ensure cbMa.getSelectedItem() is not null before calling toString()
        Object selectedItem = cbMa.getSelectedItem();
        if (selectedItem != null) {
            updateMoTaLabel(selectedItem.toString());
        } else {
            updateMoTaLabel(""); // or any default value
        }
    }

    public void updateMoTaLabel(String i) {
        // Lấy mã voucher hiện tại trong ComboBox
        String selectedMaVoucher = i;
        // Kiểm tra xem mã voucher có khác null không
        if (selectedMaVoucher != null && !selectedMaVoucher.trim().isEmpty()) {
            // Gọi phương thức getMoTaByMaVoucher để lấy mô tả
            String moTa = service.getMoTaByMaVoucher(selectedMaVoucher);

            // Hiển thị mô tả lên JLabel
            lblMota.setText(moTa != null ? "Chú thích: " + moTa : "Mô tả không tồn tại.");
        } else {
            lblMota.setText("Chú thích: Bạn chưa có ưu đãi, hãy tích điểm để đổi lấy ưu đãi.");
        }
    }

    public void loadVouchersToComboBox() {
        try {
            String idkhStr = service.getIdKhByIdNd(user.getUserID());
            int idkhBan = Integer.parseInt(idkhStr);
            // Lấy tất cả các mã voucher từ phương thức getAllMaVouchers()
            List<String> maVouchers = service.getMaVouchersByIdKH(idkhBan);

            // Lấy mô hình của ComboBox để thêm các item vào
            DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) cbMa.getModel();
            model.removeAllElements(); // Xóa tất cả các item cũ trong ComboBox

            // Thêm từng mã voucher vào ComboBox nếu chưa có
            for (String mavoucher : maVouchers) {
                if (model.getIndexOf(mavoucher) == -1) { // Kiểm tra mã voucher đã có trong ComboBox chưa
                    model.addElement(mavoucher); // Thêm mavoucher vào ComboBox
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkAndAddVoucher(int idnd) {
        try (Connection connection = cn.getConnection()) {
            String getPhoneQuery = "SELECT idkh FROM qlkhachhang WHERE id_nd = ?";
            try (PreparedStatement ps = connection.prepareStatement(getPhoneQuery)) {
                ps.setInt(1, idnd);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String sdt = rs.getString("idkh");
                        checkAndAddVoucherIfNotExists(sdt);
                    } else {
                        System.out.println("Không tìm thấy khách hàng với ID người dùng: " + idnd);
                    }
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void checkAndAddVoucherIfNotExists(String sdt) {
        String checkSDTQuery = "SELECT COUNT(*) FROM dsban WHERE idkh = ?";
        try (Connection connection = cn.getConnection(); PreparedStatement psCheck = connection.prepareStatement(checkSDTQuery)) {
            psCheck.setString(1, sdt);
            try (ResultSet rsCheck = psCheck.executeQuery()) {
                if (rsCheck.next() && rsCheck.getInt(1) == 0) {
                    addVoucherToComboBox("Voucher001");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addVoucherToComboBox(String voucherCode) {
        DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) cbMa.getModel();
        if (model.getIndexOf(voucherCode) == -1) {
            model.addElement(voucherCode);
        }
    }

//    public String displayUserInfo(ModelUser user) {
//        if (user == null) {
//            JOptionPane.showMessageDialog(null, "User information is not available.");
//            return null;
//        }
//
//        int userID = user.getUserID();
//        String idkh = null;
//
//        String sql = "SELECT kh.idkh FROM qlkhachhang kh JOIN nguoidung nd ON kh.ID_ND = nd.ID_ND WHERE nd.ID_ND = ?";
//        try (Connection conn = cn.getConnection(); // Connection closed automatically
//                 PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
//            stmt.setInt(1, userID);
//            if (rs.next()) {
//                idkh = rs.getString("idkh");
//            } else {
//                JOptionPane.showMessageDialog(null, "Không tìm thấy thông tin khách hàng cho ID: " + userID);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Không thể tải thông tin khách hàng.");
//        }
//
//        return idkh;
//    }
    private void updateLabels() {
        String selectedItem = idTang.getSelectedItem().toString();
        int index = selectedItem.lastIndexOf(" ") + 1;
        String numberPart = selectedItem.substring(index);
        JLabel[] labels = {jLabel15, jLabel17, jLabel19, jLabel21};
        try {
            for (int i = 0; i < labels.length; i++) {
                mdb.setIDBan("Ban" + (i + 1) + "T" + numberPart);
                mdb.setTang(selectedItem);
                if (service.checkDuplicateBanTrong(mdb.getIDBan(), mdb.getTang())) {
                    labels[i].setText("Ban" + (i + 1) + "T" + numberPart + " (Còn Trống)");
                } else {
                    labels[i].setText("Ban" + (i + 1) + "T" + numberPart + " (Đã Đặt)"); // Có thể thay đổi theo yêu cầu
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getDataCombobox() {
        DefaultComboBoxModel<DanhSachTang> model = (DefaultComboBoxModel) idTang.getModel();
        for (DanhSachTang dst : daoTang.getAllDSTang()) {
            model.addElement(dst);
        }
    }

    private void intt() {
        layout = new MigLayout("fill, insets 0");
        bg.setLayout(layout);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateChooser1 = new com.raven.datechooser.DateChooser();
        timePicker1 = new com.raven.swing.TimePicker();
        background1 = new com.raven.swing.Background();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        button5 = new qlnh.swing.Button();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        button6 = new qlnh.swing.Button();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        button7 = new qlnh.swing.Button();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        button8 = new qlnh.swing.Button();
        jLabel22 = new javax.swing.JLabel();
        idTang = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        button1 = new qlnh.swing.Button();
        button4 = new qlnh.swing.Button();
        jLabel9 = new javax.swing.JLabel();
        button9 = new qlnh.swing.Button();
        button10 = new qlnh.swing.Button();
        BanID = new qlnh.swing.MyTextField();
        NameKH = new qlnh.swing.MyTextField();
        SDT = new qlnh.swing.MyTextField();
        SoLuong = new qlnh.swing.MyTextField();
        txtDate1 = new qlnh.swing.MyTextField();
        txtTime = new qlnh.swing.MyTextField();
        jLabel1 = new javax.swing.JLabel();
        cbMa = new javax.swing.JComboBox<>();
        bg = new javax.swing.JLayeredPane();
        lblMota = new javax.swing.JLabel();

        dateChooser1.setTextRefernce(txtDate1);

        timePicker1.setDisplayText(txtTime);

        setLayout(new java.awt.BorderLayout());

        jScrollPane2.setPreferredSize(new java.awt.Dimension(700, 538));
        jScrollPane2.setWheelScrollingEnabled(false);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(700, 536));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(153, 153, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Ban1T1");

        button5.setBackground(new java.awt.Color(204, 152, 255));
        button5.setForeground(new java.awt.Color(51, 51, 51));
        button5.setText("Đặt bàn");
        button5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        button5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button5ActionPerformed(evt);
            }
        });

        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/table2.png"))); // NOI18N

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(153, 153, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Ban2T1");

        button6.setBackground(new java.awt.Color(204, 153, 255));
        button6.setForeground(new java.awt.Color(51, 51, 51));
        button6.setText("Đặt bàn");
        button6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        button6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button6ActionPerformed(evt);
            }
        });

        jLabel18.setBackground(new java.awt.Color(255, 255, 255));
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/table2.png"))); // NOI18N

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(153, 153, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Ban3T1");

        button7.setBackground(new java.awt.Color(204, 153, 255));
        button7.setForeground(new java.awt.Color(51, 51, 51));
        button7.setText("Đặt bàn");
        button7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        button7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button7ActionPerformed(evt);
            }
        });

        jLabel20.setBackground(new java.awt.Color(255, 255, 255));
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/table2.png"))); // NOI18N

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(153, 153, 255));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Ban4T1");

        button8.setBackground(new java.awt.Color(204, 153, 255));
        button8.setForeground(new java.awt.Color(51, 51, 51));
        button8.setText("Đặt bàn");
        button8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        button8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button8ActionPerformed(evt);
            }
        });

        jLabel22.setBackground(new java.awt.Color(255, 255, 255));
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/table2.png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addComponent(button5, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(195, 195, 195)
                        .addComponent(button6, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(0, 16, Short.MAX_VALUE))
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(12, 12, 12)
                                .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addGap(106, 106, 106)
                                .addComponent(button7, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(197, 197, 197)
                                .addComponent(button8, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel22)))
                        .addGap(6, 6, 6)))
                .addGap(85, 85, 85))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(button6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(button5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(button8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        jScrollPane2.setViewportView(jPanel4);

        idTang.setBackground(new java.awt.Color(83, 105, 118));
        idTang.setPreferredSize(new java.awt.Dimension(50, 22));
        idTang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idTangActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("ĐẶT BÀN");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Thông tin chi tiết");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Bàn: ");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Số điện thoại: ");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("Số lượng Người: ");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setText("Thời gian: ");

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel23.setText("Ngày: ");

        button1.setBackground(new java.awt.Color(204, 153, 255));
        button1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/inventory (1).png"))); // NOI18N
        button1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        button4.setBackground(new java.awt.Color(204, 153, 255));
        button4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/clock.png"))); // NOI18N
        button4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button4ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Tên khách hàng: ");

        button9.setBackground(new java.awt.Color(204, 153, 255));
        button9.setForeground(new java.awt.Color(51, 51, 51));
        button9.setText("Đặt bàn");
        button9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button9ActionPerformed(evt);
            }
        });

        button10.setBackground(new java.awt.Color(204, 153, 255));
        button10.setForeground(new java.awt.Color(51, 51, 51));
        button10.setText("Hủy đặt bàn");
        button10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button10ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Mã giảm giá:");

        cbMa.setBackground(new java.awt.Color(83, 105, 118));
        cbMa.setForeground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 72, Short.MAX_VALUE)
        );

        lblMota.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblMota.setText("Chú thích: ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(button9, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(button10, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel14))
                                .addGap(2, 2, 2)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(SoLuong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtDate1, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                                            .addComponent(txtTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(button4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(button1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(cbMa, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(BanID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(NameKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(SDT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jLabel1))))
                .addGap(11, 11, 11))
            .addComponent(bg, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMota, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(BanID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(NameKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(SDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(SoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel23)
                        .addComponent(txtDate1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(button1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(button4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbMa, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblMota, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(button9, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(button10, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addComponent(bg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background1);
        background1.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(background1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(289, 289, 289)
                        .addComponent(idTang, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(background1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 715, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        background1Layout.setVerticalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(background1Layout.createSequentialGroup()
                        .addComponent(idTang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(background1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(5, 5, 5)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        add(background1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void idTangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idTangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idTangActionPerformed

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        // TODO add your handling code here:
        dateChooser1.showPopup(this, (getWidth() - dateChooser1.getPreferredSize().width) / 2, (getHeight() - dateChooser1.getPreferredSize().height) / 2);

    }//GEN-LAST:event_button1ActionPerformed

    private void button4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button4ActionPerformed
        // TODO add your handling code here:
        timePicker1.showPopup(this, (getWidth() - timePicker1.getPreferredSize().width) / 2, (getHeight() - timePicker1.getPreferredSize().height) / 2);
    }//GEN-LAST:event_button4ActionPerformed

    public void resetForm() {
        BanID.setText("");
        idTang.setSelectedItem("");
        NameKH.setText("");
        SDT.setText("");
        SoLuong.setText("");
        txtDate1.setText("");
        txtTime.setText("");
    }

    public boolean checkValidateForm() {
        if (BanID.getText().isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean checkValidateForm2() {
        if (NameKH.getText().isEmpty() || SDT.getText().isEmpty()
                || SoLuong.getText().isEmpty()
                || txtDate1.getText().isEmpty()
                || txtTime.getText().isEmpty()) {
            return false;
        }
        return true;
    }

    ModelHD mdbHD;
    ServiesHD serviceHD;
    ModelVoucher mdbVC;
    ServiceVoucher serviceVC;
    private void button9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button9ActionPerformed
        //Dat ban 
        try {
            // Kiểm tra xem form đã được điền đầy đủ hay chưa
            if (!checkValidateForm()) {
                showMessage(Message.MessageType.ERROR, "Vui lòng chọn bàn cần đặt.");
            } else if (!checkValidateForm2()) {
                showMessage(Message.MessageType.ERROR, "Vui lòng điền đầy đủ thông tin.");
            } else {
                // Thực hiện các thiết lập thông tin đặt bàn

                mdb.setIDBan(BanID.getText());
                mdb.setTang(idTang.getSelectedItem().toString());
                mdb.setNameKH(NameKH.getText());
                mdb.setSDT(SDT.getText());
                int slNguoi = Integer.parseInt(SoLuong.getText());
                mdb.setSLNguoi(slNguoi);
                mdb.setDate(txtDate1.getText());
                mdb.setTime(txtTime.getText());
                String idHD = serviceHD.getLatestMaHD(); // lay idhd lon nhat trong bang
                if (idHD == null || idHD.isEmpty()) {
                    idHD = "HD001"; //idhd lon nhat Neu trong bang chua co idhd nao thi 
                } else {
                    int k = Integer.parseInt(idHD.substring(2));//ep kieu chuoi con lay tu chuoi idHD, chuoi con bat dau tu ki tu thu 2(Dem tu 0)
                    int tangidHD = k + 1;//tang idhd len 1
                    idHD = "HD" + String.format("%03d", tangidHD); // tao idhd moi
                }
                mdbHD.setIdHD(idHD);//set gia tri cho idhd, idban, tang, ngaytt, gia 
                mdbHD.setIdBan(BanID.getText());
                mdbHD.setTang(idTang.getSelectedItem().toString());
                LocalDate currentDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String formattedDate = currentDate.format(formatter);
                mdbHD.setNgayTT(formattedDate);
                if (cbMa.getSelectedItem() == null || cbMa.getSelectedItem().toString().isEmpty()) {
                    mdbHD.setGia(slNguoi * 90);
                } else {
                    String maVoucher = cbMa.getSelectedItem().toString();
                    if (serviceVC.checkDuplicateVoucher(maVoucher)) {
                        float giamGia = serviceVC.getVoucherDiscount(maVoucher);
                        float gia = slNguoi * 90 * (1 - giamGia);
                        mdbHD.setGia(gia);
                    } else {
                        mdbHD.setGia(slNguoi * 90);
                    }
                }
                String idkhStr = service.getIdKhByIdNd(user.getUserID());
                int idkhBan = Integer.parseInt(idkhStr);
                mdb.setIDKH(idkhBan);
                if (idkhStr != null) {
                    int idkh = Integer.parseInt(idkhStr);
                    mdbHD.setIDKH(idkh);
                } else {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy ID khách hàng.");
                }
                Object selectedItem = cbMa.getSelectedItem();
                if (selectedItem == null || selectedItem.equals("")) {
                    mdbVC.setMaVoucher("");
                } else {
                    mdbVC.setMaVoucher(selectedItem.toString());
                }

                // Kiểm tra sự trùng lặp và thực hiện đặt bàn
                try {
                    if (service.checkDuplicateBan(mdb.getIDBan(), mdb.getTang())) {
                        showMessage(Message.MessageType.ERROR, "Bàn đã có người ");
                    } else {
                        service.UpdateBan(mdb);
                        service.insertBan(mdb);
                        serviceVC.deleteVC(mdbVC, idkhBan);
                        serviceHD.insertHD(mdbHD);
                        showMessage(Message.MessageType.SUCCESS, "Đặt bàn thành công.");
                        JLabel[] labels = {jLabel15, jLabel17, jLabel19, jLabel21};
                        for (JLabel label : labels) {
                            String currentText = label.getText();
                            if (currentText.contains("(Còn Trống)")) {
                                if (label.getText().contains(mdb.getIDBan())) {
                                    label.setText(label.getText().replace("(Còn Trống)", "(Đã Đặt)")); // Chỉ thay đổi văn bản của bàn đã đặt
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    showMessage(Message.MessageType.ERROR, "Lỗi khi đặt bàn.");
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            showMessage(Message.MessageType.ERROR, "Đã xảy ra lỗi không xác định.");
            e.printStackTrace();
        }
    }//GEN-LAST:event_button9ActionPerformed


    private void button10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button10ActionPerformed
        //Huy Dat ban 
        try {
            mdb.setIDBan(BanID.getText());
            mdb.setTang(idTang.getSelectedItem().toString());
            mdb.setSDT(SDT.getText());
            try {
                if (service.checkDuplicateBan(mdb.getIDBan(), mdb.getTang())) {
                    if (SDT.getText().isEmpty()) {
                        showMessage(Message.MessageType.ERROR, "Vui lòng điền SDT đã đặt để xác nhận hủy.");
                    } else {
                        if (service.checkSDT(mdb.getIDBan(), mdb.getTang(), mdb.getSDT())) {
                            jFame = new JFrame("Exit");
                            if (JOptionPane.showConfirmDialog(jFame, "Bạn có chắc muốn hủy đặt bàn hay không?", "Hủy đặt bàn",
                                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                service.HUYBan(mdb);
                                resetForm();
                                showMessage(Message.MessageType.SUCCESS, "Bàn đã được hủy thành công.");
                                JLabel[] labels = {jLabel15, jLabel17, jLabel19, jLabel21};
                                for (JLabel label : labels) {
                                    String currentText = label.getText();
                                    if (currentText.contains("(Đã Đặt)")) {
                                        if (label.getText().contains(mdb.getIDBan())) {
                                            label.setText(label.getText().replace("(Đã Đặt)", "(Còn Trống)")); // Chỉ thay đổi văn bản của bàn đã đặt
                                        }
                                    }
                                }
                            }
                        } else {
                            showMessage(Message.MessageType.ERROR, "Số điện thoại không chính xác.");
                        }
                    }
                } else {
                    showMessage(Message.MessageType.ERROR, "Bàn chưa được đặt.");
                }
            } catch (Exception e) {
                showMessage(Message.MessageType.ERROR, "Lỗi khi hủy bàn.");
                e.printStackTrace();
            }
        } catch (Exception e) {
            showMessage(Message.MessageType.ERROR, "Đã xảy ra lỗi không xác định.");
            e.printStackTrace();
        }
    }//GEN-LAST:event_button10ActionPerformed

    private void button5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button5ActionPerformed
        String a = idTang.getSelectedItem().toString();
        int index = a.lastIndexOf(" ") + 1;
        String numberPart = a.substring(index);
        BanID.setText("Ban1T" + numberPart);

    }//GEN-LAST:event_button5ActionPerformed

    private void button6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button6ActionPerformed
        String a = idTang.getSelectedItem().toString();
        int index = a.lastIndexOf(" ") + 1;
        String numberPart = a.substring(index);
        BanID.setText("Ban2T" + numberPart);
    }//GEN-LAST:event_button6ActionPerformed

    private void button7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button7ActionPerformed
        String a = idTang.getSelectedItem().toString();
        int index = a.lastIndexOf(" ") + 1;
        String numberPart = a.substring(index);
        BanID.setText("Ban3T" + numberPart);
    }//GEN-LAST:event_button7ActionPerformed

    private void button8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button8ActionPerformed
        String a = idTang.getSelectedItem().toString();
        int index = a.lastIndexOf(" ") + 1;
        String numberPart = a.substring(index);
        BanID.setText("Ban4T" + numberPart);
    }//GEN-LAST:event_button8ActionPerformed

    //hien thong bao
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
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private qlnh.swing.MyTextField BanID;
    private qlnh.swing.MyTextField NameKH;
    private qlnh.swing.MyTextField SDT;
    private qlnh.swing.MyTextField SoLuong;
    private com.raven.swing.Background background1;
    private javax.swing.JLayeredPane bg;
    private qlnh.swing.Button button1;
    private qlnh.swing.Button button10;
    private qlnh.swing.Button button4;
    private qlnh.swing.Button button5;
    private qlnh.swing.Button button6;
    private qlnh.swing.Button button7;
    private qlnh.swing.Button button8;
    private qlnh.swing.Button button9;
    private javax.swing.JComboBox<String> cbMa;
    private com.raven.datechooser.DateChooser dateChooser1;
    private javax.swing.JComboBox<String> idTang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblMota;
    private com.raven.swing.TimePicker timePicker1;
    private qlnh.swing.MyTextField txtDate1;
    private qlnh.swing.MyTextField txtTime;
    // End of variables declaration//GEN-END:variables

}
