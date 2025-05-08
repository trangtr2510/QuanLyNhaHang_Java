package com.raven.form;

import com.raven.Connection.DatabaseConnection;
import com.raven.model.ModelUser;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

public class Form extends javax.swing.JPanel {

    
    private DatabaseConnection cn = new DatabaseConnection();

    
    public Form(ModelUser user) {
        initComponents();
        displayUserInfo(user);
        randomText.setEnabled(false);
        try {
            String captcha = generateCaptchaText(5);
            BufferedImage image = generateCaptchaImage(captcha);

            // Tạo thư mục "captcha" nếu chưa có
            File dir = new File("captcha");
            if (!dir.exists()) {
                dir.mkdir();
            }

            // Tạo đường dẫn file
            File outputFile = new File(dir, "captcha_" + captcha + ".png");

            // Ghi ảnh vào file
            ImageIO.write(image, "png", outputFile);
//            randomText.setPrefixIcon(new ImageIcon(image));
            ImageIcon icon = new ImageIcon(image);
            randomText.setPrefixIcon(icon);
            randomText.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
            randomText.revalidate();
            randomText.repaint();
            System.out.println("Captcha saved to: " + outputFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        btnGui.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnGuiActionPerformed(evt);
        }
    });
        
    }
    
    // Phương thức hiển thị thông tin khách hàng
    public void displayUserInfo(ModelUser user) {
        // Thay thế user.getUserID() bằng ID_ND để lấy thông tin từ bảng khachhang và nguoidung
        int userID = user.getUserID();  // ID_ND của khách hàng

        try {
            Connection conn = cn.getConnection();
            // Truy vấn để lấy thông tin khách hàng
            String sql = "SELECT kh.tenKH, kh.Sdt, nd.Vaitro, nd.Email "
                    + "FROM qlkhachhang kh "
                    + "JOIN nguoidung nd ON kh.ID_ND = nd.ID_ND "
                    + "WHERE nd.ID_ND = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userID);

            ResultSet rs = stmt.executeQuery();
            // Đặt dữ liệu vào các trường giao diện nếu tìm thấy khách hàng
            if (rs.next()) {
                txtTen.setText(rs.getString("tenKH"));
                txtSDT.setText(rs.getString("Sdt"));
                txtEmail.setText(rs.getString("Email"));
            }

            // Đóng kết nối
            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể tải thông tin khách hàng.");
        }
    }

    public static String generateCaptchaText(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder captcha = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            captcha.append(chars.charAt(rand.nextInt(chars.length())));
        }
        return captcha.toString();
    }
    
    public static BufferedImage generateCaptchaImage(String captchaText) {
        int width = 180;
        int height = 60;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // Nền trắng
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        Random rand = new Random();

        String[] fonts = {"Jokerman", "Kristen ITC", "Comic Sans MS", "Serif"};
        Font font = new Font(fonts[rand.nextInt(fonts.length)], Font.BOLD, 35);
        g.setFont(font);

        // Vẽ từng ký tự với lệch vị trí và xoay
        for (int i = 0; i < captchaText.length(); i++) {
            AffineTransform orig = g.getTransform();

            // Xoay ngẫu nhiên
            double angle = Math.toRadians(rand.nextInt(30) - 15);
            g.rotate(angle, 30 * i + 15, 40);

            // Vẽ ký tự
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(captchaText.charAt(i)), 30 * i + 10, 40);

            g.setTransform(orig);
        }

        g.dispose();
        return image;
    }
    
    private void btnGuiActionPerformed(java.awt.event.ActionEvent evt) {
        // Get user input
        String name = txtTen.getText().trim();
        String phone = txtSDT.getText().trim();
        String email = txtEmail.getText().trim();
        String message = txtMess.getText().trim();
        String captchaEntered = myTextField6.getText().trim();

        // Validate input fields
        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || message.isEmpty() || captchaEntered.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate email format using regex
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!email.matches(emailRegex)) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get the current captcha text from the image
        // This would be the text that was used to generate the captcha image
        String currentCaptcha = getGeneratedCaptchaText();

        // Validate captcha
        if (!captchaEntered.equals(currentCaptcha)) {
            JOptionPane.showMessageDialog(this, "Mã xác nhận không đúng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            generateNewCaptcha(); // Generate a new captcha after failed attempt
            return;
        }

        // Prepare the email content (for demonstration)
        String recipientEmail = "changcherchou@gmail.com";
        String subject = "Tin nhắn liên hệ từ " + name;
        String emailContent = prepareEmailContent(name, phone, email, message);

        // In a real application, this is where you would send the email
        // For now, we'll just show a success message

        JOptionPane.showMessageDialog(this, 
                                     "Đã gửi tin nhắn thành công tới " + recipientEmail, 
                                     "Thành công", 
                                     JOptionPane.INFORMATION_MESSAGE);

        // Clear the form fields after successful submission
        clearForm();

        // Generate new captcha for security
        generateNewCaptcha();
    }

    /**
     * Prepares the email content from the form data
     */
    private String prepareEmailContent(String name, String phone, String email, String message) {
        StringBuilder content = new StringBuilder();
        content.append("Tin nhắn mới từ form liên hệ:\n\n");
        content.append("Tên: ").append(name).append("\n");
        content.append("Số điện thoại: ").append(phone).append("\n");
        content.append("Email: ").append(email).append("\n");
        content.append("Nội dung tin nhắn:\n").append(message).append("\n");
        content.append("\n---\n");
        content.append("Tin nhắn này được gửi từ form liên hệ Quản lý nhà hàng Buffet.");

        return content.toString();
    }

    /**
     * Clear all form fields after submission
     */
    private void clearForm() {
        txtHo.setText("");
        txtTen.setText("");
        txtSDT.setText("");
        txtEmail.setText("");
        txtMess.setText("");
        myTextField6.setText("");
    }

    /**
     * Store the current captcha text for validation
     */
    private String captchaText;

    /**
     * Returns the current captcha text
     */
    private String getGeneratedCaptchaText() {
        return captchaText;
    }

    /**
     * Generates a new captcha and updates the display
     */
    private void generateNewCaptcha() {
        try {
            // Generate new captcha text
            captchaText = generateCaptchaText(5);

            // Generate captcha image
            BufferedImage image = generateCaptchaImage(captchaText);

            // Create captcha directory if it doesn't exist
            File dir = new File("captcha");
            if (!dir.exists()) {
                dir.mkdir();
            }

            // Save captcha image
            File outputFile = new File(dir, "captcha_" + captchaText + ".png");
            ImageIO.write(image, "png", outputFile);

            // Update the display
            ImageIcon icon = new ImageIcon(image);
            randomText.setPrefixIcon(icon);
            randomText.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
            randomText.revalidate();
            randomText.repaint();

            // Clear the captcha input field
            myTextField6.setText("");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể tạo mã xác nhận mới.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background1 = new com.raven.swing.Background();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        phonePanel = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        emailPanel = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        locationPanel = new javax.swing.JPanel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        hoursPanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMess = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        myTextField6 = new qlnh.swing.MyTextField();
        randomText = new qlnh.swing.MyTextField();
        jLabel9 = new javax.swing.JLabel();
        btnGui = new qlnh.swing.Button();
        jPanel5 = new javax.swing.JPanel();
        txtHo = new qlnh.swing.MyTextField();
        txtTen = new qlnh.swing.MyTextField();
        txtSDT = new qlnh.swing.MyTextField();
        txtEmail = new qlnh.swing.MyTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.BorderLayout());

        background1.setBackground(new java.awt.Color(255, 255, 255));
        background1.setLayout(new java.awt.GridLayout(1, 0, 5, 0));

        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setBackground(new java.awt.Color(126, 171, 208));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("<html> <i style=\"color:rgb(255,255,255);font-size:14px;\">Liên lạc với chúng tôi ngay bây giờ!<i> </html>");
        jLabel1.setMaximumSize(new java.awt.Dimension(70, 50));
        jLabel1.setMinimumSize(new java.awt.Dimension(60, 50));
        jLabel1.setOpaque(true);
        jLabel1.setPreferredSize(new java.awt.Dimension(37, 50));
        jPanel1.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.GridLayout(2, 3));

        phonePanel.setBackground(new java.awt.Color(255, 255, 255));
        phonePanel.setToolTipText("");

        jSeparator1.setBackground(new java.awt.Color(0, 204, 255));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator4.setBackground(new java.awt.Color(0, 204, 255));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("<html>\n<head>\n <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css\" />\n</head>\n<body>\n  <div style=\"text-align:center;\">\n    <div style=\"font-size:30px;\">📞</div>\n    <div style=\"font-weight:bold; font-size:16px; color:#1c2a4d;\">\n      Phone Number\n    </div>\n    <div style=\"color:#1c2a4d; font-size:14px;\">\n      +91 80004 36640\n    </div>\n  </div>\n</body>\n</html>");

        javax.swing.GroupLayout phonePanelLayout = new javax.swing.GroupLayout(phonePanel);
        phonePanel.setLayout(phonePanelLayout);
        phonePanelLayout.setHorizontalGroup(
            phonePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, phonePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(phonePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(phonePanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jSeparator4, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        phonePanelLayout.setVerticalGroup(
            phonePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(phonePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(phonePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(phonePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, phonePanelLayout.createSequentialGroup()
                    .addGap(0, 296, Short.MAX_VALUE)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel3.add(phonePanel);

        emailPanel.setBackground(new java.awt.Color(255, 255, 255));

        jSeparator2.setBackground(new java.awt.Color(0, 204, 255));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("<html>\n  <div style=\"text-align:center;\">\n    <div style=\"font-size:30px;\">✉</div>\n    <div style=\"font-weight:bold; font-size:16px; color:#1c2a4d;\">Get In Touch</div>\n    <div style=\"color:#1c2a4d; font-size:14px;\">Với chúng tôi bây giờ!</div>\n  </div>\n</html>\n");

        javax.swing.GroupLayout emailPanelLayout = new javax.swing.GroupLayout(emailPanel);
        emailPanel.setLayout(emailPanelLayout);
        emailPanelLayout.setHorizontalGroup(
            emailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(emailPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(emailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(emailPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        emailPanelLayout.setVerticalGroup(
            emailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(emailPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(emailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, emailPanelLayout.createSequentialGroup()
                    .addGap(0, 296, Short.MAX_VALUE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel3.add(emailPanel);

        locationPanel.setBackground(new java.awt.Color(255, 255, 255));

        jSeparator3.setBackground(new java.awt.Color(0, 204, 255));
        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("<html>\n  <div style=\"text-align:center;\">\n    <div style=\"font-size:30px;\">📍</div>\n    <div style=\"font-weight:bold; font-size:16px; color:#1c2a4d;\">Location</div>\n    <div style=\"color:#1c2a4d; font-size:14px;\">\n      518, Tòa Star Tower, Dương Đình Nghệ,<br>\nYên Hòa, Cầu Giấy, Hà Nội – 100000\n    </div>\n  </div>\n</html>\n");

        javax.swing.GroupLayout locationPanelLayout = new javax.swing.GroupLayout(locationPanel);
        locationPanel.setLayout(locationPanelLayout);
        locationPanelLayout.setHorizontalGroup(
            locationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(locationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(locationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, locationPanelLayout.createSequentialGroup()
                    .addGap(0, 247, Short.MAX_VALUE)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        locationPanelLayout.setVerticalGroup(
            locationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(locationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(locationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(locationPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jSeparator3, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jPanel3.add(locationPanel);

        hoursPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("<html>   <div style=\"text-align:center;\">     <div style=\"font-size:30px;\">🕒</div>     <div style=\"font-weight:bold; font-size:16px; color:#1c2a4d;\">Working Hours</div>     <div style=\"color:#1c2a4d; font-size:14px;\">       Thứ Hai đến Thứ Bảy <br> 09:00 AM đến 08:00 PM     </div>   </div> </html> ");

        javax.swing.GroupLayout hoursPanelLayout = new javax.swing.GroupLayout(hoursPanel);
        hoursPanel.setLayout(hoursPanelLayout);
        hoursPanelLayout.setHorizontalGroup(
            hoursPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(hoursPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                .addContainerGap())
        );
        hoursPanelLayout.setVerticalGroup(
            hoursPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(hoursPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.add(hoursPanel);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        background1.add(jPanel1);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jLabel2.setBackground(new java.awt.Color(126, 171, 208));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("<html> <i style=\"color:rgb(255,255,255);font-size:14px;\">Contact us<i> </html>");
        jLabel2.setMaximumSize(new java.awt.Dimension(37, 50));
        jLabel2.setMinimumSize(new java.awt.Dimension(37, 50));
        jLabel2.setOpaque(true);
        jLabel2.setPreferredSize(new java.awt.Dimension(37, 50));
        jPanel2.add(jLabel2, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setText("Gửi yêu cầu, thắc mắc:");

        txtMess.setColumns(20);
        txtMess.setRows(5);
        txtMess.setToolTipText("");
        jScrollPane1.setViewportView(txtMess);

        jLabel8.setText("Vui lòng nhập các ký tự:");

        randomText.setForeground(new java.awt.Color(255, 255, 255));

        jLabel9.setText("Điều này giúp chúng tôi ngăn chặn thư rác, cảm ơn bạn.");

        btnGui.setBackground(new java.awt.Color(126, 171, 208));
        btnGui.setForeground(new java.awt.Color(255, 255, 255));
        btnGui.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/message.png"))); // NOI18N
        btnGui.setText("Gửi ");
        btnGui.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        btnGui.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/send (1).jpg"))); // NOI18N

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.GridLayout(2, 2, 10, 10));

        txtHo.setHint("Họ ");
        txtHo.setPrefixIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/user (2).png"))); // NOI18N
        jPanel5.add(txtHo);

        txtTen.setHint("Tên");
        txtTen.setName(""); // NOI18N
        txtTen.setPreferredSize(new java.awt.Dimension(99, 25));
        txtTen.setPrefixIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/user (2).png"))); // NOI18N
        jPanel5.add(txtTen);

        txtSDT.setHint("SDT");
        txtSDT.setPrefixIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/phone-alt-solid (1).png"))); // NOI18N
        txtSDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSDTActionPerformed(evt);
            }
        });
        jPanel5.add(txtSDT);

        txtEmail.setHint("Email");
        txtEmail.setMinimumSize(new java.awt.Dimension(64, 25));
        txtEmail.setPrefixIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/mail.png"))); // NOI18N
        jPanel5.add(txtEmail);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGui, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(myTextField6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 306, Short.MAX_VALUE))
                            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(38, 38, 38))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(randomText, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(myTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(randomText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGui, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel4, java.awt.BorderLayout.CENTER);

        background1.add(jPanel2);

        add(background1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void txtSDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSDTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSDTActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.swing.Background background1;
    private qlnh.swing.Button btnGui;
    private javax.swing.JPanel emailPanel;
    private javax.swing.JPanel hoursPanel;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JPanel locationPanel;
    private qlnh.swing.MyTextField myTextField6;
    private javax.swing.JPanel phonePanel;
    private qlnh.swing.MyTextField randomText;
    private qlnh.swing.MyTextField txtEmail;
    private qlnh.swing.MyTextField txtHo;
    private javax.swing.JTextArea txtMess;
    private qlnh.swing.MyTextField txtSDT;
    private qlnh.swing.MyTextField txtTen;
    // End of variables declaration//GEN-END:variables
}
