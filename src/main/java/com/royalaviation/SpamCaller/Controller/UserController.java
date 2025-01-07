package com.royalaviation.SpamCaller.Controller;


import com.royalaviation.SpamCaller.Model.UserModel;
import com.royalaviation.SpamCaller.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("createUser")
    public String createNewUser(@RequestBody UserModel userModel) {
        // Add debug statements to check what is received
        System.out.println("UserModel received: " + userModel);
        return userService.createUser(userModel);
    }

}
