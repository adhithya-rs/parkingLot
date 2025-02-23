package com.interview.parkinglotspring.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Operator extends BaseClass{
    private String name;

    public Operator(String name) {
        this.name = name;

    }

}
