package com.example.userservice.controller;

import com.example.userservice.dto.UserRequest;
import com.example.userservice.entity.User;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.service.UserService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@Slf4j
public class UserController {
    @Autowired
    UserService service;

    @PostMapping()
    @ApiOperation(value = "Save the user ", produces = "application/json")
    public ResponseEntity<User> saveUser(@RequestBody @Valid UserRequest userRequest){
        log.info("Create user request is received");
        return new ResponseEntity<>(service.saveUser(userRequest), HttpStatus.CREATED);
    }

    @GetMapping
    @ApiOperation(value = "Get all users", produces = "application/json")
    public ResponseEntity<List<User>> getAllTheUsers(){
        log.info("Request received");
        return ResponseEntity.ok(service.getAllTheUsers());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get the user by id ", produces = "application/json")
    public ResponseEntity<?> getUserById(@PathVariable(name = "id") Integer userId){
        log.info("get user by userId request is received");
        try {
            return ResponseEntity.ok(service.getUserById(userId));
        }
        catch (UserNotFoundException e){
            log.error("Could not find given user in the database with id: " + userId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with given id: " + userId);
        }
    }

    @GetMapping("/first-name/{fn}")
    @ApiOperation(value = "Get the user by firstName ", produces = "application/json")
    public ResponseEntity<User> getUserByFirstName(@PathVariable(name = "fn") String firstName){
        log.info("get user user's firstName request is received");
        return ResponseEntity.ok(service.getUserByFirstName(firstName));
    }


    @PutMapping("{id}")
    @ApiOperation(value = "Update the user", produces = "application/json")
    public ResponseEntity updateUser(@RequestBody UserRequest userRequest, @PathVariable(name = "id") Integer userId){
        log.info("update user request is received");
        try {
            return new ResponseEntity<>(service.updateUser(userRequest, userId), HttpStatus.CREATED);
        }
        catch (UserNotFoundException e){
            log.error("Could not find given user in the database with id: " + userId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with given id: " + userId);
        }
    }
}
