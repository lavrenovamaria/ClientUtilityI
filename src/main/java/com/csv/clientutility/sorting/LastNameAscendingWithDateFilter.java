package com.csv.clientutility.sorting;

import com.csv.clientutility.domain.model.Person;
import com.csv.clientutility.filter.Filter;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class LastNameAscendingWithDateFilter implements Filter<Person> {
    @Override
    public List<Person> apply(List<Person> collection) {
        for (int i = 0; i < collection.size() - 1; i++) {
            Person currentPerson = collection.get(i);
            Person nextPerson = collection.get(i + 1);

            if (currentPerson.getBirthdate().isEqual(nextPerson.getBirthdate())) {
                collection.sort(Comparator.comparing(Person::getLastName));
                break;
            }
        }

        return collection;
    }
}
