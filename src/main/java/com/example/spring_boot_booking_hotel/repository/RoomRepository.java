package com.example.spring_boot_booking_hotel.repository;

import com.example.spring_boot_booking_hotel.model.Hotel;
import com.example.spring_boot_booking_hotel.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    @Query("SELECT r FROM Room r WHERE r.name LIKE %:name%")
    List<Room> finByName(String name);
}
