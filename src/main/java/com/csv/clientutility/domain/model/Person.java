package com.csv.clientutility.domain.model;

import com.csv.clientutility.filter.Gender;

import java.time.LocalDate;


public class Person {

    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate birthdate;

    public Person(String firstName, String lastName, String gender, LocalDate birthdate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = Gender.valueOf(gender.toUpperCase());
        this.birthdate = birthdate;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", birthdate=" + birthdate +
                '}';
    }
}
