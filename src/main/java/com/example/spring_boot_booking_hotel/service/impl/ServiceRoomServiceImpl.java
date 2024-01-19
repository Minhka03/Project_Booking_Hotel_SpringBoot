package com.example.spring_boot_booking_hotel.service.impl;

import com.example.spring_boot_booking_hotel.dto.ServiceRoomDTO;
import com.example.spring_boot_booking_hotel.model.*;
import com.example.spring_boot_booking_hotel.repository.RoomRepository;
import com.example.spring_boot_booking_hotel.repository.ServiceRepository;
import com.example.spring_boot_booking_hotel.repository.ServiceRoomRepository;
import com.example.spring_boot_booking_hotel.service.ServiceRoomService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public  class ServiceRoomServiceImpl implements ServiceRoomService {

    private  final ServiceRoomRepository serviceRoomRepository;
    private  final ModelMapper modelMapper;
    private  final RoomRepository roomRepository;
    private final ServiceRepository serviceRepository;

    @Override
    public List<Service_Room> finAll() {
        return this.serviceRoomRepository.findAll();
    }

    @Override
    public Service_Room getById(Integer id) {
        return this.serviceRoomRepository.findById(id).orElse(null);
    }

    @Override
    public Service_Room save(Service_Room value) {
        return this.serviceRoomRepository.save(value);
    }

    @Override
    public Service_Room update(Service_Room value) {
        return this.serviceRoomRepository.save(value);
    }

    @Override
    public void delete(Service_Room value) {
        this.serviceRoomRepository.delete(value);
    }

    @Override
    public Page<Service_Room> page(Integer pageSize, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageSize, pageNo);
        return  this.serviceRoomRepository.findAll(pageable);
    }

    @Override
    public List<Service_Room> search(String name) {
        return null;
    }

    @Override
    public Page<Service_Room> searchPage(String keyword, Integer pageNo, Integer pageSize) {
        return null;
    }

    @Override
    public Service_Room insert_ServiceRoom(ServiceRoomDTO serviceRomDTO) throws IOException {
        Service_Room serviceRoom = modelMapper.map(serviceRomDTO, Service_Room.class);
        serviceRoom.setId(null);
        Services services = serviceRepository.findById(serviceRomDTO.getService_id()).orElse(null);
        Room room = roomRepository.findById(serviceRomDTO.getRoom_id()).orElse(null);
        if(services==null) {
            // Ném ngoại lệ tùy chỉnh nếu không tìm thấy booking
            throw new UsernameNotFoundException("Service not found with id: " + serviceRomDTO.getService_id());
        }
        if(room==null) {
            // Ném ngoại lệ tùy chỉnh nếu không tìm thấy room
            throw new UsernameNotFoundException("Room not found with id: " + serviceRomDTO.getRoom_id());
        }
        serviceRoom.setService(services);
        serviceRoom.setRoom(room);
        return serviceRoomRepository.save(serviceRoom);
    }

}
