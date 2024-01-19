package com.example.spring_boot_booking_hotel.service;
import com.example.spring_boot_booking_hotel.dto.BookingDetailDTO;
import com.example.spring_boot_booking_hotel.model.BookingDetail;
import org.springframework.stereotype.Service;

@Service
public interface BookingDetailService extends GennericService<BookingDetail, Integer>{
    BookingDetail insert_BookingDetail(BookingDetailDTO bookingDetailDTO);
}
