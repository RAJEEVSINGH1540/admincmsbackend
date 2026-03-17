package admin.panel.controller;

import admin.panel.dto.ApiResponse;
import admin.panel.dto.vendor.*;
import admin.panel.entity.vendor.VendorUser;
import admin.panel.service.VendorAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/vendor/auth")
@RequiredArgsConstructor
public class VendorAuthController {

    private final VendorAuthService authService;

    @Value("${app.frontend-url}")
    private String frontendUrl;

    // ==================== REGISTER ====================
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<VendorAuthResponse>> register(
            @Valid @RequestBody VendorRegisterRequest request
    ) {
        VendorAuthResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Registration successful! Please check your email to verify your account.", response));
    }

    // ==================== VERIFY EMAIL (GET — clicked from email) ====================
    @GetMapping("/verify-email")
    public ResponseEntity<Void> verifyEmail(@RequestParam String token) {
        try {
            authService.verifyEmail(token);
            // Redirect to frontend success page
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(frontendUrl + "/vendor/email-verified?status=success"))
                    .build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(frontendUrl + "/vendor/email-verified?status=error&message=" + e.getMessage()))
                    .build();
        }
    }

    // ==================== RESEND VERIFICATION ====================
    @PostMapping("/resend-verification")
    public ResponseEntity<ApiResponse<Void>> resendVerification(
            @RequestBody Map<String, String> request
    ) {
        authService.resendVerification(request.get("email"));
        return ResponseEntity.ok(ApiResponse.success("Verification email sent", null));
    }

    // ==================== LOGIN ====================
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<VendorAuthResponse>> login(
            @Valid @RequestBody VendorLoginRequest request
    ) {
        VendorAuthResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Login successful", response));
    }

    // ==================== REFRESH TOKEN ====================
    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<VendorAuthResponse>> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request
    ) {
        VendorAuthResponse response = authService.refreshToken(request);
        return ResponseEntity.ok(ApiResponse.success("Token refreshed", response));
    }

    // ==================== FORGOT PASSWORD ====================
    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<Void>> forgotPassword(
            @Valid @RequestBody ForgotPasswordRequest request
    ) {
        authService.forgotPassword(request);
        return ResponseEntity.ok(ApiResponse.success("Password reset email sent. Check your inbox.", null));
    }

    // ==================== RESET PASSWORD ====================
    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<Void>> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request
    ) {
        authService.resetPassword(request);
        return ResponseEntity.ok(ApiResponse.success("Password reset successful. You can now login.", null));
    }

    // ==================== CHANGE PASSWORD (Authenticated) ====================
    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @AuthenticationPrincipal VendorUser user,
            @Valid @RequestBody ChangePasswordRequest request
    ) {
        authService.changePassword(user.getId(), request);
        return ResponseEntity.ok(ApiResponse.success("Password changed successfully", null));
    }

    // ==================== LOGOUT (Authenticated) ====================
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            @AuthenticationPrincipal VendorUser user
    ) {
        authService.logout(user.getId());
        return ResponseEntity.ok(ApiResponse.success("Logged out successfully", null));
    }

    // ==================== GET PROFILE (Authenticated) ====================
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<VendorProfileResponse>> getProfile(
            @AuthenticationPrincipal VendorUser user
    ) {
        VendorProfileResponse profile = authService.getProfile(user.getId());
        return ResponseEntity.ok(ApiResponse.success(profile));
    }
}