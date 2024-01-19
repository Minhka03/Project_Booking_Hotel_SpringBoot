package com.example.spring_boot_booking_hotel.dto;

import com.example.spring_boot_booking_hotel.model.GennericEntity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ServiceRoomDTO extends GennericEntity {
    @NotNull(message = "Trường này không để trống")
   private Integer room_id;
    @NotNull(message = "Trường này không để trống")
   private Integer service_id;
}
