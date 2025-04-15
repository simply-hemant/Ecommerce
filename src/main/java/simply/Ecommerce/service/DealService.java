package simply.Ecommerce.service;

import simply.Ecommerce.model.Deal;

import java.util.List;

public interface DealService {

    Deal createDeal(Deal deal);
    List<Deal> getDeals();
    Deal updateDeal(Deal deal, Long id) throws Exception;
    void deleteDeal(Long id) throws Exception;


}
