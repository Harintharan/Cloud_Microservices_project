package Bookingmicroservice.example.Bookingservice.controller;

import Bookingmicroservice.example.Bookingservice.domain.BookingStatus;
import Bookingmicroservice.example.Bookingservice.domain.PaymentMethode;
import Bookingmicroservice.example.Bookingservice.dto.*;
import Bookingmicroservice.example.Bookingservice.mapper.BookingMapper;
import Bookingmicroservice.example.Bookingservice.model.Booking;
import Bookingmicroservice.example.Bookingservice.model.SaloonReport;
import Bookingmicroservice.example.Bookingservice.service.BookingService;
import Bookingmicroservice.example.Bookingservice.service.client.PaymentFeignClient;
import Bookingmicroservice.example.Bookingservice.service.client.SaloonFeignClient;
import Bookingmicroservice.example.Bookingservice.service.client.ServiceOfferingFeignClient;
import Bookingmicroservice.example.Bookingservice.service.client.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final SaloonFeignClient saloonFeignClient;
    private final UserFeignClient userFeignClient;
    private final ServiceOfferingFeignClient serviceOfferingFeignClient;
    private final PaymentFeignClient paymentFeignClient;


@PostMapping
    public ResponseEntity<PaymentLinkResponse> createBooking(@RequestParam Long saloonId, @RequestParam PaymentMethode paymentMethode, @RequestBody BookingRequest bookingRequest, @RequestHeader("Authorization") String jwt) throws Exception {

        UserDTO user = userFeignClient.getUserProfile(jwt).getBody();

        SaloonDTO saloon = saloonFeignClient.getSaloonById(saloonId).getBody();


        Set<ServiceDTO> serviceDTOSet =serviceOfferingFeignClient.getServicesById(bookingRequest.getServiceIds()).getBody();

        if(serviceDTOSet.isEmpty())
        {
            throw new Exception("service not found.....");
        }

        Booking booking =bookingService.createBooking(bookingRequest,user,saloon,serviceDTOSet);

        BookingDTO bookingDTO = BookingMapper.toDTO(booking);


PaymentLinkResponse res=paymentFeignClient.createPaymentLink( bookingDTO,paymentMethode,jwt).getBody();


        return ResponseEntity.ok(res);


    }

@GetMapping("/customer")
    public ResponseEntity<Set<BookingDTO>> getBookingByCustomer(@RequestHeader("Authorization") String jwt) throws Exception {

        UserDTO user = userFeignClient.getUserProfile(jwt).getBody();

        if(user == null || user.getId()==null ){
            throw new Exception("user not found from jwt token");
        }
        List<Booking>  bookings = bookingService.getBookingsByCustomerId(user.getId());

        return ResponseEntity.ok(getBookingDTOs(bookings));
    }


    private Set<BookingDTO> getBookingDTOs (List<Booking> bookings)
    {

        return bookings.stream().map(booking -> { return BookingMapper.toDTO(booking);
        }).collect(Collectors.toSet());

    }


    @GetMapping("/saloon")
    public ResponseEntity<Set<BookingDTO>> getBookingsBySaloon (@RequestHeader("Authorization") String jwt) throws Exception {
        SaloonDTO saloonDTO = saloonFeignClient.getSaloonByOwnerId(jwt).getBody();

        List<Booking> bookings =bookingService.getBookingBySaloon(saloonDTO.getId());

        return ResponseEntity.ok(getBookingDTOs(bookings));
    }


    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long bookingId) throws Exception {

        Booking booking = bookingService.getBookingById(bookingId);

         return ResponseEntity.ok(BookingMapper.toDTO(booking));
    }




    @PutMapping("/{bookingId}/status")
    public ResponseEntity<BookingDTO> updateBookingStatus(@PathVariable Long bookingId, @RequestParam BookingStatus status) throws Exception {

        Booking booking = bookingService.updateBooking(bookingId,status);

        return ResponseEntity.ok(BookingMapper.toDTO(booking));
    }

    @GetMapping("/slots/saloon/{saloonId}/date/{date}")
    public ResponseEntity<List< BookingSlotDTO>> getBookedSlot(@PathVariable Long saloonId, @RequestParam  (required = false)LocalDate date){

       List<Booking>  bookings= bookingService.getBookingsByDate(date, saloonId);

       List<BookingSlotDTO> slotsDTOS = bookings.stream().map(booking -> { BookingSlotDTO bookingSlotDTO = new BookingSlotDTO();
       bookingSlotDTO.setStartTime(booking.getStartTime());
       bookingSlotDTO.setEndTime(booking.getEndTime());
       return bookingSlotDTO;}).collect(Collectors.toList());

       return ResponseEntity.ok(slotsDTOS);



    }

    @GetMapping("/report")
    public ResponseEntity<SaloonReport > getSaloonReport(@RequestHeader("Authorization")String jwt ) throws Exception {

        SaloonDTO saloonDTO = saloonFeignClient.getSaloonByOwnerId(jwt).getBody();



        SaloonReport saloonReport = bookingService. getSaloonReport(saloonDTO.getId());

        return ResponseEntity.ok(saloonReport);



    }












}
