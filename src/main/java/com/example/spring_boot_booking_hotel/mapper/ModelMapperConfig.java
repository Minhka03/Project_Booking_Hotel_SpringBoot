package com.example.spring_boot_booking_hotel.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper;
        modelMapper = new ModelMapper();
        return  modelMapper;
    }
}
