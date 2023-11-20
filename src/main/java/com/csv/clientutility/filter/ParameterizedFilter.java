// ParameterizedFilter class
package com.csv.clientutility.filter;

import com.csv.clientutility.domain.model.Person;

abstract class ParameterizedFilter<E, V> implements Filter<E> {
    protected V value;

    public ParameterizedFilter(V value) {
        this.value = value;
    }
}
