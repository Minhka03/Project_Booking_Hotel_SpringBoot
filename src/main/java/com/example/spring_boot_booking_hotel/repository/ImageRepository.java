package com.example.spring_boot_booking_hotel.repository;

import com.example.spring_boot_booking_hotel.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Integer> {
}
