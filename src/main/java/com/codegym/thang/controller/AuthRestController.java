package com.codegym.thang.controller;

import com.codegym.thang.configuration.JWTProvider;
import com.codegym.thang.model.dto.JwtResponse;
import com.codegym.thang.model.dto.LoginRequest;
import com.codegym.thang.model.dto.SignUpForm;
import com.codegym.thang.model.entity.User;
import com.codegym.thang.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class AuthRestController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserService userService;

    @Autowired
    private JWTProvider jwtProvider;


    @Autowired
    private PasswordEncoder passwordEncoder;
    // Hiển thị danh sách thông tin user
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        //Kiểm tra username và pass có đúng hay không
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        //Lưu user đang đăng nhập vào trong context của security
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentUser = userService.findByUsername(loginRequest.getUsername());
        return ResponseEntity.ok(new JwtResponse(jwt, currentUser.getId(), currentUser.getUsername(), userDetails.getAuthorities()));
    }
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody SignUpForm user) {
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User newUser = new User(user.getUsername(), user.getPassword());
        if (user.getRole().equals("user")) {
            userService.save(newUser);
        }
        if (user.getRole().equals("shop")) {
            userService.saveShop(newUser);
        }
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
