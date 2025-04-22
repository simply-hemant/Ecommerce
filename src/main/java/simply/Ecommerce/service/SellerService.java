package simply.Ecommerce.service;

import org.springframework.stereotype.Service;
import simply.Ecommerce.Enum.AccountStatus;
import simply.Ecommerce.exception.SellerException;
import simply.Ecommerce.model.Seller;

import java.util.List;

@Service
public interface SellerService {

    Seller getSellerProfile(String jwt) throws SellerException;
    Seller createSeller(Seller seller)throws SellerException;
    Seller getSellerById(Long id)throws SellerException;
    Seller getSellerByEmail(String email)throws SellerException;
    List<Seller> getAllSellers(AccountStatus status)throws SellerException;
    Seller updateSeller(Long id, Seller seller)throws SellerException;
    void deleteSeller(Long id)throws SellerException;
    Seller verifyEmail(String email, String otp)throws SellerException;
    Seller updateSellerAccountStatus(Long sellerId, AccountStatus status)throws SellerException;

}
