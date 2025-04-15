package simply.Ecommerce.service;

import org.springframework.stereotype.Service;
import simply.Ecommerce.Enum.USER_ROLE;
import simply.Ecommerce.Response.AuthResponse;
import simply.Ecommerce.Response.SignupRequest;
import simply.Ecommerce.request.LoginRequest;

@Service
public interface AuthService {

    void sentLoginOtp(String email, USER_ROLE role) throws Exception;
    String createUser(SignupRequest req) throws Exception;
    AuthResponse signing(LoginRequest req) throws Exception;

}
