package simply.Ecommerce.request;

import lombok.Data;
import simply.Ecommerce.Enum.USER_ROLE;

@Data
public class LoginOtpRequest {

    private String email;
    private String otp;
    private USER_ROLE role;

}
