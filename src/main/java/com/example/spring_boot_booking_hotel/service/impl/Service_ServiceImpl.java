package com.example.spring_boot_booking_hotel.service.impl;

import com.example.spring_boot_booking_hotel.model.Services;
import com.example.spring_boot_booking_hotel.repository.ServiceRepository;
import com.example.spring_boot_booking_hotel.service.Service_Service;
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
public class Service_ServiceImpl implements Service_Service {
    private  final ModelMapper modelMapper;
    private  final ServiceRepository serviceRepository;
    @Override
    public List<Services> finAll() {
        return this.serviceRepository.findAll();
    }

    @Override
    public Services getById(Integer id) {
        return this.serviceRepository.findById(id).orElse(null);
    }

    @Override
    public Services save(Services value) {
        return this.serviceRepository.save(value);
    }

    @Override
    public Services update(Services value) {
        return this.serviceRepository.save(value);
    }

    @Override
    public void delete(Services value) {
        this.serviceRepository.delete(value);
    }

    @Override
    public Page<Services> page(Integer pageSize, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageSize, pageNo);
        return  this.serviceRepository.findAll(pageable);
    }

    @Override
    public List<Services> search(String name) {
        return this.serviceRepository.finByName(name);
    }

    @Override
    public Page<Services> searchPage(String keyword, Integer pageNo, Integer pageSize) {
        List<Services> list = this.search(keyword);
        Pageable pageable = PageRequest.of(pageNo , pageSize);
        int start = (int) pageable.getOffset();
        int end = (int) ((pageable.getOffset()+ pageable.getPageSize())> list.size() ? list.size() : pageable.getOffset()+ pageable.getPageSize());
        list = list.subList(start, end);
        return  new PageImpl<Services>(list, pageable, list.size());
    }

}
