package com.raven.formQL;

import com.raven.Connection.DatabaseConnection;
import com.raven.componert.Message;
import com.raven.datechooser.SelectedDate;
import com.raven.model.ModelDatBan;
import com.raven.model.ModelHD;
import com.raven.model.ModelUser;
import com.raven.model.Model_Cart;
import com.raven.service.ServiceBan;
import com.raven.service.ServiesHD;
import com.raven.swing.ScrollBar;
import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class formHD extends javax.swing.JPanel {

    private MigLayout layout;
    private ModelHD mdb;
    private ServiesHD service;
    private ServiceBan serviceDB;
    private ModelDatBan mdbDB;
    formBan formBan;
    private ModelUser user;
    final String header[] = {"Mã hóa đơn", "Bàn", "Tầng", "Ngày thanh toán", "Giá", "Mã khách hàng", "Trạng thái"};
    DefaultTableModel tb = new DefaultTableModel(header, 0);
    DefaultTableModel tb1 = new DefaultTableModel(header, 0);
    int q, i;
    DatabaseConnection cn = new DatabaseConnection();
    Connection connection;
    JLabel[] lb = new JLabel[]{
        new JLabel("0"),
        new JLabel("0"),
        new JLabel("0"),
        new JLabel("0"),
        new JLabel("0"),
        new JLabel("0"),
        new JLabel("0"),
        new JLabel("0"),
        new JLabel("0"),
        new JLabel("0"),
        new JLabel("0"),
        new JLabel("0")
    };

    public formHD() {
        initComponents();
        service = new ServiesHD();
        mdb = new ModelHD();
        serviceBan = new ServiceBan();
        mdbB = new ModelDatBan();
        formBan = new formBan();
        txtID.setEnabled(false);
        txtGia.setEnabled(false);
        loadBang();
        loadBang2();
        updateTongDT();
        intt();
        txtTK.setHint("Tìm kiếm theo mã Hóa đơn");
        jScrollPane1.setVerticalScrollBar(new ScrollBar());
        JLabel[] labels = {
            jLabel7, jLabel8, jLabel9, jLabel10, jLabel11, jLabel12, jLabel13,
            jLabel14, jLabel15, jLabel16, jLabel17, jLabel18, jLabel19, jLabel20,
            jLabel21, jLabel22, jLabel23, jLabel24, jLabel25, jLabel26, jLabel27,
            jLabel28, jLabel29, jLabel30
        };
        for (JLabel label : labels) {
            label.setVisible(false);
        }
    }

    private void intt() {
        layout = new MigLayout("fill, insets 0");
        bg.setLayout(layout);
    }

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

    public void hienthi() {
        JLabel[] labels = {
            jLabel15, jLabel16, jLabel17, jLabel18, jLabel19, jLabel20, jLabel21,
            jLabel22, jLabel23, jLabel24, jLabel25, jLabel26, jLabel27, jLabel28,
            jLabel29, jLabel30
        };
//        JLabel[] labels2 = {
//            jLabel7, jLabel8, jLabel9, jLabel10, jLabel11, jLabel12, jLabel13, 
//            jLabel14
//        };
        JLabel[] labels3 = {
            lb[0], lb[1], lb[2], lb[3], lb[4], lb[5],
            lb[6], lb[7], lb[8], lb[9], lb[10], lb[11]
        };
        String[] bans = {"Ban 1", "Ban 2", "Ban 3", "Ban 4"};
        String[] tangs = {"Tầng 1", "Tầng 2", "Tầng 3", "Tầng 4"};
        int[] thangs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        int i = 0;
        int j = 0;
        int k = 0;
        for (String tang : tangs) {
            for (String ban : bans) {
                hienThiSoLan(dgwHD, labels[i], 1, 2, ban, tang);
                i++;
            }
        }
        for (int thang : thangs) {
            hienThiDoanhThuThang(dgwHD, labels3[k], 3, 4, thang);
            k++;
        }
    }

    public void updateTongDT() {
        float tong = tinhTongTien(dgwHD, 4);
        String gia = "" + tong + "VND";
        cart1.setData(new Model_Cart(new ImageIcon(getClass().getResource("/Icons/customer (1).png")), "Tổng doanh thu", "" + gia));

    }

    public void loadBang2() {
        try {
            connection = cn.getConnection();
            int number;
            Vector row;
            String sql = "select * from qlhoadon where trangthai= 'Chua thanh toan'";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            ResultSetMetaData metadata = rs.getMetaData();
            number = metadata.getColumnCount();
            tb1.setRowCount(0);
            while (rs.next()) {
                row = new Vector();
                for (int i = 1; i <= number; i++) {
                    row.addElement(rs.getString(i));
                }
                tb1.addRow(row);
            }
            dgwCTT.setModel(tb1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadBang() {
        try {
            connection = cn.getConnection();
            int number;
            Vector row;
            String sql = "select * from qlhoadon where trangthai= 'Da thanh toan'";
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
            dgwHD.setModel(tb);
            hienthi();
            updateTongDT();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void timKiem(JTable table, int cot) {
        DefaultTableModel ob = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(ob);
        table.setRowSorter(obj);
        obj.setRowFilter(RowFilter.regexFilter(txtTK.getText(), cot));
    }

    public int demSoLan(JTable table, int cotIdBan, int cotTang, String idBanCanTinh, String tangCanTinh) {
        int dem = 0;
        // Duyệt qua tất cả các hàng của bảng
        for (int row = 0; row < table.getRowCount(); row++) {
            // Lấy giá trị của cột bàn va tầng
            Object idBan = table.getValueAt(row, cotIdBan);
            Object tang = table.getValueAt(row, cotTang);
            // Kiểm tra nếu idban là bàn cần tính và tầng là tầng cần tính
            if (idBan != null && idBan.toString().trim().equalsIgnoreCase(idBanCanTinh.trim())
                    && tang != null && tang.toString().trim().equalsIgnoreCase(tangCanTinh.trim())) {
                dem++;
            }
        }

        return dem;
    }

    public void hienThiSoLan(JTable table, JLabel label, int cotIdBan, int cotTang, String idBanCanTinh, String tangCanTinh) {
        int soLan = demSoLan(table, cotIdBan, cotTang, idBanCanTinh, tangCanTinh);
        label.setText(String.format("%d", soLan));
    }

    public float tinhTongTien(JTable table, int cotGia) {
        float tongGiaTien = 0.0f;
        // Duyệt qua tất cả các hàng của bảng
        for (int row = 0; row < table.getRowCount(); row++) {
            // Lấy giá trị của cột giá tiền
            Object giaTien = table.getValueAt(row, cotGia);
            if (giaTien != null) {
                try {
                    // Chuyển đổi giá trị của cột giá tiền thành float (kể cả khi là chuỗi)
                    tongGiaTien += Float.parseFloat(giaTien.toString());
                } catch (NumberFormatException e) {
                    System.out.println("Không thể chuyển đổi giá tiền");
                    e.printStackTrace();
                }
            }
        }
        return tongGiaTien;
    }

    public void hienThiTongTien(JTable table, JLabel label, int cotGia) {
        float tong = tinhTongTien(table, cotGia);
        label.setText(String.format("%.2f đ", tong));
    }

    public float doanhThuThang(JTable table, int cotNgayThang, int cotGia, int thangCanTinh) {
        float tongGiaTien = 0.0f;
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // dinh dang cua ngay thanh toan
        for (int row = 0; row < table.getRowCount(); row++) {
            //lay gia tri 2 cot tien va cot ngay
            Object giaTien = table.getValueAt(row, cotGia);
            Object ngaythang = table.getValueAt(row, cotNgayThang);
            if (giaTien != null && ngaythang != null) {
                try {
                    // Chuyen chuoi thanh ngay thang
                    LocalDate ngay = LocalDate.parse(ngaythang.toString(), dateFormat);
                    int thang = ngay.getMonthValue();// lay thang 
                    if (thang == thangCanTinh) {
                        tongGiaTien += Float.parseFloat(giaTien.toString());
                    }
                } catch (DateTimeParseException ex) {
                    System.out.println("Loi dinh dang ngay");
                } catch (NumberFormatException ex) {
                    System.out.println("Loi chuyen doi gia tien");
                }
            }
        }
        return tongGiaTien;
    }

    public void hienThiDoanhThuThang(JTable table, JLabel label, int cotNgayThang, int cotGia, int thangCanTinh) {
        float tong = doanhThuThang(table, cotNgayThang, cotGia, thangCanTinh);
        label.setText(String.format("%.2f", tong));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateChooser1 = new com.raven.datechooser.DateChooser();
        background1 = new com.raven.swing.Background();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cbBan = new javax.swing.JComboBox<>();
        cbTang = new javax.swing.JComboBox<>();
        button1 = new qlnh.swing.Button();
        button3 = new qlnh.swing.Button();
        btnSua = new qlnh.swing.Button();
        btnXoa = new qlnh.swing.Button();
        bg = new javax.swing.JLayeredPane();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        button4 = new qlnh.swing.Button();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        btnTKThang = new qlnh.swing.Button();
        jLabel43 = new javax.swing.JLabel();
        txtID = new qlnh.swing.MyTextField();
        txtDate = new qlnh.swing.MyTextField();
        txtGia = new qlnh.swing.MyTextField();
        txtTK = new qlnh.swing.MyTextField();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        cart1 = new com.raven.componert.Cart();
        jScrollPane1 = new javax.swing.JScrollPane();
        dgwHD = new javax.swing.JTable();
        jLabel31 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        dgwCTT = new javax.swing.JTable();

        dateChooser1.setTextRefernce(txtDate);

        setPreferredSize(new java.awt.Dimension(1052, 500));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Quản Lý Hóa Đơn");

        jLabel1.setText("Mã hóa đơn:");

        jLabel3.setText("Mã bàn: ");

        jLabel4.setText("Tầng");

        jLabel5.setText("Ngày thanh toán:");

        jLabel6.setText("Giá:");

        cbBan.setBackground(new java.awt.Color(83, 105, 118));
        cbBan.setForeground(new java.awt.Color(204, 204, 204));
        cbBan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ban1", "Ban2", "Ban3", "Ban4", " " }));

        cbTang.setBackground(new java.awt.Color(83, 105, 118));
        cbTang.setForeground(new java.awt.Color(204, 204, 204));
        cbTang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tang 1", "Tang 2", "Tang 3", "Tang 4" }));
        cbTang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTangActionPerformed(evt);
            }
        });

        button1.setBackground(new java.awt.Color(204, 183, 253));
        button1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/inventory (1).png"))); // NOI18N
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        button3.setBackground(new java.awt.Color(204, 183, 253));
        button3.setForeground(new java.awt.Color(51, 51, 51));
        button3.setText("Làm mới");
        button3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button3ActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(204, 183, 253));
        btnSua.setForeground(new java.awt.Color(51, 51, 51));
        btnSua.setText("Cập nhật");
        btnSua.setPreferredSize(new java.awt.Dimension(57, 26));
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(204, 183, 253));
        btnXoa.setForeground(new java.awt.Color(51, 51, 51));
        btnXoa.setText("Xóa");
        btnXoa.setPreferredSize(new java.awt.Dimension(57, 26));
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
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
            .addGap(0, 83, Short.MAX_VALUE)
        );

        jLabel7.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel7.setText("jLabel7");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel8.setText("jLabel8");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel9.setText("jLabel9");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel10.setText("jLabel10");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel11.setText("jLabel11");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel12.setText("jLabel12");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel13.setText("jLabel13");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel14.setText("jLabel14");

        button4.setBackground(new java.awt.Color(204, 183, 253));
        button4.setForeground(new java.awt.Color(51, 51, 51));
        button4.setText("Thống kê Bàn");
        button4.setPreferredSize(new java.awt.Dimension(130, 26));
        button4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button4ActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel15.setText("jLabel15");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel16.setText("jLabel16");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel17.setText("jLabel17");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel18.setText("jLabel18");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel19.setText("jLabel19");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel20.setText("jLabel20");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel21.setText("jLabel21");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel22.setText("jLabel22");

        jLabel23.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel23.setText("jLabel23");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel24.setText("jLabel24");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel25.setText("jLabel25");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel26.setText("jLabel26");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel27.setText("jLabel27");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel28.setText("jLabel28");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel29.setText("jLabel29");

        jLabel30.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel30.setText("jLabel30");

        btnTKThang.setBackground(new java.awt.Color(204, 183, 253));
        btnTKThang.setForeground(new java.awt.Color(51, 51, 51));
        btnTKThang.setText("Thống kê theo Tháng");
        btnTKThang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTKThangActionPerformed(evt);
            }
        });

        jLabel43.setText("Tìm kiếm:");

        txtGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaActionPerformed(evt);
            }
        });

        txtTK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTKKeyReleased(evt);
            }
        });

        jLayeredPane1.setLayout(new java.awt.CardLayout());

        cart1.setColor1(new java.awt.Color(153, 255, 255));
        cart1.setColor2(new java.awt.Color(0, 204, 204));
        jLayeredPane1.add(cart1, "card2");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel19))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel43))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtGia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(txtTK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel22)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(bg, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel12)
                                            .addGap(18, 18, 18)
                                            .addComponent(jLabel20))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel13)
                                            .addGap(18, 18, 18)
                                            .addComponent(jLabel21)))
                                    .addGap(32, 32, 32)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel29)
                                        .addComponent(jLabel28)
                                        .addComponent(jLabel30)
                                        .addComponent(jLabel27))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel7)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jLabel17))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel23)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jLabel8))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel24)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jLabel9))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel15)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jLabel25)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel26)
                                        .addComponent(jLabel18)
                                        .addComponent(jLabel16)
                                        .addComponent(jLabel10)))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(28, 28, 28)
                                    .addComponent(btnTKThang, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(button4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(140, 140, 140)
                                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(90, 90, 90)
                            .addComponent(jLabel2))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4))
                            .addGap(42, 42, 42)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbBan, javax.swing.GroupLayout.Alignment.TRAILING, 0, 211, Short.MAX_VALUE)
                                .addComponent(cbTang, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbBan, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cbTang, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(txtTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(button4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTKThang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel12)
                    .addComponent(jLabel20)
                    .addComponent(jLabel28)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel13)
                    .addComponent(jLabel21)
                    .addComponent(jLabel29)
                    .addComponent(jLabel25)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel22)
                    .addComponent(jLabel30)
                    .addComponent(jLabel23)
                    .addComponent(jLabel8)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel19)
                    .addComponent(jLabel27)
                    .addComponent(jLabel24)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addGap(154, 154, 154))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(592, Short.MAX_VALUE)
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        dgwHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ));
        dgwHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dgwHDMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(dgwHD);

        jLabel31.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel31.setText("Chưa thanh toán:");

        dgwCTT.setModel(new javax.swing.table.DefaultTableModel(
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
        dgwCTT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dgwCTTMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(dgwCTT);

        javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background1);
        background1.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 696, Short.MAX_VALUE)
                    .addGroup(background1Layout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        background1Layout.setVerticalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(background1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        add(background1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void dgwHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dgwHDMouseClicked
        int x = dgwHD.getSelectedRow();
        if (x >= 0) {
            txtID.setText(dgwHD.getValueAt(dgwHD.getSelectedRow(), 0) + "");
            String banData = dgwHD.getValueAt(dgwHD.getSelectedRow(), 1) + "";
            if (banData.startsWith("Ban") && banData.contains("T")) {
                // Lấy phần số giữa "Ban" và "T"
                String[] parts = banData.split("T");
                String banNumber = parts[0].replace("Ban", "").trim();
                cbBan.setSelectedItem("Ban" + banNumber);
            } else {
                // Nếu dữ liệu không hợp lệ, đặt giá trị mặc định
                cbBan.setSelectedItem("N/A");
            }
            cbTang.setSelectedItem(dgwHD.getValueAt(dgwHD.getSelectedRow(), 2) + "");
            txtDate.setText(dgwHD.getValueAt(dgwHD.getSelectedRow(), 3) + "");
            txtGia.setText(dgwHD.getValueAt(dgwHD.getSelectedRow(), 4) + "");

            txtID.setEnabled(false);
        }
    }//GEN-LAST:event_dgwHDMouseClicked

    public boolean checkValidateForm2() {
        if (txtDate.getText().isEmpty()) {
            return false;
        }
        return true;
    }

    public void resetForm() {
        txtID.setText("");
        txtGia.setText("");
        txtDate.setText("");
        cbBan.setSelectedItem("");
        cbTang.setSelectedItem("");
        txtID.setEnabled(true);
        loadBang();
        loadBang2();
        updateTongDT();
    }
    private void button3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button3ActionPerformed
        resetForm();
    }//GEN-LAST:event_button3ActionPerformed


    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        dateChooser1.showPopup(this, (getWidth() - dateChooser1.getPreferredSize().width) / 2, (getHeight() - dateChooser1.getPreferredSize().height) / 2);

    }//GEN-LAST:event_button1ActionPerformed

    ModelDatBan mdbB;
    ServiceBan serviceBan;
    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // Kiểm tra xem có dòng nào được chọn trong bảng hay không
        if (dgwCTT.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần thanh toán");
            return;
        }
        try {
            // Kiểm tra xem form đã được điền đầy đủ hay chưa
            if (!checkValidateForm2()) {
                showMessage(Message.MessageType.ERROR, "Vui lòng điền đầy đủ thông tin.");
            } else {
                mdb.setIdHD(txtID.getText());
                mdb.setNgayTT(txtDate.getText());
                String a = cbTang.getSelectedItem().toString();
                int index = a.lastIndexOf(" ") + 1;
                String numberPart = a.substring(index);
                String idb = cbBan.getSelectedItem().toString() + "T" + numberPart;
                mdbB.setIDBan(idb);
                mdbB.setTang(cbTang.getSelectedItem().toString());
                try {
                    service.UpdateHD(mdb);
                    serviceBan.HUYBan(mdbB);
                    resetForm();
                    hienthi();
                    showMessage(Message.MessageType.SUCCESS, "Cap nhat HD thành công.");
                    updateTongDT();
                } catch (Exception e) {
                    showMessage(Message.MessageType.ERROR, "Lỗi khi cap nhat.");
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            showMessage(Message.MessageType.ERROR, "Đã xảy ra lỗi không xác định.");
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed

        connection = cn.getConnection();
        // Kiểm tra xem có dòng nào được chọn trong bảng hay không
        if (txtID.getText().isEmpty()) {
            if (dgwCTT.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần xóa");
                return;
            }
        }

        try {
            if (txtID.getText().isEmpty()) {
                showMessage(Message.MessageType.ERROR, "Vui lòng điền mã hóa đơn cần xóa.");
            } else {
                String sql = "Delete qlhoadon where idhd ='" + txtID.getText() + "'";
                Statement st = connection.createStatement();
                int ck = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn xóa HD này?", "Thông báo", JOptionPane.YES_NO_OPTION);
                if (ck == JOptionPane.YES_OPTION) {
                    st.executeUpdate(sql);
                    resetForm();
                    hienthi();
                    showMessage(Message.MessageType.SUCCESS, "Xóa HD thành công.");
                    updateTongDT();
                }
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void button4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button4ActionPerformed
        JLabel[] labels = {
            jLabel15, jLabel16, jLabel17, jLabel18, jLabel19, jLabel20, jLabel21,
            jLabel22, jLabel23, jLabel24, jLabel25, jLabel26, jLabel27, jLabel28,
            jLabel29, jLabel30
        };
        String[] tangs = {
            "B1T1", "B2T1", "B3T1", "B4T1",
            "B1T2", "B2T2", "B3T2", "B4T2",
            "B1T3", "B2T3", "B3T3", "B4T3",
            "B1T4", "B2T4", "B3T4", "B4T4"
        };

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < labels.length; i++) {
            String pressure = labels[i].getText().trim(); // Xóa khoảng trắng
            try {
                int value = Integer.parseInt(pressure);
                dataset.setValue(value, "Lần", tangs[i]);
            } catch (NumberFormatException e) {
                System.err.println("Giá trị không hợp lệ cho " + tangs[i] + ": " + pressure);
                dataset.setValue(0, "Tang", tangs[i]); // Gán giá trị 0 nếu không hợp lệ
            }
        }

        JFreeChart chart = ChartFactory.createBarChart("Thống kê Bàn ", "Tầng", "Số lần", dataset, PlotOrientation.VERTICAL, false, true, false);
        CategoryPlot p = chart.getCategoryPlot();
        p.setRangeGridlinePaint(new Color(204, 183, 253));
        ChartFrame frame = new ChartFrame("Bar chart for Bàn", chart);
        frame.setVisible(true);
        frame.setSize(450, 350);
    }//GEN-LAST:event_button4ActionPerformed

    public void thongKeDTThang(JLabel[] labels, String[] thangs) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();//tao dataset chua du lieu 
        for (int i = 0; i < labels.length; i++) {
            String pressure = labels[i].getText().trim(); // Xóa khoảng trắng
            try {
                float value = Float.parseFloat(pressure);//ep kieu chuoi thanh float
                dataset.setValue(value, "vnđ", thangs[i]);//them value vao dataset
            } catch (NumberFormatException e) {
                System.err.println("Giá trị không hợp lệ cho " + thangs[i] + ": " + pressure);
                dataset.setValue(0, "vnđ", thangs[i]); // Gán giá trị 0 nếu không hợp lệ
            }
        }
        //tao bieu do
        JFreeChart chart = ChartFactory.createBarChart("Doanh thu tháng", "Tháng",
                "Doanh thu", dataset, PlotOrientation.VERTICAL, false, true, false);
        CategoryPlot p = chart.getCategoryPlot();
        p.setRangeGridlinePaint(new Color(204, 183, 253));
        //Hien thi bieu do
        ChartFrame frame = new ChartFrame("Bar chart for Tháng", chart);
        frame.setVisible(true);
        frame.setSize(450, 350);
    }

    private void btnTKThangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTKThangActionPerformed
        JLabel[] labels = {
            lb[0], lb[1], lb[2], lb[3], lb[4], lb[5],
            lb[6], lb[7], lb[8], lb[9], lb[10], lb[11]
        };//cac lb de in doanh thu theo tung thang
        String[] thangs = {
            "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
            "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8",
            "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"
        };// cac thang
        thongKeDTThang(labels, thangs);
    }//GEN-LAST:event_btnTKThangActionPerformed

    private void txtGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaActionPerformed

    private void txtTKKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTKKeyReleased
        timKiem(dgwHD, 0);
    }//GEN-LAST:event_txtTKKeyReleased

    private void cbTangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbTangActionPerformed

    private void dgwCTTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dgwCTTMouseClicked
        int x = dgwCTT.getSelectedRow();
        if (x >= 0) {
            txtID.setText(dgwCTT.getValueAt(dgwCTT.getSelectedRow(), 0) + "");
            String banData = dgwCTT.getValueAt(dgwCTT.getSelectedRow(), 1) + "";
            if (banData.startsWith("Ban") && banData.contains("T")) {
                // Lấy phần số giữa "Ban" và "T"
                String[] parts = banData.split("T");
                String banNumber = parts[0].replace("Ban", "").trim();
                cbBan.setSelectedItem("Ban" + banNumber);
            } else {
                // Nếu dữ liệu không hợp lệ, đặt giá trị mặc định
                cbBan.setSelectedItem("N/A");
            }
            cbTang.setSelectedItem(dgwCTT.getValueAt(dgwCTT.getSelectedRow(), 2) + "");
            txtDate.setText(dgwCTT.getValueAt(dgwCTT.getSelectedRow(), 3) + "");
            txtGia.setText(dgwCTT.getValueAt(dgwCTT.getSelectedRow(), 4) + "");

            txtID.setEnabled(false);
        }
    }//GEN-LAST:event_dgwCTTMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.swing.Background background1;
    private javax.swing.JLayeredPane bg;
    private qlnh.swing.Button btnSua;
    private qlnh.swing.Button btnTKThang;
    private qlnh.swing.Button btnXoa;
    private qlnh.swing.Button button1;
    private qlnh.swing.Button button3;
    private qlnh.swing.Button button4;
    private com.raven.componert.Cart cart1;
    private javax.swing.JComboBox<String> cbBan;
    private javax.swing.JComboBox<String> cbTang;
    private com.raven.datechooser.DateChooser dateChooser1;
    private javax.swing.JTable dgwCTT;
    private javax.swing.JTable dgwHD;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private qlnh.swing.MyTextField txtDate;
    private qlnh.swing.MyTextField txtGia;
    private qlnh.swing.MyTextField txtID;
    private qlnh.swing.MyTextField txtTK;
    // End of variables declaration//GEN-END:variables
}
