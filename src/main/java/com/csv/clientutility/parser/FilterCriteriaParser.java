//package com.csv.clientutility.parser;
//
//import com.csv.clientutility.filter.*;
//import com.csv.clientutility.domain.model.Person;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//public class FilterCriteriaParser implements FilterParser {
//
//    private Map<String, Function<String, Filter>> filterFactories;
//
//    public FilterCriteriaParser() {
//        this.filterFactories = new HashMap<>();
//        initializeFilterFactories();
//    }
//
//    private void initializeFilterFactories() {
//
//        this.filterFactories.put("--top", value -> new TopNFilter(Integer.parseInt(value)));
//        this.filterFactories.put("--last", value -> new LastNFilter(Integer.parseInt(value)));
//        this.filterFactories.put("--males-only", value -> createGenderFilter(value, Gender.MALE));
//        this.filterFactories.put("--females-only", value -> createGenderFilter(value, Gender.FEMALE));
//        this.filterFactories.put("--desc-birth-date", value -> createSortingFilter(value, new BirthDateDescending()));
//        this.filterFactories.put("--desc-surname-order", value -> createSortingFilter(value, new SurnameDescending()));
//
//    }
//
//    private Filter createGenderFilter(String value, Gender gender) {
//        boolean apply = Boolean.parseBoolean(value);
//        return new GenderFilter(gender, apply);
//    }
//
//    private Filter createSortingFilter(String value, SortingStrategy strategy) {
//        boolean apply = Boolean.parseBoolean(value);
//        return new SortingFilter(apply, strategy);
//    }
//
//    @Override
//    public List<Filter> parse(Map<String, String> paramToValue) {
//        return paramToValue.entrySet()
//                .stream().map(this::entryToFilter)
//                .collect(Collectors.toList());
//    }
//
//    private Filter entryToFilter(Map.Entry<String, String> entry) {
//        String key = entry.getKey();
//        String value = entry.getValue();
//
//        if (filterFactories.containsKey(key)) {
//            return filterFactories.get(key).apply(value);
//        } else {
//            throw new IllegalArgumentException("Unsupported parameter: " + key);
//        }
//    }
//}
