package com.csv.clientutility.filter;

public class WomenOnlyFilter extends GenderFilter {
    public WomenOnlyFilter(boolean value) {
        super(Gender.F, value);
    }
}
