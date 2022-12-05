package com.example.userservice.service;

import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.dto.UserRequest;
import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepository repository;
    public User saveUser(UserRequest userRequest) {
        User user = User.builder()
                        .firstName(userRequest.getFirstName()).
                lastName(userRequest.getLastName())
                .age(userRequest.getAge())
                .address(userRequest.getAddress()).build();
        log.info("created the user object to save");
        return repository.save(user);
    }

    public User updateUser(UserRequest userRequest, Integer userId) {
        Optional<User> optional = repository.findById(userId);
        if(optional.isEmpty()){
            throw new UserNotFoundException("USER NOT FOUND");
        }
        User user = optional.get();
        user.setAddress(userRequest.getAddress());
        return repository.save(user);
    }

    public List<User> getAllTheUsers() {
        return repository.findAll();
    }

    public User getUserById(Integer userId) {
        Optional<User> optional = repository.findById(userId);
        if(optional.isEmpty()){
            throw new UserNotFoundException("USER NOT FOUND");
        }
        return optional.get();
    }

    public User getUserByFirstName(String firstName) {
        return repository.findByfirstName(firstName);
    }
}
