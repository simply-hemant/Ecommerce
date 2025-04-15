package simply.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import simply.Ecommerce.Enum.AccountStatus;
import simply.Ecommerce.model.Seller;

import java.util.List;

@Repository
public interface SellerRepo extends JpaRepository<Seller, Long> {

    Seller findByEmail(String email);
    List<Seller> findByAccountStatus(AccountStatus status);

}
