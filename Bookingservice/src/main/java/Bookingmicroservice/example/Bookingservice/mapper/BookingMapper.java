package Bookingmicroservice.example.Bookingservice.mapper;

import Bookingmicroservice.example.Bookingservice.dto.BookingDTO;
import Bookingmicroservice.example.Bookingservice.model.Booking;

public class BookingMapper {

    public static BookingDTO toDTO(Booking booking)
    {

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setCustomerId(booking.getCustomerId());
        bookingDTO.setStatus(booking.getStatus());
        bookingDTO.setEndTime(booking.getEndTime());
        bookingDTO.setStartTime(booking.getStartTime());
        bookingDTO.setSaloonId(booking.getSaloonId());
        bookingDTO.setServiceIds(booking.getServiceIds());
        bookingDTO.setTotalPrice(booking.getTotalPrice());

        return bookingDTO;
    }

}
