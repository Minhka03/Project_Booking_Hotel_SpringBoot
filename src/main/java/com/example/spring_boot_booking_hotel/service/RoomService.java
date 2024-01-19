package com.example.spring_boot_booking_hotel.service;

import com.example.spring_boot_booking_hotel.dto.RoomDTO;
import com.example.spring_boot_booking_hotel.exception.base.NotFoundException;
import com.example.spring_boot_booking_hotel.model.Room;
import org.springframework.stereotype.Service;

@Service
public interface RoomService extends  GennericService<Room, Integer>{
    Room insert_Room(RoomDTO roomDTO) throws NotFoundException;

}
