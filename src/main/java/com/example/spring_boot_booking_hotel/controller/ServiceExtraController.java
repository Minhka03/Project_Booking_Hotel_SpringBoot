package com.example.spring_boot_booking_hotel.controller;

import com.example.spring_boot_booking_hotel.dto.ServiceExtraDTO;
import com.example.spring_boot_booking_hotel.exception.base.NotFoundException;
import com.example.spring_boot_booking_hotel.model.Result;
import com.example.spring_boot_booking_hotel.model.Service_Extra;
import com.example.spring_boot_booking_hotel.service.Service_ExtraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class ServiceExtraController {

    @Qualifier("service_ExtraService")
    private  final Service_ExtraService serviceExtraService;
    private final ModelMapper modelMapper;
    @GetMapping("/serviceExtra")
    ResponseEntity<Result> getAll(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(value = "pageSize", defaultValue = "2") Integer pageSize){
        Page<Service_Extra> list = serviceExtraService.page(pageNo, pageSize);
        if(!list.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(new Result(true, "success", list));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result(false, "not success", null));
        }
    }

    @GetMapping("/serviceExtra/{id}")
    ResponseEntity<?> getByServiceExtra( @PathVariable Integer id){
        Service_Extra getServiceExtra = serviceExtraService.getById(id);
        if(getServiceExtra!=null){
            return  ResponseEntity.status(HttpStatus.OK).body(new Result(true, "success", getServiceExtra));
        }
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result(false, "not success", null));
    }

    @PostMapping("/serviceExtra")
    ResponseEntity<?> insertServiceExtra(@Valid @RequestBody ServiceExtraDTO serviceExtraDTO, BindingResult result) throws IOException, NotFoundException {
        if(result.hasErrors()){
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Result(false, "Validation failed", errorMessages));
        }

        if (serviceExtraDTO.getService_id() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Result(false, "User ID does not exist", serviceExtraDTO));
        }

        Service_Extra serviceExtra_insert = serviceExtraService.insert_ServiceExtra(serviceExtraDTO);
        if (serviceExtra_insert == null || serviceExtra_insert.getService() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Result(false, "User ID does not exist", serviceExtra_insert));
        }

        ServiceExtraDTO serviceExtraDTO_insert = modelMapper.map(serviceExtra_insert, ServiceExtraDTO.class);
        serviceExtraDTO_insert.setService_id(serviceExtra_insert.getService().getId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Result(true, "Success", serviceExtraDTO_insert));
    }

    @PutMapping("/serviceExtra/{id}")
    ResponseEntity<?> updateServiceExtra(@Valid @RequestBody ServiceExtraDTO serviceExtraDTO, BindingResult result, @PathVariable Integer id ) throws Exception {
        // Kiểm tra lỗi
        if(result.hasErrors()){
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Result(false, "Validation failed", errorMessages));
        }
        // Lấy thông tin khách sạn hiện tại
        ServiceExtraDTO currentserviceExtraDTO = modelMapper.map(serviceExtraService.getById(id), ServiceExtraDTO.class);
        if (currentserviceExtraDTO != null) {
            // Cập nhật thông tin khách sạn nếu khách sạn hiện tại tồn tại
            modelMapper.map(currentserviceExtraDTO, ServiceExtraDTO.class);
            currentserviceExtraDTO.setName(serviceExtraDTO.getName());
            currentserviceExtraDTO.setService_id(serviceExtraDTO.getService_id());
            Service_Extra serviceExtraUpdate = serviceExtraService.insert_ServiceExtra(currentserviceExtraDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new Result(true, " success", serviceExtraUpdate));
        } else {
            // Tạo mới khách sạn nếu khách sạn hiện tại không tồn tại
            serviceExtraDTO.setId(id);
            Service_Extra createdServiceExtra = serviceExtraService.update(modelMapper.map(serviceExtraDTO, Service_Extra.class));
            return ResponseEntity.status(HttpStatus.OK).body(new Result(true, " success", createdServiceExtra));
        }
    }

}
