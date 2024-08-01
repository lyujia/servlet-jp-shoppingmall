package com.nhnacademy.nhnpage.domain;


import lombok.Getter;

public class User extends Person{
    @Getter
    private Role role;
    public User(String id, String name, String password) {
        super(id, name, password);
        this.role = Role.USER;
    }
}

