package payment.example.paymentMicroserviceforsaloonProject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import payment.example.paymentMicroserviceforsaloonProject.domain.PaymentMethode;
import payment.example.paymentMicroserviceforsaloonProject.domain.PaymentOrderStatus;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentOrder {

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;



    @Column(nullable = false)
private Long amount;


    @Column(nullable = false)
private PaymentOrderStatus status=PaymentOrderStatus.PENDING;

@Column(nullable = false)

private PaymentMethode paymentMethode;


private String paymentLinkId;

    @Column(nullable = false)
private Long userId;
    @Column(nullable = false)
private Long bookingId;
    @Column(nullable = false)
private Long saloonId;






}
