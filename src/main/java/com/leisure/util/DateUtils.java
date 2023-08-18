package com.leisure.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static String dataPattern = "yyyy-MM-dd";

    public static Date convertStringToDate(String dateString) throws Exception{
        Date date = new SimpleDateFormat(dataPattern).parse(dateString);
        return date;
    }
}
