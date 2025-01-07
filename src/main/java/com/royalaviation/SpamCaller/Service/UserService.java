package com.royalaviation.SpamCaller.Service;


import com.royalaviation.SpamCaller.Entity.UserEntity;
import com.royalaviation.SpamCaller.Model.UserDetailResponse;
import com.royalaviation.SpamCaller.Model.UserModel;

import java.util.List;
import java.util.Optional;

public interface UserService {
    String createUser(UserModel userModel);

    String loginUser(UserModel userModel);

    String markPhoneNumberAsSpam(String phoneNumber);

    List<UserEntity> searchUsersByName(String name);

    List<UserEntity> searchUsersByPhoneNumber(String phoneNumber);


    UserDetailResponse getUserDetails(String phoneNumber, String loggedInEmail);
}
