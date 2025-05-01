package simply.Ecommerce.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import simply.Ecommerce.model.User;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class JwtProvider {

    SecretKey key = Keys.hmacShaKeyFor(Jwt_Constant.SECRET_KEY.getBytes());

    public String generateToken(Authentication auth) {
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String roles = populateAuthorities(authorities);

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+86400000))
                .claim("email",auth.getName())
                .claim("authorities", roles)
                .signWith(key)
                .compact();
        
      //  return jwt;

    }

    public String generateTokenGoogle(User user) {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .claim("email", user.getEmail())
                .claim("userId", user.getUserId())
                .claim("role", user.getRole().toString())
                .signWith(key)
                .compact();
    }


    public String getEmailFromJwtToken(String jwt) {
        jwt = jwt.substring(7);

        Claims claims=Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(jwt).getBody();

        return String.valueOf(claims.get("email"));

       // return email;
    }

    public String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> auths=new HashSet<>();

        for(GrantedAuthority authority:collection) {
            auths.add(authority.getAuthority());
        }
        return String.join(",",auths);
    }

}
