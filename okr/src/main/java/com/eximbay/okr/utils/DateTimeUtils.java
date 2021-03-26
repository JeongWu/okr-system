package com.eximbay.okr.utils;

import com.eximbay.okr.constant.AppConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    public static boolean isCurrentlyValid(String startDate, String endDate){
        String now = LocalDate.now().format(DateTimeFormatter.ofPattern(AppConst.DATE_FORMAT_YYYYMMDD));
        return (now.compareTo(startDate) >= 0 && now.compareTo(endDate) <= 0);
    }
}
