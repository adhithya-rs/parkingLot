package com.interview.parkinglotspring.singleton;

import lombok.Getter;
import lombok.Setter;

import java.util.Scanner;

@Getter
@Setter
public class ScannerObject {
    private static Scanner sc = new Scanner(System.in);

    public static Scanner getInstance() {
        return sc;
    }

    public static void close(){
        sc.close();
    }
}
