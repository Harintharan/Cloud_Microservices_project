package ReviewMicroservice.example.ReviewMicroservice.payload.dto;

import lombok.Data;

@Data
public class ReviewRequest {

    private String  reviewText;
    private double rating;


}
