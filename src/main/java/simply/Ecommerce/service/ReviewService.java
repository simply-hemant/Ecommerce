package simply.Ecommerce.service;

import simply.Ecommerce.exception.ReviewNotFoundException;
import simply.Ecommerce.model.Product;
import simply.Ecommerce.model.Review;
import simply.Ecommerce.model.User;
import simply.Ecommerce.request.CreateReviewRequest;

import javax.naming.AuthenticationException;
import java.util.List;

public interface ReviewService {

    Review createReview(CreateReviewRequest req,
                        User user,
                        Product product);

    List<Review> getReviewsByProductId(Long productId);

    Review updateReview(Long reviewId,
                        String reviewText,
                        double rating,
                        Long userId) throws ReviewNotFoundException, AuthenticationException;

    void deleteReview(Long reviewId, Long userId) throws ReviewNotFoundException, AuthenticationException;

}
