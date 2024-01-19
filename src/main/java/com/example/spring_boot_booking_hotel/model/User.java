package com.example.spring_boot_booking_hotel.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User extends GennericEntity{
    private  String username;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String roles;
    private String avatar;
}
