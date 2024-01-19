package com.example.spring_boot_booking_hotel.model;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name="hotels")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Hotel extends  GennericEntity{
    private String name;
    private Boolean status;
}
