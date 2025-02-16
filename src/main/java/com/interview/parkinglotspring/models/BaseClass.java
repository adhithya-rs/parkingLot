package com.interview.parkinglotspring.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class BaseClass {
    private long id;
    private Date createdAt;
}
