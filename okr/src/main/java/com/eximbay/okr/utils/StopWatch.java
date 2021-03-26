package com.eximbay.okr.utils;

import lombok.Data;

@Data
public class StopWatch {
    static long begin;
    static long end;

    public static void start(){
        begin = System.currentTimeMillis();
    }

    public static void stop(){
        end = System.currentTimeMillis();
        System.out.println(end - begin);
    }
}
