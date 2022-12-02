package com.example.userservice.controller;

import com.example.userservice.dto.UserRequest;
import com.example.userservice.entity.User;
import com.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    UserService service;

    @PostMapping()
    public ResponseEntity<User> saveUser(@RequestBody UserRequest userRequest){
        return new ResponseEntity<>(service.saveUser(userRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllTheUsers(){
        return ResponseEntity.ok(service.getAllTheUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Integer userId){
        return ResponseEntity.ok(service.getUserById(userId));
    }

    @GetMapping("/first-name/{fn}")
    public ResponseEntity<User> getUserByFirstName(@PathVariable(name = "fn") String firstName){
        return ResponseEntity.ok(service.getUserByFirstName(firstName));
    }


    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@RequestBody UserRequest userRequest, @PathVariable Integer id){
        try {
            return new ResponseEntity<>(service.updateUser(userRequest, id), HttpStatus.CREATED);
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

}
