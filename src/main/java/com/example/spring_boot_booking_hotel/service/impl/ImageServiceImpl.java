package com.example.spring_boot_booking_hotel.service.impl;

import com.example.spring_boot_booking_hotel.dto.ImageDTO;
import com.example.spring_boot_booking_hotel.exception.base.NotFoundException;
import com.example.spring_boot_booking_hotel.model.Image;
import com.example.spring_boot_booking_hotel.model.Room;
import com.example.spring_boot_booking_hotel.repository.ImageRepository;
import com.example.spring_boot_booking_hotel.repository.RoomRepository;
import com.example.spring_boot_booking_hotel.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private  final ImageRepository imageRepository;
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<Image> finAll() {
        return this.imageRepository.findAll();
    }
    @Override
    public Image getById(Integer id) {
        return this.imageRepository.findById(id).orElse(null);
    }
    @Override
    public Image save(Image value) {
        return this.imageRepository.save(value);
    }
    @Override
    public Image update(Image value) {
        return this.imageRepository.save(value);
    }

    @Override
    public void delete(Image value) {
         this.imageRepository.delete(value); ;
    }
    @Override
    public Page<Image> page(Integer pageSize, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageSize, pageNo);
        return  this.imageRepository.findAll(pageable);
    }

    @Override
    public List<Image> search(String name) {
        return null;
    }

    @Override
    public Page<Image> searchPage(String keyword, Integer pageNo, Integer pageSize) {
        return null;
    }

    @Override
    public Image insert_Image(ImageDTO imageDTO) throws NotFoundException {
        Image image = modelMapper.map(imageDTO, Image.class);
        image.setId(null);
        Room room= roomRepository.findById(imageDTO.getRoom_id()).orElse(null);
        if (room == null) {
            // Ném ngoại lệ tùy chỉnh nếu không tìm thấy user
            System.out.println(1);
            throw new NotFoundException("Room not found with id: " + imageDTO.getRoom_id());
        }
        System.out.println(room.getId());
        image.setRoom(room);
        return imageRepository.save(image);
    }

}
