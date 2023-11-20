package com.csv.clientutility.filter;

import com.csv.clientutility.domain.model.Person;

import java.util.List;

public interface Filter<E> {
    List<E> apply(List<E> collection);
}
