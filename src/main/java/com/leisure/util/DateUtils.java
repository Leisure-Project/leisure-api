package com.leisure.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtils {
    private static String dataPattern = "yyyy-MM-dd";
    private static String dataPatternFull = "yyyy-MM-dd HH:mm:ss";

    public static Date convertStringToDate(String dateString) throws Exception{
        Date date = new SimpleDateFormat(dataPattern).parse(dateString);
        return date;
    }

    public static Date getCurrentDateAndHour(){
        ZoneId zona = ZoneId.systemDefault();
        Date currentDate = Date.from(LocalDateTime.now().atZone(zona).toInstant());
        return currentDate;
    }
    public static String convertDateToString(Date date){
        SimpleDateFormat df = new SimpleDateFormat(dataPatternFull);
        return df.format(date);
    }
}
