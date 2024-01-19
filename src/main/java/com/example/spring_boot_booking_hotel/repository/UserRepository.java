package com.example.spring_boot_booking_hotel.repository;

import com.example.spring_boot_booking_hotel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.username LIKE %:username%")
    User findByUsername(String username);
}
