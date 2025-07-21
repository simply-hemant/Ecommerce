package simply.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import simply.Ecommerce.model.Deal;

@Repository
public interface DealRepo extends JpaRepository<Deal, Long> {
}
