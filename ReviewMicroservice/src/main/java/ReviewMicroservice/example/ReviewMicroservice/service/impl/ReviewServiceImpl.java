package ReviewMicroservice.example.ReviewMicroservice.service.impl;

import ReviewMicroservice.example.ReviewMicroservice.model.Review;
import ReviewMicroservice.example.ReviewMicroservice.payload.dto.ReviewRequest;
import ReviewMicroservice.example.ReviewMicroservice.repository.ReviewRepository;
import ReviewMicroservice.example.ReviewMicroservice.service.ReviewService;
import com.microService.notifications.payload.dto.SaloonDTO;
import com.microService.notifications.payload.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public Review createReview(ReviewRequest req, UserDTO user, SaloonDTO saloon) {

        Review review = new Review();
        review.setReviewText(req.getReviewText());
        review.setRating(req.getRating());
        review.setUserId(user.getId());
        review.setSaloonId(saloon.getId());
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewsBySaloonId(Long saloonId) {
        return reviewRepository.findBySaloonId(saloonId);
    }


    private Review getReviewById(Long id) throws Exception {

        return reviewRepository.findById(id).orElseThrow(()-> new Exception("review not exist..."));

    }

    @Override
    public Review updateReview(ReviewRequest req, Long reviewId, Long userId) throws Exception {

        Review review=  getReviewById(reviewId);
        if(!review.getUserId().equals(userId)){
            throw new Exception("you don't have permission to update this review");
        }

        review.setReviewText(req.getReviewText());
        review.setRating(req.getRating());
        return reviewRepository.save(review);

    }

    @Override
    public void deleteReview(Long reviewId, Long userId) throws Exception {

        Review review=  getReviewById(reviewId);
        if(!review.getUserId().equals(userId)){
            throw new Exception("you don't have permission to delete this review");
        }
        reviewRepository.delete(review);

    }
}
