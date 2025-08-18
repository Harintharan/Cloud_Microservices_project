package ReviewMicroservice.example.ReviewMicroservice.service;

import ReviewMicroservice.example.ReviewMicroservice.model.Review;
import ReviewMicroservice.example.ReviewMicroservice.payload.dto.ReviewRequest;
import com.microService.notifications.payload.dto.SaloonDTO;
import com.microService.notifications.payload.dto.UserDTO;

import java.util.List;

public interface ReviewService {

    Review createReview(ReviewRequest req, UserDTO user, SaloonDTO saloon);

    List<Review> getReviewsBySaloonId(Long saloonId);

    Review  updateReview(ReviewRequest req, Long reviewId,Long userId) throws Exception;

    void deleteReview (Long reviewId, Long userId) throws Exception;




}
