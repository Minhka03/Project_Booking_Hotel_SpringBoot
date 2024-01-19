package com.example.spring_boot_booking_hotel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name="bookings")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Booking extends GennericEntity{

    private  String name;
    private String sure_name;
    private String city;
    private String country;
    private String zipcode;
    private String note;
    private Integer coupon;
    private Boolean status;
    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

}
