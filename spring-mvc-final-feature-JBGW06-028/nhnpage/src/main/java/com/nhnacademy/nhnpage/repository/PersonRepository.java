package com.nhnacademy.nhnpage.repository;

import com.nhnacademy.nhnpage.domain.Person;

public interface PersonRepository {
    Person findById(String id);
    Person matches(String id, String password);
    Person register(Person person);
    Person update(Person person);
    void delete(String id);
}
