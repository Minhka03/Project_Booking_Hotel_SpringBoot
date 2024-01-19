package com.example.spring_boot_booking_hotel.dto;

import com.example.spring_boot_booking_hotel.model.GennericEntity;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ServiceDTO extends GennericEntity {
    @NotEmpty(message = "Trường này không để trống")
    private  String name;
}
