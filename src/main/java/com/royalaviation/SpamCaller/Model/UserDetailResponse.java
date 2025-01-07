package com.royalaviation.SpamCaller.Model;

public class UserDetailResponse {
    private String name;
    private String phoneNumber;
    private String spamStatus;
    private String email; // Null if not visible

    public UserDetailResponse(String name, String phoneNumber, String spamStatus, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.spamStatus = spamStatus;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSpamStatus() {
        return spamStatus;
    }

    public String getEmail() {
        return email;
    }
}
