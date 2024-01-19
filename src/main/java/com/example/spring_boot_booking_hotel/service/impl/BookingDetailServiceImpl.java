package com.example.spring_boot_booking_hotel.service.impl;
import com.example.spring_boot_booking_hotel.dto.BookingDetailDTO;
import com.example.spring_boot_booking_hotel.model.Booking;
import com.example.spring_boot_booking_hotel.model.BookingDetail;
import com.example.spring_boot_booking_hotel.model.Room;
import com.example.spring_boot_booking_hotel.repository.BookingDetailRepository;
import com.example.spring_boot_booking_hotel.repository.BookingRepository;
import com.example.spring_boot_booking_hotel.repository.RoomRepository;
import com.example.spring_boot_booking_hotel.service.BookingDetailService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public  class BookingDetailServiceImpl implements BookingDetailService {
    private  final BookingDetailRepository bookingDetailRepository;
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<BookingDetail> finAll() {
        return this.bookingDetailRepository.findAll();
    }

    @Override
    public BookingDetail getById(Integer id) {
        return this.bookingDetailRepository.findById(id).orElse(null);
    }

    @Override
    public BookingDetail save(BookingDetail value) {
        return this.bookingDetailRepository.save(value);
    }

    @Override
    public BookingDetail update(BookingDetail value) {
        return this.bookingDetailRepository.save(value);
    }

    @Override
    public void delete(BookingDetail value) {
        this.bookingDetailRepository.delete(value);
    }

    @Override
    public Page<BookingDetail> page(Integer pageSize, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageSize, pageNo);
        return  this.bookingDetailRepository.findAll(pageable);
    }

    @Override
    public List<BookingDetail> search(String name) {
        return null;
    }

    @Override
    public Page<BookingDetail> searchPage(String keyword, Integer pageNo, Integer pageSize) {
        return null;
    }

    @Override
    public BookingDetail insert_BookingDetail(BookingDetailDTO bookingDetailDTO) {
        BookingDetail bookingDetail = modelMapper.map(bookingDetailDTO, BookingDetail.class);
        bookingDetail.setId(null);
        Booking booking = bookingRepository.findById(bookingDetailDTO.getBooking_id()).orElse(null);
        Room room = roomRepository.findById(bookingDetailDTO.getRoom_id()).orElse(null);
        if(booking==null) {
            // Ném ngoại lệ tùy chỉnh nếu không tìm thấy booking
            throw new UsernameNotFoundException("Booking not found with id: " + bookingDetailDTO.getBooking_id());
        }
        if(room==null) {
            // Ném ngoại lệ tùy chỉnh nếu không tìm thấy room
            throw new UsernameNotFoundException("Room not found with id: " + bookingDetailDTO.getRoom_id());
        }
        bookingDetail.setBooking(booking);
        bookingDetail.setRoom(room);
        return bookingDetailRepository.save(bookingDetail);
    }
}
