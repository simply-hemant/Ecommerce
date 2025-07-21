package simply.Ecommerce.Response;

import lombok.Data;
import simply.Ecommerce.Enum.USER_ROLE;
import simply.Ecommerce.model.User;

@Data
public class AuthResponse {

    private String jwt;
    private String message;
    private USER_ROLE role;

}
