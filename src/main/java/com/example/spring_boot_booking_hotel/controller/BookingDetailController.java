package com.example.spring_boot_booking_hotel.controller;

import com.example.spring_boot_booking_hotel.dto.BookingDetailDTO;
import com.example.spring_boot_booking_hotel.model.BookingDetail;
import com.example.spring_boot_booking_hotel.model.Result;
import com.example.spring_boot_booking_hotel.service.BookingDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor

public class BookingDetailController {
    private final BookingDetailService bookingDetailService;
    private final ModelMapper modelMapper;
    @GetMapping("/bookingDetails")
    ResponseEntity<Result> getAll(){
        List<BookingDetail> list = bookingDetailService.finAll();
        if(!list.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(new Result(true, "success", list));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result(false, "not success", null));
        }
    }

    @GetMapping("/bookingDetails/{id}")
    ResponseEntity<?> getByBookingDetailId( @PathVariable Integer id){
        BookingDetail getBookingDetails = bookingDetailService.getById(id);
        if(getBookingDetails!=null){
            return  ResponseEntity.status(HttpStatus.OK).body(new Result(true, "success", getBookingDetails));
        }
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result(false, "not success", null));
    }

    @PostMapping("/bookingDetails")
    ResponseEntity<?> insertBookingDetails(@Valid @RequestBody BookingDetailDTO bookingDetailDTO, BindingResult result) {
        if(result.hasErrors()){
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Result(false, "Validation failed", errorMessages));
        }
        if (bookingDetailDTO.getBooking_id() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Result(false, "User ID does not exist", bookingDetailDTO));
        }

        if (bookingDetailDTO.getRoom_id() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Result(false, "User ID does not exist", bookingDetailDTO));
        }

        BookingDetail bookingDetail_insert = bookingDetailService.insert_BookingDetail(bookingDetailDTO);
        if (bookingDetail_insert == null || bookingDetail_insert.getBooking() == null || bookingDetail_insert.getRoom()==null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Result(false, "User ID does not exist", bookingDetail_insert));
        }
        BookingDetailDTO bookingDetailDTO_insert = modelMapper.map(bookingDetail_insert, BookingDetailDTO.class);
        bookingDetailDTO_insert.setBooking_id(bookingDetail_insert.getBooking().getId());
        bookingDetailDTO_insert.setRoom_id(bookingDetail_insert.getRoom().getId());
        bookingDetailService.insert_BookingDetail(bookingDetailDTO_insert);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Result(true, "Success", bookingDetailDTO_insert));
    }

    @PutMapping("/bookingDetails/{id}")
    ResponseEntity<?> updateRoom(@Valid @RequestBody BookingDetailDTO bookingDetailDTO, BindingResult result, @PathVariable Integer id ) throws Exception {
        // Kiểm tra lỗi
        if(result.hasErrors()){
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Result(false, "Validation failed", errorMessages));
        }
        // Lấy thông tin khách sạn hiện tại
        BookingDetailDTO currentBookingDetailDTO = modelMapper.map(bookingDetailService.getById(id), BookingDetailDTO.class);
        if (currentBookingDetailDTO != null) {
            // Cập nhật thông tin khách sạn nếu khách sạn hiện tại tồn tại
            modelMapper.map(currentBookingDetailDTO, BookingDetailDTO.class);
            currentBookingDetailDTO.setCheck_in(bookingDetailDTO.getCheck_in());
            currentBookingDetailDTO.setCheck_out(bookingDetailDTO.getCheck_out());
            currentBookingDetailDTO.setBooking_id(bookingDetailDTO.getBooking_id());
            currentBookingDetailDTO.setRoom_id(bookingDetailDTO.getRoom_id());
            BookingDetail bookingDetailUpdate = bookingDetailService.insert_BookingDetail(currentBookingDetailDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new Result(true, " success", bookingDetailUpdate));
        } else {
            // Tạo mới khách sạn nếu khách sạn hiện tại không tồn tại
            bookingDetailDTO.setId(id);
            BookingDetail createdBookingDetail = bookingDetailService.update(modelMapper.map(bookingDetailDTO, BookingDetail.class));
            return ResponseEntity.status(HttpStatus.OK).body(new Result(true, " success", createdBookingDetail));
        }
    }

}
