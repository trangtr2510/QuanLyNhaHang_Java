package com.raven.formQL;

import com.raven.Connection.DatabaseConnection;
import com.raven.componert.Message;
import com.raven.datechooser.SelectedDate;
import com.raven.model.ModelDatBan;
import com.raven.model.ModelHD;
import com.raven.service.ServiceBan;
import com.raven.service.ServiesHD;
import com.raven.swing.ScrollBar;
import com.raven.swing.TimePicker.TimePickerListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Vector;
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

public class formBan extends javax.swing.JPanel {

    private MigLayout layout;
    private ModelDatBan mdb;
    private ServiceBan service;
    final String header[] = {"Bàn", "Tầng", "Tên KH", "Số điện thoại", "Số lượng người", "Ngày đặt",
        "Giờ đặt", "Trạng thái"};
    DefaultTableModel tb = new DefaultTableModel(header, 0);
    int q, i;
    DatabaseConnection cn = new DatabaseConnection();
    Connection connection;

    public formBan() {
        initComponents();
        service = new ServiceBan();
        mdb = new ModelDatBan();
        loadBang();
        intt();
        serviceHD = new ServiesHD();
        mdbHD = new ModelHD();
        jScrollPane1.setVerticalScrollBar(new ScrollBar());
        txtIDB.setEnabled(false);
        txtTang.setEnabled(false);
        txtDate.setEditable(false);
        txtTime.setEditable(false);

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
            String sql = "select * from datban";
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
            dgwKH.setModel(tb);
            //updateTongNhanVien();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetForm() {
        txtIDB.setText("");
        txtTang.setText("");
        txtNameKH.setText("");
        txtSDT.setText("");
        txtSLN.setText("");
        txtDate.setText("");
        txtTime.setText("");
        txtIDB.setEnabled(false);
        txtTang.setEnabled(false);
        loadBang();
    }

    public boolean checkValidateForm() {
        if (txtIDB.getText().isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean checkValidateForm2() {
        if (txtNameKH.getText().isEmpty() || txtSDT.getText().isEmpty()
                || txtSLN.getText().isEmpty()
                || txtDate.getText().isEmpty()
                || txtTime.getText().isEmpty()) {
            return false;
        }
        return true;
    }

    public void timKiem(JTable table, JTextField text, int cot) {
        DefaultTableModel ob = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(ob);
        table.setRowSorter(obj);
        obj.setRowFilter(RowFilter.regexFilter(text.getText(), 0));
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
        timePicker1 = new com.raven.swing.TimePicker();
        background1 = new com.raven.swing.Background();
        jScrollPane1 = new javax.swing.JScrollPane();
        dgwKH = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        button1 = new qlnh.swing.Button();
        button3 = new qlnh.swing.Button();
        button5 = new qlnh.swing.Button();
        button6 = new qlnh.swing.Button();
        button8 = new qlnh.swing.Button();
        button9 = new qlnh.swing.Button();
        bg = new javax.swing.JLayeredPane();
        txtIDB = new qlnh.swing.MyTextField();
        txtTang = new qlnh.swing.MyTextField();
        txtSDT = new qlnh.swing.MyTextField();
        txtSLN = new qlnh.swing.MyTextField();
        txtNameKH = new qlnh.swing.MyTextField();
        txtDate = new qlnh.swing.MyTextField();
        txtTime = new qlnh.swing.MyTextField();
        txtTK = new qlnh.swing.MyTextField();

        dateChooser1.setTextRefernce(txtDate);

        timePicker1.setDisplayText(txtTime);

        setLayout(new java.awt.BorderLayout());

        dgwKH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8"
            }
        ));
        dgwKH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dgwKHMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(dgwKH);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Quản Lý Đặt Bàn");

        jLabel2.setText("Bàn:");

        jLabel3.setText("SDT:");

        jLabel4.setText("Tầng:");

        jLabel5.setText("Số lượng người:");

