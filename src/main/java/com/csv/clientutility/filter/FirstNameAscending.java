package com.csv.clientutility.filter;

import com.csv.clientutility.domain.model.Person;

import java.util.Comparator;
import java.util.List;

public class FirstNameAscending implements PersonSortingStrategy {
    @Override
    public List<Person> sorted(List<Person> collection) {
        collection.sort(Comparator.comparing(Person::getFirstName));
        return collection;
    }

}
