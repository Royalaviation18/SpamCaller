package com.royalaviation.SpamCaller.Model;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@Data
public class UserModel {
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String email;
    @NotNull
    private String password;

    private boolean active = true;
    private boolean spam = false;

    private List<ContactModel> contacts; // Add this field

    @Override
    public String toString() {
        return "UserModel{" +
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