        jLabel6.setText("Tên KH:");

        jLabel7.setText("Ngày đặt:");

        jLabel8.setText("Giờ đặt:");

        jLabel9.setText("Tìm kiếm:");

        button1.setBackground(new java.awt.Color(204, 183, 253));
        button1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/inventory (1).png"))); // NOI18N
        button1.setPreferredSize(new java.awt.Dimension(30, 30));
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        button3.setBackground(new java.awt.Color(204, 183, 253));
        button3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/clock.png"))); // NOI18N
        button3.setPreferredSize(new java.awt.Dimension(30, 30));
        button3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button3ActionPerformed(evt);
            }
        });

        button5.setBackground(new java.awt.Color(204, 183, 253));
        button5.setForeground(new java.awt.Color(51, 51, 51));
        button5.setText("Làm mới");
        button5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button5ActionPerformed(evt);
            }
        });

        button6.setBackground(new java.awt.Color(204, 183, 253));
        button6.setForeground(new java.awt.Color(51, 51, 51));
        button6.setText("Đặt bàn");
        button6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button6ActionPerformed(evt);
            }
        });

        button8.setBackground(new java.awt.Color(204, 183, 253));
        button8.setForeground(new java.awt.Color(51, 51, 51));
        button8.setText("Hủy bàn");
        button8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button8ActionPerformed(evt);
            }
        });

        button9.setBackground(new java.awt.Color(204, 183, 253));
        button9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/search.png"))); // NOI18N
        button9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button9ActionPerformed(evt);
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
            .addGap(0, 56, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bg)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(30, 30, 30)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtIDB, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel4))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel7)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(85, 85, 85)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel1)
                                            .addComponent(txtTang, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                                            .addComponent(txtSLN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtTK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(button9, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)))
                                .addGap(18, 18, 18))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(button5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(button6, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(button8, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNameKH, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(66, 66, 66)))
                .addGap(22, 22, 22))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(txtIDB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNameKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(jLabel7)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtSLN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 20, Short.MAX_VALUE)
                        .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(button9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(button5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(button6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(button8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(button3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background1);
        background1.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1017, Short.MAX_VALUE)
            .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(background1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap()))
        );
        background1Layout.setVerticalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 397, Short.MAX_VALUE)
            .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(background1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        add(background1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        dateChooser1.showPopup(this, (getWidth() - dateChooser1.getPreferredSize().width) / 2, (getHeight() - dateChooser1.getPreferredSize().height) / 2);

    }//GEN-LAST:event_button1ActionPerformed

    private void button5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button5ActionPerformed
        resetForm();
    }//GEN-LAST:event_button5ActionPerformed


    private void button3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button3ActionPerformed
        timePicker1.showPopup(this, (getWidth() - timePicker1.getPreferredSize().width) / 2, (getHeight() - timePicker1.getPreferredSize().height) / 2);

    }//GEN-LAST:event_button3ActionPerformed

    private void button9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button9ActionPerformed
        timKiem(dgwKH, txtTK, 7);
    }//GEN-LAST:event_button9ActionPerformed

    private void button8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button8ActionPerformed
        //Huy Dat ban 
        try {
            mdb.setIDBan(txtIDB.getText());
            mdb.setTang(txtTang.getText());
            try {
                if (service.checkDuplicateBan(mdb.getIDBan(), mdb.getTang())) {
                    service.HUYBan(mdb);
                    resetForm();
                    showMessage(Message.MessageType.SUCCESS, "Bàn đã được hủy thành công.");
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
    }//GEN-LAST:event_button8ActionPerformed

    ModelHD mdbHD;
    ServiesHD serviceHD;
    private void button6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button6ActionPerformed
        //Dat ban 
        try {
            // Kiểm tra xem form đã được điền đầy đủ hay chưa
            if (!checkValidateForm()) {
                showMessage(Message.MessageType.ERROR, "Vui lòng chọn bàn cần đặt.");
            } else if (!checkValidateForm2()) {
                showMessage(Message.MessageType.ERROR, "Vui lòng điền đầy đủ thông tin.");
            } else {
                // Kiểm tra sự trùng lặp và thực hiện đặt bàn
                try {
                    // Thực hiện các thiết lập thông tin đặt bàn
                    mdb.setIDBan(txtIDB.getText());
                    mdb.setTang(txtTang.getText());
                    mdb.setNameKH(txtNameKH.getText());
                    mdb.setSDT(txtSDT.getText());
                    String sl = txtSLN.getText();
                    int slNguoi = Integer.parseInt(sl);
                    mdb.setSLNguoi(slNguoi);
                    mdb.setDate(txtDate.getText());
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
                    mdbHD.setIdBan(txtIDB.getText());
                    mdbHD.setTang(txtTang.getText());
                    LocalDate currentDate = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    String formattedDate = currentDate.format(formatter);
                    mdbHD.setNgayTT(formattedDate);
                    mdbHD.setGia(slNguoi * 90);
                    String idkhStr = "000";
                    if (idkhStr != null) {
                        int idkh = Integer.parseInt(idkhStr);
                        mdbHD.setIDKH(idkh); // Set ID_KH as an integer
                    } else {
                        JOptionPane.showMessageDialog(null, "Không tìm thấy ID khách hàng.");
                    }

                    if (service.checkDuplicateBan(mdb.getIDBan(), mdb.getTang())) {
                        showMessage(Message.MessageType.ERROR, "Bàn đã có người ");
                    } else {
                        service.UpdateBan(mdb);
                        serviceHD.insertHD(mdbHD);
                        //service.insertBan(mdb);
                        resetForm();
                        showMessage(Message.MessageType.SUCCESS, "Đặt bàn thành công.");
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
    }//GEN-LAST:event_button6ActionPerformed

    private void dgwKHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dgwKHMouseClicked
        int x = dgwKH.getSelectedRow();
        if (x >= 0) {
            txtIDB.setText(dgwKH.getValueAt(dgwKH.getSelectedRow(), 0) + "");
            txtTang.setText(dgwKH.getValueAt(dgwKH.getSelectedRow(), 1) + "");
            txtNameKH.setText(dgwKH.getValueAt(dgwKH.getSelectedRow(), 2) + "");
            txtSDT.setText(dgwKH.getValueAt(dgwKH.getSelectedRow(), 3) + "");
            txtSLN.setText(dgwKH.getValueAt(dgwKH.getSelectedRow(), 4) + "");
            txtDate.setText(dgwKH.getValueAt(dgwKH.getSelectedRow(), 5) + "");
            txtTime.setText(dgwKH.getValueAt(dgwKH.getSelectedRow(), 6) + "");
            txtIDB.setEnabled(false);
            txtTang.setEnabled(false);
        }
    }//GEN-LAST:event_dgwKHMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.swing.Background background1;
    private javax.swing.JLayeredPane bg;
    private qlnh.swing.Button button1;
    private qlnh.swing.Button button3;
    private qlnh.swing.Button button5;
    private qlnh.swing.Button button6;
    private qlnh.swing.Button button8;
    private qlnh.swing.Button button9;
    private com.raven.datechooser.DateChooser dateChooser1;
    private javax.swing.JTable dgwKH;
    private javax.swing.JLabel jLabel1;
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
    private com.raven.swing.TimePicker timePicker1;
    private qlnh.swing.MyTextField txtDate;
    private qlnh.swing.MyTextField txtIDB;
    private qlnh.swing.MyTextField txtNameKH;
    private qlnh.swing.MyTextField txtSDT;
    private qlnh.swing.MyTextField txtSLN;
    private qlnh.swing.MyTextField txtTK;
    private qlnh.swing.MyTextField txtTang;
    private qlnh.swing.MyTextField txtTime;
    // End of variables declaration//GEN-END:variables
}
