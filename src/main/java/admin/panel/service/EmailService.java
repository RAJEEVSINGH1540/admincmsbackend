package admin.panel.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.email.from}")
    private String fromEmail;

    @Value("${app.frontend-url}")
    private String frontendUrl;

    @Value("${app.base-url}")
    private String baseUrl;

    @Async
    public void sendVerificationEmail(String toEmail, String name, String token) {
        String verifyUrl = baseUrl + "/api/vendor/auth/verify-email?token=" + token;
        String subject = "Verify Your Email - Vendor Registration";

        String html = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
            </head>
            <body style="margin:0;padding:0;background-color:#f9fafb;font-family:'Inter','Segoe UI',system-ui,sans-serif;">
                <div style="max-width:560px;margin:40px auto;background:#ffffff;border-radius:16px;overflow:hidden;box-shadow:0 4px 6px rgba(0,0,0,0.07);">
                    
                    <!-- Header -->
                    <div style="background:#e11d48;padding:32px 40px;text-align:center;">
                        <h1 style="color:#ffffff;font-size:22px;font-weight:700;margin:0;">Email Verification</h1>
                    </div>
                    
                    <!-- Body -->
                    <div style="padding:40px;">
                        <p style="color:#111827;font-size:16px;font-weight:600;margin:0 0 8px;">Hello %s,</p>
                        <p style="color:#6b7280;font-size:14px;line-height:1.7;margin:0 0 28px;">
                            Thank you for registering as a vendor/supplier. Please verify your email address by clicking the button below.
                        </p>
                        
                        <!-- Button -->
                        <div style="text-align:center;margin:32px 0;">
                            <a href="%s" 
                               style="display:inline-block;background:#e11d48;color:#ffffff;text-decoration:none;padding:14px 40px;border-radius:12px;font-size:14px;font-weight:600;letter-spacing:0.5px;">
                                Verify Email Address
                            </a>
                        </div>
                        
                        <p style="color:#9ca3af;font-size:12px;line-height:1.6;margin:24px 0 0;">
                            If the button doesn't work, copy and paste this link:<br>
                            <a href="%s" style="color:#e11d48;word-break:break-all;">%s</a>
                        </p>
                        
                        <p style="color:#9ca3af;font-size:12px;margin:20px 0 0;">
                            This link expires in 24 hours. If you didn't create this account, you can safely ignore this email.
                        </p>
                    </div>
                    
                    <!-- Footer -->
                    <div style="background:#f9fafb;padding:20px 40px;text-align:center;border-top:1px solid #f3f4f6;">
                        <p style="color:#9ca3af;font-size:11px;margin:0;">© 2025 Admin Panel. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(name, verifyUrl, verifyUrl, verifyUrl);

        sendHtmlEmail(toEmail, subject, html);
    }

    @Async
    public void sendPasswordResetEmail(String toEmail, String name, String token) {
        String resetUrl = frontendUrl + "/vendor/reset-password?token=" + token;
        String subject = "Reset Your Password";

        String html = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
            </head>
            <body style="margin:0;padding:0;background-color:#f9fafb;font-family:'Inter','Segoe UI',system-ui,sans-serif;">
                <div style="max-width:560px;margin:40px auto;background:#ffffff;border-radius:16px;overflow:hidden;box-shadow:0 4px 6px rgba(0,0,0,0.07);">
                    
                    <div style="background:#e11d48;padding:32px 40px;text-align:center;">
                        <h1 style="color:#ffffff;font-size:22px;font-weight:700;margin:0;">Password Reset</h1>
                    </div>
                    
                    <div style="padding:40px;">
                        <p style="color:#111827;font-size:16px;font-weight:600;margin:0 0 8px;">Hello %s,</p>
                        <p style="color:#6b7280;font-size:14px;line-height:1.7;margin:0 0 28px;">
                            We received a request to reset your password. Click the button below to create a new password.
                        </p>
                        
                        <div style="text-align:center;margin:32px 0;">
                            <a href="%s" 
                               style="display:inline-block;background:#e11d48;color:#ffffff;text-decoration:none;padding:14px 40px;border-radius:12px;font-size:14px;font-weight:600;">
                                Reset Password
                            </a>
                        </div>
                        
                        <p style="color:#9ca3af;font-size:12px;line-height:1.6;margin:24px 0 0;">
                            If you didn't request this, you can safely ignore this email. This link expires in 24 hours.
                        </p>
                    </div>
                    
                    <div style="background:#f9fafb;padding:20px 40px;text-align:center;border-top:1px solid #f3f4f6;">
                        <p style="color:#9ca3af;font-size:11px;margin:0;">© 2025 Admin Panel</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(name, resetUrl);

        sendHtmlEmail(toEmail, subject, html);
    }

    @Async
    public void sendWelcomeEmail(String toEmail, String name) {
        String subject = "Welcome - Email Verified Successfully!";

        String html = """
            <!DOCTYPE html>
            <html>
            <head><meta charset="UTF-8"></head>
            <body style="margin:0;padding:0;background-color:#f9fafb;font-family:'Inter','Segoe UI',system-ui,sans-serif;">
                <div style="max-width:560px;margin:40px auto;background:#ffffff;border-radius:16px;overflow:hidden;box-shadow:0 4px 6px rgba(0,0,0,0.07);">
                    
                    <div style="background:#16a34a;padding:32px 40px;text-align:center;">
                        <h1 style="color:#ffffff;font-size:22px;font-weight:700;margin:0;">✓ Email Verified</h1>
                    </div>
                    
                    <div style="padding:40px;text-align:center;">
                        <p style="color:#111827;font-size:16px;font-weight:600;margin:0 0 8px;">Welcome, %s!</p>
                        <p style="color:#6b7280;font-size:14px;line-height:1.7;margin:0 0 28px;">
                            Your email has been verified successfully. You can now log in to your vendor account.
                        </p>
                        
                        <div style="text-align:center;margin:32px 0;">
                            <a href="%s/vendor/login" 
                               style="display:inline-block;background:#e11d48;color:#ffffff;text-decoration:none;padding:14px 40px;border-radius:12px;font-size:14px;font-weight:600;">
                                Login Now
                            </a>
                        </div>
                    </div>
                    
                    <div style="background:#f9fafb;padding:20px 40px;text-align:center;border-top:1px solid #f3f4f6;">
                        <p style="color:#9ca3af;font-size:11px;margin:0;">© 2025 Admin Panel</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(name, frontendUrl);

        sendHtmlEmail(toEmail, subject, html);
    }

    private void sendHtmlEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            mailSender.send(message);
            log.info("Email sent to: {}", to);
        } catch (MessagingException e) {
            log.error("Failed to send email to {}: {}", to, e.getMessage());
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }
}