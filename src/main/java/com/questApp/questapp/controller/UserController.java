package com.questApp.questapp.controller;


import com.questApp.questapp.dto.UserDto;
import com.questApp.questapp.entity.User;
import com.questApp.questapp.service.impl.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private ModelMapper modelMapper;
    private UserServiceImpl userService;

    public UserController(ModelMapper modelMapper, UserServiceImpl userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getAllUsers(){
        return userService.getAllUsers().stream().map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }
    
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        // convert DTO to entity
        User userRequest = modelMapper.map(userDto, User.class);

        User user = userService.createUser(userRequest);

        // convert entity to DTO
        UserDto userResponse = modelMapper.map(user, UserDto.class);

        return new ResponseEntity<UserDto>(userResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getOneUser(@PathVariable Long userId){
        User user = userService.getOneUser(userId);

        // convert entity to DTO
        UserDto userResponse = modelMapper.map(user, UserDto.class);

        return ResponseEntity.ok().body(userResponse);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId, @RequestBody UserDto userDto){
        // Convert DTO to Entity
        User userRequest = modelMapper.map(userDto, User.class);

        User user = userService.updateUser(userId, userRequest);

        // convert entity to DTO
        UserDto userResponse = modelMapper.map(user, UserDto.class);

        return ResponseEntity.ok().body(userResponse);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
    }


}
