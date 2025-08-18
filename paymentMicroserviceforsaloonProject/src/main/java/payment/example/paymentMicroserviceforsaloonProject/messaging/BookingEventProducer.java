package payment.example.paymentMicroserviceforsaloonProject.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import payment.example.paymentMicroserviceforsaloonProject.model.PaymentOrder;

@Component
@RequiredArgsConstructor
public class BookingEventProducer {

    private final RabbitTemplate rabbitTemplate;
    public void sentBookingUpdateEvent(PaymentOrder paymentOrder){

        rabbitTemplate.convertAndSend("booking-queue",paymentOrder);


    }
}
