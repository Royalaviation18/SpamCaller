package com.royalaviation.SpamCaller.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "phoneNumber"),
        @UniqueConstraint(columnNames = "email") })
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    private String email;

    private String password;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    private boolean spam = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContactEntity> contacts;

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", spam=" + spam +
                ", contacts=" + contacts +
                '}';
    }
}
