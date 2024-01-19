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
public class UserDTO extends GennericEntity {

    private  Integer id;
    @NotEmpty(message = "Trường này ko để trống")
    private  String username;
    @NotEmpty(message = "Trường này ko để trống")
    private String email;
    @NotNull(message = "Trường này ko để trống")
    private String password;
    @NotEmpty(message = "Trường này ko để trống")
    private String phone;
    @NotEmpty(message = "Trường này ko để trống")
    private String address;
    @NotEmpty(message = "Trường này ko để trống")
    private String roles;
    @NotEmpty(message = "Trường này ko để trống")
    private String avatar;
}
