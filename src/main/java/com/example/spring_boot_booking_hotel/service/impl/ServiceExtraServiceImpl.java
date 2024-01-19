package com.example.spring_boot_booking_hotel.service.impl;

import com.example.spring_boot_booking_hotel.dto.ServiceExtraDTO;
import com.example.spring_boot_booking_hotel.exception.base.NotFoundException;
import com.example.spring_boot_booking_hotel.model.Service_Extra;
import com.example.spring_boot_booking_hotel.model.Services;
import com.example.spring_boot_booking_hotel.repository.ServiceExtraRepository;
import com.example.spring_boot_booking_hotel.repository.ServiceRepository;
import com.example.spring_boot_booking_hotel.service.Service_ExtraService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ServiceExtraServiceImpl implements  Service_ExtraService{

    private final ServiceExtraRepository serviceExtraRepository;
    private  final ServiceRepository serviceRepository;
    private final ModelMapper modelMapper;


    @Override
    public List<Service_Extra> finAll() {
        return this.serviceExtraRepository.findAll();
    }

    @Override
    public Service_Extra getById(Integer id) {
        return this.serviceExtraRepository.findById(id).orElse(null);
    }

    @Override
    public Service_Extra save(Service_Extra value) {
        return this.serviceExtraRepository.save(value);
    }

    @Override
    public Service_Extra update(Service_Extra value) {
        return this.serviceExtraRepository.save(value);
    }

    @Override
    public void delete(Service_Extra value) {
        this.serviceExtraRepository.delete(value);
    }

    @Override
    public Page<Service_Extra> page(Integer pageSize, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageSize, pageNo);
        return  this.serviceExtraRepository.findAll(pageable);
    }

    @Override
    public List<Service_Extra> search(String name) {
        return this.serviceExtraRepository.finByName(name);
    }

    @Override
    public Page<Service_Extra> searchPage(String keyword, Integer pageNo, Integer pageSize) {
        List<Service_Extra> list = this.search(keyword);
        Pageable pageable = PageRequest.of(pageNo , pageSize);
        int start = (int) pageable.getOffset();
        int end = (int) ((pageable.getOffset()+ pageable.getPageSize())> list.size() ? list.size() : pageable.getOffset()+ pageable.getPageSize());
        list = list.subList(start, end);
        return  new PageImpl<Service_Extra>(list, pageable, list.size());
    }

    @Override
    public Service_Extra insert_ServiceExtra(ServiceExtraDTO serviceExtraDTO) throws IOException, NotFoundException {
        Service_Extra serviceExtra = modelMapper.map(serviceExtraDTO, Service_Extra.class);
        serviceExtra.setId(null);
        Services services = serviceRepository.findById(serviceExtraDTO.getService_id()).orElse(null);
        if (services == null) {
            // Ném ngoại lệ tùy chỉnh nếu không tìm thấy user
            System.out.println(1);
            throw new NotFoundException("Service not found with id: " + serviceExtraDTO.getService_id());
        }
        serviceExtra.setService(services);
        return serviceExtraRepository.save(serviceExtra);
    }
}
