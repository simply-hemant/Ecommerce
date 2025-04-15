package simply.Ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import simply.Ecommerce.model.SellerReport;

@Repository
public interface SellerReportRepo extends JpaRepository<SellerReport, Long> {

    SellerReport findBySellerId(Long sellerId);

}
