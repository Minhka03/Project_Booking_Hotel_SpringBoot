package com.example.spring_boot_booking_hotel.dto;

import com.example.spring_boot_booking_hotel.model.GennericEntity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class HotelDTO extends GennericEntity {
    @NotEmpty(message = "Tên danh mục không được để trống")
    @Length(min = 2 , message = "Tên danh mục tối thiểu 2 ký tự")
    private String name;
    @NotNull(message = "Bạn chưa chọn trạng thái")
    private Boolean status;
}
