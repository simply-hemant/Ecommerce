package simply.Ecommerce.service;

import simply.Ecommerce.model.Seller;
import simply.Ecommerce.model.SellerReport;

public interface SellerReportService {

    SellerReport getSellerReport(Seller seller);
    SellerReport updateSellerReport(SellerReport sellerReport);

}
