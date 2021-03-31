package com.eximbay.okr.utils;

import com.eximbay.okr.constant.AppConst;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;

public class DateTimeUtils {
    public static final String[] firstDayOfQuarter = new String[]{"", "01-01", "04-01", "07-01", "10-01"};
    public static final String[] lastDayOfQuarter = new String[]{"", "03-31", "06-30", "09-30", "12-31"};

    public static boolean isCurrentlyValid(String startDate, String endDate) {
        String now = LocalDate.now().format(DateTimeFormatter.ofPattern(AppConst.DATE_FORMAT_YYYYMMDD));
        return (now.compareTo(startDate) >= 0 && now.compareTo(endDate) <= 0);
    }

    public static LocalDate changeDate(String date) {
        return StringUtils.stringToLocalDate(date, "yyyy-MM-dd");
    }

    public static String findCurrentQuarter() {
        LocalDate now = LocalDate.now();
        return now.getYear() + "-" + now.get(IsoFields.QUARTER_OF_YEAR) + "Q";
    }

    public static String getCurrentDateInString() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(AppConst.DATE_FORMAT_YYYYMMDD));
    }

    public static int getCurrentYear() {
        return LocalDate.now().getYear();
    }

    public static int getCurrentQuarter() {
        return LocalDate.now().get(IsoFields.QUARTER_OF_YEAR);
    }

    public static int getCurrentMonth() {
        return LocalDate.now().getMonthValue();
    }

    public static String toDBString(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern(AppConst.DATE_FORMAT_YYYYMMDD));
    }

    public static String todayDBString() {
        return toDBString(LocalDate.now());
    }

    public static LocalDate fromDBString(String dateStringOfDBPattern) {
        return LocalDate.parse(dateStringOfDBPattern, DateTimeFormatter.ofPattern(AppConst.DATE_FORMAT_YYYYMMDD));
    }

    public static String oneDayAfter(String dbDateString) {
        LocalDate dbLocalDate = DateTimeUtils.fromDBString(dbDateString);
        return DateTimeUtils.toDBString(dbLocalDate.plusDays(1));
    }

    public static String oneDayBefore(String dbDateString) {
        LocalDate dbLocalDate = DateTimeUtils.fromDBString(dbDateString);
        return DateTimeUtils.toDBString(dbLocalDate.plusDays(-1));
    }
}
