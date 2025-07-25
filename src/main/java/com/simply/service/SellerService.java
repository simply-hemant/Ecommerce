package com.simply.service;

import com.simply.dto.SellerResponseDTO;
import com.simply.enums.AccountStatus;
import com.simply.exception.SellerException;
import com.simply.model.Seller;
import java.util.List;

public interface SellerService {
    Seller getSellerProfile(String jwt) throws SellerException;
    Seller createSeller(Seller seller) throws SellerException;
    SellerResponseDTO getSellerById(Long id) throws SellerException;
    Seller getSellerByEmail(String email) throws SellerException;
    List<Seller> getAllSellers(AccountStatus status);
    Seller updateSeller(Long id, Seller seller) throws SellerException;
    void deleteSeller(Long id) throws SellerException;
    Seller verifyEmail(String email,String otp) throws SellerException;

    Seller updateSellerAccountStatus(Long sellerId, AccountStatus status) throws SellerException;

    Seller fetchSellerEntityById(Long id) throws SellerException;
}
