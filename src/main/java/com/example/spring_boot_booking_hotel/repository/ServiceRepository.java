package com.example.spring_boot_booking_hotel.repository;

import com.example.spring_boot_booking_hotel.model.Room;
import com.example.spring_boot_booking_hotel.model.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Services, Integer> {
    @Query("SELECT s FROM Services s  WHERE s.name LIKE %:name%")
    List<Services> finByName(String name);
}
