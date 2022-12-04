package com.example.userservice.controller;

import com.example.userservice.dto.UserRequest;
import com.example.userservice.entity.User;
import com.example.userservice.service.UserService;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    UserService service;

    @PostMapping()
    @ApiOperation(value = "Save the user ", produces = "application/json")
    public ResponseEntity<User> saveUser(@RequestBody @Valid UserRequest userRequest){
        return new ResponseEntity<>(service.saveUser(userRequest), HttpStatus.CREATED);
    }

    @GetMapping
    @ApiOperation(value = "Get all users", produces = "application/json")
    public ResponseEntity<List<User>> getAllTheUsers(){
        return ResponseEntity.ok(service.getAllTheUsers());
    }

    @GetMapping("/{userId}")
    @ApiOperation(value = "Get the user by id ", produces = "application/json")
    public ResponseEntity<User> getUserById(@PathVariable Integer userId){
        return ResponseEntity.ok(service.getUserById(userId));
    }

    @GetMapping("/first-name/{fn}")
    @ApiOperation(value = "Get the user by firstName ", produces = "application/json")
    public ResponseEntity<User> getUserByFirstName(@PathVariable(name = "fn") String firstName){
        return ResponseEntity.ok(service.getUserByFirstName(firstName));
    }


    @PutMapping("{id}")
    @ApiOperation(value = "Update the user's address ", produces = "application/json")
    public ResponseEntity<User> updateUser(@RequestBody UserRequest userRequest, @PathVariable Integer id){
        try {
            return new ResponseEntity<>(service.updateUser(userRequest, id), HttpStatus.CREATED);
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

}
