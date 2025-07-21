package simply.Ecommerce.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import simply.Ecommerce.model.Deal;
import simply.Ecommerce.model.HomeCategory;
import simply.Ecommerce.repository.DealRepo;
import simply.Ecommerce.repository.HomeCategoryRepo;
import simply.Ecommerce.service.DealService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {

    private final DealRepo dealRepo;
    private final HomeCategoryRepo homeCategoryRepo;

    @Override
    public Deal createDeal(Deal deal) {

        HomeCategory category = homeCategoryRepo
                .findById(deal.getCategory().getId()).orElse(null);

        Deal newDeal = new Deal();
        newDeal.setCategory(category);
        newDeal.setDiscount(deal.getDiscount());

        return dealRepo.save(newDeal);
    }

    @Override
    public List<Deal> getDeals() {
        return dealRepo.findAll();
    }

    @Override
    public Deal updateDeal(Deal deal, Long id) throws Exception {

        Deal existingDeal = dealRepo.findById(id).orElse(null);
        HomeCategory category = homeCategoryRepo.findById(deal.getCategory().getId()).orElse(null);

        if(existingDeal != null){
            if(deal.getDiscount() != null){
                existingDeal.setDiscount(deal.getDiscount());
            }

            if(deal.getCategory() != null){
                existingDeal.setCategory(deal.getCategory());
            }

            return dealRepo.save(existingDeal);
        }
        throw  new Exception("Deal not found");
    }

    @Override
    public void deleteDeal(Long id) throws Exception {

        Deal deal = dealRepo.findById(id).orElse(null);

        if(deal != null){
            dealRepo.delete(deal);
        }

    }
}
