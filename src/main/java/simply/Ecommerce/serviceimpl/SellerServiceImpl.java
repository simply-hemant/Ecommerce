<<<<<<< HEAD
package simply.Ecommerce.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import simply.Ecommerce.Enum.AccountStatus;
import simply.Ecommerce.Enum.USER_ROLE;
import simply.Ecommerce.config.JwtProvider;
import simply.Ecommerce.exception.SellerException;
import simply.Ecommerce.model.Address;
import simply.Ecommerce.model.Seller;
import simply.Ecommerce.repository.AddressRepo;
import simply.Ecommerce.repository.SellerRepo;
import simply.Ecommerce.service.SellerService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerRepo sellerRepo;
    private final AddressRepo addressRepo;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Seller getSellerProfile(String jwt) throws SellerException {
        String email = jwtProvider.getEmailFromJwtToken(jwt);

        return this.getSellerByEmail(email);
    }

    @Override
    public Seller createSeller(Seller seller) throws SellerException {
        Seller sellerExist = sellerRepo.findByEmail(seller.getEmail());
        if(sellerExist != null){
            throw new SellerException("Seller already exists, use different email");
        }

        Address savedAddress = addressRepo.save(seller.getPickupAddress());

        Seller newSeller = new Seller();
        newSeller.setEmail(seller.getEmail());
        newSeller.setPickupAddress(savedAddress);
        newSeller.setRole(USER_ROLE.ROLE_SELLER);
        newSeller.setMobile(seller.getMobile());
        newSeller.setSellerName(seller.getSellerName());
        newSeller.setGSTIN(seller.getGSTIN());

        newSeller.setPassword(passwordEncoder.encode(seller.getPassword()));
        newSeller.setBankDetails(seller.getBankDetails());
        newSeller.setBusinessDetails(seller.getBusinessDetails());

        System.out.println(newSeller);
        return sellerRepo.save(newSeller);
    }

    @Override
    public Seller getSellerById(Long id) throws SellerException {
        Optional<Seller> optionalSeller = sellerRepo.findById(id);

        if (optionalSeller.isPresent()) {
            return optionalSeller.get();
        } else {
            throw new SellerException("Seller not found with id " + id);
        }

//        return sellerRepo.findById(id)
//                .orElseThrow(() -> new Exception("seller not found with id");
    }

    @Override
    public Seller getSellerByEmail(String email) throws SellerException {
        Seller seller=sellerRepo.findByEmail(email);
        if(seller == null){
            throw new SellerException("seller not found");
        }
        return seller;
    }

    @Override
    public List<Seller> getAllSellers(AccountStatus status) {
        return sellerRepo.findByAccountStatus(status);
    }

    @Override
    public Seller updateSeller(Long id, Seller seller) throws SellerException {

        Seller existingSeller = this.getSellerById(id);

        if (seller.getSellerName() != null){
            existingSeller.setSellerName(seller.getSellerName());
        }

        if (seller.getMobile() != null){
            existingSeller.setMobile(seller.getMobile());
        }

        if (seller.getEmail() != null){
            existingSeller.setEmail(seller.getEmail());
        }

        if (seller.getBusinessDetails() != null
                && seller.getBusinessDetails().getBusinessName() != null){

            existingSeller.getBusinessDetails().setBusinessName(
                    seller.getBusinessDetails().getBusinessName());
        }

        if (seller.getBankDetails() != null
                && seller.getBankDetails().getAccountHolderName() != null
                && seller.getBankDetails().getIfscCode() != null
                && seller.getBankDetails().getAccountNumber() != null
        ){
            existingSeller.getBankDetails().setAccountHolderName(
                    seller.getBankDetails().getAccountHolderName()
            );
            existingSeller.getBankDetails().setIfscCode(
                    seller.getBankDetails().getIfscCode()
            );
            existingSeller.getBankDetails().setAccountNumber(
                    seller.getBankDetails().getAccountNumber()
            );

        }

        if (seller.getPickupAddress() != null
                && seller.getPickupAddress().getAddress() != null
                && seller.getPickupAddress().getMobile() != null
                && seller.getPickupAddress().getCity() != null
                && seller.getPickupAddress().getState() != null
        ) {
            existingSeller.getPickupAddress()
                    .setAddress(seller.getPickupAddress().getAddress());
            existingSeller.getPickupAddress().setCity(seller.getPickupAddress().getCity());
            existingSeller.getPickupAddress().setState(seller.getPickupAddress().getState());
            existingSeller.getPickupAddress().setMobile(seller.getPickupAddress().getMobile());
            existingSeller.getPickupAddress().setPinCode(seller.getPickupAddress().getPinCode());
        }
        if (seller.getGSTIN() != null) {
            existingSeller.setGSTIN(seller.getGSTIN());
        }

        return sellerRepo.save(existingSeller);
    }

    @Override
    public void deleteSeller(Long id) throws SellerException {

//        if (sellerRepo.existsById(id)) {
//            sellerRepo.deleteById(id);
//        } else {
//            throw new Exception("Seller not found with id " + id);
//        }

        Seller seller = getSellerById(id);
        sellerRepo.delete(seller);
    }

    @Override
    public Seller verifyEmail(String email, String otp) throws SellerException{

        Seller seller = this.getSellerByEmail(email);
        seller.setEmailVerified(true);

        return sellerRepo.save(seller);
    }

    @Override
    public Seller updateSellerAccountStatus(Long sellerId, AccountStatus status) throws  SellerException {

        Seller seller = this.getSellerById(sellerId);
        seller.setAccountStatus(status);

        return sellerRepo.save(seller);
    }
}
=======
package simply.Ecommerce.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import simply.Ecommerce.Enum.AccountStatus;
import simply.Ecommerce.Enum.USER_ROLE;
import simply.Ecommerce.config.JwtProvider;
import simply.Ecommerce.exception.SellerException;
import simply.Ecommerce.model.Address;
import simply.Ecommerce.model.Seller;
import simply.Ecommerce.repository.AddressRepo;
import simply.Ecommerce.repository.SellerRepo;
import simply.Ecommerce.service.SellerService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerRepo sellerRepo;
    private final AddressRepo addressRepo;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Seller getSellerProfile(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);

        return this.getSellerByEmail(email);
    }

    @Override
    public Seller createSeller(Seller seller) throws Exception {
        Seller sellerExist = sellerRepo.findByEmail(seller.getEmail());
        if(sellerExist != null){
            throw new Exception("Seller already exists, use different email");
        }

        Address savedAddress = addressRepo.save(seller.getPickupAddress());

        Seller newSeller = new Seller();
        newSeller.setEmail(seller.getEmail());
        newSeller.setPickupAddress(savedAddress);
        newSeller.setRole(USER_ROLE.ROLE_SELLER);
        newSeller.setMobile(seller.getMobile());
        newSeller.setSellerName(seller.getSellerName());
        newSeller.setGSTIN(seller.getGSTIN());

        newSeller.setPassword(passwordEncoder.encode(seller.getPassword()));
        newSeller.setBankDetails(seller.getBankDetails());
        newSeller.setBusinessDetails(seller.getBusinessDetails());

        System.out.println(newSeller);
        return sellerRepo.save(newSeller);
    }

    @Override
    public Seller getSellerById(Long id) throws SellerException {
        Optional<Seller> optionalSeller = sellerRepo.findById(id);

        if (optionalSeller.isPresent()) {
            return optionalSeller.get();
        } else {
            throw new SellerException("Seller not found with id " + id);
        }

//        return sellerRepo.findById(id)
//                .orElseThrow(() -> new Exception("seller not found with id");
    }

    @Override
    public Seller getSellerByEmail(String email) throws Exception {
        Seller seller=sellerRepo.findByEmail(email);
        if(seller == null){
            throw new Exception("seller not found");
        }
        return seller;
    }

    @Override
    public List<Seller> getAllSellers(AccountStatus status) {
        return sellerRepo.findByAccountStatus(status);
    }

    @Override
    public Seller updateSeller(Long id, Seller seller) throws Exception {
        Seller existingSeller = this.getSellerById(id);

        if (seller.getSellerName() != null){
            existingSeller.setSellerName(seller.getSellerName());
        }

        if (seller.getMobile() != null){
            existingSeller.setMobile(seller.getMobile());
        }

        if (seller.getEmail() != null){
            existingSeller.setEmail(seller.getEmail());
        }

        if (seller.getBusinessDetails() != null
                && seller.getBusinessDetails().getBusinessName() != null){

            existingSeller.getBusinessDetails().setBusinessName(
                    seller.getBusinessDetails().getBusinessName());
        }

        if (seller.getBankDetails() != null
                && seller.getBankDetails().getAccountHolderName() != null
                && seller.getBankDetails().getIfscCode() != null
                && seller.getBankDetails().getAccountNumber() != null
        ){
            existingSeller.getBankDetails().setAccountHolderName(
                    seller.getBankDetails().getAccountHolderName()
            );
            existingSeller.getBankDetails().setIfscCode(
                    seller.getBankDetails().getIfscCode()
            );
            existingSeller.getBankDetails().setAccountNumber(
                    seller.getBankDetails().getAccountNumber()
            );

        }

        if (seller.getPickupAddress() != null
                && seller.getPickupAddress().getAddress() != null
                && seller.getPickupAddress().getMobile() != null
                && seller.getPickupAddress().getCity() != null
                && seller.getPickupAddress().getState() != null
        ) {
            existingSeller.getPickupAddress()
                    .setAddress(seller.getPickupAddress().getAddress());
            existingSeller.getPickupAddress().setCity(seller.getPickupAddress().getCity());
            existingSeller.getPickupAddress().setState(seller.getPickupAddress().getState());
            existingSeller.getPickupAddress().setMobile(seller.getPickupAddress().getMobile());
            existingSeller.getPickupAddress().setPinCode(seller.getPickupAddress().getPinCode());
        }
        if (seller.getGSTIN() != null) {
            existingSeller.setGSTIN(seller.getGSTIN());
        }

        return sellerRepo.save(existingSeller);
    }

    @Override
    public void deleteSeller(Long id) throws Exception {

//        if (sellerRepo.existsById(id)) {
//            sellerRepo.deleteById(id);
//        } else {
//            throw new Exception("Seller not found with id " + id);
//        }

        Seller seller = getSellerById(id);
        sellerRepo.delete(seller);
    }

    @Override
    public Seller verifyEmail(String email, String otp) throws Exception{

        Seller seller = this.getSellerByEmail(email);
        seller.setEmailVerified(true);

        return sellerRepo.save(seller);
    }

    @Override
   @PreAuthorize("hasRole('ADMIN')")
    public Seller updateSellerAccountStatus(Long sellerId, AccountStatus status) throws  Exception {

        Seller seller = this.getSellerById(sellerId);
        seller.setAccountStatus(status);

        return sellerRepo.save(seller);
    }
}
>>>>>>> 852346b (Added DataInitializationComponent and other updates)
