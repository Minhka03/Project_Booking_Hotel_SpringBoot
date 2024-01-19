package com.example.spring_boot_booking_hotel.service;

import com.example.spring_boot_booking_hotel.dto.BookingDTO;
import com.example.spring_boot_booking_hotel.exception.base.NotFoundException;
import com.example.spring_boot_booking_hotel.model.Booking;
import org.springframework.stereotype.Service;

@Service
public interface BookingService extends GennericService<Booking, Integer>{
    Booking insert_Booking(BookingDTO bookingDTO) throws NotFoundException;

}
