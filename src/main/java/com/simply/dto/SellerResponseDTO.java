package com.simply.dto;

import com.simply.enums.AccountStatus;
import com.simply.enums.USER_ROLE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class SellerResponseDTO {

    private Long id;
    private String sellerName;
    private String mobile;
    private String email;
    private boolean isEmailVerified;
    private AccountStatus accountStatus;
    private USER_ROLE role;

}
