package com.example.spring_boot_booking_hotel.service;

import com.example.spring_boot_booking_hotel.dto.ImageDTO;
import com.example.spring_boot_booking_hotel.dto.RoomDTO;
import com.example.spring_boot_booking_hotel.exception.base.NotFoundException;
import com.example.spring_boot_booking_hotel.model.Image;
import com.example.spring_boot_booking_hotel.model.Room;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface ImageService extends  GennericService<Image, Integer>{
    Image insert_Image(ImageDTO imageDTO) throws NotFoundException;
}
