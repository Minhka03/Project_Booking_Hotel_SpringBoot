package com.example.spring_boot_booking_hotel.repository;

import com.example.spring_boot_booking_hotel.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    @Query("SELECT h FROM Hotel h WHERE h.name LIKE %:name%")
    List<Hotel> finByName(String name);
}
