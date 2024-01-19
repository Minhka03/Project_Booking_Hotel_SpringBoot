package com.example.spring_boot_booking_hotel.service.impl;

import com.example.spring_boot_booking_hotel.dto.BookingDTO;
import com.example.spring_boot_booking_hotel.exception.base.NotFoundException;
import com.example.spring_boot_booking_hotel.model.Booking;
import com.example.spring_boot_booking_hotel.model.User;
import com.example.spring_boot_booking_hotel.repository.BookingRepository;
import com.example.spring_boot_booking_hotel.repository.UserRepository;
import com.example.spring_boot_booking_hotel.service.BookingService;
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
public class BookingServiceImpl implements BookingService {

    private  final BookingRepository bookingRepository;
    private final ModelMapper modelMapper;
    private  final UserRepository userRepository;

    @Override
    public List<Booking> finAll() {
        return this.bookingRepository.findAll();
    }

    @Override
    public Booking getById(Integer id) {
        return this.bookingRepository.findById(id).orElse(null);
    };

    @Override
    public Booking save(Booking value) {
        return this.bookingRepository.save(value);
    }

    @Override
    public Booking update(Booking value) {
        return this.bookingRepository.save(value);
    }

    @Override
    public void delete(Booking value) {
        this.bookingRepository.delete(value);
    }

    @Override
    public Page<Booking> page(Integer pageSize, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageSize, pageNo);
        return  this.bookingRepository.findAll(pageable);
    }
    @Override
    public List<Booking> search(String name) {
        return this.bookingRepository.finByName(name);
    }
    @Override
    public Page<Booking> searchPage(String keyword, Integer pageNo, Integer pageSize) {
        List<Booking> list = this.search(keyword);
        Pageable pageable = PageRequest.of(pageNo , pageSize);
        int start = (int) pageable.getOffset();
        int end = (int) ((pageable.getOffset()+ pageable.getPageSize()) > list.size() ? list.size() : pageable.getOffset()+ pageable.getPageSize());
        list = list.subList(start, end);
        return  new PageImpl<Booking>(list, pageable, list.size());
    }

    @Override
    public Booking insert_Booking(BookingDTO bookingDTO) throws NotFoundException {
        Booking booking = modelMapper.map(bookingDTO, Booking.class);
        booking.setId(null);
        User user = userRepository.findById(bookingDTO.getUser_id()).orElse(null);
        System.out.println(userRepository.findById(bookingDTO.getUser_id()));
        if (user == null) {
            // Ném ngoại lệ tùy chỉnh nếu không tìm thấy user
            throw new NotFoundException("User not found with id: " + bookingDTO.getUser_id());
        }
        booking.setUser(user);
        return bookingRepository.save(booking);
    }
}
