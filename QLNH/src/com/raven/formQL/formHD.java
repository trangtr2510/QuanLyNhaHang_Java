
package com.raven.formQL;

import com.raven.Connection.DatabaseConnection;
import com.raven.componert.Message;
import com.raven.datechooser.SelectedDate;
import com.raven.model.ModelHD;
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
import java.util.Vector;
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
    final  String header[] = {"Mã hóa đơn", "Bàn","Tầng", "Ngày thanh toán","Giá"};
    DefaultTableModel tb = new DefaultTableModel(header, 0);
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
//        txtID.setEnabled(false);
        loadBang();
        intt();
        jScrollPane1.setVerticalScrollBar(new ScrollBar());
        btnThongKeBan.setVisible(false);
        btnThongKeTang.setVisible(false);
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

    private void intt(){
        layout = new MigLayout("fill, insets 0");
        bg.setLayout(layout);
    }
    
     //hien thong bao
    private void showMessage(Message.MessageType messageType, String message){
        Message ms = new Message();
        ms.showMessage(messageType, message);
        TimingTarget target = new TimingTargetAdapter(){
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
    public void hienthi(){
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
        for(int thang : thangs){
            hienThiDoanhThuThang(dgwHD, labels3[k], 3, 4, thang);
            k++;
        }
//        for (String ban : bans) {
//            if(j < 4){
//                hienThiTongTien(dgwHD, labels2[j], 1, 4, ban);
//                j++;
//            }
//        }
//        for (String tang : tangs) {
//            if(j >= 4) {
//                hienThiTongTien(dgwHD, labels2[j], 2, 4, tang);
//                j++;
//            }
//        }
    }
    
    public void loadBang(){
        try {
            connection = cn.getConnection();
            int number;
            Vector row;
            String sql = "select * from qlhoadon";
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
            dgwHD.setModel(tb);
            hienthi();
            hienThiTongTien(dgwHD, tongDT, 4);
        } catch (Exception e) {
             e.printStackTrace(); 
        }
    }  
    public void timKiem(JTable table, int cot){
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
            if (idBan != null && idBan.toString().trim().equalsIgnoreCase(idBanCanTinh.trim()) &&
                tang != null && tang.toString().trim().equalsIgnoreCase(tangCanTinh.trim())) 
            {
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
    public float doanhThuThang(JTable table, int cotNgayThang, int cotGia, int thangCanTinh ){
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
        label.setText(String.format("%.2f",tong));
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateChooser1 = new com.raven.datechooser.DateChooser();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cbBan = new javax.swing.JComboBox<>();
        cbTang = new javax.swing.JComboBox<>();
        txtID = new javax.swing.JTextField();
        txtDate = new javax.swing.JTextField();
        txtGia = new javax.swing.JTextField();
        button1 = new qlnh.swing.Button();
        button2 = new qlnh.swing.Button();
        button3 = new qlnh.swing.Button();
        btnThem = new qlnh.swing.Button();
        btnSua = new qlnh.swing.Button();
        btnXoa = new qlnh.swing.Button();
        bg = new javax.swing.JLayeredPane();
        btnThongKeBan = new qlnh.swing.Button();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btnThongKeTang = new qlnh.swing.Button();
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
        txtTK = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        tongDT = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        dgwHD = new javax.swing.JTable();

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Quản Lý Hóa Đơn");

        jLabel1.setText("Mã hóa đơn:");

        jLabel3.setText("Mã bàn: ");

        jLabel4.setText("Tầng");

        jLabel5.setText("Ngày thanh toán:");

        jLabel6.setText("Giá:");

        cbBan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ban 1", "Ban 2", "Ban 3", "Ban 4" }));

        cbTang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tầng 1", "Tầng 2", "Tầng 3", "Tầng 4" }));

        txtGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaActionPerformed(evt);
            }
        });

        button1.setBackground(new java.awt.Color(204, 183, 253));
        button1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/inventory (1).png"))); // NOI18N
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        button2.setBackground(new java.awt.Color(204, 183, 253));
        button2.setForeground(new java.awt.Color(51, 51, 51));
        button2.setText("OK");
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
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

        btnThem.setBackground(new java.awt.Color(204, 183, 253));
        btnThem.setForeground(new java.awt.Color(51, 51, 51));
        btnThem.setText("Thêm");
        btnThem.setPreferredSize(new java.awt.Dimension(57, 26));
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(204, 183, 253));
        btnSua.setForeground(new java.awt.Color(51, 51, 51));
        btnSua.setText("Sửa");
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
            .addGap(0, 71, Short.MAX_VALUE)
        );

        btnThongKeBan.setBackground(new java.awt.Color(204, 183, 253));
        btnThongKeBan.setForeground(new java.awt.Color(51, 51, 51));
        btnThongKeBan.setText("Thống kê theo Bàn ");
        btnThongKeBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThongKeBanActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel7.setText("jLabel7");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel8.setText("jLabel8");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel9.setText("jLabel9");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel10.setText("jLabel10");

        btnThongKeTang.setBackground(new java.awt.Color(204, 183, 253));
        btnThongKeTang.setForeground(new java.awt.Color(51, 51, 51));
        btnThongKeTang.setText("Thống kê theo Tầng");
        btnThongKeTang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThongKeTangActionPerformed(evt);
            }
        });

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

        txtTK.setText("Tìm kiếm theo mã HD....");
        txtTK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTKKeyReleased(evt);
            }
        });

        jLabel44.setText("Tổng doanh thu:");

        tongDT.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        tongDT.setText("jLabel45");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(43, 43, 43)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtID)
                            .addComponent(cbBan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbTang, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel7))
                                .addGap(28, 28, 28)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel17)
                                        .addComponent(jLabel18)
                                        .addComponent(jLabel19)
                                        .addComponent(jLabel20)
                                        .addComponent(jLabel21)
                                        .addComponent(jLabel22))
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel15))
                                .addGap(23, 23, 23)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel24)
                                    .addComponent(jLabel25)
                                    .addComponent(jLabel26)
                                    .addComponent(jLabel27)
                                    .addComponent(jLabel28)
                                    .addComponent(jLabel29)
                                    .addComponent(jLabel30)
                                    .addComponent(jLabel23)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(176, 176, 176)
                                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnTKThang, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnThongKeTang, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(button4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnThongKeBan, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel43))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtTK)
                                .addGap(1, 1, 1))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel44)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tongDT)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cbBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cbTang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(button2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(txtTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(button4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTKThang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThongKeTang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThongKeBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(tongDT))
                .addGap(4, 4, 4)
                .addComponent(bg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel22))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel30)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaActionPerformed

    private void dgwHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dgwHDMouseClicked
        int x = dgwHD.getSelectedRow();
        if(x >= 0){
            txtID.setText(dgwHD.getValueAt(dgwHD.getSelectedRow(), 0) + "");
            cbBan.setSelectedItem(dgwHD.getValueAt(dgwHD.getSelectedRow(), 1) + "");
            cbTang.setSelectedItem(dgwHD.getValueAt(dgwHD.getSelectedRow(), 2) + "");
            txtDate.setText(dgwHD.getValueAt(dgwHD.getSelectedRow(), 3) + "");
            txtGia.setText(dgwHD.getValueAt(dgwHD.getSelectedRow(), 4) + "");
            
            txtID.setEnabled(false);
        }
    }//GEN-LAST:event_dgwHDMouseClicked

    
    public boolean checkValidateForm2(){
        if(txtDate.getText().isEmpty() || txtGia.getText().isEmpty()){
            return false;
        }
        return true;
    }
    public void resetForm(){
        txtID.setText("");
        txtGia.setText("");
        txtDate.setText("");
        cbBan.setSelectedItem("");
        cbTang.setSelectedItem("");
        txtID.setEnabled(true);
        loadBang();
    }
    private void button3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button3ActionPerformed
        resetForm();
    }//GEN-LAST:event_button3ActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
         //Dat ban 
        try {
            // Kiểm tra xem form đã được điền đầy đủ hay chưa
            if (!checkValidateForm2()) {
                showMessage(Message.MessageType.ERROR, "Vui lòng điền đầy đủ thông tin.");
            } else {
                String idHD = service.getLatestMaHD(); // lay idhd lon nhat trong bang
                if (idHD == null || idHD.isEmpty()) {
                    idHD = "HD001"; //idhd lon nhat Neu trong bang chua co idhd nao thi 
                } else {
                    int k = Integer.parseInt(idHD.substring(2));//ep kieu chuoi con lay tu chuoi idHD, chuoi con bat dau tu ki tu thu 2(Dem tu 0)
                    int tangidHD = k + 1;//tang idhd len 1
                    idHD = "HD" + String.format("%03d", tangidHD); // tao idhd moi
                }
        
                mdb.setIdHD(idHD);//set gia tri cho idhd, idban, tang, ngaytt, gia 
                mdb.setIdBan(cbBan.getSelectedItem().toString());
                mdb.setTang(cbTang.getSelectedItem().toString());
                mdb.setNgayTT(txtDate.getText());
                float gia = Float.parseFloat(txtGia.getText()); 
                mdb.setGia(gia);
                try {
                    if(service.checkDuplicateHD(mdb.getIdHD())){//kiem tra idhd da ton tai hay chua
                        showMessage(Message.MessageType.ERROR, "Ma HD da ton tai ");//hien thi loi
                    }else{
                        service.insertHD(mdb);//thuc hien them vao database
                        resetForm();//lam moi lai cac o textbox
                        hienthi();//
                        showMessage(Message.MessageType.SUCCESS, "Them HD thành công.");
                    }
                } catch (Exception e) {
                    showMessage(Message.MessageType.ERROR, "Lỗi khi them.");
                    e.printStackTrace();
                }
            }
            }catch (Exception e) {
            showMessage(Message.MessageType.ERROR, "Đã xảy ra lỗi không xác định.");
            e.printStackTrace();
        } 
    }//GEN-LAST:event_btnThemActionPerformed

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        dateChooser1.showPopup(this, (getWidth() - dateChooser1.getPreferredSize().width) / 2, (getHeight() - dateChooser1.getPreferredSize().height) / 2);
        
    }//GEN-LAST:event_button1ActionPerformed

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        SelectedDate selectedDate = dateChooser1.getSelectedDate();
        if (selectedDate != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date(selectedDate.getYear() - 1900, selectedDate.getMonth() - 1, selectedDate.getDay());
            String formattedDate = dateFormat.format(date);
            txtDate.setText(formattedDate);
        }
    }//GEN-LAST:event_button2ActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        //Dat ban 
        try {
            // Kiểm tra xem form đã được điền đầy đủ hay chưa
            if (!checkValidateForm2()) {
                showMessage(Message.MessageType.ERROR, "Vui lòng điền đầy đủ thông tin.");
            } else {
                mdb.setIdHD(txtID.getText());
                mdb.setIdBan(cbBan.getSelectedItem().toString());
                mdb.setTang(cbTang.getSelectedItem().toString());
                mdb.setNgayTT(txtDate.getText());
                float gia = Float.parseFloat(txtGia.getText()); 
                mdb.setGia(gia);
                try {
                     service.UpdateHD(mdb);
                     resetForm();
                     hienthi();
                     showMessage(Message.MessageType.SUCCESS, "Sua HD thành công.");
                    
                } catch (Exception e) {
                    showMessage(Message.MessageType.ERROR, "Lỗi khi sua.");
                    e.printStackTrace();
                }
            }
            }catch (Exception e) {
            showMessage(Message.MessageType.ERROR, "Đã xảy ra lỗi không xác định.");
            e.printStackTrace();
        } 
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        connection = cn.getConnection();
        try {
            if(txtID.getText().isEmpty()){
                showMessage(Message.MessageType.ERROR, "Vui lòng điền mã hóa đơn cần xóa.");
            }else{
                String sql = "Delete qlhoadon where idhd ='"+txtID.getText()+"'";
                Statement st = connection.createStatement();
                int ck = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn xóa HD này?", "Thông báo", JOptionPane.YES_NO_OPTION);
                if(ck == JOptionPane.YES_OPTION){
                    st.executeUpdate(sql);
                    resetForm();
                    hienthi();
                    showMessage(Message.MessageType.SUCCESS, "Xóa HD thành công.");
                }
            } 
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnThongKeBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThongKeBanActionPerformed
        JLabel[] labels = {
            jLabel7, jLabel8, jLabel9, jLabel10
        };
        String[] tangs = {
            "Ban 1", "Ban 2", "Ban 3", "Ban 4"
        };
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < labels.length; i++) {
            String pressure = labels[i].getText();
            int index = pressure.lastIndexOf(":");
            if (index != -1) {
                // Tách phần số và thêm vào dataset
                String value = pressure.substring(index + 1).replace("đ", "").trim();
                dataset.setValue(Float.parseFloat(value), "Gia", tangs[i]);
            }
        }
        
        JFreeChart chart = ChartFactory.createBarChart("Ban Score", "Bàn", "Giá", dataset, PlotOrientation.VERTICAL, false, true, false);
        CategoryPlot p = chart.getCategoryPlot();
        p.setRangeGridlinePaint(new Color(204,183,253));
        ChartFrame frame = new ChartFrame("Bar chart for Ban", chart);
        frame.setVisible(true);
        frame.setSize(450, 350);
    }//GEN-LAST:event_btnThongKeBanActionPerformed

    private void btnThongKeTangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThongKeTangActionPerformed
        JLabel[] labels = {
            jLabel11, jLabel12, jLabel13, jLabel14
        };
        String[] tangs = {
            "Tầng 1", "Tầng 2", "Tầng 3", "Tầng 4"
        };
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < labels.length; i++) {
            String pressure = labels[i].getText();
            int index = pressure.lastIndexOf(":");
            if (index != -1) {
                // Tách phần số và thêm vào dataset
                String value = pressure.substring(index + 1).replace("đ", "").trim();
                dataset.setValue(Float.parseFloat(value), "Gia", tangs[i]);
            }
        }
        
        JFreeChart chart = ChartFactory.createBarChart("Tang Score", "Tầng", "Giá", dataset, PlotOrientation.VERTICAL, false, true, false);
        CategoryPlot p = chart.getCategoryPlot();
        p.setRangeGridlinePaint(new Color(204,183,253));
        ChartFrame frame = new ChartFrame("Bar chart for Tầng", chart);
        frame.setVisible(true);
        frame.setSize(450, 350);
    }//GEN-LAST:event_btnThongKeTangActionPerformed

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

    public void thongKeDTThang(JLabel[] labels, String[] thangs){
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

    private void txtTKKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTKKeyReleased
        timKiem(dgwHD, 0);
    }//GEN-LAST:event_txtTKKeyReleased
        

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane bg;
    private qlnh.swing.Button btnSua;
    private qlnh.swing.Button btnTKThang;
    private qlnh.swing.Button btnThem;
    private qlnh.swing.Button btnThongKeBan;
    private qlnh.swing.Button btnThongKeTang;
    private qlnh.swing.Button btnXoa;
    private qlnh.swing.Button button1;
    private qlnh.swing.Button button2;
    private qlnh.swing.Button button3;
    private qlnh.swing.Button button4;
    private javax.swing.JComboBox<String> cbBan;
    private javax.swing.JComboBox<String> cbTang;
    private com.raven.datechooser.DateChooser dateChooser1;
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel tongDT;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtTK;
    // End of variables declaration//GEN-END:variables
}
