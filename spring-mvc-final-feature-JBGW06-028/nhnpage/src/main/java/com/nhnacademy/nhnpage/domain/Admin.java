package com.nhnacademy.nhnpage.domain;

import lombok.Getter;

public class Admin extends Person {
    @Getter
    private Role role;
   public Admin(String id, String name, String password){
       super(id,name,password);
       this.role = Role.ADMIN;
   }
}
