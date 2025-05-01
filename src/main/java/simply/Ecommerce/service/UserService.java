package simply.Ecommerce.service;


import simply.Ecommerce.exception.UserException;
import simply.Ecommerce.model.User;

public interface UserService {

    public User findUserByJwtToken(String jwt) throws UserException;

    User findUserByEmail(String Email) throws UserException;
}
