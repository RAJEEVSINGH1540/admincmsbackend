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

import java.util.Map;

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

    @Value("${app.cms-url}")
    private String cmsUrl;

    @Value("${app.admin.email}")
    private String adminEmail;

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

    // Replace these 2 methods in EmailService.java

    @Async
    public void sendVendorApprovedEmail(String toEmail, String name) {
        String subject = "Colossal - Your Vendor Account Has Been Approved! ✓";

        String html = """
            <!DOCTYPE html>
            <html>
            <head><meta charset="UTF-8"></head>
            <body style="margin:0;padding:0;background-color:#f9fafb;font-family:'Inter','Segoe UI',system-ui,sans-serif;">
                <div style="max-width:560px;margin:40px auto;background:#ffffff;border-radius:16px;overflow:hidden;box-shadow:0 4px 6px rgba(0,0,0,0.07);">
                    
                    <div style="background:linear-gradient(135deg,#240000,#8b0000,#1a0000);padding:32px 40px;text-align:center;">
                        <h1 style="color:#ffffff;font-size:24px;font-weight:700;margin:0 0 4px;">Colossal</h1>
                        <p style="color:rgba(255,255,255,0.7);font-size:13px;margin:0;">Vendor Portal</p>
                    </div>
                    
                    <div style="padding:40px;">
                        <div style="text-align:center;margin-bottom:24px;">
                            <div style="width:64px;height:64px;background:#dcfce7;border-radius:50%%;display:inline-flex;align-items:center;justify-content:center;">
                                <span style="font-size:32px;">✓</span>
                            </div>
                        </div>
                        
                        <p style="color:#111827;font-size:18px;font-weight:700;margin:0 0 8px;text-align:center;">
                            Account Approved!
                        </p>
                        
                        <p style="color:#111827;font-size:15px;font-weight:600;margin:16px 0 8px;">Hello %s,</p>
                        <p style="color:#6b7280;font-size:14px;line-height:1.7;margin:0 0 24px;">
                            Great news! Your vendor account has been reviewed and <strong style="color:#16a34a;">approved</strong> by the Colossal admin team. 
                            You can now log in to access your vendor portal and manage your account.
                        </p>
                        
                        <div style="background:#f0fdf4;border:1px solid #bbf7d0;border-radius:12px;padding:16px;margin:20px 0;">
                            <p style="color:#166534;font-size:13px;margin:0;">
                                ✓ Email Verified<br>
                                ✓ Admin Approved<br>
                                ✓ Account Active — Ready to Login
                            </p>
                        </div>
                        
                        <div style="text-align:center;margin:32px 0;">
                            <a href="%s/vendor" 
                               style="display:inline-block;background:#e11d48;color:#ffffff;text-decoration:none;padding:14px 48px;border-radius:12px;font-size:14px;font-weight:700;letter-spacing:0.5px;">
                                Login to Your Account
                            </a>
                        </div>
                        
                        <p style="color:#9ca3af;font-size:12px;text-align:center;margin:20px 0 0;">
                            If you have any questions, please contact our support team.
                        </p>
                    </div>
                    
                    <div style="background:#f9fafb;padding:20px 40px;text-align:center;border-top:1px solid #f3f4f6;">
                        <p style="color:#9ca3af;font-size:11px;margin:0;">© 2025 Colossal. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(name, frontendUrl);

        sendHtmlEmail(toEmail, subject, html);
    }

    @Async
    public void sendVendorRejectedEmail(String toEmail, String name, String reason) {
        String subject = "Colossal - Vendor Account Verification Update";
        String reasonText = (reason != null && !reason.isBlank()) ? reason : "No specific reason provided.";

        String html = """
            <!DOCTYPE html>
            <html>
            <head><meta charset="UTF-8"></head>
            <body style="margin:0;padding:0;background-color:#f9fafb;font-family:'Inter','Segoe UI',system-ui,sans-serif;">
                <div style="max-width:560px;margin:40px auto;background:#ffffff;border-radius:16px;overflow:hidden;box-shadow:0 4px 6px rgba(0,0,0,0.07);">
                    
                    <div style="background:linear-gradient(135deg,#240000,#8b0000,#1a0000);padding:32px 40px;text-align:center;">
                        <h1 style="color:#ffffff;font-size:24px;font-weight:700;margin:0 0 4px;">Colossal</h1>
                        <p style="color:rgba(255,255,255,0.7);font-size:13px;margin:0;">Vendor Portal</p>
                    </div>
                    
                    <div style="padding:40px;">
                        <div style="text-align:center;margin-bottom:24px;">
                            <div style="width:64px;height:64px;background:#fef2f2;border-radius:50%%;display:inline-flex;align-items:center;justify-content:center;">
                                <span style="font-size:32px;">✗</span>
                            </div>
                        </div>
                        
                        <p style="color:#111827;font-size:18px;font-weight:700;margin:0 0 8px;text-align:center;">
                            Account Not Approved
                        </p>
                        
                        <p style="color:#111827;font-size:15px;font-weight:600;margin:16px 0 8px;">Hello %s,</p>
                        <p style="color:#6b7280;font-size:14px;line-height:1.7;margin:0 0 20px;">
                            We regret to inform you that your vendor account could not be approved by the Colossal admin team at this time.
                        </p>
                        
                        <div style="background:#fef2f2;border:1px solid #fecaca;border-radius:12px;padding:20px;margin:20px 0;">
                            <p style="color:#991b1b;font-size:13px;font-weight:700;margin:0 0 8px;">Reason for Rejection:</p>
                            <p style="color:#b91c1c;font-size:13px;line-height:1.6;margin:0;">%s</p>
                        </div>
                        
                        <p style="color:#6b7280;font-size:14px;line-height:1.7;margin:20px 0;">
                            If you believe this was a mistake or have additional information to provide, 
                            please contact our support team and we'll be happy to assist you.
                        </p>
                        
                        <p style="color:#9ca3af;font-size:12px;text-align:center;margin:24px 0 0;">
                            Thank you for your interest in partnering with Colossal.
                        </p>
                    </div>
                    
                    <div style="background:#f9fafb;padding:20px 40px;text-align:center;border-top:1px solid #f3f4f6;">
                        <p style="color:#9ca3af;font-size:11px;margin:0;">© 2025 Colossal. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(name, reasonText);

        sendHtmlEmail(toEmail, subject, html);
    }

    @Async
    public void sendAdminNewVendorNotification(String vendorName, String vendorEmail, String vendorPhone, String vendorOrganisation) {
        String subject = "Colossal - 🔔 New Vendor Registration - Action Required";

        String html = """
            <!DOCTYPE html>
            <html>
            <head><meta charset="UTF-8"></head>
            <body style="margin:0;padding:0;background-color:#f9fafb;font-family:'Inter','Segoe UI',system-ui,sans-serif;">
                <div style="max-width:560px;margin:40px auto;background:#ffffff;border-radius:16px;overflow:hidden;box-shadow:0 4px 6px rgba(0,0,0,0.07);">
                    
                    <div style="background:linear-gradient(135deg,#240000,#8b0000,#1a0000);padding:32px 40px;text-align:center;">
                        <h1 style="color:#ffffff;font-size:24px;font-weight:700;margin:0 0 4px;">Colossal</h1>
                        <p style="color:rgba(255,255,255,0.7);font-size:13px;margin:0;">Admin Panel</p>
                    </div>
                    
                    <div style="padding:40px;">
                        <p style="color:#111827;font-size:16px;font-weight:700;margin:0 0 16px;">
                            🔔 New Vendor Registration
                        </p>
                        <p style="color:#6b7280;font-size:14px;line-height:1.7;margin:0 0 20px;">
                            A new vendor has registered on the Colossal Vendor Portal and requires your verification.
                        </p>
                        
                        <div style="background:#f8fafc;border:1px solid #e2e8f0;border-radius:12px;padding:20px;margin:20px 0;">
                            <table style="width:100%%;border-collapse:collapse;">
                                <tr>
                                    <td style="padding:8px 0;color:#64748b;font-size:13px;font-weight:600;width:120px;">Name</td>
                                    <td style="padding:8px 0;color:#1e293b;font-size:14px;font-weight:600;">%s</td>
                                </tr>
                                <tr>
                                    <td style="padding:8px 0;color:#64748b;font-size:13px;font-weight:600;">Email</td>
                                    <td style="padding:8px 0;color:#1e293b;font-size:14px;">%s</td>
                                </tr>
                                <tr>
                                    <td style="padding:8px 0;color:#64748b;font-size:13px;font-weight:600;">Phone</td>
                                    <td style="padding:8px 0;color:#1e293b;font-size:14px;">%s</td>
                                </tr>
                                <tr>
                                    <td style="padding:8px 0;color:#64748b;font-size:13px;font-weight:600;">Organisation</td>
                                    <td style="padding:8px 0;color:#1e293b;font-size:14px;">%s</td>
                                </tr>
                            </table>
                        </div>
                        
                        <p style="color:#6b7280;font-size:14px;line-height:1.7;margin:20px 0;">
                            Please log in to the Colossal CMS Admin Panel to review and approve or reject this vendor.
                        </p>
                        
                        <div style="text-align:center;margin:28px 0;">
                            <a href="%s" 
                               style="display:inline-block;background:#e11d48;color:#ffffff;text-decoration:none;padding:14px 40px;border-radius:12px;font-size:14px;font-weight:600;">
                                Open Admin Panel
                            </a>
                        </div>
                    </div>
                    
                    <div style="background:#f9fafb;padding:20px 40px;text-align:center;border-top:1px solid #f3f4f6;">
                        <p style="color:#9ca3af;font-size:11px;margin:0;">© 2025 Colossal. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(vendorName, vendorEmail, vendorPhone != null ? vendorPhone : "N/A", vendorOrganisation != null ? vendorOrganisation : "N/A", cmsUrl);

        sendHtmlEmail(adminEmail, subject, html);
    }

    @Async
    public void sendVendorRegistrationConfirmation(String toEmail, String name) {
        String subject = "Colossal - Registration Received Successfully";

        String html = """
            <!DOCTYPE html>
            <html>
            <head><meta charset="UTF-8"></head>
            <body style="margin:0;padding:0;background-color:#f9fafb;font-family:'Inter','Segoe UI',system-ui,sans-serif;">
                <div style="max-width:560px;margin:40px auto;background:#ffffff;border-radius:16px;overflow:hidden;box-shadow:0 4px 6px rgba(0,0,0,0.07);">
                    
                    <div style="background:linear-gradient(135deg,#240000,#8b0000,#1a0000);padding:32px 40px;text-align:center;">
                        <h1 style="color:#ffffff;font-size:24px;font-weight:700;margin:0 0 4px;">Colossal</h1>
                        <p style="color:rgba(255,255,255,0.7);font-size:13px;margin:0;">Vendor Portal</p>
                    </div>
                    
                    <div style="padding:40px;">
                        <p style="color:#111827;font-size:16px;font-weight:600;margin:0 0 8px;">Hello %s,</p>
                        <p style="color:#6b7280;font-size:14px;line-height:1.7;margin:0 0 20px;">
                            Thank you for registering as a vendor/supplier with <strong>Colossal</strong>. 
                            Your registration has been received successfully.
                        </p>
                        
                        <div style="background:#fffbeb;border:1px solid #fde68a;border-radius:12px;padding:20px;margin:20px 0;">
                            <p style="color:#92400e;font-size:14px;font-weight:600;margin:0 0 8px;">⏳ What happens next?</p>
                            <ol style="color:#78350f;font-size:13px;line-height:1.8;margin:0;padding-left:20px;">
                                <li>First, please verify your email by clicking the link we sent separately.</li>
                                <li>The Colossal admin team will review your registration details.</li>
                                <li>Once verified, you'll receive a confirmation email from us.</li>
                                <li>After approval, you can log in to your vendor account.</li>
                            </ol>
                        </div>
                        
                        <p style="color:#6b7280;font-size:13px;line-height:1.6;margin:20px 0 0;">
                            You will be notified via email about the status of your verification. 
                            Please keep an eye on your inbox (and spam folder).
                        </p>
                    </div>
                    
                    <div style="background:#f9fafb;padding:20px 40px;text-align:center;border-top:1px solid #f3f4f6;">
                        <p style="color:#9ca3af;font-size:11px;margin:0;">© 2025 Colossal. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(name);

        sendHtmlEmail(toEmail, subject, html);
    }

