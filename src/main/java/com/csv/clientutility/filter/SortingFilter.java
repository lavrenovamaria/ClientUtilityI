//package com.csv.clientutility.filter;
//
//import com.csv.clientutility.domain.model.Person;
//
//import java.util.List;
//
//public class SortingFilter extends ParameterizedFilter implements PostFilter {
//    private final SortingStrategy strategy;
//
//    public SortingFilter(boolean apply, SortingStrategy strategy) {
//        super(apply);
//        this.strategy = strategy;
//    }
//
//    @Override
//    public List<Person> apply(List<Person> collection) {
//        if (!getApply()) return collection;
//        return strategy.sorted(collection);
//    }
//}
