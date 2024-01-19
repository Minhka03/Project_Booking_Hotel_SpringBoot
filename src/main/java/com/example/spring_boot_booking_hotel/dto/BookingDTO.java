package com.example.spring_boot_booking_hotel.dto;

import com.example.spring_boot_booking_hotel.model.GennericEntity;
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
public class BookingDTO extends GennericEntity {

    @NotEmpty(message = "Trường này không để trống")
    private  String name;
    @NotEmpty(message = "Trường này không để trống")
    private String sure_name;
    @NotEmpty(message = "Trường này không để trống ")
    private String city;
    @NotEmpty(message = "Trường này không để trống")
    private String country;
    @NotEmpty(message = "Trường này không để trống")
    private String zipcode;
    @NotEmpty(message = "Trường này không để trống")
    private String note;
    @NotNull(message = "Trường này không để trống")
    private Integer coupon;
    @NotNull(message = "Trường này không để trống")
    private Boolean status;
    @NotNull(message = "Trường này không để trống")
    private  Integer user_id;
}
