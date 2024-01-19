package com.example.spring_boot_booking_hotel.repository;

import com.example.spring_boot_booking_hotel.model.Booking;
import com.example.spring_boot_booking_hotel.model.BookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingDetailRepository extends JpaRepository<BookingDetail, Integer> {

}
