package com.simply.service;

import com.simply.exception.SellerException;
import com.simply.exception.UserException;
import com.simply.request.LoginRequest;
import com.simply.request.SignupRequest;
import com.simply.response.AuthResponse;
import jakarta.mail.MessagingException;

public interface AuthService {

    void sentLoginOtp(String email) throws UserException, MessagingException;
    String createUser(SignupRequest req) throws SellerException;
    AuthResponse signin(LoginRequest req) throws SellerException;

}
