package com.stories.stories.controllers;

import com.stories.stories.models.User;
import com.stories.stories.models.UserDto;
import com.stories.stories.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
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

}
