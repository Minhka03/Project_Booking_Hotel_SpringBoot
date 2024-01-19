package com.example.spring_boot_booking_hotel.controller;

import com.example.spring_boot_booking_hotel.dto.HotelDTO;
import com.example.spring_boot_booking_hotel.model.Hotel;
import com.example.spring_boot_booking_hotel.model.Result;
import com.example.spring_boot_booking_hotel.service.HotelService;
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
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;
    private final ModelMapper modelMapper;
    @GetMapping("/hotel")
    ResponseEntity<Result> getAll(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(value = "pageSize", defaultValue = "2") Integer pageSize){
        Page<Hotel> list = hotelService.page(pageNo, pageSize);
        if(!list.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(new Result(true, "success", list));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result(false, "not success", null));
        }
    }

    @GetMapping("/hotel/{id}")
    ResponseEntity<?> getByHotelId( @PathVariable Integer id){
        Hotel getHotel = hotelService.getById(id);
        if(getHotel!=null){
            return  ResponseEntity.status(HttpStatus.OK).body(new Result(true, "success", getHotel));
        }
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result(false, "not success", null));
    }
    @PostMapping("/hotel")
    ResponseEntity<?> insertHotel(@Valid @RequestBody HotelDTO newHotel, BindingResult result) {
        Hotel hotel =  modelMapper.map(newHotel, Hotel.class);
        if(result.hasErrors()){
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Result(false, "Validation failed", errorMessages));
        }
        hotelService.save(hotel);
        System.out.println(newHotel.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Result(true, "Success", hotel));
    }
    @PutMapping("/hotel/{id}")
    ResponseEntity<?> updateHotel(@Valid @RequestBody HotelDTO newHotel, BindingResult result, @PathVariable Integer id ){
        Hotel hotelDTO =  modelMapper.map(newHotel, Hotel.class);

        // Kiểm tra lỗi
        if(result.hasErrors()){
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Result(false, "Validation failed", errorMessages));
        }
        // Lấy thông tin khách sạn hiện tại
        Hotel currentHotel = hotelService.getById(id);

        if (currentHotel != null) {
            // Cập nhật thông tin khách sạn nếu khách sạn hiện tại tồn tại
            modelMapper.map(newHotel, currentHotel);
            currentHotel.setName(hotelDTO.getName());
            currentHotel.setStatus(hotelDTO.getStatus());
            Hotel updatedHotel = hotelService.save(currentHotel);
            return ResponseEntity.status(HttpStatus.OK).body(new Result(true, " success", updatedHotel));
        } else {
            // Tạo mới khách sạn nếu khách sạn hiện tại không tồn tại
            newHotel.setId(id);
            Hotel createdHotel = hotelService.update(modelMapper.map(newHotel, Hotel.class));
            return ResponseEntity.status(HttpStatus.OK).body(new Result(true, " success", createdHotel));
        }
    }

    @DeleteMapping("/hotel/{id}")
    ResponseEntity<?> deleteHotel(@PathVariable Integer id){
        Hotel data = hotelService.getById(id);
        if(data!=null){
            hotelService.delete(data);
            return ResponseEntity.status(HttpStatus.OK).body(new Result(true, " success", data));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result(true, "not success", null));
    }

    @GetMapping("/hotel/search/{name}")
    ResponseEntity<Result> search(@PathVariable String name){
        List<Hotel> data = hotelService.search(name);
        if(data!=null){
            return ResponseEntity.status(HttpStatus.OK).body(new Result(true, " success", data));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result(true, " success", null));
    }

    @GetMapping("hotel/searchPage/{keyword}")
   ResponseEntity<?> searchPage(@PathVariable String keyword ,@RequestParam(value = "pageNo", defaultValue = "1")Integer pageNo, @RequestParam(value = "pageSize", defaultValue = "2") Integer pageSize){
        Page<Hotel> data= hotelService.searchPage(keyword,pageNo, pageSize);
        if(data!=null){
            return ResponseEntity.status(HttpStatus.OK).body(new Result(true, " success", data));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result(true, " success", null));
    }
}