// ==================== CONTACT FORM EMAILS ====================

@Async
public void sendContactThankYouEmail(String toEmail, String userName, String topic, String message) {
    String subject = "Colossal - Thank You for Contacting Us!";

    String html = """
        <!DOCTYPE html>
        <html>
        <head><meta charset="UTF-8"></head>
        <body style="margin:0;padding:0;background-color:#f9fafb;font-family:'Inter','Segoe UI',system-ui,sans-serif;">
            <div style="max-width:560px;margin:40px auto;background:#ffffff;border-radius:16px;overflow:hidden;box-shadow:0 4px 6px rgba(0,0,0,0.07);">
                
                <div style="background:linear-gradient(135deg,#240000,#8b0000,#1a0000);padding:32px 40px;text-align:center;">
                    <h1 style="color:#ffffff;font-size:24px;font-weight:700;margin:0 0 4px;">Colossal</h1>
                    <p style="color:rgba(255,255,255,0.7);font-size:13px;margin:0;">Contact Us</p>
                </div>
                
                <div style="padding:40px;">
                    <div style="text-align:center;margin-bottom:24px;">
                        <div style="width:64px;height:64px;background:#dcfce7;border-radius:50%%;display:inline-flex;align-items:center;justify-content:center;">
                            <span style="font-size:32px;">✉</span>
                        </div>
                    </div>
                    
                    <p style="color:#111827;font-size:18px;font-weight:700;margin:0 0 8px;text-align:center;">
                        Thank You for Reaching Out!
                    </p>
                    
                    <p style="color:#111827;font-size:15px;font-weight:600;margin:16px 0 8px;">Hello %s,</p>
                    <p style="color:#6b7280;font-size:14px;line-height:1.7;margin:0 0 24px;">
                        We have received your message and truly appreciate you taking the time to contact us. 
                        Our team will review your inquiry and get back to you as soon as possible.
                    </p>
                    
                    <div style="background:#f8fafc;border:1px solid #e2e8f0;border-radius:12px;padding:20px;margin:20px 0;">
                        <p style="color:#64748b;font-size:13px;font-weight:700;margin:0 0 12px;text-transform:uppercase;letter-spacing:0.5px;">
                            Your Submission Summary
                        </p>
                        <table style="width:100%%;border-collapse:collapse;">
                            <tr>
                                <td style="padding:8px 0;color:#64748b;font-size:13px;font-weight:600;width:100px;vertical-align:top;">Topic</td>
                                <td style="padding:8px 0;color:#1e293b;font-size:14px;">%s</td>
                            </tr>
                            <tr>
                                <td style="padding:8px 0;color:#64748b;font-size:13px;font-weight:600;vertical-align:top;">Message</td>
                                <td style="padding:8px 0;color:#1e293b;font-size:14px;line-height:1.6;">%s</td>
                            </tr>
                        </table>
                    </div>
                    
                    <div style="background:#eff6ff;border:1px solid #bfdbfe;border-radius:12px;padding:16px;margin:20px 0;">
                        <p style="color:#1e40af;font-size:13px;margin:0;line-height:1.6;">
                            💡 <strong>What happens next?</strong><br>
                            Our team typically responds within 24-48 business hours. 
                            If your matter is urgent, please don't hesitate to call us directly.
                        </p>
                    </div>
                    
                    <p style="color:#9ca3af;font-size:12px;text-align:center;margin:24px 0 0;">
                        This is an automated confirmation. Please do not reply to this email.
                    </p>
                </div>
                
                <div style="background:#f9fafb;padding:20px 40px;text-align:center;border-top:1px solid #f3f4f6;">
                    <p style="color:#9ca3af;font-size:11px;margin:0;">© 2025 Colossal. All rights reserved.</p>
                </div>
            </div>
        </body>
        </html>
        """.formatted(
            userName != null ? userName : "Valued Customer",
            topic != null && !topic.isBlank() ? topic : "General Inquiry",
            message != null && !message.isBlank() ? message : "N/A"
    );

    sendHtmlEmail(toEmail, subject, html);
}

