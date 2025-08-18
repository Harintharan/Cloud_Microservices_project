package payment.example.paymentMicroserviceforsaloonProject.messaging;


import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import payment.example.paymentMicroserviceforsaloonProject.payload.dto.NotificationDTO;

@Component
@RequiredArgsConstructor
public class NotificationEventProducer {

    private final RabbitTemplate rabbitTemplate;
    public void sentNotification(Long bookingId,Long userId, Long saloonId){

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setBookingId(bookingId);
        notificationDTO.setUserId(userId);
        notificationDTO.setSaloonId(saloonId);
        notificationDTO.setDescription("new booking got confirmed");
        notificationDTO.setType("BOOKING");

        rabbitTemplate.convertAndSend("notification-queue", notificationDTO);



    }
}
