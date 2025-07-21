package simply.Ecommerce.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import simply.Ecommerce.exception.ReviewNotFoundException;
import simply.Ecommerce.model.Product;
import simply.Ecommerce.model.Review;
import simply.Ecommerce.model.User;
import simply.Ecommerce.repository.ReviewRepo;
import simply.Ecommerce.request.CreateReviewRequest;
import simply.Ecommerce.service.ReviewService;
import simply.Ecommerce.service.UserService;

import javax.naming.AuthenticationException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

     private final ReviewRepo reviewRepo;

    @Override
    public Review createReview(CreateReviewRequest req, User user, Product product) {

        Review newReview = new Review();

        newReview.setReviewText(req.getReviewText());
        newReview.setRating(req.getReviewRating());
        newReview.setProductImages(req.getProductImages());
        newReview.setUser(user);
        newReview.setProduct(product);

        product.getReviews().add(newReview);

        return reviewRepo.save(newReview);
    }

    @Override
    public List<Review> getReviewsByProductId(Long productId) {
        return reviewRepo.findReviewsByProduct_ProductId(productId);
    }

    @Override
    public Review updateReview(Long reviewId, String reviewText, double rating, Long userId) throws ReviewNotFoundException, AuthenticationException {

        Review review = reviewRepo.findById(reviewId).orElse(null);

        if(review == null){
            throw new ReviewNotFoundException("Review not found");
        }

        if(!review.getUser().getUserId().equals(userId)){
            throw new AuthenticationException("You do not have permission to delete this review");
        }

        review.setReviewText(reviewText);
        review.setRating(rating);

        return reviewRepo.save(review);
    }

    @Override
    public void deleteReview(Long reviewId, Long userId) throws ReviewNotFoundException, AuthenticationException {

        Review review = reviewRepo.findById(reviewId).orElse(null);

        if(review == null){
            throw new ReviewNotFoundException("Reviw not found");
        }

        if(!review.getUser().getUserId().equals(userId)){
            throw new AuthenticationException("You do not have permission to delete this review");
        }

        reviewRepo.delete(review);
    }
}
