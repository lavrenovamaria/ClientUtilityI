package com.csv.clientutility.filter;

import com.csv.clientutility.domain.model.Person;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FirstNameDescending implements PersonSortingStrategy {
    @Override
    public List<Person> sorted(List<Person> collection) {
        return collection.stream().sorted(Collections.reverseOrder(Comparator.comparing(Person::getFirstName)))
                .collect(Collectors.toList());
    }
}
