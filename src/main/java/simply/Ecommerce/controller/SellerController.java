package simply.Ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import simply.Ecommerce.Enum.AccountStatus;
import simply.Ecommerce.Response.AuthResponse;
import simply.Ecommerce.config.JwtProvider;
import simply.Ecommerce.exception.SellerException;
import simply.Ecommerce.model.Seller;
import simply.Ecommerce.model.SellerReport;
import simply.Ecommerce.model.VerificationCode;
import simply.Ecommerce.repository.VerificationCodeRepo;
import simply.Ecommerce.request.LoginRequest;
import simply.Ecommerce.service.*;
import simply.Ecommerce.utils.OtpUtils;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sellers")
public class SellerController {

    private final SellerService sellerService;
    private final EmailService emailService;
    private final VerificationService verificationService;
    private final VerificationCodeRepo verificationCodeRepo;
    private final AuthService authService;
    private final JwtProvider jwtProvider;
    private final SellerReportService sellerReportService;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> sentLoginOtp(
            @RequestBody LoginRequest req) throws Exception {

        String otp = req.getOtp();
        String email = req.getEmail();

        req.setEmail("seller_"+email);
        AuthResponse authResponse = authService.signing(req);

        return ResponseEntity.ok(authResponse);
    }

    @PatchMapping("/verify/{otp}")
    public ResponseEntity<Seller> verifySellerEmail(
            @PathVariable String otp) throws Exception {

        VerificationCode verificationCode=verificationCodeRepo.findByOtp(otp);

        if(verificationCode == null ||
              !verificationCode.getOtp().equals(otp)){
            throw new Exception("wrong otp");
        }

        Seller seller = sellerService.verifyEmail(verificationCode.getEmail(), otp);

        return new ResponseEntity<>(seller, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<Seller> createSeller(@RequestBody Seller seller) throws Exception {
        Seller savedSeller = sellerService.createSeller(seller);

        String otp = OtpUtils.generateOtp();

        VerificationCode verificationCode=new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(seller.getEmail());
        verificationCodeRepo.save(verificationCode);

        String subject = "simply buy Email Verification Code";
        String text = "Welcome to simply buy, verify your account using this link "+otp;
        String frontend_url = "http://localhost:3000/verify-seller/";

        emailService.sendVerificationOtpEmail(seller.getEmail(), verificationCode.getOtp(), subject, text + frontend_url);
        return new ResponseEntity<>(savedSeller, HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<List<Seller>> getAllSellers(
            @RequestParam(required = false) AccountStatus status) throws Exception {
        List<Seller> sellers = sellerService.getAllSellers(status);
        return ResponseEntity.ok(sellers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seller> getSellerById(@PathVariable Long id) throws SellerException {
        Seller seller = sellerService.getSellerById(id);
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

    @PatchMapping()
    public ResponseEntity<Seller> updateSeller(
            @RequestHeader("Authorization") String jwt,
            @RequestBody Seller seller) throws Exception {

        Seller profile = sellerService.getSellerProfile(jwt);
        Seller updatedSeller = sellerService.updateSeller(profile.getId(), seller);
        return ResponseEntity.ok(updatedSeller);

    }

    @GetMapping("/profile")
    public ResponseEntity<Seller> getSellerByJwt(
            @RequestHeader("Authorization") String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        Seller seller = sellerService.getSellerByEmail(email);
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable Long id) throws Exception {

        sellerService.deleteSeller(id);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/report")
    public ResponseEntity<SellerReport> getSellerReport(
            @RequestHeader("Authorization") String jwt) throws Exception {

        Seller seller = sellerService.getSellerProfile(jwt);
        SellerReport report = sellerReportService.getSellerReport(seller);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

}
