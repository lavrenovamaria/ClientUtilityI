package com.csv.clientutility.filter;

import com.csv.clientutility.exception.PostFilterException;

import java.util.List;
import java.util.stream.Collectors;

public class LastNFilter<E> extends ParameterizedFilter<E, Integer> implements PostFilter<E> {
    public LastNFilter(int value) {
        super(value);
    }

    @Override
    public List<E> apply(List<E> collection) {
        if (value >= collection.size()) {
            return collection;
        } else {
            return collection.stream().skip(collection.size() - value).collect(Collectors.toList());
        }
    }
}