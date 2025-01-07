package com.royalaviation.SpamCaller.Repository;

import com.royalaviation.SpamCaller.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByPhoneNumber(String phoneNumber);
    List<UserEntity> findByNameStartingWithIgnoreCase(String name);
    List<UserEntity> findByNameContainingIgnoreCase(String name);

    List<UserEntity> findByPhoneNumberContaining(String phoneNumber);
}
