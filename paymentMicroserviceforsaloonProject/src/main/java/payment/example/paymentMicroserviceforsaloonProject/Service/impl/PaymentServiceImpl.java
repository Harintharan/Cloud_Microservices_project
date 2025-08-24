package payment.example.paymentMicroserviceforsaloonProject.Service.impl;

import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
//import lombok.Value;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import payment.example.paymentMicroserviceforsaloonProject.Repository.PaymentOrderRepository;
import payment.example.paymentMicroserviceforsaloonProject.Service.PaymentService;
import payment.example.paymentMicroserviceforsaloonProject.domain.PaymentMethode;
import payment.example.paymentMicroserviceforsaloonProject.domain.PaymentOrderStatus;
import payment.example.paymentMicroserviceforsaloonProject.messaging.BookingEventProducer;
import payment.example.paymentMicroserviceforsaloonProject.messaging.NotificationEventProducer;
import payment.example.paymentMicroserviceforsaloonProject.model.PaymentOrder;
import payment.example.paymentMicroserviceforsaloonProject.payload.dto.BookingDTO;
import payment.example.paymentMicroserviceforsaloonProject.payload.dto.UserDTO;
import payment.example.paymentMicroserviceforsaloonProject.payload.response.PaymentLinkResponse;

@Service
@RequiredArgsConstructor

public class PaymentServiceImpl implements PaymentService {

    private final PaymentOrderRepository paymentOrderRepository;
    private final BookingEventProducer bookingEventProducer;
    private final NotificationEventProducer notificationEventProducer;


    @Value("${stripe.api.secret}")
    private String stripeSecretKey;

    @Value("${razorpay.api.key}")
    private String razorpayApiKey;

    @Value("${razorpay.api.secret}")
    private String razorPayApiSecret;


    @Override
    public PaymentLinkResponse createOrder(UserDTO user, BookingDTO booking, PaymentMethode paymentMethode) throws RazorpayException, StripeException {
        Long amount = (long) booking.getTotalPrice();
        PaymentOrder paymentOrder = new PaymentOrder();

        paymentOrder.setAmount(amount);
        //  paymentOrder.setAmount(amount*100);
        paymentOrder.setPaymentMethode(paymentMethode);
        paymentOrder.setBookingId(booking.getId());
        paymentOrder.setSaloonId(booking.getSaloonId());

        paymentOrder.setUserId(user.getId());

        PaymentOrder savedOrder = paymentOrderRepository.save(paymentOrder);

        System.out.println(paymentOrder);

        PaymentLinkResponse paymentLinkResponse = new PaymentLinkResponse();
        if (paymentMethode.equals(PaymentMethode.RAZORPAY)) {
            PaymentLink payment = createRazorpayPaymentLink(user, savedOrder.getAmount(), savedOrder.getId());

            String paymentUrl = payment.get("short_url");

            String paymentUrlId = payment.get("id");

            paymentLinkResponse.setPayment_Link_url(paymentUrl);
            paymentLinkResponse.setGetPayment_Link_id(paymentUrlId);

            savedOrder.setPaymentLinkId(paymentUrlId);

            paymentOrderRepository.save(savedOrder);


        } else {

            String paymentUrl = createStripePaymentLink(user, savedOrder.getAmount(), savedOrder.getId());

            paymentLinkResponse.setPayment_Link_url(paymentUrl);
        }

        return paymentLinkResponse;


    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) throws Exception {
        PaymentOrder paymentOrder = paymentOrderRepository.findById(id).orElse(null);

        if (paymentOrder == null) {
            throw new Exception("payment order not found");


        }

        return paymentOrder;

    }

    @Override
    public PaymentOrder getPaymentOrderByPaymentId(String paymentId) {
        PaymentOrder paymentOrder = paymentOrderRepository.findByPaymentLinkId(paymentId);

        return paymentOrder;
    }

