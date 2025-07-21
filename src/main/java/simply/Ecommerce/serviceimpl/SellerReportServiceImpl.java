package simply.Ecommerce.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import simply.Ecommerce.model.Seller;
import simply.Ecommerce.model.SellerReport;
import simply.Ecommerce.repository.SellerReportRepo;
import simply.Ecommerce.service.SellerReportService;

@Service
@RequiredArgsConstructor
public class SellerReportServiceImpl implements SellerReportService {

    private final SellerReportRepo sellerReportRepo;

    @Override
    public SellerReport getSellerReport(Seller seller) {

        SellerReport report = sellerReportRepo.findBySellerId(seller.getId());
        if(report == null){

            SellerReport newReport = new SellerReport();
            newReport.setSeller(seller);

            return sellerReportRepo.save(newReport);
        }
        return report;
    }

    @Override
    public SellerReport updateSellerReport(SellerReport sellerReport) {

        return sellerReportRepo.save(sellerReport);
    }
}
