package com.royalaviation.SpamCaller.Service;

import com.royalaviation.SpamCaller.Config.JwtUtil;
import com.royalaviation.SpamCaller.Entity.UserEntity;
import com.royalaviation.SpamCaller.Model.UserModel;
import com.royalaviation.SpamCaller.Repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

}
