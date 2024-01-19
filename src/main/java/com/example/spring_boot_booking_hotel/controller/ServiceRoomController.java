package com.example.spring_boot_booking_hotel.controller;

import com.example.spring_boot_booking_hotel.dto.ServiceRoomDTO;
import com.example.spring_boot_booking_hotel.model.Result;
import com.example.spring_boot_booking_hotel.model.Service_Room;
import com.example.spring_boot_booking_hotel.service.ServiceRoomService;
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
public class ServiceRoomController {
    @Qualifier("serviceRoomService")
    private  final ServiceRoomService elementServiceRoom;
    private final ModelMapper modelMapper;
    @GetMapping("/serviceRoom")
    ResponseEntity<Result> getAll(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(value = "pageSize", defaultValue = "2") Integer pageSize){
        Page<Service_Room> list = elementServiceRoom.page(pageNo, pageSize);
        if(!list.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(new Result(true, "success", list));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result(false, "not success", null));
        }
    }

    @GetMapping("/serviceRoom/{id}")
    ResponseEntity<?> getByServiceRoomId( @PathVariable Integer id){
        Service_Room getServiceRoom = elementServiceRoom.getById(id);
        if(getServiceRoom!=null){
            return  ResponseEntity.status(HttpStatus.OK).body(new Result(true, "success", getServiceRoom));
        }
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result(false, "not success", null));
    }

    @PostMapping("/serviceRoom")
    ResponseEntity<?> insertBooking(@Valid @RequestBody ServiceRoomDTO serviceRoomDTO, BindingResult result) throws IOException {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Result(false, "Validation failed", errorMessages));
        }

        if (serviceRoomDTO.getService_id() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Result(false, "User ID does not exist", serviceRoomDTO));
        }
        if (serviceRoomDTO.getRoom_id() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Result(false, "User ID does not exist", serviceRoomDTO));
        }

        Service_Room serviceRoom_insert = elementServiceRoom.insert_ServiceRoom(serviceRoomDTO);
        if (serviceRoom_insert == null || serviceRoom_insert.getService() == null || serviceRoom_insert.getRoom()==null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Result(false, "User ID does not exist", serviceRoom_insert));
        }
        ServiceRoomDTO serviceRoomDTO_insert = modelMapper.map(serviceRoom_insert, ServiceRoomDTO.class);
        serviceRoomDTO_insert.setService_id(serviceRoom_insert.getService().getId());
        serviceRoomDTO_insert.setRoom_id(serviceRoom_insert.getRoom().getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Result(true, "Success", serviceRoomDTO_insert));
    }

    @PutMapping("/serviceRoom/{id}")
    ResponseEntity<?> updateRoom(@Valid @RequestBody ServiceRoomDTO serviceRoomDTO, BindingResult result, @PathVariable Integer id ) throws Exception {
        // Kiểm tra lỗi
        if(result.hasErrors()){
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Result(false, "Validation failed", errorMessages));
        }
        // Lấy thông tin khách sạn hiện tại
        ServiceRoomDTO currentServiceRoomDTO = modelMapper.map(elementServiceRoom.getById(id), ServiceRoomDTO.class);
        if (currentServiceRoomDTO != null) {
            // Cập nhật thông tin khách sạn nếu khách sạn hiện tại tồn tại
            modelMapper.map(currentServiceRoomDTO, ServiceRoomDTO.class);
            currentServiceRoomDTO.setService_id(serviceRoomDTO.getService_id());
            currentServiceRoomDTO.setRoom_id(serviceRoomDTO.getRoom_id());
            Service_Room ServiceRoomUpdate = elementServiceRoom.insert_ServiceRoom(currentServiceRoomDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new Result(true, " success", ServiceRoomUpdate));
        } else {
            // Tạo mới khách sạn nếu khách sạn hiện tại không tồn tại
            serviceRoomDTO.setId(id);
            Service_Room createdServiceRoom = elementServiceRoom.update(modelMapper.map(serviceRoomDTO, Service_Room.class));
            return ResponseEntity.status(HttpStatus.OK).body(new Result(true, " success", createdServiceRoom));
        }
    }

}
