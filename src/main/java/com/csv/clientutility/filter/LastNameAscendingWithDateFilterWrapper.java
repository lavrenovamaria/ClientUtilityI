package com.csv.clientutility.filter;

import com.csv.clientutility.domain.model.Person;

import java.util.List;

public class LastNameAscendingWithDateFilterWrapper implements PreFilter<Person> {

    private final LastNameAscendingWithDateFilter filter;

    public LastNameAscendingWithDateFilterWrapper() {
        this.filter = new LastNameAscendingWithDateFilter();
    }

    @Override
    public List<Person> apply(List<Person> collection) {
        return filter.apply(collection);
    }
}
