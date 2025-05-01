package simply.Ecommerce.serviceimpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import simply.Ecommerce.Enum.USER_ROLE;
import simply.Ecommerce.config.JwtProvider;
import simply.Ecommerce.model.User;
import simply.Ecommerce.repository.UserRepo;
import simply.Ecommerce.service.GoogleAuthService;
import simply.Ecommerce.service.UserService;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class GoogleAuthServiceImpl implements GoogleAuthService {


    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    private final RestTemplate restTemplate;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final UserRepo userRepo;
    private final UserService userService;

    @Override
    public ResponseEntity<?> authenticateWithGoogle(String code) {
        try {
            String tokenEndpoint = "https://oauth2.googleapis.com/token";
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("code", code);
            params.add("client_id", clientId);
            params.add("client_secret", clientSecret);
            params.add("redirect_uri", "https://developers.google.com/oauthplayground");
            params.add("grant_type", "authorization_code");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
            ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(tokenEndpoint, request, Map.class);

            if (!tokenResponse.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Failed to fetch token from Google");
            }

            String idToken = (String) tokenResponse.getBody().get("id_token");
            String userInfoUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken;

            ResponseEntity<Map> userInfoResponse = restTemplate.getForEntity(userInfoUrl, Map.class);

            if (userInfoResponse.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> userInfo = userInfoResponse.getBody();
                String email = (String) userInfo.get("email");

                User user;
                try {
                    user = userService.findUserByEmail(email);
                } catch (Exception e) {
                    user = new User();
                    user.setEmail(email);
                    user.setFirstName(email);
                    user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
                    user.setRole(USER_ROLE.ROLE_CUSTOMER);
                    userRepo.save(user);
                }

                String jwtToken = jwtProvider.generateTokenGoogle(user);
                return ResponseEntity.ok(Collections.singletonMap("token", jwtToken));
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Failed to fetch user info from Google");
        } catch (Exception e) {
            log.error("Exception during Google authentication", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong during authentication");
        }
    }

}
