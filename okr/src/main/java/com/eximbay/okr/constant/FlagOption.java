package com.eximbay.okr.constant;

public class FlagOption {
    public static String Y = "Y";
    public static String N = "N";

    public static boolean isYes(String flag) {
        return Y.equals(flag);
    }
}
