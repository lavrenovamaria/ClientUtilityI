// PersonSorter.java
package com.csv.clientutility.filter;

import com.csv.clientutility.domain.model.Person;

import java.util.Comparator;
import java.util.List;

public class PersonSorter {
    private final PersonSortingStrategy sortingStrategy;
    private final List<PreFilter<Person>> preFilters;
    private final List<PostFilter<Person>> postFilters;

    public PersonSorter(PersonSortingStrategy sortingStrategy, List<PreFilter<Person>> preFilters, List<PostFilter<Person>> postFilters) {
        this.sortingStrategy = sortingStrategy;
        this.preFilters = preFilters;
        this.postFilters = postFilters;
    }

    public List<Person> sorted(List<Person> persons) {

        List<Person> preFiltered = foldOn(preFilters, persons);
        List<Person> sorted = sortingStrategy.sorted(preFiltered);
        List<Person> postFiltered = foldOn(postFilters, sorted);

        return postFiltered;
    }

    public static <E> List<E> foldOn(List<? extends Filter<E>> filters, List<E> collection) {
        for (Filter<E> filter : filters) {
            collection = filter.apply(collection);
        }
        return collection;
    }
}

