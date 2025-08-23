package payment.example.paymentMicroserviceforsaloonProject.controller;

import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import payment.example.paymentMicroserviceforsaloonProject.Service.PaymentService;
import payment.example.paymentMicroserviceforsaloonProject.Service.client.UserFeignClient;
import payment.example.paymentMicroserviceforsaloonProject.domain.PaymentMethode;
import payment.example.paymentMicroserviceforsaloonProject.model.PaymentOrder;
import payment.example.paymentMicroserviceforsaloonProject.payload.dto.BookingDTO;
import payment.example.paymentMicroserviceforsaloonProject.payload.dto.UserDTO;
import payment.example.paymentMicroserviceforsaloonProject.payload.response.PaymentLinkResponse;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final UserFeignClient userFeignClient;

    @PostMapping("/create")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(@RequestBody BookingDTO booking,
                                                                 @RequestParam PaymentMethode paymentMethode, @RequestHeader("Authorization") String jwt) throws Exception {

        UserDTO user = userFeignClient.getUserProfile(jwt).getBody();


        PaymentLinkResponse response = paymentService.createOrder(user, booking, paymentMethode);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/{paymentOrderId}")
    public ResponseEntity<PaymentOrder> getPaymentOrderById(@PathVariable Long paymentOrderId) throws Exception {


        PaymentOrder paymentOrder = paymentService.getPaymentOrderById(paymentOrderId);

        return ResponseEntity.ok(paymentOrder);
    }


    @PatchMapping("/{proceed}")
    public ResponseEntity<Boolean> proceedPayment(@RequestParam String paymentId, @RequestParam String paymentLinkId) throws Exception {


        PaymentOrder paymentOrder = paymentService.getPaymentOrderByPaymentId(paymentLinkId);

        Boolean response = paymentService.proceedPayment(paymentOrder, paymentId, paymentLinkId);

        return ResponseEntity.ok(response);


    }


}
