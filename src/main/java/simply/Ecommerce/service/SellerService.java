<<<<<<< HEAD
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
=======
package simply.Ecommerce.service;

import org.springframework.stereotype.Service;
import simply.Ecommerce.Enum.AccountStatus;
import simply.Ecommerce.exception.SellerException;
import simply.Ecommerce.model.Seller;

import java.util.List;

@Service
public interface SellerService {

    Seller getSellerProfile(String jwt) throws Exception;
    Seller createSeller(Seller seller)throws Exception;
    Seller getSellerById(Long id)throws SellerException;
    Seller getSellerByEmail(String email)throws Exception;
    List<Seller> getAllSellers(AccountStatus status)throws Exception;
    Seller updateSeller(Long id, Seller seller)throws Exception;
    void deleteSeller(Long id)throws Exception;
    Seller verifyEmail(String email, String otp)throws Exception;
    Seller updateSellerAccountStatus(Long sellerId, AccountStatus status)throws Exception;

}
>>>>>>> 852346b (Added DataInitializationComponent and other updates)
