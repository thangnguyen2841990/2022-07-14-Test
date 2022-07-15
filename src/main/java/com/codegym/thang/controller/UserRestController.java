package com.codegym.thang.controller;

import com.codegym.thang.model.dto.UserInfoForm;
import com.codegym.thang.model.entity.User;
import com.codegym.thang.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserRestController {
    @Autowired
    IUserService userService;

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_SHOP')")
    @PostMapping("/updateUserInfo/userId/{userId}")
    public ResponseEntity<User> updateUserInfo(@PathVariable Long userId, @RequestBody UserInfoForm userInfoForm) {
        Optional<User> userOptional = this.userService.findById(userId);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = userOptional.get();
        user.setEmail(userInfoForm.getEmail());
        user.setAddress(userInfoForm.getAddress());
        user.setPhoneNumber(userInfoForm.getPhoneNumber());
        user.setBirthDay(userInfoForm.getBirthDay());

      this.userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
