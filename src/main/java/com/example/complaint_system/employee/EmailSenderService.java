package com.example.complaint_system.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String toEmail,String sub,String body){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("hamza.sa.khalil@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(sub);

        mailSender.send(message);
        System.out.println("Mail Sent Successfully");
    }
}
