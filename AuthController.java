package com.pradeep.demo_jwt_authentication.controller;

import com.pradeep.demo_jwt_authentication.dto.JwtAuthResponse;
import com.pradeep.demo_jwt_authentication.dto.LoginDto;
import com.pradeep.demo_jwt_authentication.entity.User;
import com.pradeep.demo_jwt_authentication.service.AuthServiceImpl;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static Logger log = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private AuthServiceImpl authService;

//API to register new user
    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody User user){
        log.info("Going to register new user with details {} " , user);
        User obj= authService.register(user);
        log.info("User saved in database is {} " , obj);
        return new ResponseEntity(obj,HttpStatus.OK);
    }
//APi to view all the registered users
    @GetMapping("/view")
    public ResponseEntity<List<User>> registerUser(){
        log.info("Fetching all the user");
        List<User> obj= authService.showAllUsers();
        log.info("Size of user list is {} ", obj.size());
        return new ResponseEntity(obj,HttpStatus.OK);
    }

    // Login REST API
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        log.info("User will be logged in with details {} " , loginDto.toString());
        String token = authService.login(loginDto);
        log.info("Token generated from system is {} " , token);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
log.info("Goign to return the reponse");
        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }


}
