package payment.example.paymentMicroserviceforsaloonProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import payment.example.paymentMicroserviceforsaloonProject.model.PaymentOrder;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {

    PaymentOrder findByPaymentLinkId(String paymentLinkId);
}
