package com.royalaviation.SpamCaller.Model;

import lombok.Data;

@Data
public class ContactModel {
    private String name;
    private String phoneNumber;

    @Override
    public String toString() {
        return "ContactModel{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
