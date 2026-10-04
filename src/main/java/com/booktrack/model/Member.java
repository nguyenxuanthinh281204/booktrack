package com.booktrack.model;

public class Member {

    private final String code;
    private final String fullName;
    private final String email;

    public Member(String code, String fullName, String email) {
        this.code = code;
        this.fullName = fullName;
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }
}
