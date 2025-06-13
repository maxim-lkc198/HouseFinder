/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import service.EmailService;
import java.util.Properties;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailServiceImpl implements EmailService {

    private static final String SMTP_HOST = "localhost";
    private static final String SMTP_PORT = "1025";
    private static final String FROM_EMAIL = "no-reply@housefinder.com";

    @Override
    public void sendEmail(String to, String subject, String htmlBody) {
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.auth", "false");
        props.put("mail.smtp.starttls.enable", "false");

        Session session = Session.getInstance(props);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject); 
            message.setContent(htmlBody, "text/html; charset=utf-8");

            Transport.send(message);

            System.out.println("EmailServiceImpl: Email sent successfully to MailHog for: " + to);

        } catch (MessagingException e) {
            System.err.println("EmailServiceImpl: Error sending email via MailHog.");
            e.printStackTrace();
            throw new RuntimeException("Failed to send email", e);
        }
    }
}