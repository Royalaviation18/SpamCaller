package com.royalaviation.SpamCaller.Mapper;

import com.royalaviation.SpamCaller.Entity.ContactEntity;
import com.royalaviation.SpamCaller.Entity.UserEntity;
import com.royalaviation.SpamCaller.Model.ContactModel;
import com.royalaviation.SpamCaller.Model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // Map UserModel to UserEntity without mapping the password
    UserEntity userModelToUserEntity(UserModel userModel);

    // Map UserModel contacts to ContactEntity
    List<ContactEntity> userModelContactsToContactEntity(List<ContactModel> contacts);
}