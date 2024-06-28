package com.pradeep.demo_jwt_authentication.service;

import com.pradeep.demo_jwt_authentication.dto.LoginDto;
import com.pradeep.demo_jwt_authentication.entity.User;
import com.pradeep.demo_jwt_authentication.repository.UserRepository;
import com.pradeep.demo_jwt_authentication.util.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class AuthServiceImpl {

    private static Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;


    @Autowired
    private UserRepository userRepository;

    public String login(LoginDto loginDto) {
log.info("SHow logined user {}" , loginDto.toString());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
log.info("Show token {}",token);
        return token;
    }

    public User register(User user) {
        log.info("Going to register new user with details {}" , user.toString());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        log.info("Encoded password will be saved in db");
        return userRepository.save(user);
    }

    public List<User> showAllUsers() {
        log.info("Fetching details of all users");
        return userRepository.findAll();
    }
}
