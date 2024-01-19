package com.example.spring_boot_booking_hotel.dto;

import com.example.spring_boot_booking_hotel.model.GennericEntity;
import com.example.spring_boot_booking_hotel.model.Services;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ServiceExtraDTO extends GennericEntity {
    @NotEmpty(message = "Trường này không để trống")
    private  String name;
    @NotNull(message = "Trường này không để trống")
    private  Integer service_id;
    @ManyToOne
    @JoinColumn(name = "service_id", referencedColumnName = "id")
    private Services service;
}
