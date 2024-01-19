package com.example.spring_boot_booking_hotel.dto;

import com.example.spring_boot_booking_hotel.model.GennericEntity;
import com.example.spring_boot_booking_hotel.model.Hotel;
import com.example.spring_boot_booking_hotel.validator.FileNotNull;
import com.example.spring_boot_booking_hotel.validator.ValidImage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoomDTO extends GennericEntity {

    @NotEmpty(message = "Trường này không được để trống")
    private String name;
    @NotNull(message ="Trường này không được để trống")
    private Integer guests;
    @NotEmpty(message = "Trường này không được để trống ")
//    @ValidImage(type = {"image/jpeg","image/png","image/jpg"},message = "Khong dung dinh dang nhe")
    private String image;
    @NotNull(message = "Trường này không được để trống")
    private Integer per_night;
    @NotNull(message = "Trường này không được để trống")
    private Float week_price;
    @NotNull(message = "Trường này không được để trống")
    private Float day_price;
    @NotNull(message = "Trường này không được để trống")
    private Float sale_price;
    private Boolean status;
    @NotNull(message = "Trường này không được để trống")
    private Integer id_hotel;

}
