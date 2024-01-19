package com.example.spring_boot_booking_hotel.controller;

import com.example.spring_boot_booking_hotel.dto.RoomDTO;
import com.example.spring_boot_booking_hotel.exception.base.NotFoundException;
import com.example.spring_boot_booking_hotel.model.Result;
import com.example.spring_boot_booking_hotel.model.Room;
import com.example.spring_boot_booking_hotel.service.RoomService;
import com.example.spring_boot_booking_hotel.uploads_File.Upload;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;
    private final ModelMapper modelMapper;
    private  final Upload upload;
    @GetMapping("/room")
    ResponseEntity<Result> getAll(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(value = "pageSize", defaultValue = "2") Integer pageSize) {
        Page<Room> list = roomService.page(pageNo, pageSize);
        if (!list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new Result(true, "success", list));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result(false, "not success", null));
        }
    }
    @GetMapping("/room/{id}")
    ResponseEntity<?> getByRoomId(@PathVariable Integer id) {
        Room getRoom = roomService.getById(id);
        if (getRoom != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new Result(true, "success", getRoom));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result(false, "not success", null));
    }

        @PostMapping("/room")
        public ResponseEntity<?> createRoomWithImage(@Valid @RequestBody RoomDTO newRoom, BindingResult result) throws NotFoundException {
            if(result.hasErrors()){
                List<String> errorMessages = result.getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.toList());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Result(false, "Validation failed", errorMessages));
            }
            if (newRoom.getId_hotel() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Result(false, "Hotel ID does not exist", newRoom));
            }

            Room room_insert = roomService.insert_Room(newRoom);
            if (room_insert == null || room_insert.getHotel() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Result(false, "Hotel ID does not exist", room_insert));
            }

            RoomDTO roomDTO_insert = modelMapper.map(room_insert, RoomDTO.class);
            roomDTO_insert.setId_hotel(room_insert.getHotel().getId());

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new Result(true, "Success", roomDTO_insert));
        }

//    @PostMapping("/room/image")
//    public String uploadImage(@RequestParam("imageFile") MultipartFile imageFile) throws IOException {
//        return roomService.saveImage(imageFile);
//    }

    @PostMapping(value = "/room/image", consumes = {"multipart/form-data"})
    public ResponseEntity<Result> uploadFile(@RequestParam("imageFile") MultipartFile imageFile) {
        try {
            String fileName = upload.saveImage(imageFile);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Result(true, "upload file successfully", fileName));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                    .body(new Result(false, e.getMessage(), ""));
        }
    }
    @PutMapping("/room/{id}")
    ResponseEntity<?> updateRoom(@Valid @RequestBody RoomDTO newRoom, BindingResult result, @PathVariable Integer id ) throws IOException, NotFoundException {
        Room roomDTO =  modelMapper.map(newRoom, Room.class);

        // Kiểm tra lỗi
        if(result.hasErrors()){
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Result(false, "Validation failed", errorMessages));
        }
        // Lấy thông tin khách sạn hiện tại
        RoomDTO currentRoom = modelMapper.map(roomService.getById(id), RoomDTO.class);
        if (currentRoom != null) {
            // Cập nhật thông tin khách sạn nếu khách sạn hiện tại tồn tại
            modelMapper.map(currentRoom, RoomDTO.class);
            currentRoom.setName(newRoom.getName());
            currentRoom.setGuests(newRoom.getGuests());
            currentRoom.setImage(newRoom.getImage());
            currentRoom.setPer_night(newRoom.getPer_night());
            currentRoom.setDay_price(newRoom.getDay_price());
            currentRoom.setWeek_price(newRoom.getWeek_price());
            currentRoom.setSale_price(newRoom.getSale_price());
            currentRoom.setStatus(newRoom.getStatus());
            currentRoom.setId_hotel(newRoom.getId_hotel());
            Room updateRoom = roomService.insert_Room(currentRoom);
            return ResponseEntity.status(HttpStatus.OK).body(new Result(true, " success", updateRoom));
        } else {
            // Tạo mới khách sạn nếu khách sạn hiện tại không tồn tại
            newRoom.setId(id);
            Room createdRoom = roomService.update(modelMapper.map(newRoom, Room.class));
            return ResponseEntity.status(HttpStatus.OK).body(new Result(true, " success", createdRoom));
        }
    }
    @DeleteMapping("/room/{id}")
    ResponseEntity<?> deleteHotel(@PathVariable Integer id){
        Room data = roomService.getById(id);
        if(data!=null){
            roomService.delete(data);
            return ResponseEntity.status(HttpStatus.OK).body(new Result(true, " success", data));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result(true, "not success", null));
    }

    @GetMapping("/room/search/{name}")
    ResponseEntity<Result> search(@PathVariable String name){
        List<Room> data = roomService.search(name);
        if(data!=null){
            return ResponseEntity.status(HttpStatus.OK).body(new Result(true, " success", data));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result(true, " success", null));
    }

    @GetMapping("room/searchPage/{keyword}")
    ResponseEntity<?> searchPage(@PathVariable String keyword ,@RequestParam(value = "pageNo", defaultValue = "1")Integer pageNo, @RequestParam(value = "pageSize", defaultValue = "2") Integer pageSize){
        Page<Room> data= roomService.searchPage(keyword,pageNo, pageSize);
        if(data!=null){
            return ResponseEntity.status(HttpStatus.OK).body(new Result(true, " success", data));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result(true, " success", null));
    }
}




