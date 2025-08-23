package payment.example.paymentMicroserviceforsaloonProject.Service;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import payment.example.paymentMicroserviceforsaloonProject.domain.PaymentMethode;

import payment.example.paymentMicroserviceforsaloonProject.model.PaymentOrder;
import payment.example.paymentMicroserviceforsaloonProject.payload.dto.BookingDTO;
import payment.example.paymentMicroserviceforsaloonProject.payload.dto.UserDTO;
import payment.example.paymentMicroserviceforsaloonProject.payload.response.PaymentLinkResponse;

public interface PaymentService {

    PaymentLinkResponse createOrder(UserDTO user,
                                    BookingDTO booking,
                                    PaymentMethode paymentMethode) throws RazorpayException, StripeException;


    PaymentOrder getPaymentOrderById(Long id) throws Exception;

    PaymentOrder getPaymentOrderByPaymentId(String paymentId);

    PaymentLink createRazorpayPaymentLink(UserDTO user, Long amount, Long orderId) throws RazorpayException;

    String createStripePaymentLink(UserDTO user, Long amount, Long orderId) throws StripeException;

    Boolean proceedPayment(PaymentOrder paymentOrder,String paymentId,String paymentLinkId) throws RazorpayException, StripeException;

    Boolean proceedPaymentStripe(PaymentOrder paymentOrder) throws StripeException;


}
