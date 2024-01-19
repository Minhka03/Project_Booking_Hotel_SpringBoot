package com.example.spring_boot_booking_hotel.service;

import com.example.spring_boot_booking_hotel.model.Hotel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface HotelService extends GennericService<Hotel, Integer> {

}
