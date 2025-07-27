package com.simply.service;

import com.simply.config.JwtProvider;
import com.simply.enums.USER_ROLE;
import com.simply.model.User;
import com.simply.repository.UserRepository;
import com.simply.response.AuthResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

import java.util.*;

@Service
@Slf4j
public class GoogleOAuthService {


    @Value("${google.client-id}")
    private String clientId;

    @Value("${google.client-secret}")
    private String clientSecret;


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    public AuthResponse handleOAuthCallback(String code) throws Exception {
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

        String idToken = (String) tokenResponse.getBody().get("id_token");

        String userInfoUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken;

        ResponseEntity<Map> userInfoResponse = restTemplate.getForEntity(userInfoUrl, Map.class);

        if (userInfoResponse.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> userInfo = userInfoResponse.getBody();
            String email = (String) userInfo.get("email");

            User user = userRepository.findByEmail(email);

            if (user == null) {
                // If user not found, register new
                user = new User();
                user.setEmail(email);
                user.setFullName(email); // or extract name from Google if needed
                user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
                user.setRole(USER_ROLE.ROLE_CUSTOMER);
                user = userRepository.save(user);
            }

            String jwt = jwtProvider.generateTokenForOAuth(user.getEmail(), user.getRole().name());


            AuthResponse authResponse = new AuthResponse();

            authResponse.setJwt(jwt);
            authResponse.setRole(USER_ROLE.valueOf(user.getRole().name()));
            authResponse.setStatus(true);
            authResponse.setMessage("Google login successful");

//            return jwtProvider.generateTokenForOAuth(user.getEmail(), user.getRole().name());

            return authResponse;
        }

        throw new RuntimeException("Failed to fetch user info from Google");
    }
}

