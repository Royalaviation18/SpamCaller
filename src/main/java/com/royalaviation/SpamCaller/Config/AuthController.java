//package com.royalaviation.SpamCaller.Config;
//
//import com.royalaviation.SpamCaller.Model.UserModel;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//public class AuthController {
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @PostMapping("/login")
//    public String login(@RequestBody UserModel userModel) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(userModel.getEmail(), userModel.getPassword()));
//        User user = (User) authentication.getPrincipal();
//        return jwtUtil.generateToken(user.getUsername());
//    }
//}
