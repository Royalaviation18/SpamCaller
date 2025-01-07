package com.royalaviation.SpamCaller.Service;

import com.royalaviation.SpamCaller.Entity.UserEntity;
import com.royalaviation.SpamCaller.Model.UserModel;
import com.royalaviation.SpamCaller.Repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public String createUser(UserModel userModel) {
        System.out.println("UserModel data: " + userModel);
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userModel, userEntity);
        System.out.println("UserEntity after copy: " + userEntity);
        userRepository.save(userEntity);
        return "User created successfully";
    }

}
