package com.example.spring_boot_booking_hotel.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name="services")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Services extends  GennericEntity{
    private  String name;
}
