package com.csv.clientutility.filter;

import com.csv.clientutility.domain.model.Person;

import java.util.List;

public interface SortingStrategy<E> {
    List<E> sorted(List<E> collection);
}

