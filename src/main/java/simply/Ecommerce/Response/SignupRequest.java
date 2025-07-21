package simply.Ecommerce.Response;

import lombok.Data;

@Data
public class SignupRequest {

    private String email;
    private String firstName;
    private String lastName;
    private String otp;

}
