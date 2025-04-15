package simply.Ecommerce.serviceimpl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import simply.Ecommerce.Enum.USER_ROLE;
import simply.Ecommerce.Response.AuthResponse;
import simply.Ecommerce.Response.SignupRequest;
import simply.Ecommerce.config.JwtProvider;
import simply.Ecommerce.model.Cart;
import simply.Ecommerce.model.Seller;
import simply.Ecommerce.model.User;
import simply.Ecommerce.model.VerificationCode;
import simply.Ecommerce.repository.CartRepo;
import simply.Ecommerce.repository.SellerRepo;
import simply.Ecommerce.repository.UserRepo;
import simply.Ecommerce.repository.VerificationCodeRepo;
import simply.Ecommerce.request.LoginRequest;
import simply.Ecommerce.service.AuthService;
import simply.Ecommerce.service.EmailService;
import simply.Ecommerce.utils.OtpUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final CartRepo cartRepo;
    private final JwtProvider jwtProvider;
    private final VerificationCodeRepo verificationCodeRepo;
    private final EmailService emailService;
    private final CustomUserServiceImpl customUserService;
    private final SellerRepo sellerRepo;

    @Override
    public void sentLoginOtp(String email, USER_ROLE role) throws Exception {
        String SIGNING_PREFIX = "signing_";

        if(email.startsWith(SIGNING_PREFIX))
        {
            email=email.substring(SIGNING_PREFIX.length());

            if(role.equals(USER_ROLE.ROLE_SELLER)){
                Seller seller=sellerRepo.findByEmail(email);
                if(seller==null){
                    throw new Exception("seller not found");
                }
            }
        }

        if(role != null && role.equals(USER_ROLE.ROLE_CUSTOMER)){
            User user = userRepo.findByEmail(email);
            if(user==null){
                throw new Exception("user not exist with provided email");
            }
        }

        VerificationCode isExist=verificationCodeRepo.findByEmail(email);

        if(isExist!=null){
            verificationCodeRepo.delete(isExist);
        }

        String otp= OtpUtils.generateOtp();

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(email);

       verificationCodeRepo.save(verificationCode);

       String subject = "simply buy login/signup otp";
       String text="your login/signup otp is - " + otp;

        System.out.println("Sending OTP to: " + email + " OTP: " + otp);

        emailService.sendVerificationOtpEmail(email,otp,subject,text);
    }

    @Override
    public String createUser(SignupRequest req) throws Exception {

        VerificationCode verificationCode = verificationCodeRepo.findByEmail(req.getEmail());

        if(verificationCode==null || !verificationCode.getOtp().equals(req.getOtp())){
            throw new Exception("wrong otp....");
        }


        User user = userRepo.findByEmail(req.getEmail());

        if(user==null){
            User createdUser = new User();
            createdUser.setFirstName(req.getFirstName());
            createdUser.setLastName(req.getLastName());
            createdUser.setEmail(req.getEmail());
            createdUser.setRole(USER_ROLE.ROLE_CUSTOMER);
            createdUser.setMobile("9119703177");
            createdUser.setPassword(passwordEncoder.encode(req.getOtp()));

            user=userRepo.save(createdUser);

            Cart cart = new Cart();
            cart.setUser(user);
            cartRepo.save(cart);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(USER_ROLE.ROLE_CUSTOMER.toString()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(req.getEmail(),null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtProvider.generateToken(authentication);
    }


    @Override
    public AuthResponse signing(LoginRequest req) throws Exception{

        String username = req.getEmail();
        String otp = req.getOtp();

        Authentication authentication = authenticate(username, otp);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token=jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Login success");

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roleName=authorities.isEmpty()?null:authorities.iterator().next().getAuthority();

        authResponse.setRole(USER_ROLE.valueOf(roleName));

        return authResponse;
    }

    private Authentication authenticate(String username, String otp) throws Exception {
        UserDetails userDetails = customUserService.loadUserByUsername(username);

        String SELLER_PREFIX = "seller_";
        if(username.startsWith(SELLER_PREFIX)){
            username=username.substring(SELLER_PREFIX.length());
        }

        if(userDetails == null) {
            throw  new Exception("invalid username");
        }

        VerificationCode verificationCode = verificationCodeRepo.findByEmail(username);
        if(verificationCode == null || !verificationCode.getOtp().equals(otp)){
            throw new Exception("wrong otp");
        }

        return new UsernamePasswordAuthenticationToken(userDetails,
                null,
                userDetails.getAuthorities());
    }
}

