package com.simply.service.impl;

import com.simply.config.JwtProvider;
import com.simply.dto.SellerResponseDTO;
import com.simply.enums.AccountStatus;
import com.simply.enums.USER_ROLE;
import com.simply.exception.SellerException;
import com.simply.model.Address;
import com.simply.model.Seller;
import com.simply.repository.AddressRepository;
import com.simply.repository.SellerRepository;
import com.simply.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;


    @Override
    public Seller getSellerProfile(String jwt) throws SellerException {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        return this.getSellerByEmail(email);
    }

    @Override
    public Seller createSeller(Seller seller) throws SellerException {
        Seller sellerExist = sellerRepository.findByEmail(seller.getEmail());
        if (sellerExist != null) {
            throw new SellerException("Seller already exists used different email");
        }

        Address savedAddress = addressRepository.save(seller.getPickupAddress());


        Seller newSeller = new Seller();
        newSeller.setEmail(seller.getEmail());
        newSeller.setPickupAddress(savedAddress);
        newSeller.setSellerName(seller.getSellerName());
        newSeller.setGSTIN(seller.getGSTIN());
        newSeller.setRole(USER_ROLE.ROLE_SELLER);
        newSeller.setMobile(seller.getMobile());

        newSeller.setPassword(passwordEncoder.encode(seller.getPassword()));
        newSeller.setBankDetails(seller.getBankDetails());
        newSeller.setBusinessDetails(seller.getBusinessDetails());

        System.out.println(newSeller);
        return sellerRepository.save(newSeller);
    }

    @Override
    public SellerResponseDTO getSellerById(Long id) throws SellerException {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new SellerException("Seller not found with ID: " + id));

        SellerResponseDTO dto = new SellerResponseDTO();
        dto.setId(seller.getId());
        dto.setSellerName(seller.getSellerName());
        dto.setMobile(seller.getMobile());
        dto.setEmail(seller.getEmail());
        dto.setEmailVerified(seller.isEmailVerified());
        dto.setAccountStatus(seller.getAccountStatus());
        dto.setRole(seller.getRole());

        return dto;
    }


    @Override
    public Seller getSellerByEmail(String email) throws SellerException {
        Seller seller = sellerRepository.findByEmail(email);
        if (seller != null) {
            return seller;
        }
        throw new SellerException("Seller not found");
    }

    @Override
    public List<Seller> getAllSellers(AccountStatus status) {
        if (status != null) {
            return sellerRepository.findByAccountStatus(status);
        }
        return sellerRepository.findAll();
    }


    @Override
    public Seller updateSeller(Long id, Seller seller) throws SellerException {
        Seller existingSeller = sellerRepository.findById(id)
                .orElseThrow(() ->
                        new SellerException("Seller not found with id " + id));


        if (seller.getSellerName() != null) {
            existingSeller.setSellerName(seller.getSellerName());
        }
        if (seller.getMobile() != null) {
            existingSeller.setMobile(seller.getMobile());
        }
        if (seller.getEmail() != null) {
            existingSeller.setEmail(seller.getEmail());
        }

        if (seller.getBusinessDetails() != null
                && seller.getBusinessDetails().getBusinessName() != null
        ) {

            existingSeller.getBusinessDetails().setBusinessName(
                    seller.getBusinessDetails().getBusinessName()
            );
        }

        if (seller.getBankDetails() != null
                && seller.getBankDetails().getAccountHolderName() != null
                && seller.getBankDetails().getIfscCode() != null
                && seller.getBankDetails().getAccountNumber() != null
        ) {

            existingSeller.getBankDetails().setAccountHolderName(
                    seller.getBankDetails().getAccountHolderName()
            );
            existingSeller.getBankDetails().setAccountNumber(
                    seller.getBankDetails().getAccountNumber()
            );
            existingSeller.getBankDetails().setIfscCode(
                    seller.getBankDetails().getIfscCode()
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


        return sellerRepository.save(existingSeller);

    }

    @Override
    public void deleteSeller(Long id) throws SellerException {
        if (sellerRepository.existsById(id)) {
            sellerRepository.deleteById(id);
        } else {
            throw new SellerException("Seller not found with id " + id);
        }
    }

    @Override
    public Seller verifyEmail(String email, String otp) throws SellerException {
        Seller seller = this.getSellerByEmail(email);
        seller.setEmailVerified(true);

        return sellerRepository.save(seller);
    }

    @Override
    public Seller updateSellerAccountStatus(Long sellerId, AccountStatus status) throws SellerException {
        Seller seller = fetchSellerEntityById(sellerId); // ✅ Entity object
        seller.setAccountStatus(status);
        return sellerRepository.save(seller);            // ✅ Save works
    }


    public Seller fetchSellerEntityById(Long id) throws SellerException {
        return sellerRepository.findById(id)
                .orElseThrow(() -> new SellerException("Seller not found with id: " + id));
    }


}
