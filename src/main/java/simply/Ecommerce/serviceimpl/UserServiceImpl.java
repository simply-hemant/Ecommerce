package simply.Ecommerce.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import simply.Ecommerce.config.JwtProvider;
import simply.Ecommerce.model.User;
import simply.Ecommerce.repository.UserRepo;
import simply.Ecommerce.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final JwtProvider jwtProvider;

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {

        String email=jwtProvider.getEmailFromJwtToken(jwt);

       return this.findUserByEmail(email);

    }

    @Override
    public User findUserByEmail(String email) throws Exception {

        User user = userRepo.findByEmail(email);
        if(user == null){
            throw new Exception("user not found with email - "+email);
        }

        return user;
    }
}
