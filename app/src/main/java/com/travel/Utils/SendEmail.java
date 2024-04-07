package com.travel.Utils;

import android.os.AsyncTask;

import com.travel.Activity.SignUpActivity;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
public class SendEmail extends AsyncTask<Void, Void, Boolean> {
    /*public boolean sendOTP(String email, int code) {
        final String senderEmail = "travelapp99@gmail.com";
        final String senderPassword = "ercueztwtihqhykk";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Xác thực email từ ứng dụng TravelApp");
            message.setText("Mã OTP xác nhận của bạn là: " + code + ". Mã có thời hạn trong vòng 5 phút từ lúc nhận email này. Vui lòng xác nhận ngay để hoàn tất quá trình đăng ký tài khoản. Xin cảm ơn!");

            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }*/
    private String email;
    private int code;

    public SendEmail(String email, int code) {
        this.email = email;
        this.code = code;
    }

    public SendEmail() {

    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        final String senderEmail = "travelapp99@gmail.com";
        final String senderPassword = "ercueztwtihqhykk";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Xác thực email từ ứng dụng TravelApp");
            message.setText("Mã OTP xác nhận của bạn là: " + code + ". Mã có thời hạn trong vòng 5 phút từ lúc nhận email này. Vui lòng xác nhận ngay để hoàn tất quá trình đăng ký tài khoản. Xin cảm ơn!");

            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
