package simply.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import simply.Ecommerce.model.Transaction;

import java.util.List;

@Repository
public interface TransactionRepo  extends JpaRepository<Transaction, Long> {

    List<Transaction> findBySellerId(Long SellerId);

}
