package ReviewMicroservice.example.ReviewMicroservice.controller;

import ReviewMicroservice.example.ReviewMicroservice.model.Review;
import ReviewMicroservice.example.ReviewMicroservice.payload.dto.ApiResponse;
import ReviewMicroservice.example.ReviewMicroservice.payload.dto.ReviewRequest;
import ReviewMicroservice.example.ReviewMicroservice.service.ReviewService;
import ReviewMicroservice.example.ReviewMicroservice.service.client.SaloonFeignClient;
import ReviewMicroservice.example.ReviewMicroservice.service.client.UserFeignClient;
import com.microService.notifications.payload.dto.SaloonDTO;
import com.microService.notifications.payload.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserFeignClient userFeignClient;
    private final SaloonFeignClient saloonFeignClient;
@PostMapping("/saloon/{saloonId}")
    public ResponseEntity<Review> createReview(
            @PathVariable Long saloonId,@RequestBody ReviewRequest req,
            @RequestHeader("Authorization") String jwt) throws Exception {

        UserDTO user =userFeignClient.getUserProfile(jwt).getBody();
    SaloonDTO saloon = saloonFeignClient.getSaloonById(saloonId).getBody();

    Review review = reviewService.createReview(req,user,saloon);

    return ResponseEntity.ok(review);



    }



    @GetMapping("/saloon/{saloonId}")
    public ResponseEntity<List<Review>> createReview(
            @PathVariable Long saloonId,
            @RequestHeader("Authorization") String jwt) throws Exception {


        SaloonDTO saloon = saloonFeignClient.getSaloonById(saloonId).getBody();

        List<Review> reviews = reviewService.getReviewsBySaloonId(saloon.getId());

        return ResponseEntity.ok(reviews);



    }




    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(
            @PathVariable Long reviewId,@RequestBody ReviewRequest req,
            @RequestHeader("Authorization") String jwt) throws Exception {

        UserDTO user =userFeignClient.getUserProfile(jwt).getBody();


        Review reviews = reviewService.updateReview(req,
                reviewId,
                user.getId());

        return ResponseEntity.ok(reviews);



    }


    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse> deleteReview(
            @PathVariable Long reviewId,@RequestHeader("Authorization") String jwt) throws Exception {

        UserDTO user =userFeignClient.getUserProfile(jwt).getBody();


        reviewService.deleteReview(reviewId,user.getId());

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Review deleted");


        return ResponseEntity.ok(apiResponse);



    }

}
