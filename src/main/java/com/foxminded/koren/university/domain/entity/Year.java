package com.foxminded.koren.university.domain.entity;

public enum Year {
    FIRST(1),
    SECOND(2),
    THIRD(3),
    FOURTH(4),
    FIFTH(5),
    SIXTH(6);
    
    private int yearNumber;
    
    Year(int yearNumber) {
       this.yearNumber = yearNumber;
    }
    
    public int getYear() {
        return yearNumber;
    }
}