package com.example.spring_boot_booking_hotel.service;

import com.example.spring_boot_booking_hotel.dto.ServiceRoomDTO;
import com.example.spring_boot_booking_hotel.model.Service_Room;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface ServiceRoomService extends GennericService<Service_Room, Integer>{
    Service_Room insert_ServiceRoom(ServiceRoomDTO serviceRomDTO) throws IOException;
}
