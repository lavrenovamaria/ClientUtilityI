package com.csv.clientutility.sorting;

import com.csv.clientutility.domain.model.Person;

import java.util.Comparator;
import java.util.List;

public interface PersonSortingStrategy {
    List<Person> sorted(List<Person> collection);
}