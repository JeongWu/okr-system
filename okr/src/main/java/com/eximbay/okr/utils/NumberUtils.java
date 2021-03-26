package com.eximbay.okr.utils;

public class NumberUtils {
    public static Double formatDouble(Double value, Integer decimal){
        return Math.round(value * Math.pow(10, decimal)) / Math.pow(10, decimal);
    }
}
