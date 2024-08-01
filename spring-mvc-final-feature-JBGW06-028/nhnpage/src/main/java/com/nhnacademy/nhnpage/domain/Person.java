package com.nhnacademy.nhnpage.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class Person {
    private String id;
    private String name;
    private String password;

    public enum Role{
        ADMIN,USER
    }

}
