package com.example.spring_boot_booking_hotel.exception.base;

import lombok.Data;

@Data
public class NotFoundException extends  Exception {
    public NotFoundException(String message) {
        super(message);
    }
}

