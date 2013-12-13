package com.hk.Components;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Hari on 11/28/13.
 */
public class DDate {


    public static Date UtcToLocal(String s)
    {


        Date parsed = ParsedDate(s);
        TimeZone tz = TimeZone.getDefault();
        SimpleDateFormat destFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
        destFormat.setTimeZone(tz);
        String result = destFormat.format(parsed);
        System.out.print(result);
        return parsed;

    }
    public static Date LocalToUtc(String s)
    {

        return null;
    }
    public static String ToUtcString(Date d)
    {

        return null;
    }
    public static String ToLocalString(Date d)
    {

        return null;
    }

    public static Date ParsedDate(String s)
    {
        Date parsed = null;
        //match 11/28/2013 1:20:31 PM format
        String df1 = "(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d) ([1-9]|1[012]):[0-5][0-9]:[0-9][0-9](\\s)?(?i)(am|pm)";
        //match 2013-11-28T13:55:12.5819042Z format
        String df2 = "((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])[T]([01]?[0-9]|2[0-3]):([0-5][0-9]:[0-9][0-9]\\.\\d\\d\\d\\d\\d\\d\\d)(\\s)?(?i)(Z)";
        try {
        if(s.matches(df1))
        {
            SimpleDateFormat sourceFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
            sourceFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            parsed = sourceFormat.parse(s);
            return  parsed;

        }else if(s.matches(df2)){

            SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'");
            sourceFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            parsed = sourceFormat.parse(s);
            return  parsed;
          }
          else
          {
             return  parsed;
           }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  parsed;
    }

}
