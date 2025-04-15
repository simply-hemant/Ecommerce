package simply.Ecommerce.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import simply.Ecommerce.exception.ProductException;
import simply.Ecommerce.model.Product;
import simply.Ecommerce.model.Seller;
import simply.Ecommerce.request.CreateProductRequest;

import java.util.List;

@Service
public interface ProductService {

    Product createProduct(CreateProductRequest req,
                          Seller seller) throws ProductException;

    void deleteProduct(Long productId) throws ProductException;

    Product updateProduct(Long productId,Product product)throws ProductException;

    Product updateProductStock(Long productId)throws ProductException;


    Product findProductById(Long productId) throws ProductException;


    List<Product> searchProduct(String query);

    Page<Product> getAllProduct(String category,
                                String brand,
                                String colors,
                                String sizes,
                                Integer minPrice,
                                Integer maxPrice,
                                Integer minDiscount,
                                String sort,
                                String stock,
                                Integer pageNumber);

    public List<Product> recentlyAddedProduct();

    List<Product> getProductBySellerId(Long sellerId);

}
