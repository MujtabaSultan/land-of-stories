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
        System.out.println("calling forgot in controller ========>");
        userService.resetPassword(emailDto.getEmailAddress());
    }
    @PostMapping("/reset-password")
    public void passwordResetActivator(@RequestBody ForgotPasswordForm form ,@RequestParam String token){

       try{
           System.out.println("this got called for some reason xxxx" + form );
           System.out.println("calling reset activator in controller ========>");
           userService.resetPasswordActivator(token,form);
       } catch (Exception e) {
           e.printStackTrace();
           throw new RuntimeException(e);
       }

        //userService.resetPassword(user.getEmailAddress());
    }
    @PutMapping("/change-password")
    public void changePassword(@RequestBody ChangePasswordRequest request){
        System.out.println("calling change password in controller ========>");
        userService.changePassword(request.getOldPass(), request.getNewPass() );
        //userService.resetPassword(user.getEmailAddress());
    }

    @DeleteMapping("/delete")
    public void softDelete(){
        System.out.println("calling soft delete user in user controller ========>");
        userService.softDelete();
    }



}
