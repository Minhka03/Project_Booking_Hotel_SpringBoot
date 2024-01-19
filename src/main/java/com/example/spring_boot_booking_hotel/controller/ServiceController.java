package com.example.spring_boot_booking_hotel.controller;

import com.example.spring_boot_booking_hotel.model.Result;
import com.example.spring_boot_booking_hotel.model.Services;
import com.example.spring_boot_booking_hotel.service.Service_Service;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class ServiceController {
    @Qualifier("service_Service")
    private final Service_Service service_service;
    private final ModelMapper modelMapper;

    @GetMapping("/service")
    ResponseEntity<Result> getAll(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(value = "pageSize", defaultValue = "2") Integer pageSize){
        Page<Services> list = service_service.page(pageNo, pageSize);
        if(!list.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(new Result(true, "success", list));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result(false, "not success", null));
        }
    }
}
