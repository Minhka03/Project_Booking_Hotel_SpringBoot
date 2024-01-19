package com.example.spring_boot_booking_hotel.model;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name="rooms")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Room extends  GennericEntity{
    private String name;
    private Integer guests;
    private String image;
    private Integer per_night;
    private Float week_price;
    private Float day_price;
    private Float sale_price;
    private Boolean status;
    @ManyToOne
    @JoinColumn(name = "hotel_id", referencedColumnName = "id")
    private Hotel hotel;



}
