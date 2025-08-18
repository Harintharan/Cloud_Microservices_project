package Bookingmicroservice.example.Bookingservice.service.client;

import Bookingmicroservice.example.Bookingservice.dto.BookingDTO;
import Bookingmicroservice.example.Bookingservice.dto.PaymentLinkResponse;
import Bookingmicroservice.example.Bookingservice.domain.PaymentMethode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("PAYMENT-MICROSERVICE")
public interface PaymentFeignClient {



    @PostMapping("/api/payments/create")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(@RequestBody BookingDTO booking,
                                                                 @RequestParam PaymentMethode paymentMethode,@RequestHeader("Authorization") String jwt
    ) ;
}
