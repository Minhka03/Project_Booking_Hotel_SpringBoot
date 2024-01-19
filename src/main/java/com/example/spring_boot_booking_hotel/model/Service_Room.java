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
@Table(name="services_rooms")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Service_Room extends  GennericEntity{
    @ManyToOne
    @JoinColumn(name = "service_id", referencedColumnName = "id")
    private Services service;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

}
