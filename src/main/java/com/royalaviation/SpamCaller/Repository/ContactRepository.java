package com.royalaviation.SpamCaller.Repository;

import com.royalaviation.SpamCaller.Entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<ContactEntity, Long> {
    List<ContactEntity> findByPhoneNumber(String phoneNumber);
}
