package Bookingmicroservice.example.Bookingservice.messaging;

import Bookingmicroservice.example.Bookingservice.model.PaymentOrder;
import Bookingmicroservice.example.Bookingservice.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingEventConsumer {

    private final BookingService bookingService;

@RabbitListener(queues = "booking-queue")
    public void bookingUpdateListener (PaymentOrder paymentOrder) throws Exception {

        bookingService.bookingSuccess(paymentOrder);


    }


}
