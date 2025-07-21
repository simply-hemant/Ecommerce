package simply.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import simply.Ecommerce.model.Address;

public interface AddressRepo extends JpaRepository<Address, Long> {
}
