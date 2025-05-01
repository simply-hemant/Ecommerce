package simply.Ecommerce.service;

import org.springframework.http.ResponseEntity;

public interface GoogleAuthService {

    ResponseEntity<?> authenticateWithGoogle(String code);

}
