package ReviewMicroservice.example.ReviewMicroservice.repository;

import ReviewMicroservice.example.ReviewMicroservice.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findBySaloonId(Long saloonId);



}
