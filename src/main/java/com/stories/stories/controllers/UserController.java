package com.stories.stories.controllers;

import com.stories.stories.models.*;
import com.stories.stories.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        System.out.println("calling login request in service ==========>");
        return userService.loginUser(loginRequest);
    }

    @PostMapping("/register")
    public User createUser(@RequestBody UserDto userObj){

        return userService.createUser(userObj);
    }

    @GetMapping("/register/verify")
    public void validate(@RequestParam String token){
        System.out.println("calling verify in controller ========>");
        userService.validate(token);
    }

    @GetMapping("/forgot-password")
    public void passwordReset(@RequestBody ForgotPasswordDto emailDto){
        System.out.println(emailDto.getEmailAddress());
        System.out.println("calling reset in controller ========>");
        userService.resetPassword(emailDto.getEmailAddress());
    }
    @PutMapping("/change-password")
    public void changePassword(@RequestBody ChangePasswordRequest request){
        System.out.println("calling change password in controller ========>");
        userService.changePassword(request.getOldPass(), request.getNewPass() );
        //userService.resetPassword(user.getEmailAddress());
    }



}
