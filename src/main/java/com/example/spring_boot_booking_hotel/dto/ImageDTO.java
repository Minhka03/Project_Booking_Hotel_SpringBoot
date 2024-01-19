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
public class ImageDTO extends GennericEntity {
    @NotEmpty(message = "Trường này ko để trống")
    private  String name;
    @NotEmpty(message = "Bạn chưa chọn ảnh")
    private  String image;
    @NotNull(message = "Bạn chưa chọn phòng")
    private Integer room_id;
}
