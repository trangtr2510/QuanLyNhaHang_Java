package com.raven.formQL;

import com.raven.Connection.DatabaseConnection;
import com.raven.componert.Message;
import com.raven.model.ModelDatBan;
import com.raven.model.ModelVoucher;
import com.raven.service.ServiceBan;
import com.raven.service.ServiceVoucher;
import com.raven.swing.ScrollBar;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class formVoucher extends javax.swing.JPanel {

    private MigLayout layout;
    private ModelVoucher mdb;
    private ServiceVoucher service;
    final String header[] = {"Mã khuyến mại", "Tên mã khuyến mại", "Mô tả", "Giảm giá", "Ngày bắt đầu", "Ngày kết thúc", "Trạng thái", "Điểm tích lũy"};
    DefaultTableModel tb = new DefaultTableModel(header, 0);
    int q, i;
    DatabaseConnection cn = new DatabaseConnection();
    Connection connection;

    public formVoucher() {
        initComponents();
        service = new ServiceVoucher();
        mdb = new ModelVoucher();
        jScrollPane1.setVerticalScrollBar(new ScrollBar());
        txtID.setEnabled(false);
        intt();
        loadBang();
        ((AbstractDocument) txtDTL.getDocument()).setDocumentFilter(new NumericFilter());
    }

    static class NumericFilter extends DocumentFilter {

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string != null && string.matches("\\d+")) { // Chỉ cho phép các ký tự là số
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text != null && text.matches("\\d+")) { // Chỉ cho phép các ký tự là số
                super.replace(fb, offset, length, text, attrs);
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            super.remove(fb, offset, length);
        }
    }

    private void intt() {
        layout = new MigLayout("fill, insets 0");
        bg.setLayout(layout);
    }

    public void loadBang() {
        try {
            connection = cn.getConnection();
            int number;
            Vector row;
            String sql = "select * from Voucher";
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

    public void resetForm() {
        txtID.setText("");
        txtName.setText("");
        txtMota.setText("");
        txtNBD.setText("");
        txtNKT.setText("");
        cbGiam.setSelectedItem("");
        cbTT.setSelectedItem("");
        txtID.setEnabled(false);
        loadBang();
    }

    public void timKiem(JTable table, int cot) {
        DefaultTableModel ob = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(ob);
        table.setRowSorter(obj);
        obj.setRowFilter(RowFilter.regexFilter(txtTK.getText(), cot));
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateChooser1 = new com.raven.datechooser.DateChooser();
        dateChooser2 = new com.raven.datechooser.DateChooser();
        background1 = new com.raven.swing.Background();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cbGiam = new javax.swing.JComboBox<>();
        cbTT = new javax.swing.JComboBox<>();
        txtName = new qlnh.swing.MyTextField();
        txtMota = new qlnh.swing.MyTextField();
        txtNBD = new qlnh.swing.MyTextField();
        txtNKT = new qlnh.swing.MyTextField();
        txtTK = new qlnh.swing.MyTextField();
        button1 = new qlnh.swing.Button();
        button2 = new qlnh.swing.Button();
        button3 = new qlnh.swing.Button();
        btnLM = new qlnh.swing.Button();
        btnThem = new qlnh.swing.Button();
        btnSua = new qlnh.swing.Button();
        btnXoa = new qlnh.swing.Button();
        bg = new javax.swing.JLayeredPane();
        jLabel9 = new javax.swing.JLabel();
        txtID = new qlnh.swing.MyTextField();
        jLabel10 = new javax.swing.JLabel();
        txtDTL = new qlnh.swing.MyTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        dgwVoucher = new javax.swing.JTable();

        dateChooser1.setTextRefernce(txtNBD);

        dateChooser2.setTextRefernce(txtNKT);

        setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 153, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Quản lý Mã khuyến mại");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setText("Tên mã:");

        jLabel3.setText("Mô tả:");

        jLabel4.setText("Giảm giá");

        jLabel5.setText("Ngày bắt đầu:");

        jLabel6.setText("Ngày kết thúc:");

        jLabel7.setText("Trạng thái:");

        jLabel8.setText("Tìm kiếm");

        cbGiam.setBackground(new java.awt.Color(83, 105, 118));
        cbGiam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "15%", "30%", "50%", "75%" }));
        cbGiam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbGiamActionPerformed(evt);
            }
        });

        cbTT.setBackground(new java.awt.Color(83, 105, 118));
        cbTT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hoat dong", "Het han", "Tam dung" }));
        cbTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTTActionPerformed(evt);
            }
        });

        txtTK.setHint("Tìm kiếm theo tên...");
        txtTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTKActionPerformed(evt);
            }
        });
        txtTK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTKKeyPressed(evt);
            }
        });

        button1.setBackground(new java.awt.Color(153, 153, 255));
        button1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar.png"))); // NOI18N
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        button2.setBackground(new java.awt.Color(153, 153, 255));
        button2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/calendar.png"))); // NOI18N
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });

        button3.setBackground(new java.awt.Color(153, 153, 255));
        button3.setForeground(new java.awt.Color(51, 51, 51));
        button3.setText("Tìm kiếm");
        button3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button3ActionPerformed(evt);
            }
        });

        btnLM.setBackground(new java.awt.Color(153, 153, 255));
        btnLM.setForeground(new java.awt.Color(51, 51, 51));
        btnLM.setText("Làm mới");
        btnLM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLMActionPerformed(evt);
            }
        });

        btnThem.setBackground(new java.awt.Color(153, 153, 255));
        btnThem.setForeground(new java.awt.Color(51, 51, 51));
        btnThem.setText("Thêm mới");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(153, 153, 255));
        btnSua.setForeground(new java.awt.Color(51, 51, 51));
        btnSua.setText("Chỉnh sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(153, 153, 255));
        btnXoa.setForeground(new java.awt.Color(51, 51, 51));
        btnXoa.setText("Xóa");
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
            .addGap(0, 44, Short.MAX_VALUE)
        );

        jLabel9.setText("Mã giảm giá:");

        jLabel10.setText("Điểm tích lũy:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtID, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                            .addComponent(txtMota, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(120, 120, 120)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtNBD, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtNKT, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(95, 95, 95))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtDTL, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbTT, 0, 150, Short.MAX_VALUE)
                                    .addComponent(cbGiam, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtTK, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnLM, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(223, 223, 223))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbGiam, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(jLabel4)
                                .addComponent(jLabel9)
                                .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(txtNBD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(cbTT, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNKT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(txtMota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDTL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLM, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

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

        javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background1);
        background1.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        background1Layout.setVerticalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE))
        );

        add(background1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void txtTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTKActionPerformed

    }//GEN-LAST:event_txtTKActionPerformed

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        dateChooser1.showPopup(this, (getWidth() - dateChooser1.getPreferredSize().width) / 2, (getHeight() - dateChooser1.getPreferredSize().height) / 2);

    }//GEN-LAST:event_button1ActionPerformed

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        dateChooser2.showPopup(this, (getWidth() - dateChooser1.getPreferredSize().width) / 2, (getHeight() - dateChooser1.getPreferredSize().height) / 2);

    }//GEN-LAST:event_button2ActionPerformed

    private void button3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button3ActionPerformed
        timKiem(dgwVoucher, 0);
    }//GEN-LAST:event_button3ActionPerformed

    private void btnLMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLMActionPerformed
        resetForm();
    }//GEN-LAST:event_btnLMActionPerformed

    public boolean checkValidateForm() {
        if (txtID.getText().isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean checkValidateForm2() {
        if (txtName.getText().isEmpty() || txtMota.getText().isEmpty()
                || txtNBD.getText().isEmpty()
                || txtNKT.getText().isEmpty()
                || cbGiam.getSelectedItem().toString().isEmpty()
                || cbTT.getSelectedItem().toString().isEmpty()) {
            return false;
        }
        return true;
    }
    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed

        try {
            if (!checkValidateForm2()) {
                showMessage(Message.MessageType.ERROR, "Vui lòng điền đầy đủ thông tin.");
            } else {
                // Kiểm tra sự trùng lặp và thực hiện đặt bàn
                try {
                    // Thực hiện các thiết lập thông tin đặt bàn

                    String idVC = service.getLatestVoucher(); // lay idhd lon nhat trong bang
                    if (idVC == null || idVC.isEmpty()) {
                        idVC = "Voucher001"; //idhd lon nhat Neu trong bang chua co idhd nao thi 
                    } else {
                        int k = Integer.parseInt(idVC.substring(7));//ep kieu chuoi con lay tu chuoi idHD, chuoi con bat dau tu ki tu thu 2(Dem tu 0)
                        int tangidHD = k + 1;//tang idhd len 1
                        idVC = "Voucher" + String.format("%03d", tangidHD); // tao idhd moi
                    }
                    mdb.setMaVoucher(idVC);//set gia tri cho idhd, idban, tang, ngaytt, gia 

                    mdb.setTenVoucher(txtName.getText());
                    mdb.setMoTa(txtMota.getText());
                    LocalDate currentDate = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate ngayBatDauLocal = LocalDate.parse(txtNBD.getText(), formatter);
                    LocalDate ngayKetThucLocal = LocalDate.parse(txtNKT.getText(), formatter);

                    Date ngayBatDau = Date.valueOf(ngayBatDauLocal);
                    Date ngayKetThuc = Date.valueOf(ngayKetThucLocal);

                    mdb.setNgayBatDau(ngayBatDau);
                    mdb.setNgayKetThuc(ngayKetThuc);

                    String sl = cbGiam.getSelectedItem().toString();
                    String numericPart = sl.replace("%", "").trim();
                    float giam = Float.parseFloat(numericPart);
                    float gia = giam / 100;
                    DecimalFormat df = new DecimalFormat("#.00");
                    String formattedGia = df.format(gia);
                    float giamgia = Float.parseFloat(formattedGia);
                    mdb.setGiamGia(giamgia);
                    mdb.setTrangThai(cbTT.getSelectedItem().toString());
                    String diem = txtDTL.getText();
                    int diemTL = Integer.parseInt(diem);
                    mdb.setDiemTL(diemTL);

                    // Kiểm tra trùng lặp và thực hiện thêm
                    try {
                        if (service.checkDuplicateVoucher(mdb.getTenVoucher())) {
                            showMessage(Message.MessageType.ERROR, "Voucher đã tồn tại. ");
                        } else {
                            service.insertVoucher(mdb);
                            resetForm();
                            showMessage(Message.MessageType.SUCCESS, "Thêm thành công.");
                        }
                    } catch (Exception e) {
                        showMessage(Message.MessageType.ERROR, "Lỗi khi thêm.");
                        e.printStackTrace();
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
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        try {
            if (!checkValidateForm()) {
                showMessage(Message.MessageType.ERROR, "Vui lòng điền Mã khuyến mại cần chỉnh sửa.");
            } else {
                // Kiểm tra sự trùng lặp và thực hiện đặt bàn
                try {
                    // Thực hiện các thiết lập thông tin đặt bàn
                    mdb.setMaVoucher(txtID.getText());
                    mdb.setTenVoucher(txtName.getText());
                    mdb.setMoTa(txtMota.getText());
                    LocalDate currentDate = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

                    // Parse text input to LocalDate
                    LocalDate ngayBatDauLocal = LocalDate.parse(txtNBD.getText(), formatter);
                    LocalDate ngayKetThucLocal = LocalDate.parse(txtNKT.getText(), formatter);

                    Date ngayBatDau = Date.valueOf(ngayBatDauLocal);
                    Date ngayKetThuc = Date.valueOf(ngayKetThucLocal);

                    mdb.setNgayBatDau(ngayBatDau);
                    mdb.setNgayKetThuc(ngayKetThuc);

                    // Get the discount percentage from the ComboBox
                    String sl = cbGiam.getSelectedItem().toString();
                    String numericPart = sl.replace("%", "").trim();
                    float giam = Float.parseFloat(numericPart);
                    float gia = giam / 100;
                    BigDecimal roundedGia = new BigDecimal(gia).setScale(2, RoundingMode.HALF_UP);
                    mdb.setGiamGia(roundedGia.floatValue());
                    mdb.setTrangThai(cbTT.getSelectedItem().toString());
                    String diem = txtDTL.getText();
                    int diemTL = Integer.parseInt(diem);
                    mdb.setDiemTL(diemTL);

                    service.UpdateVoucher(mdb);
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
    }//GEN-LAST:event_btnSuaActionPerformed

    private void dgwVoucherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dgwVoucherMouseClicked
        int x = dgwVoucher.getSelectedRow();
        if (x >= 0) {
            txtID.setText(dgwVoucher.getValueAt(dgwVoucher.getSelectedRow(), 0) + "");
            txtName.setText(dgwVoucher.getValueAt(dgwVoucher.getSelectedRow(), 1) + "");
            txtMota.setText(dgwVoucher.getValueAt(dgwVoucher.getSelectedRow(), 2) + "");
            txtNBD.setText(dgwVoucher.getValueAt(dgwVoucher.getSelectedRow(), 4) + "");
            String giamGiaStr = dgwVoucher.getValueAt(x, 3).toString();
            float giamGiaFloat = Float.parseFloat(giamGiaStr);
            String giamGiaPercent = String.format("%.0f%%", giamGiaFloat * 100);
            cbGiam.setSelectedItem(giamGiaPercent);
            txtNKT.setText(dgwVoucher.getValueAt(dgwVoucher.getSelectedRow(), 5) + "");
            cbTT.setSelectedItem(dgwVoucher.getValueAt(dgwVoucher.getSelectedRow(), 6) + "");
            txtDTL.setText(dgwVoucher.getValueAt(dgwVoucher.getSelectedRow(), 7) + "");
            txtID.setEnabled(false);
        }
    }//GEN-LAST:event_dgwVoucherMouseClicked

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        try {
            if (txtID.getText().isEmpty()) {//kiểm tra ô nhập mã nhân viên, nếu trống thì thông báo lỗi
                showMessage(Message.MessageType.ERROR, "Vui lòng điền mã Voucher cần xóa.");
            } else {

                mdb.setMaVoucher(txtID.getText());//set dữ liệu cần cho phương thức 
                //tạo form thông báo xác nhận xóa nhân viên
                int ck = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn xóa Voucher này?", "Thông báo", JOptionPane.YES_NO_OPTION);
                if (ck == JOptionPane.YES_OPTION) {//nếu chọn có thì thực hiện xóa nhân viên
                    service.deleteVoucher(mdb);
                    //st.executeUpdate(sql);
                    resetForm();//làm mới lại form
                    showMessage(Message.MessageType.SUCCESS, "Xóa Voucher thành công.");//thong báo thành công 
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void timKiem2(JTable table, int columnIndex) {
        String selectedValue = cbTT.getSelectedItem().toString(); // Get selected value from JComboBox

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        if (selectedValue.isEmpty()) {
            sorter.setRowFilter(null); // Show all rows if no selection
        } else {
            sorter.setRowFilter(RowFilter.regexFilter(selectedValue, columnIndex)); // Filter based on the selected value
        }
    }
    private void cbTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTTActionPerformed
        if (txtName.getText().isEmpty()) {
            timKiem2(dgwVoucher, 6);
        }
    }//GEN-LAST:event_cbTTActionPerformed

    private void cbGiamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbGiamActionPerformed
        if (txtName.getText().isEmpty()) {
            String selectedPercentage = cbGiam.getSelectedItem().toString();

            float selectedDiscount = Float.parseFloat(selectedPercentage.replace("%", "")) / 100;

            DefaultTableModel model = (DefaultTableModel) dgwVoucher.getModel();
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
            dgwVoucher.setRowSorter(sorter);
            sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
                @Override
                public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                    String discountStr = entry.getStringValue(3);
                    float discountValue = Float.parseFloat(discountStr);
                    return discountValue == selectedDiscount;
                }
            });
        }
    }//GEN-LAST:event_cbGiamActionPerformed

    private void txtTKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTKKeyPressed
        String searchQuery = txtTK.getText().trim();
        DefaultTableModel model = (DefaultTableModel) dgwVoucher.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        dgwVoucher.setRowSorter(sorter);

        if (searchQuery.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchQuery));
        }
    }//GEN-LAST:event_txtTKKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.swing.Background background1;
    private javax.swing.JLayeredPane bg;
    private qlnh.swing.Button btnLM;
    private qlnh.swing.Button btnSua;
    private qlnh.swing.Button btnThem;
    private qlnh.swing.Button btnXoa;
    private qlnh.swing.Button button1;
    private qlnh.swing.Button button2;
    private qlnh.swing.Button button3;
    private javax.swing.JComboBox<String> cbGiam;
    private javax.swing.JComboBox<String> cbTT;
    private com.raven.datechooser.DateChooser dateChooser1;
    private com.raven.datechooser.DateChooser dateChooser2;
    private javax.swing.JTable dgwVoucher;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private qlnh.swing.MyTextField txtDTL;
    private qlnh.swing.MyTextField txtID;
    private qlnh.swing.MyTextField txtMota;
    private qlnh.swing.MyTextField txtNBD;
    private qlnh.swing.MyTextField txtNKT;
    private qlnh.swing.MyTextField txtName;
    private qlnh.swing.MyTextField txtTK;
    // End of variables declaration//GEN-END:variables
}
