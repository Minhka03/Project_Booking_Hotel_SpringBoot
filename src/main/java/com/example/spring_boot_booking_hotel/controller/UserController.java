package com.example.spring_boot_booking_hotel.controller;

import com.example.spring_boot_booking_hotel.model.Result;
import com.example.spring_boot_booking_hotel.model.User;
import com.example.spring_boot_booking_hotel.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserServiceImpl UserserviceImpl;

    @GetMapping("")
    ResponseEntity<Result> getAll(){
        List<User> list = UserserviceImpl.getAllUser();
        if(!list.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(new Result(true, "success", list));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result(false, "not success", null));
        }
    }
    @GetMapping("/{id}")
    ResponseEntity<Result> getById(@PathVariable Integer id){
        User user = UserserviceImpl.getUser(id);
        if(user!=null){
            return ResponseEntity.status(HttpStatus.OK).body(new Result(true, "success", user));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result(false, "not success", null));
        }
    }
    @PostMapping("/addNewUser")
    public User addNewUser(@RequestBody User user) {
        return UserserviceImpl.addUser(user);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteHotel(@PathVariable Integer id){
        User data = UserserviceImpl.getUser(id);
        if(data!=null){
            UserserviceImpl.deleteUser(data);
            return ResponseEntity.status(HttpStatus.OK).body(new Result(true, " success", data));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result(true, "not success", null));
    }

}
