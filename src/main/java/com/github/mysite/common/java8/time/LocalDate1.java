package com.github.mysite.common.java8.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 * description:
 *
 * @author :    jy.chen
 *  @version  :  1.0
 * @since  : 2015/8/28 - 14:15
 */
public class LocalDate1 {
    public static void main(String[] args) {
        
        //LocalDate today = LocalDate.now();
        //LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
        //LocalDate yesterday = tomorrow.minusDays(2);
        //
        //System.out.println(today);
        //System.out.println(tomorrow);
        //System.out.println(yesterday);
        //
        //LocalDate independenceDay = LocalDate.of(2014, Month.JULY, 4);
        //DayOfWeek dayOfWeek = independenceDay.getDayOfWeek();
        //System.out.println(dayOfWeek);    // FRIDAY

        DateTimeFormatter germanFormatter =
                DateTimeFormatter
                        .ofLocalizedDate(FormatStyle.MEDIUM)
                        .withLocale(Locale.GERMAN);

        LocalDate xmas = LocalDate.parse("24.12.2014", germanFormatter);
        System.out.println(xmas);   // 2014-12-24


    }
}
