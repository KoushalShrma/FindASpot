package com.findaspot.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.security.SecureRandom;

/**
 * Email Service - yahan par email aur OTP related operations hain
 */
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${findaspot.mail.from}")
    private String fromEmail;

    private final SecureRandom random = new SecureRandom();

    // yahan par OTP generate karne ka method hai
    public String generateOtp() {
        int otp = 1000 + random.nextInt(9000); // 4 digit OTP
        return String.valueOf(otp);
    }

    // yahan par OTP email bhejne ka method hai
    public void sendOtpEmail(String toEmail, String name, String otp) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(fromEmail);
        helper.setTo(toEmail);
        helper.setSubject("Email Verification - Find-A-Spot");

        // yahan par email template hai
        String htmlContent = createOtpEmailTemplate(name, otp);
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }

    // yahan par welcome email bhejne ka method hai
    public void sendWelcomeEmail(String toEmail, String name) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(fromEmail);
        helper.setTo(toEmail);
        helper.setSubject("Welcome to Find-A-Spot!");

        String htmlContent = createWelcomeEmailTemplate(name);
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }

    // yahan par OTP email template hai
    private String createOtpEmailTemplate(String name, String otp) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Email Verification - Find-A-Spot</title>
            </head>
            <body style="margin: 0; padding: 0; font-family: Arial, sans-serif; background-color: #f5f5f5;">
                <div style="max-width: 600px; margin: 20px auto; background-color: white; border-radius: 10px; overflow: hidden; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);">
                    <div style="background: linear-gradient(90deg, #175d69, #1d4ed8); padding: 20px; text-align: center; color: white;">
                        <h1 style="margin: 0;">Find-A-Spot</h1>
                        <p style="margin-top: 5px;">Smart Parking, Simplified</p>
                    </div>
                    <div style="padding: 30px; text-align: center;">
                        <h2 style="color: #333;">Verify Your Email</h2>
                        <p style="font-size: 16px; color: #555;">Hi <strong>""" + name + """
            </strong>,</p>
                        <p style="font-size: 15px; color: #666;">Thank you for registering with <strong>Find-A-Spot</strong>.</p>
                        <p style="font-size: 16px; margin: 20px 0; font-weight: bold;">Your One-Time Password (OTP) is:</p>
                        <div style="font-size: 28px; font-weight: bold; background-color: #f0f8ff; padding: 12px 24px; display: inline-block; border-radius: 8px; color: #1d4ed8; letter-spacing: 4px;">
                            """ + otp + """
                        </div>
                        <p style="font-size: 14px; color: #999; margin-top: 30px;">This OTP is valid for 10 minutes. Do not share it with anyone.</p>
                    </div>
                    <div style="background-color: #fafafa; text-align: center; padding: 15px; font-size: 13px; color: #777;">
                        &copy; 2025 Find-A-Spot. All rights reserved.
                    </div>
                </div>
            </body>
            </html>
            """;
    }

    // yahan par welcome email template hai
    private String createWelcomeEmailTemplate(String name) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Welcome to Find-A-Spot</title>
            </head>
            <body style="margin: 0; padding: 0; font-family: Arial, sans-serif; background-color: #f5f5f5;">
                <div style="max-width: 600px; margin: 20px auto; background-color: white; border-radius: 10px; overflow: hidden; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);">
                    <div style="background: linear-gradient(90deg, #175d69, #1d4ed8); padding: 20px; text-align: center; color: white;">
                        <h1 style="margin: 0;">Welcome to Find-A-Spot!</h1>
                        <p style="margin-top: 5px;">Smart Parking, Simplified</p>
                    </div>
                    <div style="padding: 30px; text-align: center;">
                        <h2 style="color: #333;">Welcome, """ + name + """
            !</h2>
                        <p style="font-size: 16px; color: #555;">Your account has been successfully verified.</p>
                        <p style="font-size: 15px; color: #666;">You can now enjoy hassle-free parking with Find-A-Spot.</p>
                        <div style="margin: 30px 0;">
                            <a href="#" style="background-color: #1d4ed8; color: white; padding: 12px 24px; text-decoration: none; border-radius: 5px; font-weight: bold;">Start Parking</a>
                        </div>
                    </div>
                    <div style="background-color: #fafafa; text-align: center; padding: 15px; font-size: 13px; color: #777;">
                        &copy; 2025 Find-A-Spot. All rights reserved.
                    </div>
                </div>
            </body>
            </html>
            """;
    }
}