    @Override
    public PaymentLink createRazorpayPaymentLink(UserDTO user, Long Amount, Long orderId) throws RazorpayException {
        Long amount = Amount * 100;


        RazorpayClient razerpay = new RazorpayClient(razorpayApiKey, razorPayApiSecret);

        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount", amount);

        paymentLinkRequest.put("currency", "INR");


        JSONObject customer = new JSONObject();
        customer.put("name", user.getFullName());
        customer.put("email", user.getEmail());

        paymentLinkRequest.put("customer", customer);


        JSONObject notify = new JSONObject();
        notify.put("email", true);


        paymentLinkRequest.put("notify", notify);


        paymentLinkRequest.put("reminder_enable", true);

        paymentLinkRequest.put("callback_url", "http://localhost:3000/payment-success/" + orderId);

        // when deployed need to chnage deployed url

        paymentLinkRequest.put("callback_method", "get");

        return razerpay.paymentLink.create(paymentLinkRequest);


    }

    @Override
    public String createStripePaymentLink(UserDTO user, Long amount, Long orderId) throws StripeException {

        Stripe.apiKey = stripeSecretKey;

        SessionCreateParams params = SessionCreateParams.builder().addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT).setSuccessUrl("http://localhost:3000/payment-success/" + orderId)
                .setCancelUrl("http://localhost:3000/payment-cancel")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L).setPriceData(SessionCreateParams
                                .LineItem.PriceData.builder().setCurrency("usd")
                                .setUnitAmount(amount * 100)
                                .setProductData(SessionCreateParams
                                        .LineItem
                                        .PriceData
                                        .ProductData
                                        .builder().setName("service appointment booking").build()).build()

                        ).build()).build();

        Session session = Session.create(params);
        PaymentOrder existingOrder = paymentOrderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order ID"));

        existingOrder.setPaymentLinkId(session.getId()); // Stripe sessionId
        existingOrder.setStatus(PaymentOrderStatus.PENDING);

        paymentOrderRepository.save(existingOrder);




        return session.getUrl();
    }

    @Override
    public Boolean proceedPayment(PaymentOrder paymentOrder, String paymentId, String paymentLinkId) throws RazorpayException, StripeException {


        if (paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING)) {

            if (paymentOrder.getPaymentMethode().equals(PaymentMethode.RAZORPAY)) {
                RazorpayClient razorpay = new RazorpayClient(razorpayApiKey, razorPayApiSecret);

                Payment payment = razorpay.payments.fetch(paymentId);


                Integer amount = payment.get("amount");


                String status = payment.get("status");


                if (status.equals("captured")) {

                    // kafka

                    bookingEventProducer.sentBookingUpdateEvent(paymentOrder);
                    notificationEventProducer.sentNotification(
                            paymentOrder.getBookingId(),
                            paymentOrder.getUserId(),
                            paymentOrder.getSaloonId());

                    paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
                    paymentOrderRepository.save(paymentOrder);

                    return true;


                }

                return false;


            } else if (paymentOrder.getPaymentMethode().equals(PaymentMethode.STRIPE)) {
                return proceedPaymentStripe(paymentOrder);
            } else {


                paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
                paymentOrderRepository.save(paymentOrder);
                return true;

            }
        }

        return false;


    }

    @Override
    public Boolean proceedPaymentStripe(PaymentOrder paymentOrder) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

//        // Get checkout session
//        Session session = Session.retrieve(sessionId);
//
//        // From session, get PaymentIntent ID
//        String paymentIntentId = session.getPaymentIntent();
//
//        // Fetch PaymentIntent
//        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);


        // Use saved sessionId
        String sessionId = paymentOrder.getPaymentLinkId();
        Session session = Session.retrieve(sessionId);

        // Get PaymentIntent ID from session
        String paymentIntentId = session.getPaymentIntent();

        // Fetch PaymentIntent
        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);

        // Check status
        if ("succeeded".equals(paymentIntent.getStatus())) {

            System.out.println("true");
            bookingEventProducer.sentBookingUpdateEvent(paymentOrder);
            notificationEventProducer.sentNotification(
                    paymentOrder.getBookingId(),
                    paymentOrder.getUserId(),
                    paymentOrder.getSaloonId());

            paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
            paymentOrderRepository.save(paymentOrder);
            return true;
        }
        return false;
    }



}


