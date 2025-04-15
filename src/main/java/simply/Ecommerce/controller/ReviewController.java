package simply.Ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import simply.Ecommerce.Response.ApiResponse;
import simply.Ecommerce.exception.ReviewNotFoundException;
import simply.Ecommerce.exception.UserException;
import simply.Ecommerce.model.Product;
import simply.Ecommerce.model.Review;
import simply.Ecommerce.model.User;
import simply.Ecommerce.repository.ProductRepo;
import simply.Ecommerce.repository.ReviewRepo;
import simply.Ecommerce.request.CreateReviewRequest;
import simply.Ecommerce.service.ProductService;
import simply.Ecommerce.service.ReviewService;
import simply.Ecommerce.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;
    private final ProductService productService;
    private final UserService userService;

    @GetMapping("/products/{productId}/reviews")
    public ResponseEntity<List<Review>> getReviewByProductId(
            @PathVariable Long productId){

        List<Review> reviews = reviewService.getReviewsByProductId(productId);
        return ResponseEntity.ok(reviews);

    }

    @PostMapping("/products/{productId}/reviews")
    public ResponseEntity<Review> writeReview(
            @RequestBody CreateReviewRequest req,
            @PathVariable Long productId,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Product product = productService.findProductById(productId);

        Review review = reviewService.createReview(
                req,
                user,
                product
        );

        return ResponseEntity.ok(review);
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<ApiResponse> deleteReview(
            @PathVariable Long reviewId,
            @RequestHeader("Authorization") String jwt) throws Exception, AuthenticationException, ReviewNotFoundException {

        User user = userService.findUserByJwtToken(jwt);

        reviewService.deleteReview(reviewId, user.getUserId());
        ApiResponse res = new ApiResponse();
        res.setMessage("Review deleted Successfully");
        res.setStatus(true);

        return ResponseEntity.ok(res);
    }

    @PatchMapping("/reviews/{reviewId}")
    public ResponseEntity<Review> updateReview(
            @PathVariable Long reviewId,
            @RequestBody CreateReviewRequest req,
            @RequestHeader("Authorization") String jwt) throws Exception, ReviewNotFoundException, AuthenticationException{

        User user = userService.findUserByJwtToken(jwt);

        Review review = reviewService.updateReview(
                reviewId,
                req.getReviewText(),
                req.getReviewRating(),
                user.getUserId()
        );
        return ResponseEntity.ok(review);

    }

}
