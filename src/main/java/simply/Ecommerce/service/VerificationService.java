package simply.Ecommerce.service;

import simply.Ecommerce.model.VerificationCode;

public interface VerificationService {

    VerificationCode createVerificationCode(String otp, String email);

}
