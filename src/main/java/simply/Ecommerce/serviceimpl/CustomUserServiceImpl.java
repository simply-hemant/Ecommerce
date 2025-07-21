package simply.Ecommerce.serviceimpl;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import simply.Ecommerce.Enum.USER_ROLE;
import simply.Ecommerce.model.Seller;
import simply.Ecommerce.model.User;
import simply.Ecommerce.repository.SellerRepo;
import simply.Ecommerce.repository.UserRepo;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;
    private final SellerRepo sellerRepo;
    private static final String SELLER_PREFIX = "seller_";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if(username.startsWith(SELLER_PREFIX)){
            String actualUsername = username.substring(SELLER_PREFIX.length());
            Seller seller=sellerRepo.findByEmail(actualUsername);

            if(seller!=null){
                return buildUserDetails(seller.getEmail(),seller.getPassword(), seller.getRole());
            }

        }else{
            User user = userRepo.findByEmail(username);
            if(user!=null){
                return buildUserDetails(user.getEmail(), user.getPassword(),user.getRole());
            }
        }

        throw new UsernameNotFoundException("user or seller not found with email - "+username);
    }

    private UserDetails buildUserDetails(String email, String password, USER_ROLE role){
        if(role==null) role = USER_ROLE.ROLE_CUSTOMER;

        List<GrantedAuthority> authorities=new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.toString()));

        return new org.springframework.security.core.userdetails.User(email, password, authorities);
    }

}
