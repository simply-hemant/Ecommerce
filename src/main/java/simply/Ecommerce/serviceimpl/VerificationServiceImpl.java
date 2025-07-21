package simply.Ecommerce.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import simply.Ecommerce.model.VerificationCode;
import simply.Ecommerce.repository.VerificationCodeRepo;
import simply.Ecommerce.service.VerificationService;

@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {

    private final VerificationCodeRepo verificationCodeRepo;

//    public VerificationServiceImpl(VerificationCodeRepo verificationCodeRepo) {
//        this.verificationCodeRepo = verificationCodeRepo;
//    }

    @Override
    public VerificationCode createVerificationCode(String otp, String email) {

        VerificationCode isExist = verificationCodeRepo.findByEmail(email);

        if(isExist!=null){
            verificationCodeRepo.delete(isExist);
        }

        VerificationCode verificationCode=new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(email);

        return verificationCodeRepo.save(verificationCode);

    }
}
