package simply.Ecommerce.serviceimpl;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.criteria.Join;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import simply.Ecommerce.exception.ProductException;
import simply.Ecommerce.model.Category;
import simply.Ecommerce.model.Product;
import simply.Ecommerce.model.Seller;
import simply.Ecommerce.repository.CategoryRepo;
import simply.Ecommerce.repository.ProductRepo;
import simply.Ecommerce.request.CreateProductRequest;
import simply.Ecommerce.service.ProductService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Join;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;

    private final CategoryRepo categoryRepo;

    @Override
    public Product createProduct(CreateProductRequest req, Seller seller) throws ProductException {

        Category category1 = categoryRepo.findByCategoryId(req.getCategory());
        if(category1 == null){
            Category category = new Category();
            category.setCategoryId(req.getCategory());
            category.setLevel(1);
//            category.setName(req.getCategory().replace("_", " "));
            category1=categoryRepo.save(category);
        }

        Category category2 = categoryRepo.findByCategoryId(req.getCategory());
        if(category2 == null){
            Category category = new Category();
            category.setCategoryId(req.getCategory2());
            category.setLevel(2);

            category2=categoryRepo.save(category);
        }

        Category category3 = categoryRepo.findByCategoryId(req.getCategory());
        if(category3 == null){
            Category category = new Category();
            category.setCategoryId(req.getCategory3());
            category.setLevel(3);

            category3=categoryRepo.save(category);
        }

        int discountPercentage = calculateDiscountPercentage(req.getMrpPrice(), req.getSellingPrice());


        Product product = new Product();

        product.setSeller(seller);
        product.setCategory(category3);
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setDescription(req.getDescription());
        product.setDiscountPercent(discountPercentage);
        product.setSellingPrice(req.getSellingPrice());
        product.setImages(req.getImages());
        product.setMrpPrice(req.getMrpPrice());
        product.setSize(req.getSizes());
//        product.setCreatedAt(LocalDateTime.now());

        return productRepo.save(product);
    }

    public int calculateDiscountPercentage(int mrpPrice, int sellingPrice) {

        if(mrpPrice <= 0){
            throw new IllegalArgumentException("Actual price must be greater then zero");
        }

        double discount = mrpPrice - sellingPrice;
        double discountPercentage = (discount / mrpPrice) * 100;
        return (int) discountPercentage;
    }

    @Override
    public void deleteProduct(Long productId) throws ProductException {

        Product product = findProductById(productId);
        productRepo.delete(product);
    }

    @Override
    public Product updateProduct(Long productId, Product product) throws ProductException {

        productRepo.findById(productId);
        product.setProductId(productId);

        return productRepo.save(product);
    }

    @Override
    public Product updateProductStock(Long productId) throws ProductException {
        return null;
    }

    @Override
    public Product findProductById(Long productId) throws ProductException {

        return productRepo.findById(productId)
                .orElseThrow(() -> new ProductException("product not found"+ productId));
    }

    @Override
    public List<Product> searchProduct(String query) {

        return productRepo.searchProduct(query);
    }

    @Override
    public Page<Product> getAllProduct(String category, String brand, String color, String sizes, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber) {

        Specification<Product> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();


            if (category != null) {
                Join<Product, Category> categoryJoin = root.join("category");
                Predicate categoryPredicate = criteriaBuilder.or(
                        criteriaBuilder.equal(categoryJoin.get("categoryId"), category)  // Match categoryId
//                        criteriaBuilder.equal(categoryJoin.get("parentCategory").get("categoryId"), category)  // Match parentCategory.categoryId
                );

                predicates.add(categoryPredicate);
            }

            if(color != null && !color.isEmpty()){
//                System.out.println("color"+color);
                predicates.add(criteriaBuilder.equal(root.get("color"),color));
            }

            // Filter by size (single value)
            if (sizes != null && !sizes.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("size"), sizes));
            }

            if (minPrice != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("sellingPrice"),
                        minPrice));
            }

            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("sellingPrice"),
                        maxPrice));
            }

            if (minDiscount != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("discountPercent"),
                        minDiscount));
            }

            if (stock != null) {
                predicates.add(criteriaBuilder.equal(root.get("stock"), stock));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Pageable pageable;

        if(sort != null && !sort.isEmpty()) {
            pageable = switch (sort){

//                case"price_low":
//                    pageable= PageRequest.of(pageNumber != null? pageNumber:0 , 10,
//                            Sort.by("sellingPrice").ascending());

                case "price_low" ->
                        PageRequest.of(pageNumber != null ? pageNumber : 0, 10, Sort.by("sellingPrice").ascending());   //Sort.by("sellingPrice").ascending() → Sorts low to high.
                case "price_high" ->
                            PageRequest.of(pageNumber != null ? pageNumber : 0, 10, Sort.by("sellingPrice").descending());   //high to low
                default -> PageRequest.of(pageNumber != null ? pageNumber : 0, 10, Sort.unsorted());
            };
        } else { //(if null)
            pageable = PageRequest.of(pageNumber != null ? pageNumber : 0, 10, Sort.unsorted());
        }

        return productRepo.findAll(spec, pageable);

    }

    @Override
    public List<Product> recentlyAddedProduct() {
        return List.of();
    }

    @Override
    public List<Product> getProductBySellerId(Long sellerId) {
        return productRepo.findBySellerId(sellerId);
    }
}
