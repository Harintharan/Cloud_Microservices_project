package payment.example.paymentMicroserviceforsaloonProject.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentLinkResponse {


    private String payment_Link_url;
    private String getPayment_Link_id;
}
