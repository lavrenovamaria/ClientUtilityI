package com.csv.clientutility.filter;

public enum Gender {
    M,
    F;

    public boolean isValid() {
        return this == M || this == F;
    }
}
