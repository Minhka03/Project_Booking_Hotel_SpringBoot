package com.example.spring_boot_booking_hotel.service;

import com.example.spring_boot_booking_hotel.dto.ServiceExtraDTO;
import com.example.spring_boot_booking_hotel.exception.base.NotFoundException;
import com.example.spring_boot_booking_hotel.model.Service_Extra;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface Service_ExtraService extends  GennericService<Service_Extra , Integer> {
    Service_Extra insert_ServiceExtra(ServiceExtraDTO serviceExtraDTO) throws IOException, NotFoundException;
}
