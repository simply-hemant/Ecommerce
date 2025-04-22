package simply.Ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import simply.Ecommerce.Enum.USER_ROLE;
import simply.Ecommerce.Response.ApiResponse;
import simply.Ecommerce.Response.AuthResponse;
import simply.Ecommerce.Response.SignupRequest;
import simply.Ecommerce.model.User;
import simply.Ecommerce.model.VerificationCode;
import simply.Ecommerce.repository.UserRepo;
import simply.Ecommerce.repository.VerificationCodeRepo;
import simply.Ecommerce.request.LoginOtpRequest;
import simply.Ecommerce.request.LoginRequest;
import simply.Ecommerce.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserRepo userRepo;

    @PostMapping("/signup")

    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody SignupRequest req) throws Exception {

        String jwt = authService.createUser(req);

        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setMessage("register success");
        res.setRole(USER_ROLE.ROLE_CUSTOMER);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/sent/login-signup-otp")
    public ResponseEntity<ApiResponse> sentOtpHandler(
            @RequestBody LoginOtpRequest req) throws Exception {
//
//        authService.sentLoginOtp(req.getEmail(),req.getRole());
//
//        ApiResponse res = new ApiResponse();
//
//        res.setMessage("otp sent successfully");
//
//        return ResponseEntity.ok(res);

        ApiResponse res = new ApiResponse();

        try {
            System.out.println("🔹 Sending OTP to: " + req.getEmail());

            authService.sentLoginOtp(req.getEmail(), req.getRole());

            res.setMessage("OTP sent successfully");
            res.setStatus(true);  // ✅ Explicitly setting status to true

            System.out.println("✅ OTP sent successfully to: " + req.getEmail());
        } catch (Exception e) {
            System.err.println("❌ Error sending OTP: " + e.getMessage());

            res.setMessage(e.getMessage());
            res.setStatus(false);
        }

        return ResponseEntity.ok(res);


    }

    @PostMapping("/signing")
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest req) throws Exception {

        AuthResponse authResponse = authService.signing(req);

        return ResponseEntity.ok(authResponse);
    }


}
