package com.simply.controller;

import com.simply.response.ApiResponse;
import com.simply.response.AuthResponse;
import com.simply.service.GoogleOAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/auth/google")
@Slf4j
public class GoogleAuthController {

    @Autowired
    private GoogleOAuthService googleOAuthService;

//    @GetMapping("/callback")
//    public ResponseEntity<?> handleGoogleCallback(@RequestParam String code) {
//
//        try {
//            String jwtToken = googleOAuthService.handleOAuthCallback(code);
//
//            return ResponseEntity.ok(Collections.singletonMap("token", jwtToken));
//        } catch (Exception e) {
//            log.error("Exception occurred while handleGoogleCallback ", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Collections.singletonMap("error", e.getMessage()));
//        }
//    }

    @GetMapping("/callback")
    public ResponseEntity<?> handleGoogleCallback(@RequestParam String code) {
        try {
            AuthResponse authResponse = googleOAuthService.handleOAuthCallback(code);
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            log.error("Exception occurred while handleGoogleCallback ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }


}
