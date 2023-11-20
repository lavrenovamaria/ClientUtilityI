package com.csv.clientutility.sorting;

import com.csv.clientutility.domain.model.Person;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LastNameDescending implements PersonSortingStrategy {
    @Override
    public List<Person> sorted(List<Person> collection) {
        return collection.stream().sorted(Collections.reverseOrder(Comparator.comparing(Person::getLastName)))
                .collect(Collectors.toList());
    }
}
