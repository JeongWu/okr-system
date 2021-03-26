package com.eximbay.okr.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StringUtils {
    public static LocalDate stringToLocalDate(String date, String pattern){
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
    }
}
