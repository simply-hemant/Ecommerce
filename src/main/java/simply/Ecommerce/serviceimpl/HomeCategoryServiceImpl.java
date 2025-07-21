package simply.Ecommerce.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import simply.Ecommerce.model.HomeCategory;
import simply.Ecommerce.repository.HomeCategoryRepo;
import simply.Ecommerce.service.HomeCategoryService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HomeCategoryServiceImpl implements HomeCategoryService {

    private final HomeCategoryRepo homeCategoryRepo;

    @Override
    public HomeCategory createCategory(HomeCategory categories) {

        return homeCategoryRepo.save(categories);
    }

    @Override
    public List<HomeCategory> createCategories(List<HomeCategory> categories) {

        if(homeCategoryRepo.findAll().isEmpty()){
            return homeCategoryRepo.saveAll(categories);
        }

        return homeCategoryRepo.saveAll(categories);
    }

    @Override
    public List<HomeCategory> getAllCategories() {

        return homeCategoryRepo.findAll();
    }

    @Override
    public HomeCategory updateCategory(HomeCategory category, Long id) throws Exception {

//        Optional<HomeCategory> optionalCategory = homeCategoryRepo.findById(id);
//        HomeCategory existingCategory;
//
//        if(optionalCategory.isPresent()){
//            existingCategory = optionalCategory.get();
//        }
//        else {
//            throw new Exception("category not found");
//        }

        HomeCategory existingCategory = homeCategoryRepo.findById(id)
                .orElseThrow(() -> new Exception("Category not found"));

        if (category.getImage() != null){
            existingCategory.setImage(category.getImage());
        }

        if(category.getId() != null){
            existingCategory.setCategoryId(category.getCategoryId());
        }

        return homeCategoryRepo.save(existingCategory);
    }
}
