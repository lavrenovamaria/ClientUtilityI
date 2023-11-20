package com.csv.clientutility.filter;

import com.csv.clientutility.domain.model.Person;

// PersonValidator interface
public interface PersonValidator {
    boolean isValidPerson(Person person);
}
