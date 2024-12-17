package com.raven.form;

import com.raven.chart.ModelChart;
import com.raven.service.ServiceKH;
import com.raven.service.ServiceNguyenLieu;
import com.raven.service.ServiesHD;
import java.awt.Color;
import java.sql.SQLException;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class Form_1 extends javax.swing.JPanel {

    private ServiceNguyenLieu serviceNguyenLieu;
    private ServiceKH serviceKH;
    private ServiesHD serviesHD;

    public Form_1() {
        initComponents();
        serviceNguyenLieu = new ServiceNguyenLieu();
        serviceKH = new ServiceKH();
        serviesHD = new ServiesHD();
        setOpaque(false);
        init();
    }
    
    private void init() {
        populateYearComboBox(jComboBox1, jComboBox2);
        jComboBox1.addActionListener(e -> loadChartData());
        jComboBox2.addActionListener(e -> loadChartData());
        loadChartData();
    }

    private void loadChartData(){
        chart.clear(); // Xóa dữ liệu cũ của chart
        lineChart.clear(); // Xóa dữ liệu cũ của lineChart
        int nam = Integer.parseInt(jComboBox1.getSelectedItem().toString());
        int nam2 = Integer.parseInt(jComboBox2.getSelectedItem().toString());

        chart.addLegend("Doanh thu", new Color(5, 125, 0), new Color(95, 209, 69));

        for (int month = 1; month <= 12; month++) {
            double doanhThu = serviesHD.getDoanhThuTheoThangNam(month, nam2);
            chart.addData(new ModelChart("Tháng " + month, new double[]{doanhThu}));
        }
        chart.start();

        lineChart.addLegend("Khách hàng đã đăng ký", new Color(12, 84, 175), new Color(0, 108, 247));
        lineChart.addLegend("Khách hàng thân thiết", new Color(54, 4, 143), new Color(104, 49, 200));

        for (int month = 1; month <= 12; month++) {
            double slKH = serviceKH.getSoLuongKHTheoThang(month, nam);
            double slKHTT = serviceKH.getSoLuongKHTTTheoThang(month, nam);
            lineChart.addData(new ModelChart("Tháng " + month, new double[]{slKH, slKHTT}));
        }
        lineChart.start();
    }

    public void populateYearComboBox(JComboBox yearComboBox, JComboBox yearComboBox2) {
        List<Integer> years = serviceKH.getAllYearsFromNgayThamGia(); 
        List<Integer> years2 = serviesHD.getAllYearsFromNgayTT(); 
        // Tạo model cho từng JComboBox
        DefaultComboBoxModel<Integer> model = new DefaultComboBoxModel<>(); // Model cho JComboBox năm nhập
        DefaultComboBoxModel<Integer> model2 = new DefaultComboBoxModel<>(); // Model cho JComboBox năm xuất
        // Thêm các năm vào model
        for (Integer year : years) {
            model.addElement(year);
        }
        for (Integer year : years2) {
            model2.addElement(year);
        }
        // Gán model cho từng JComboBox
        yearComboBox.setModel(model);
        yearComboBox2.setModel(model2);
    }

    public int SLnguyenlieutheoTT(String tt) {
        // Lấy số lượng nguyên liệu theo trạng thái
        int a = serviceNguyenLieu.getSoLuongNguyenLieuTheoTrangThai(tt);
        // Lấy tổng số lượng nguyên liệu
        int b = serviceNguyenLieu.getTongSoLuongNguyenLieu();
        // Kiểm tra trường hợp tổng số nguyên liệu là 0 để tránh chia cho 0
        if (b == 0) {
            System.err.println("Tổng số nguyên liệu bằng 0. Không thể tính tỷ lệ.");
            return 0; // Hoặc giá trị phù hợp theo yêu cầu
        }

        double c = (double) a / b * 100;
        int kq = (int) Math.round(c);

        return kq;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundPanel2 = new com.raven.swing.RoundPanel();
        chart = new com.raven.chart.Chart();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        roundPanel3 = new com.raven.swing.RoundPanel();
        lineChart = new com.raven.chart.LineChart();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();

        roundPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel6.setFont(new java.awt.Font("sansserif", 1, 15)); // NOI18N
        jLabel6.setText("Thống kê doanh thu theo từng tháng");
        jLabel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));

        jLabel3.setText("Năm:");

        javax.swing.GroupLayout roundPanel2Layout = new javax.swing.GroupLayout(roundPanel2);
        roundPanel2.setLayout(roundPanel2Layout);
        roundPanel2Layout.setHorizontalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(chart, javax.swing.GroupLayout.DEFAULT_SIZE, 1117, Short.MAX_VALUE))
                .addContainerGap())
        );
        roundPanel2Layout.setVerticalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chart, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
                .addContainerGap())
        );

        roundPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("sansserif", 1, 15)); // NOI18N
        jLabel2.setText("Thống kê số lượng khách hàng đã đăng ký và khách hàng thân thiết theo từng tháng");
        jLabel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 1));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("Năm:");

        javax.swing.GroupLayout roundPanel3Layout = new javax.swing.GroupLayout(roundPanel3);
        roundPanel3.setLayout(roundPanel3Layout);
        roundPanel3Layout.setHorizontalGroup(
            roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lineChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(roundPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        roundPanel3Layout.setVerticalGroup(
            roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lineChart, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(roundPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(roundPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.chart.Chart chart;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private com.raven.chart.LineChart lineChart;
    private com.raven.swing.RoundPanel roundPanel2;
    private com.raven.swing.RoundPanel roundPanel3;
    // End of variables declaration//GEN-END:variables
}
