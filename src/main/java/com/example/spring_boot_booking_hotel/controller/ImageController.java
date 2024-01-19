package com.example.spring_boot_booking_hotel.controller;

import com.example.spring_boot_booking_hotel.dto.ImageDTO;
import com.example.spring_boot_booking_hotel.exception.base.NotFoundException;
import com.example.spring_boot_booking_hotel.model.Image;
import com.example.spring_boot_booking_hotel.model.Result;
import com.example.spring_boot_booking_hotel.service.ImageService;
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

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class ImageController {

    private final ModelMapper modelMapper;
    private final ImageService imageService;
    private  final Upload upload;
    @GetMapping("/image")
    ResponseEntity<Result> getAll(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(value = "pageSize", defaultValue = "2") Integer pageSize){
        Page<Image> list = imageService.page(pageNo, pageSize);
        if(!list.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(new Result(true, "success", list));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result(false, "not success", null));
        }
    }
    @GetMapping("/image/{id}")
    ResponseEntity<?> getById( @PathVariable Integer id){
        Image getImage = imageService.getById(id);
        if(getImage!=null){
            return  ResponseEntity.status(HttpStatus.OK).body(new Result(true, "success", getImage));
        }
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result(false, "not success", null));
    }

    @PostMapping(value = "/fileImage", consumes = {"multipart/form-data"})
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

    @PostMapping("/image")
    ResponseEntity<?> insertImage(@Valid @RequestBody ImageDTO imageDTO, BindingResult result) throws NotFoundException {
        if(result.hasErrors()){
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Result(false, "Validation failed", errorMessages));
        }

        if (imageDTO.getRoom_id() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Result(false, "Hotel ID does not exist", imageDTO));
        }
        Image image_insert = imageService.insert_Image(imageDTO);
        if (image_insert == null || image_insert.getRoom() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Result(false, "Hotel ID does not exist", image_insert));
        }


        ImageDTO imageDTO_insert = modelMapper.map(image_insert, ImageDTO.class);
        imageDTO_insert.setRoom_id(image_insert.getRoom().getId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Result(true, "Success", imageDTO_insert));
    }
}
