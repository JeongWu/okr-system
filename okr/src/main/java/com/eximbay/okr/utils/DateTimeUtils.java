package com.eximbay.okr.utils;

import com.eximbay.okr.constant.AppConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;

public class DateTimeUtils {
    public static boolean isCurrentlyValid(String startDate, String endDate){
        String now = LocalDate.now().format(DateTimeFormatter.ofPattern(AppConst.DATE_FORMAT_YYYYMMDD));
        return (now.compareTo(startDate) >= 0 && now.compareTo(endDate) <= 0);
    }

    public static LocalDate changeDate(String date) {
        return StringUtils.stringToLocalDate(date, "yyyy-MM-dd");
    }

    public static String findCurrentQuarter(){
        LocalDate now = LocalDate.now();
        return now.getYear() + "-" + now.get(IsoFields.QUARTER_OF_YEAR) + "Q";
    }

}
