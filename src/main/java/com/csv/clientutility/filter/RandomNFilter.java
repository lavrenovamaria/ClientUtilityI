package com.csv.clientutility.filter;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RandomNFilter<E> extends ParameterizedFilter<E, Integer> implements PostFilter<E> {
    private final Random random = new Random();

    public RandomNFilter(int value) {
        super(value);
    }

    @Override
    public List<E> apply(List<E> collection) {
        Collections.shuffle(collection, random);
        return collection.stream().limit(value).collect(Collectors.toList());
    }
}
