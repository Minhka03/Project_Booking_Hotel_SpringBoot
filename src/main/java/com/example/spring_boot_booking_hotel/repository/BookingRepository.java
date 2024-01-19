package com.example.spring_boot_booking_hotel.repository;

import com.example.spring_boot_booking_hotel.model.Booking;
import com.example.spring_boot_booking_hotel.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    @Query("SELECT b FROM Booking b WHERE b.name LIKE %:name%")
    List<Booking> finByName(String name);
}
