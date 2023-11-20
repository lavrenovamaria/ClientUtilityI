package com.csv.clientutility.parser;

import com.csv.clientutility.filter.Filter;

import java.util.List;
import java.util.Map;

public interface FilterParser {
    List<Filter> parse(Map<String, String> paramToValue);
}
