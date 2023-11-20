package com.csv.clientutility.filter;

public class MenOnlyFilter extends GenderFilter {
    public MenOnlyFilter(boolean value) {
        super(Gender.M, value);
    }
}
