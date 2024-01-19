package com.example.spring_boot_booking_hotel.dto;

import com.example.spring_boot_booking_hotel.model.Booking;
import com.example.spring_boot_booking_hotel.model.GennericEntity;
import com.example.spring_boot_booking_hotel.model.Room;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookingDetailDTO extends GennericEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;
    @NotNull(message = "Trường này không để trống")
    private LocalDate check_in;
    @NotNull(message = "Trường này không để trống")
    private LocalDate check_out;
    @NotNull(message = "Trường này không để trống")
    private Integer booking_id;
    @NotNull(message = "Trường này không để trống")
    private Integer room_id;
}
