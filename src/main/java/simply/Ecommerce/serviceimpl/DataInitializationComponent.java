package simply.Ecommerce.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import simply.Ecommerce.Enum.USER_ROLE;
import simply.Ecommerce.model.User;
import simply.Ecommerce.repository.UserRepo;

@Component
@RequiredArgsConstructor
public class DataInitializationComponent implements CommandLineRunner {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initializeAdminUser();
    }

    private void initializeAdminUser() {

        String adminUsername = "simply23hemant@gmail.com";

        if(userRepo.findByEmail(adminUsername) == null){
            User adminUser = new User();

            adminUser.setPassword(passwordEncoder.encode("simplybuy"));
            adminUser.setFirstName("Hemant");
            adminUser.setLastName("Singh");
            adminUser.setEmail(adminUsername);
            adminUser.setRole(USER_ROLE.ROLE_ADMIN);

            userRepo.save(adminUser);
        }

    }
}
