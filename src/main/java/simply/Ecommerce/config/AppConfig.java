package simply.Ecommerce.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;



import java.util.Collections;

@Configuration
@EnableWebSecurity
public class AppConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.sessionManagement(management->management.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS
        )).authorizeHttpRequests(authorize-> authorize
                .requestMatchers("/api/**").authenticated()
                .requestMatchers( "/api/products/*/reviews").permitAll()
                .anyRequest().permitAll()
        ).addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                .csrf(csrf->csrf.disable())
                .cors(cors-> cors.configurationSource(corsConfigurationSource()));

        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                CorsConfiguration cfg = new CorsConfiguration();        //This object will hold all the CORS rules.
                cfg.setAllowedOrigins(Collections.singletonList("*"));               //* permit all urls access from frontend , If a frontend (React, Angular, etc.) is running on a different domain, it can still access the backend.
                cfg.setAllowedMethods(Collections.singletonList("*"));     // The backend won't block any type of request.(post,put,delte,get)
                cfg.setAllowedHeaders(Collections.singletonList("*"));      //The client can send any type of headers in the request.
                cfg.setAllowCredentials(true);                              //Without this, the frontend won’t be able to send cookies or authentication headers.
                cfg.setExposedHeaders(Collections.singletonList("Authorization"));  //Useful when sending JWT tokens in response headers.
                cfg.setMaxAge(3600l);                                       //Reduces the number of CORS preflight requests, improving performance.
                return null;
            }
        };
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

   @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
