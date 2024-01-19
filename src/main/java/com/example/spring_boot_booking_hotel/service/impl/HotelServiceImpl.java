package com.example.spring_boot_booking_hotel.service.impl;

import com.example.spring_boot_booking_hotel.model.Hotel;
import com.example.spring_boot_booking_hotel.repository.HotelRepository;
import com.example.spring_boot_booking_hotel.service.HotelService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    public HotelServiceImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public List<Hotel> finAll() {
        return this.hotelRepository.findAll();
    }

    @Override
    public Hotel getById(Integer id) {
        return this.hotelRepository.findById(id).orElse(null);
    };

    @Override
    public Hotel save(Hotel value) {
        return this.hotelRepository.save(value);
    }

    @Override
    public Hotel update(Hotel value) {
        return this.hotelRepository.save(value);
    }

    @Override
    public void delete(Hotel value) {
        this.hotelRepository.delete(value);
    }

    @Override
    public Page<Hotel> page(Integer pageSize, Integer pageNo) {
       Pageable pageable = PageRequest.of(pageSize, pageNo);
       return  this.hotelRepository.findAll(pageable);
    }
    @Override
    public List<Hotel> search(String name) {
        return this.hotelRepository.finByName(name);
    }
    @Override
    public Page<Hotel> searchPage(String keyword, Integer pageNo, Integer pageSize) {
        List<Hotel> list = this.search(keyword);
        Pageable pageable = PageRequest.of(pageNo , pageSize);
        int start = (int) pageable.getOffset();
        int end = (int) ((pageable.getOffset()+ pageable.getPageSize()) > list.size() ? list.size() : pageable.getOffset()+ pageable.getPageSize());
        list = list.subList(start, end);
        return  new PageImpl<Hotel>(list, pageable, list.size());
    }


}
