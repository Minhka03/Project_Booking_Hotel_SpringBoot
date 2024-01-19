package com.example.spring_boot_booking_hotel.controller;

import com.example.spring_boot_booking_hotel.dto.BookingDTO;
import com.example.spring_boot_booking_hotel.exception.base.NotFoundException;
import com.example.spring_boot_booking_hotel.model.Booking;
import com.example.spring_boot_booking_hotel.model.Result;
import com.example.spring_boot_booking_hotel.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final ModelMapper modelMapper;

    @GetMapping("/booking")
    ResponseEntity<Result> getAll(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(value = "pageSize", defaultValue = "2") Integer pageSize){
        Page<Booking> list = bookingService.page(pageNo, pageSize);
        if(!list.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(new Result(true, "success", list));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result(false, "not success", null));
        }
    }

    @GetMapping("/booking/{id}")
    ResponseEntity<?> getByBookingId( @PathVariable Integer id){
        Booking getBooking = bookingService.getById(id);
        if(getBooking!=null){
            return  ResponseEntity.status(HttpStatus.OK).body(new Result(true, "success", getBooking));
        }
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result(false, "not success", null));
    }

    @PostMapping("/booking")
    ResponseEntity<?> insertBooking(@Valid @RequestBody BookingDTO bookingDTO, BindingResult result) throws NotFoundException {
        if(result.hasErrors()){
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Result(false, "Validation failed", errorMessages));
        }

        if (bookingDTO.getUser_id() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Result(false, "User ID does not exist", bookingDTO));
        }

        Booking booking_insert = bookingService.insert_Booking(bookingDTO);
        if (booking_insert == null || booking_insert.getUser() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Result(false, "User ID does not exist", booking_insert));
        }

        BookingDTO bookingDTO_insert = modelMapper.map(booking_insert, BookingDTO.class);
        bookingDTO_insert.setUser_id(booking_insert.getUser().getId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Result(true, "Success", bookingDTO_insert));
    }



    @PutMapping("/booking/{id}")
    ResponseEntity<?> updateRoom(@Valid @RequestBody BookingDTO bookingDTO, BindingResult result, @PathVariable Integer id ) throws Exception {
        // Kiểm tra lỗi
        if(result.hasErrors()){
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Result(false, "Validation failed", errorMessages));
        }
        // Lấy thông tin khách sạn hiện tại
        BookingDTO currentBooking = modelMapper.map(bookingService.getById(id), BookingDTO.class);
        if (currentBooking != null) {
            // Cập nhật thông tin khách sạn nếu khách sạn hiện tại tồn tại
            modelMapper.map(currentBooking, BookingDTO.class);
            currentBooking.setName(bookingDTO.getName());
            currentBooking.setSure_name(bookingDTO.getSure_name());
            currentBooking.setCity(bookingDTO.getCity());
            currentBooking.setNote(bookingDTO.getNote());
            currentBooking.setCoupon(bookingDTO.getCoupon());
            currentBooking.setCountry(bookingDTO.getCountry());
            currentBooking.setZipcode(bookingDTO.getZipcode());
            currentBooking.setUser_id(bookingDTO.getUser_id());
            Booking bookingUpdate = bookingService.insert_Booking(currentBooking);
            return ResponseEntity.status(HttpStatus.OK).body(new Result(true, " success", bookingUpdate));
        } else {
            // Tạo mới khách sạn nếu khách sạn hiện tại không tồn tại
            bookingDTO.setId(id);
            Booking createdBooking = bookingService.update(modelMapper.map(bookingDTO, Booking.class));
            return ResponseEntity.status(HttpStatus.OK).body(new Result(true, " success", createdBooking));
        }
    }

    @GetMapping("/booking/search/{name}")
    ResponseEntity<Result> search(@PathVariable String name){
        List<Booking> data = bookingService.search(name);
        if(data!=null){
            return ResponseEntity.status(HttpStatus.OK).body(new Result(true, " success", data));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result(true, " success", null));
    }

    @GetMapping("booking/searchPage/{keyword}")
    ResponseEntity<?> searchPage(@PathVariable String keyword ,@RequestParam(value = "pageNo", defaultValue = "1")Integer pageNo, @RequestParam(value = "pageSize", defaultValue = "2") Integer pageSize){
        Page<Booking> data= bookingService.searchPage(keyword,pageNo, pageSize);
        if(data!=null){
            return ResponseEntity.status(HttpStatus.OK).body(new Result(true, " success", data));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result(true, " success", null));
    }




}
