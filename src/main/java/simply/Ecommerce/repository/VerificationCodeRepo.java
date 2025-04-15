package simply.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import simply.Ecommerce.model.VerificationCode;

@Repository
public interface VerificationCodeRepo extends JpaRepository<VerificationCode, Long> {


    VerificationCode findByEmail(String email);
    VerificationCode findByOtp(String otp);

}
