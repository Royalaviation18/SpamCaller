package com.royalaviation.SpamCaller.Controller;


import com.royalaviation.SpamCaller.Entity.UserEntity;
import com.royalaviation.SpamCaller.Model.UserDetailResponse;
import com.royalaviation.SpamCaller.Model.UserModel;
import com.royalaviation.SpamCaller.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public String createNewUser(@RequestBody UserModel userModel) {
        System.out.println("UserModel received: " + userModel);
        return userService.createUser(userModel);
    }

    @PostMapping("/login")
    public String userLogin(@RequestBody UserModel userModel){
        return userService.loginUser(userModel);
    }

    @PutMapping("/delete")
    public String softDeleteUser(@RequestParam String phoneNumber) {
        return userService.softDeleteUser(phoneNumber);
    }

    @GetMapping("/all")
    public List<UserDetailResponse> getAllUserDetails() {
        return userService.getAllUserDetails();
    }


    @PutMapping("/markSpam")
    public String markPhoneNumberAsSpam(@RequestParam String phoneNumber) {
        return userService.markPhoneNumberAsSpam(phoneNumber);
    }

    @PutMapping("/updateUser/{id}")
    public String updateUser(@PathVariable Long id, @RequestBody UserModel userModel) {
        return userService.updateUser(id, userModel);
    }

    @GetMapping("/search")
    public List<UserSearchResult> searchUsersByName(@RequestParam String name) {
        List<UserEntity> users = userService.searchUsersByName(name);
        return users.stream()
                .map(user -> new UserSearchResult(user.getName(), user.getPhoneNumber(), user.isSpam() ? "Spam Number" : "Not Spam"))
                .collect(Collectors.toList());
    }

    @GetMapping("/searchByPhoneNumber")
    public List<UserSearchResult> searchUsersByPhoneNumber(@RequestParam String phoneNumber) {
        List<UserEntity> users = userService.searchUsersByPhoneNumber(phoneNumber);
        return users.stream()
                .map(user -> new UserSearchResult(user.getName(), user.getPhoneNumber(), user.isSpam() ? "Spam Number" : "Not Spam"))
                .collect(Collectors.toList());
    }

    @GetMapping("/userDetails")
    public UserDetailResponse getUserDetails(@RequestParam String phoneNumber) {
        String loggedInEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.getUserDetails(phoneNumber, loggedInEmail);
    }



    public static class UserSearchResult {
        private String name;
        private String phoneNumber;
        private String spamStatus;

        public UserSearchResult(String name, String phoneNumber, String spamStatus) {
            this.name = name;
            this.phoneNumber = phoneNumber;
            this.spamStatus = spamStatus;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getSpamStatus() {
            return spamStatus;
        }

        public void setSpamStatus(String spamStatus) {
            this.spamStatus = spamStatus;
        }
    }

}
