package com.example.spring_boot_booking_hotel.service.impl;
import com.example.spring_boot_booking_hotel.config.user.CustomerUserDetails;
import com.example.spring_boot_booking_hotel.model.User;
import com.example.spring_boot_booking_hotel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomerUserDetails(user);
    }

    public User addUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }

    public List<User> getAllUser(){
        return  this.userRepository.findAll();
    }
    public User getUser(Integer id) {
        return  this.userRepository.findById(id).orElse(null);
    }

    public User update(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return this.userRepository.save(user);
    }
    public  void  deleteUser(User user) {
        this.userRepository.delete(user);
    }



}
