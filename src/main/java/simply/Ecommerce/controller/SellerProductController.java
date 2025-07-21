package simply.Ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simply.Ecommerce.exception.ProductException;
import simply.Ecommerce.model.Product;
import simply.Ecommerce.model.Seller;
import simply.Ecommerce.request.CreateProductRequest;
import simply.Ecommerce.service.ProductService;
import simply.Ecommerce.service.SellerService;

import java.util.List;

@RequestMapping("/sellers/product")
@RestController
@RequiredArgsConstructor
public class SellerProductController {

    private final ProductService productService;
    private final SellerService sellerService;

    @PostMapping
    public ResponseEntity<Product> createProduct(
            @RequestBody CreateProductRequest request,
            @RequestHeader("Authorization") String jwt) throws Exception{

        Seller seller = sellerService.getSellerProfile(jwt);

        Product product = productService.createProduct(request ,seller);

        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId){

        try{
            productService.deleteProduct(productId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ProductException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{product}/stock")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product product) {

        try {
            Product updateProduct = productService.updateProduct(productId, product);
            return new ResponseEntity<>(updateProduct, HttpStatus.OK);
        } catch (ProductException e) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProductBySellerId(
            @RequestHeader("Authorization") String jwt) throws Exception {

        Seller seller = sellerService.getSellerProfile(jwt);

        List<Product> products = productService.getProductBySellerId(seller.getId());
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}
