package SFG.Street_Food_Go.Port;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {this.mailSender = mailSender;}

    public void sendEmail(String to, String username) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Welcome To Street Food Go, We Are So happy You Are Here");
        message.setText("Hello "+username + " You Successfully Registered To Street Food Go");
        mailSender.send(message);
    }
}
