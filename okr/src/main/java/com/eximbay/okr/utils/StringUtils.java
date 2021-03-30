package com.eximbay.okr.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class StringUtils {


    public static boolean isNullOrEmpty(String content) {
        return (content == null || content.equals(""));
    }

    public static LocalDate stringToLocalDate(String date, String pattern) {
        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
        } catch (Exception e) {
            return null;
        }
    }

    public static Instant stringToInstant(String date, String pattern, boolean isEndOfDay) {
        if (isNullOrEmpty(date)) return null;
        try {
            LocalDate localDate = stringToLocalDate(date, pattern);
            if (isEndOfDay) return localDate.atTime(23, 59, 59, 99).atZone(ZoneId.systemDefault()).toInstant();
            return localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        } catch (Exception e) {
            return null;
        }
    }

    public static String shortenString(String str, Integer length) {
        if (isNullOrEmpty(str) || str.length() <= length) return str;
        return str.substring(0, length) + "...";
    }

    public static String addDashToDBDate(String dbDate) {
        //yyyyMMdd -> yyyy-MM-dd
        return dbDate.substring(0, 4) + "-" + dbDate.substring(4, 6) + "-" + dbDate.substring(6);
    }
}
