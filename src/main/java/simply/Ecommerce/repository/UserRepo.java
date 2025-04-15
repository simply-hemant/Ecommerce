package simply.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import simply.Ecommerce.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    User findByEmail(String email);

}
