package com.example.spring_boot_booking_hotel.repository;

import com.example.spring_boot_booking_hotel.model.Service_Extra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceExtraRepository extends JpaRepository<Service_Extra , Integer> {
    @Query("SELECT s_extra FROM Service_Extra s_extra WHERE s_extra.name LIKE %:name%")
    List<Service_Extra> finByName(String name);
}
