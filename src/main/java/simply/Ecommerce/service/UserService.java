package simply.Ecommerce.service;


import simply.Ecommerce.model.User;

public interface UserService {

    public User findUserByJwtToken(String jwt) throws Exception;

    User findUserByEmail(String Email) throws Exception;

}
