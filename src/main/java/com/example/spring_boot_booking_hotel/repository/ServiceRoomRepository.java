package com.example.spring_boot_booking_hotel.repository;

import com.example.spring_boot_booking_hotel.model.Service_Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRoomRepository extends JpaRepository<Service_Room, Integer> {

}