@Async
public void sendAdminContactFormNotification( String userName, String userEmail, String topic, String message, Map<String, String> additionalFields) {
        String subject = "Colossal - 📩 New Contact Form Submission";

        StringBuilder additionalFieldsHtml = new StringBuilder();
        if (additionalFields != null && !additionalFields.isEmpty()) {
            for (Map.Entry<String, String> entry : additionalFields.entrySet()) {
                additionalFieldsHtml.append(String.format("""
                <tr>
                    <td style="padding:8px 0;color:#64748b;font-size:13px;font-weight:600;width:120px;vertical-align:top;text-transform:capitalize;">%s</td>
                    <td style="padding:8px 0;color:#1e293b;font-size:14px;">%s</td>
                </tr>
                """, entry.getKey().replace("_", " "), entry.getValue()));
            }
        }

        String formattedTime = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' hh:mm a"));

        String html = """
        <!DOCTYPE html>
        <html>
        <head><meta charset="UTF-8"></head>
        <body style="margin:0;padding:0;background-color:#f9fafb;font-family:'Inter','Segoe UI',system-ui,sans-serif;">
            <div style="max-width:560px;margin:40px auto;background:#ffffff;border-radius:16px;overflow:hidden;box-shadow:0 4px 6px rgba(0,0,0,0.07);">
                
                <div style="background:linear-gradient(135deg,#240000,#8b0000,#1a0000);padding:32px 40px;text-align:center;">
                    <h1 style="color:#ffffff;font-size:24px;font-weight:700;margin:0 0 4px;">Colossal</h1>
                    <p style="color:rgba(255,255,255,0.7);font-size:13px;margin:0;">Admin Panel</p>
                </div>
                
                <div style="padding:40px;">
                    <p style="color:#111827;font-size:16px;font-weight:700;margin:0 0 16px;">
                        📩 New Contact Form Submission
                    </p>
                    <p style="color:#6b7280;font-size:14px;line-height:1.7;margin:0 0 20px;">
                        A visitor has submitted the contact form on the Colossal website. Here are the details:
                    </p>
                    
                    <div style="background:#f8fafc;border:1px solid #e2e8f0;border-radius:12px;padding:20px;margin:20px 0;">
                        <p style="color:#64748b;font-size:13px;font-weight:700;margin:0 0 12px;text-transform:uppercase;letter-spacing:0.5px;">
                            Contact Details
                        </p>
                        <table style="width:100%%;border-collapse:collapse;">
                            <tr>
                                <td style="padding:8px 0;color:#64748b;font-size:13px;font-weight:600;width:120px;">Name</td>
                                <td style="padding:8px 0;color:#1e293b;font-size:14px;font-weight:600;">%s</td>
                            </tr>
                            <tr>
                                <td style="padding:8px 0;color:#64748b;font-size:13px;font-weight:600;">Email</td>
                                <td style="padding:8px 0;color:#1e293b;font-size:14px;">
                                    <a href="mailto:%s" style="color:#e11d48;text-decoration:none;">%s</a>
                                </td>
                            </tr>
                            <tr>
                                <td style="padding:8px 0;color:#64748b;font-size:13px;font-weight:600;">Topic</td>
                                <td style="padding:8px 0;color:#1e293b;font-size:14px;">%s</td>
                            </tr>
                            %s
                        </table>
                    </div>
                    
                    <div style="background:#fefce8;border:1px solid #fde68a;border-radius:12px;padding:20px;margin:20px 0;">
                        <p style="color:#92400e;font-size:13px;font-weight:700;margin:0 0 8px;">💬 Message:</p>
                        <p style="color:#78350f;font-size:14px;line-height:1.7;margin:0;white-space:pre-wrap;">%s</p>
                    </div>
                    
                    <div style="text-align:center;margin:28px 0;">
                        <a href="mailto:%s?subject=Re: %s" 
                           style="display:inline-block;background:#e11d48;color:#ffffff;text-decoration:none;padding:14px 40px;border-radius:12px;font-size:14px;font-weight:600;">
                            Reply to %s
                        </a>
                    </div>
                    
                    <div style="text-align:center;margin:16px 0;">
                        <a href="%s" 
                           style="display:inline-block;background:#f1f5f9;color:#475569;text-decoration:none;padding:12px 32px;border-radius:12px;font-size:13px;font-weight:600;border:1px solid #e2e8f0;">
                            Open Admin Panel
                        </a>
                    </div>
                    
                    <p style="color:#9ca3af;font-size:12px;text-align:center;margin:20px 0 0;">
                        Submitted on: %s
                    </p>
                </div>
                
                <div style="background:#f9fafb;padding:20px 40px;text-align:center;border-top:1px solid #f3f4f6;">
                    <p style="color:#9ca3af;font-size:11px;margin:0;">© 2025 Colossal. All rights reserved.</p>
                </div>
            </div>
        </body>
        </html>
        """.formatted(
                userName != null ? userName : "Unknown",
                userEmail,
                userEmail,
                topic != null && !topic.isBlank() ? topic : "General Inquiry",
                additionalFieldsHtml.toString(),
                message != null && !message.isBlank() ? message : "No message provided",
                userEmail,
                topic != null && !topic.isBlank() ? topic : "Contact Form Inquiry",
                userName != null ? userName : "User",
                cmsUrl,
                formattedTime
        );

        sendHtmlEmail(adminEmail, subject, html);
    }

}