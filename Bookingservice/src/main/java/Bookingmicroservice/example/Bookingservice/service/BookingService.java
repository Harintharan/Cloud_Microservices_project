package Bookingmicroservice.example.Bookingservice.service;

import Bookingmicroservice.example.Bookingservice.domain.BookingStatus;
import Bookingmicroservice.example.Bookingservice.dto.BookingRequest;
import Bookingmicroservice.example.Bookingservice.dto.SaloonDTO;
import Bookingmicroservice.example.Bookingservice.dto.ServiceDTO;
import Bookingmicroservice.example.Bookingservice.dto.UserDTO;
import Bookingmicroservice.example.Bookingservice.model.Booking;
import Bookingmicroservice.example.Bookingservice.model.PaymentOrder;
import Bookingmicroservice.example.Bookingservice.model.SaloonReport;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface BookingService {

     Booking  createBooking(BookingRequest booking, UserDTO userDTO, SaloonDTO saloonDTO, Set<ServiceDTO> serviceDTOSet) throws Exception;

     List<Booking> getBookingsByCustomerId(Long customerId);
     List<Booking> getBookingBySaloon(Long saloonId);

     Booking getBookingById(Long id) throws Exception;
     Booking updateBooking(Long bookingId, BookingStatus bookingStatus) throws Exception;
     List<Booking> getBookingsByDate(LocalDate date, Long saloonId);

     SaloonReport getSaloonReport(Long saloonId);

     Booking bookingSuccess(PaymentOrder order) throws Exception;


}
