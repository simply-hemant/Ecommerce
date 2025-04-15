package simply.Ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simply.Ecommerce.Enum.AccountStatus;
import simply.Ecommerce.exception.SellerException;
import simply.Ecommerce.model.Home;
import simply.Ecommerce.model.HomeCategory;
import simply.Ecommerce.model.Seller;
import simply.Ecommerce.service.HomeCategoryService;
import simply.Ecommerce.service.HomeService;
import simply.Ecommerce.service.SellerService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeCategoryController {

    private final HomeCategoryService homeCategoryService;
    private final HomeService homeService;
    private final SellerService sellerService;


    @PostMapping("/home/categories")
    private ResponseEntity<Home> createHomeCategories(
            @RequestBody List<HomeCategory> homeCategories
            ){

        List<HomeCategory> categories = homeCategoryService.createCategories(homeCategories);
        Home home = homeService.createHomePageData(categories);

        return new ResponseEntity<>(home, HttpStatus.ACCEPTED);
    }

    @GetMapping("/admin/home-category")
    public ResponseEntity<List<HomeCategory>> getHomeCategory(
    ) throws Exception {

        List<HomeCategory> categories=homeCategoryService.getAllCategories();
        return ResponseEntity.ok(categories);

    }

    @PatchMapping("/admin/home-category/{id}")
    public ResponseEntity<HomeCategory> updateHomeCategory(
            @PathVariable Long id,
            @RequestBody HomeCategory homeCategory) throws Exception {

        HomeCategory updatedCategory=homeCategoryService.updateCategory(homeCategory,id);
        return ResponseEntity.ok(updatedCategory);

    }

}
