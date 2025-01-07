package com.royalaviation.SpamCaller.Mapper;

import com.royalaviation.SpamCaller.Entity.ContactEntity;
import com.royalaviation.SpamCaller.Entity.UserEntity;
import com.royalaviation.SpamCaller.Model.ContactModel;
import com.royalaviation.SpamCaller.Model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // Map UserModel to UserEntity without mapping the password
    UserEntity userModelToUserEntity(UserModel userModel);

    // Map UserModel contacts to ContactEntity
    default List<ContactEntity> userModelContactsToContactEntity(List<ContactModel> contacts) {
        return contacts.stream()
                .map(contactModel -> {
                    ContactEntity contactEntity = new ContactEntity();
                    contactEntity.setName(contactModel.getName());
                    contactEntity.setPhoneNumber(contactModel.getPhoneNumber());
                    // Do not set the 'user' field here, it will be done in the service layer.
                    return contactEntity;
                })
                .collect(Collectors.toList());
    }
}
