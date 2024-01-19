package com.example.spring_boot_booking_hotel.service;

import org.springframework.data.domain.Page;

import java.util.List;

public interface GennericService <T, K>{
    List<T> finAll();
    T getById(K id);
    T save(T value);
    T update(T value);
    void delete(T value);

    Page<T> page(Integer pageSize, Integer pageNo);

    List<T> search(String name);

    Page<T> searchPage(String keyword, Integer pageNo, Integer pageSize);






}
