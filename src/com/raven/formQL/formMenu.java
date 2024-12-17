package com.raven.formQL;

import com.raven.Connection.DatabaseConnection;
import com.raven.componert.Message;
import com.raven.model.ModelMenu;
import com.raven.service.ServiceMenu;
import com.raven.swing.ScrollBar;
import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
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

public class formMenu extends javax.swing.JPanel {

    private MigLayout layout;
    private ModelMenu mdb;
    private ServiceMenu service;
    final String header[] = {"Mã thực đơn", "Tên thực đơn", "Loại thực đơn"};
    DefaultTableModel tb = new DefaultTableModel(header, 0);
    DefaultTableModel tb1 = new DefaultTableModel(header, 0);
    DefaultTableModel tb2 = new DefaultTableModel(header, 0);
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
        new JLabel("0")
    };

    public formMenu() {
        initComponents();
        service = new ServiceMenu();
        mdb = new ModelMenu();
        hienThiBang();
        hienThiSoLanTD();
        intt();
        jScrollPane1.setVerticalScrollBar(new ScrollBar());
        jScrollPane2.setVerticalScrollBar(new ScrollBar());
        jScrollPane3.setVerticalScrollBar(new ScrollBar());
        txtID.setEnabled(false);
        txtTKDA.setHint("Tìm kiếm theo tên ...");
    }

    public void hienThiBang() {
        String[] sql = {
            "select * from qlmenu",
            "select * from qlmenu where loaithucdon = 'Do uong'",
            "select * from qlmenu where loaithucdon <> 'Do uong'" //SELECT * FROM qlmenu WHERE LoaiThucDon NOT IN ('Đồ uống');
        };
        loadBang(sql[0], tb, dgwMN);
        loadBang(sql[1], tb1, dgwDoU);
        loadBang(sql[2], tb2, dgwMA);
    }

    public void timKiem(JTable table, int cot) {
        DefaultTableModel ob = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(ob);
        table.setRowSorter(obj);
        obj.setRowFilter(RowFilter.regexFilter(txtTKDA.getText(), cot));
    }

    public void hienThiSoLanTD() {
        JLabel[] lbs = {
            lb[0], lb[1], lb[2], lb[3], lb[4], lb[5], lb[6], lb[7], lb[8]
        };
        String[] loaiTDs = {
            "Mon an man",
            "Mon chien",
            "Hai san",
            "Mon Au",
            "Mon nuong",
            "Mon xao",
            "Mon nhung",
            "Mon dac san",
            "Do uong"
        };
        for (int i = 0; i < loaiTDs.length; i++) {
            hienThiSoLan(dgwMN, lbs[i], 2, loaiTDs[i]);
        }
    }

    private void intt() {
        layout = new MigLayout("fill, insets 0");
        bg.setLayout(layout);
    }

    private void tongThucDon() {
        int totalEmployees = dgwMN.getRowCount();
        lblTongMN.setText("" + totalEmployees);
    }

    public void loadBang(String sqlString, DefaultTableModel tbModel, JTable table) {
        try {
            connection = cn.getConnection();
            int number;
            Vector row;
            String sql = sqlString;
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            ResultSetMetaData metadata = rs.getMetaData();
            number = metadata.getColumnCount();
            tbModel.setRowCount(0);
            while (rs.next()) {
                row = new Vector();
                for (int i = 1; i <= number; i++) {
                    row.addElement(rs.getString(i));
                }
                tbModel.addRow(row);
            }
            table.setModel(tbModel);
            tongThucDon();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int demSoLan(JTable table, int cotloaiTD, String loaiTDCanTinh) {
        int dem = 0;
        // Duyệt qua tất cả các hàng của bảng
        for (int row = 0; row < table.getRowCount(); row++) {
            // Lấy giá trị của cột loaitd
            Object loaiTD = table.getValueAt(row, cotloaiTD);
            // Kiểm tra nếu loaitd là thuc don cần tính
            if (loaiTD != null && loaiTD.toString().trim().equalsIgnoreCase(loaiTDCanTinh.trim())) {
                dem++;
            }
        }

        return dem;
    }

    public void hienThiSoLan(JTable table, JLabel label, int cotLoaiTD, String loaiTDCanTinh) {
        int soLan = demSoLan(table, cotLoaiTD, loaiTDCanTinh);
        label.setText(String.format("%d", soLan));
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

    public void doDuLieu(JTable table) {
        int x = table.getSelectedRow();
        if (x >= 0) {
            txtID.setText(table.getValueAt(table.getSelectedRow(), 0) + "");
            txtName.setText(table.getValueAt(table.getSelectedRow(), 1) + "");
            cbLoai.setSelectedItem(table.getValueAt(table.getSelectedRow(), 2) + "");
            txtID.setEnabled(false);
        }
    }

    public void resetForm() {
        txtID.setText("");
        txtName.setText("");
        cbLoai.setSelectedItem("");
        txtID.setEnabled(true);
        hienThiBang();
    }

    public boolean checkValidate() {
        if (txtName.getText().isEmpty()
                || cbLoai.getSelectedItem().toString().isEmpty()) {
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background1 = new com.raven.swing.Background();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cbLoai = new javax.swing.JComboBox<>();
        button1 = new qlnh.swing.Button();
        button2 = new qlnh.swing.Button();
        button3 = new qlnh.swing.Button();
        button4 = new qlnh.swing.Button();
        btnTK = new qlnh.swing.Button();
        bg = new javax.swing.JLayeredPane();
        lblTongMN = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        button5 = new qlnh.swing.Button();
        jLabel9 = new javax.swing.JLabel();
        txtID = new qlnh.swing.MyTextField();
        txtName = new qlnh.swing.MyTextField();
        txtTKDA = new qlnh.swing.MyTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        dgwDoU = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        dgwMA = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        dgwMN = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Quản Lý Thực đơn");

        jLabel1.setText("Mã thực đơn:");

        jLabel3.setText("Tên thực đơn:");

        jLabel4.setText("Loại thực đơn:");

        cbLoai.setBackground(new java.awt.Color(83, 105, 118));
        cbLoai.setForeground(new java.awt.Color(204, 204, 204));
        cbLoai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Mon an man", "Mon chien", "Hai san", "Mon Au", "Mon nuong", "Mon xao", "Mon nhung", "Mon dac san", "Do uong" }));
        cbLoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbLoaiActionPerformed(evt);
            }
        });

        button1.setBackground(new java.awt.Color(204, 183, 253));
        button1.setForeground(new java.awt.Color(51, 51, 51));
        button1.setText("Thêm");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        button2.setBackground(new java.awt.Color(204, 183, 253));
        button2.setForeground(new java.awt.Color(51, 51, 51));
        button2.setText("Sửa");
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });

        button3.setBackground(new java.awt.Color(204, 183, 253));
        button3.setForeground(new java.awt.Color(51, 51, 51));
        button3.setText("Xóa");
        button3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button3ActionPerformed(evt);
            }
        });

        button4.setBackground(new java.awt.Color(204, 183, 253));
        button4.setForeground(new java.awt.Color(51, 51, 51));
        button4.setText("Làm mới");
        button4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button4ActionPerformed(evt);
            }
        });

        btnTK.setBackground(new java.awt.Color(204, 183, 253));
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
            .addGap(0, 54, Short.MAX_VALUE)
        );

        lblTongMN.setText("jLabel6");

        jLabel6.setText("Tổng số thực đơn:");

        button5.setBackground(new java.awt.Color(204, 183, 253));
        button5.setForeground(new java.awt.Color(51, 51, 51));
        button5.setText("Thống kê");
        button5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button5ActionPerformed(evt);
            }
        });

        jLabel9.setText("Tìm kiếm:");

        txtTKDA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTKDAKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(button4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(button5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblTongMN)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTKDA, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(113, 113, 113)
                                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(65, 65, 65)
                                .addComponent(jLabel4)
                                .addGap(26, 26, 26)
                                .addComponent(cbLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64)
                        .addComponent(jLabel3)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(bg)
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(cbLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(button4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblTongMN)
                        .addComponent(jLabel6)
                        .addComponent(button5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)
                        .addComponent(txtTKDA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bg)
                .addGap(0, 0, 0))
        );

        dgwDoU.setModel(new javax.swing.table.DefaultTableModel(
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
        dgwDoU.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dgwDoUMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(dgwDoU);

        dgwMA.setModel(new javax.swing.table.DefaultTableModel(
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
        dgwMA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dgwMAMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(dgwMA);

        dgwMN.setModel(new javax.swing.table.DefaultTableModel(
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
        dgwMN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dgwMNMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(dgwMN);

        jLabel7.setText("Đồ uống:");

        jLabel8.setText("Món ăn:");

        javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background1);
        background1.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1015, Short.MAX_VALUE)
            .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(background1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane3)
                        .addGroup(background1Layout.createSequentialGroup()
                            .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane1)
                                .addComponent(jLabel7))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel8)
                                .addComponent(jScrollPane2))))
                    .addContainerGap()))
        );
        background1Layout.setVerticalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 485, Short.MAX_VALUE)
            .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(background1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(jLabel8))
                    .addGap(2, 2, 2)
                    .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addContainerGap()))
        );

        add(background1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void button4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button4ActionPerformed
        resetForm();
    }//GEN-LAST:event_button4ActionPerformed

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        try {
            // Kiểm tra xem form đã được điền đầy đủ hay chưa
            if (!checkValidate()) {
                showMessage(Message.MessageType.ERROR, "Vui lòng điền đầy đủ thông tin.");
            } else {

                String idTD = service.getIDMenu();
                if (idTD == null || idTD.isEmpty()) {
                    idTD = "1";
                } else {
                    int k = Integer.parseInt(idTD);
                    int id = k + 1;
                    idTD = String.format("%d", id);
                }
                // các thông tin thêm
                mdb.setIdTD(idTD);
                mdb.setNameTD(txtName.getText());
                mdb.setLoaiTD(cbLoai.getSelectedItem().toString());
                // Kiểm tra trùng lặp và thực hiện thêm
                try {
                    if (service.checkDuplicateID(mdb.getIdTD())) {
                        showMessage(Message.MessageType.ERROR, "Mã thực đơn đã tồn tại. ");
                    } else {
                        service.insertMenu(mdb);
                        showMessage(Message.MessageType.SUCCESS, "Thêm thực đơn thành công.");
                        resetForm();
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
    }//GEN-LAST:event_button1ActionPerformed

    private void dgwMNMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dgwMNMouseClicked
        doDuLieu(dgwMN);
    }//GEN-LAST:event_dgwMNMouseClicked

    private void dgwDoUMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dgwDoUMouseClicked
        doDuLieu(dgwDoU);
    }//GEN-LAST:event_dgwDoUMouseClicked

    private void dgwMAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dgwMAMouseClicked
        doDuLieu(dgwMA);
    }//GEN-LAST:event_dgwMAMouseClicked

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        try {
            // Kiểm tra xem form đã được điền đầy đủ hay chưa
            if (!checkValidate()) {
                showMessage(Message.MessageType.ERROR, "Vui lòng điền đầy đủ thông tin.");
            } else {
                try {
                    // các thông tin sửa
                    mdb.setIdTD(txtID.getText());
                    mdb.setNameTD(txtName.getText());
                    mdb.setLoaiTD(cbLoai.getSelectedItem().toString());
                    service.UpdateMenu(mdb);
                    showMessage(Message.MessageType.SUCCESS, "Sửa thực đơn thành công.");
                    resetForm();
                    //updateTongNhanVien();
                } catch (Exception e) {
                    showMessage(Message.MessageType.ERROR, "Lỗi khi sửa.");
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            showMessage(Message.MessageType.ERROR, "Đã xảy ra lỗi không xác định.");
            e.printStackTrace();
        }
    }//GEN-LAST:event_button2ActionPerformed

    private void button3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button3ActionPerformed
        connection = cn.getConnection();
        try {
            if (txtID.getText().isEmpty()) {
                showMessage(Message.MessageType.ERROR, "Vui lòng điền mã thực đơn cần xóa.");
            } else {
                String sql = "Delete qlmenu where mathucdon ='" + txtID.getText() + "'";
                Statement st = connection.createStatement();
                int ck = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn xóa thực đơn này?", "Thông báo", JOptionPane.YES_NO_OPTION);
                if (ck == JOptionPane.YES_OPTION) {
                    st.executeUpdate(sql);
                    resetForm();
                    showMessage(Message.MessageType.SUCCESS, "Xóa thực đơn thành công.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_button3ActionPerformed

    private void button5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button5ActionPerformed
        JLabel[] lbs = {
            lb[0], lb[1], lb[2], lb[3], lb[4], lb[5], lb[6], lb[7], lb[8]
        };
        String[] loaiTDs = {
            "Món ăn mặn",
            "Món chiên",
            "Hải sản",
            "Món Âu",
            "Món nướng",
            "Món xào",
            "Món nhúng",
            "Món đặc sản",
            "Đồ uống"
        };

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < lbs.length; i++) {
            String pressure = lbs[i].getText().trim(); // Xóa khoảng trắng
            try {
                int value = Integer.parseInt(pressure);
                dataset.setValue(value, "Món", loaiTDs[i]);
            } catch (NumberFormatException e) {
                System.err.println("Giá trị không hợp lệ cho " + loaiTDs[i] + ": " + pressure);
                dataset.setValue(0, "Món", loaiTDs[i]); // Gán giá trị 0 nếu không hợp lệ
            }
        }

        JFreeChart chart = ChartFactory.createBarChart("Thống kê Số món theo từng loại TD", "Loại thực đơn", "Số món", dataset, PlotOrientation.VERTICAL, false, true, false);
        CategoryPlot p = chart.getCategoryPlot();
        p.setRangeGridlinePaint(new Color(204, 183, 253));
        ChartFrame frame = new ChartFrame("Bar chart for TD", chart);
        frame.setVisible(true);
        frame.setSize(450, 350);
    }//GEN-LAST:event_button5ActionPerformed

    private void btnTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTKActionPerformed
        JTable[] tbs = {dgwMN, dgwMA, dgwDoU};
        for (JTable tb : tbs) {
            DefaultTableModel ob = (DefaultTableModel) tb.getModel();
            TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(ob);
            tb.setRowSorter(obj);
            obj.setRowFilter(RowFilter.regexFilter(cbLoai.getSelectedItem().toString(), 1));
        }
    }//GEN-LAST:event_btnTKActionPerformed

    private void timKiem2(JTable table, int columnIndex) {
        String selectedValue = cbLoai.getSelectedItem().toString(); // Get selected value from JComboBox

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        if (selectedValue.isEmpty()) {
            sorter.setRowFilter(null); // Show all rows if no selection
        } else {
            sorter.setRowFilter(RowFilter.regexFilter(selectedValue, columnIndex)); // Filter based on the selected value
        }
    }

    private void cbLoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbLoaiActionPerformed
        if (txtName.getText().isEmpty()) {
            timKiem2(dgwMN, 2);
        }
    }//GEN-LAST:event_cbLoaiActionPerformed

    private void txtTKDAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTKDAKeyPressed
        String searchQuery = txtTKDA.getText().trim();
        DefaultTableModel model = (DefaultTableModel) dgwMN.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        dgwMN.setRowSorter(sorter);

        if (searchQuery.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchQuery));
        }

    }//GEN-LAST:event_txtTKDAKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.swing.Background background1;
    private javax.swing.JLayeredPane bg;
    private qlnh.swing.Button btnTK;
    private qlnh.swing.Button button1;
    private qlnh.swing.Button button2;
    private qlnh.swing.Button button3;
    private qlnh.swing.Button button4;
    private qlnh.swing.Button button5;
    private javax.swing.JComboBox<String> cbLoai;
    private javax.swing.JTable dgwDoU;
    private javax.swing.JTable dgwMA;
    private javax.swing.JTable dgwMN;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblTongMN;
    private qlnh.swing.MyTextField txtID;
    private qlnh.swing.MyTextField txtName;
    private qlnh.swing.MyTextField txtTKDA;
    // End of variables declaration//GEN-END:variables
}
