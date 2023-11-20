package com.csv.clientutility.filter;

import java.util.List;
import java.util.stream.Collectors;

public class TopNFilter<E> extends ParameterizedFilter<E, Integer> implements PostFilter<E> {
    public TopNFilter(int value) {
        super(value);
    }

    @Override
    public List<E> apply(List<E> collection) {
        if (value >= collection.size()) {
            return collection;
        } else {
            return collection.stream().limit(this.value).collect(Collectors.toList());
        }
    }
}