package com.royalaviation.SpamCaller.Service;

import com.royalaviation.SpamCaller.Config.JwtUtil;
import com.royalaviation.SpamCaller.Entity.UserEntity;
import com.royalaviation.SpamCaller.Model.UserModel;
import com.royalaviation.SpamCaller.Repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public String createUser(UserModel userModel) {
        System.out.println("UserModel data: " + userModel);
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userModel, userEntity);
        userEntity.setPassword(passwordEncoder.encode(userModel.getPassword()));
        System.out.println("UserEntity after copy and password encryption: " + userEntity);
        userRepository.save(userEntity);
        return "User created successfully";
    }

    @Override
    public String loginUser(UserModel userModel) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(userModel.getEmail());
        if(userOptional.isEmpty()){
            return  "Invalid email or password";
        }
        UserEntity userEntity = userOptional.get();
        if (!passwordEncoder.matches(userModel.getPassword(), userEntity.getPassword())) {
            return "Invalid email or password";
        }
        return jwtUtil.generateToken(userEntity.getEmail());

    }

    public String markPhoneNumberAsSpam(String phoneNumber) {
        // Get the currently authenticated user
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<UserEntity> userOptional = userRepository.findByEmail(username);

        if (userOptional.isPresent()) {
            UserEntity userEntity = userOptional.get();
            // Find the user by the phone number and mark as spam
            Optional<UserEntity> phoneUserOptional = userRepository.findByPhoneNumber(phoneNumber);

            if (phoneUserOptional.isPresent()) {
                UserEntity phoneUser = phoneUserOptional.get();
                phoneUser.setSpam(true);
                userRepository.save(phoneUser);
                return "Phone number marked as spam successfully.";
            } else {
                return "Phone number not found.";
            }
        } else {
            return "User not authenticated.";
        }
    }

}
