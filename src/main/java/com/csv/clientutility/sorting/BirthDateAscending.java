package com.csv.clientutility.sorting;

import com.csv.clientutility.domain.model.Person;

import java.util.Comparator;
import java.util.List;

public class BirthDateAscending implements PersonSortingStrategy {
    @Override
    public List<Person> sorted(List<Person> collection) {
        collection.sort(Comparator.comparing(Person::getBirthdate));
        return collection;
    }
}
