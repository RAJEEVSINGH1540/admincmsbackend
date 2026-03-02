package admin.panel.service;

import admin.panel.dto.vendorsuplier.*;
import admin.panel.entity.vendorsuplier.VendorRole;
import admin.panel.entity.vendorsuplier.VendorUser;
import admin.panel.repository.vendorsuplier.VendorUserRepository;
import admin.panel.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class VendorAuthService {

    private final VendorUserRepository vendorUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;

    @Value("${app.email.verification-token-expiration:86400000}")
    private long verificationTokenExpiration;

    // ==================== REGISTER ====================
    @Transactional
    public VendorAuthResponse register(VendorRegisterRequest request) {
        // Validate passwords match
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        // Check if email already exists
        if (vendorUserRepository.existsByEmail(request.getEmail().toLowerCase().trim())) {
            throw new RuntimeException("Email already registered. Please login or use a different email.");
        }

        // Generate verification token
        String verificationToken = UUID.randomUUID().toString();

        // Create vendor user with hashed password
        VendorUser user = VendorUser.builder()
                .name(request.getName().trim())
                .organisation(request.getOrganisation())
                .designation(request.getDesignation())
                .email(request.getEmail().toLowerCase().trim())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .licenseNumber(request.getLicenseNumber())
                .additionalFields(request.getAdditionalFields() != null ? request.getAdditionalFields() : new HashMap<>())
                .isEmailVerified(false)
                .emailVerificationToken(verificationToken)
                .emailVerificationTokenExpiry(LocalDateTime.now().plusHours(24))
                .role(VendorRole.VENDOR)
                .isActive(true)
                .build();

        VendorUser saved = vendorUserRepository.save(user);

        // Send verification email
        emailService.sendVerificationEmail(saved.getEmail(), saved.getName(), verificationToken);

        log.info("Vendor registered: {} ({})", saved.getName(), saved.getEmail());

        return buildAuthResponse(saved, false);
    }

    // ==================== VERIFY EMAIL ====================
    @Transactional
    public String verifyEmail(String token) {
        VendorUser user = vendorUserRepository.findByEmailVerificationToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid verification token"));

        if (user.getIsEmailVerified()) {
            return "Email already verified. You can login now.";
        }

        if (user.getEmailVerificationTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Verification token has expired. Please request a new one.");
        }

        user.setIsEmailVerified(true);
        user.setEmailVerificationToken(null);
        user.setEmailVerificationTokenExpiry(null);
        vendorUserRepository.save(user);

        // Send welcome email
        emailService.sendWelcomeEmail(user.getEmail(), user.getName());

        log.info("Email verified for: {}", user.getEmail());
        return "Email verified successfully! You can now login.";
    }

    // ==================== RESEND VERIFICATION ====================
    @Transactional
    public void resendVerification(String email) {
        VendorUser user = vendorUserRepository.findByEmail(email.toLowerCase().trim())
                .orElseThrow(() -> new RuntimeException("No account found with this email"));

        if (user.getIsEmailVerified()) {
            throw new RuntimeException("Email is already verified");
        }

        String newToken = UUID.randomUUID().toString();
        user.setEmailVerificationToken(newToken);
        user.setEmailVerificationTokenExpiry(LocalDateTime.now().plusHours(24));
        vendorUserRepository.save(user);

        emailService.sendVerificationEmail(user.getEmail(), user.getName(), newToken);
        log.info("Verification email resent to: {}", user.getEmail());
    }

    // ==================== LOGIN ====================
    @Transactional
    public VendorAuthResponse login(VendorLoginRequest request) {
        VendorUser user = vendorUserRepository.findByEmail(request.getEmail().toLowerCase().trim())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // Check password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        // Check if active
        if (!user.getIsActive()) {
            throw new RuntimeException("Your account has been deactivated. Please contact support.");
        }

        // Check email verification
        if (!user.getIsEmailVerified()) {
            throw new RuntimeException("Please verify your email before logging in. Check your inbox.");
        }

        // Generate tokens
        String accessToken = jwtUtil.generateAccessToken(
                user.getEmail(), user.getId(), user.getRole().name()
        );
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail(), user.getId());

        // Save refresh token
        user.setRefreshToken(refreshToken);
        user.setLastLoginAt(LocalDateTime.now());
        vendorUserRepository.save(user);

        log.info("Vendor logged in: {}", user.getEmail());

        return buildAuthResponse(user, true, accessToken, refreshToken);
    }

    // ==================== REFRESH TOKEN ====================
    @Transactional
    public VendorAuthResponse refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        if (!jwtUtil.isTokenValid(refreshToken)) {
            throw new RuntimeException("Invalid or expired refresh token");
        }

        if (!"refresh".equals(jwtUtil.extractTokenType(refreshToken))) {
            throw new RuntimeException("Invalid token type");
        }

        String email = jwtUtil.extractEmail(refreshToken);

        VendorUser user = vendorUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Verify stored refresh token matches
        if (!refreshToken.equals(user.getRefreshToken())) {
            throw new RuntimeException("Refresh token mismatch. Please login again.");
        }

        if (!user.getIsActive()) {
            throw new RuntimeException("Account deactivated");
        }

        // Generate new tokens
        String newAccessToken = jwtUtil.generateAccessToken(
                user.getEmail(), user.getId(), user.getRole().name()
        );
        String newRefreshToken = jwtUtil.generateRefreshToken(user.getEmail(), user.getId());

        user.setRefreshToken(newRefreshToken);
        vendorUserRepository.save(user);

        return buildAuthResponse(user, true, newAccessToken, newRefreshToken);
    }

    // ==================== FORGOT PASSWORD ====================
    @Transactional
    public void forgotPassword(ForgotPasswordRequest request) {
        VendorUser user = vendorUserRepository.findByEmail(request.getEmail().toLowerCase().trim())
                .orElseThrow(() -> new RuntimeException("No account found with this email"));

        String resetToken = UUID.randomUUID().toString();
        user.setPasswordResetToken(resetToken);
        user.setPasswordResetTokenExpiry(LocalDateTime.now().plusHours(24));
        vendorUserRepository.save(user);

        emailService.sendPasswordResetEmail(user.getEmail(), user.getName(), resetToken);
        log.info("Password reset email sent to: {}", user.getEmail());
    }

    // ==================== RESET PASSWORD ====================
    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        VendorUser user = vendorUserRepository.findByPasswordResetToken(request.getToken())
                .orElseThrow(() -> new RuntimeException("Invalid reset token"));

        if (user.getPasswordResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Reset token has expired. Please request a new one.");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setPasswordResetToken(null);
        user.setPasswordResetTokenExpiry(null);
        user.setRefreshToken(null); // Invalidate all sessions
        vendorUserRepository.save(user);

        log.info("Password reset for: {}", user.getEmail());
    }

    // ==================== CHANGE PASSWORD ====================
    @Transactional
    public void changePassword(Long userId, ChangePasswordRequest request) {
        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            throw new RuntimeException("New passwords do not match");
        }

        VendorUser user = vendorUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setRefreshToken(null); // Invalidate sessions
        vendorUserRepository.save(user);

        log.info("Password changed for: {}", user.getEmail());
    }

    // ==================== LOGOUT ====================
    @Transactional
    public void logout(Long userId) {
        VendorUser user = vendorUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRefreshToken(null);
        vendorUserRepository.save(user);
        log.info("Vendor logged out: {}", user.getEmail());
    }

    // ==================== GET PROFILE ====================
    @Transactional(readOnly = true)
    public VendorProfileResponse getProfile(Long userId) {
        VendorUser user = vendorUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return VendorProfileResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .organisation(user.getOrganisation())
                .designation(user.getDesignation())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .licenseNumber(user.getLicenseNumber())
                .role(user.getRole().name())
                .isEmailVerified(user.getIsEmailVerified())
                .isActive(user.getIsActive())
                .additionalFields(user.getAdditionalFields())
                .createdAt(user.getCreatedAt())
                .lastLoginAt(user.getLastLoginAt())
                .build();
    }

    // ==================== HELPERS ====================
    private VendorAuthResponse buildAuthResponse(VendorUser user, boolean includeTokens) {
        return buildAuthResponse(user, includeTokens, null, null);
    }

    private VendorAuthResponse buildAuthResponse(
            VendorUser user, boolean includeTokens,
            String accessToken, String refreshToken
    ) {
        return VendorAuthResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .organisation(user.getOrganisation())
                .designation(user.getDesignation())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .licenseNumber(user.getLicenseNumber())
                .role(user.getRole().name())
                .isEmailVerified(user.getIsEmailVerified())
                .isActive(user.getIsActive())
                .accessToken(includeTokens ? accessToken : null)
                .refreshToken(includeTokens ? refreshToken : null)
                .tokenType(includeTokens ? "Bearer" : null)
                .expiresIn(includeTokens ? jwtUtil.getAccessTokenExpiration() : null)
                .additionalFields(user.getAdditionalFields())
                .build();
    }
}