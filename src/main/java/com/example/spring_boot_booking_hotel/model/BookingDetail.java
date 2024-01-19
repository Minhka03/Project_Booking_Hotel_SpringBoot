package com.example.spring_boot_booking_hotel.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "booking_details")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookingDetail extends  GennericEntity{

    private LocalDate check_in;
    private LocalDate check_out;
    @ManyToOne
    @JoinColumn(name="booking_id", referencedColumnName = "id")
    private Booking booking;

    @ManyToOne
    @JoinColumn(name="room_id", referencedColumnName = "id")
    private Room room;

}
