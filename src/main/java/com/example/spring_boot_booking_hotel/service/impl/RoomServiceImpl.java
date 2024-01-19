package com.example.spring_boot_booking_hotel.service.impl;

import com.example.spring_boot_booking_hotel.dto.RoomDTO;
import com.example.spring_boot_booking_hotel.exception.base.NotFoundException;
import com.example.spring_boot_booking_hotel.model.Hotel;
import com.example.spring_boot_booking_hotel.model.Room;
import com.example.spring_boot_booking_hotel.repository.HotelRepository;
import com.example.spring_boot_booking_hotel.repository.RoomRepository;
import com.example.spring_boot_booking_hotel.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private  final ModelMapper modelMapper;
    private  final HotelRepository hotelRepository;
    @Override
    public List<Room> finAll() {
        return this.roomRepository.findAll();
    }

    @Override
    public Room getById(Integer id) {
        return this.roomRepository.findById(id).orElse(null);
    };

    @Override
    public Room save(Room value) {
        return this.roomRepository.save(value);
    }

    @Override
    public Room update(Room value) {
        return this.roomRepository.save(value);
    }

    @Override
    public void delete(Room value) {
        this.roomRepository.delete(value);
    }

    @Override
    public Page<Room> page(Integer pageSize, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageSize, pageNo);
        return  this.roomRepository.findAll(pageable);
    }
    @Override
    public List<Room> search(String name) {
        return this.roomRepository.finByName(name);
    }
    @Override
    public Page<Room> searchPage(String keyword, Integer pageNo, Integer pageSize) {
        List<Room> list = this.search(keyword);
        Pageable pageable = PageRequest.of(pageNo , pageSize);
        int start = (int) pageable.getOffset();
        int end = (int) ((pageable.getOffset()+ pageable.getPageSize())> list.size() ? list.size() : pageable.getOffset()+ pageable.getPageSize());
        list = list.subList(start, end);
        return  new PageImpl<Room>(list, pageable, list.size());
    }

    @Override
    public Room insert_Room(RoomDTO roomDTO) throws NotFoundException {
        Room room = modelMapper.map(roomDTO, Room.class);
        room.setId(null);
        Hotel hotel = hotelRepository.findById(roomDTO.getId_hotel()).orElse(null);
        if (hotel == null) {
            // Ném ngoại lệ tùy chỉnh nếu không tìm thấy user
            throw new NotFoundException("Hotel not found with id: " + roomDTO.getId_hotel());
        }
        room.setHotel(hotel);
        return roomRepository.save(room);
    }


}
