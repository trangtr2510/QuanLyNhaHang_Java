package com.raven.service;


import com.raven.model.ModelMessage;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ServiceMail {
    
    /*
        Gửi mã xác minh tài khoản tới người dùng 
        đăng ký tài khoản tại nhà hàng 
        Trả về Model thông báo 
    */
    public ModelMessage sendMain(String toEmail, String code) {
        ModelMessage ms = new ModelMessage(false, "");
         //Email người gửi
        String from = "changcherchou@gmail.com";
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        String username = "changcherchou@gmail.com";//Email nhà hàng
        //Mật khẩu :changcherchou@gmail.com
        //App password :rlfgxqlwoynicsqz
        String password = "rlfgxqlwoynicsqz";    // Mật khẩu app email
        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(username, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("Verify Code");
            message.setText(code + " là mã xác minh tài khoản của bạn.");
            Transport.send(message);
            ms.setSuccess(true);
        } catch (MessagingException e) {
            if (e.getMessage().equals("Invalid Addresses")) {
                ms.setMessage("Email không chính xác");
            } else {
                ms.setMessage("Lỗi");
                e.printStackTrace();
            }
        }
        return ms;
    }
}
