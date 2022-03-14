package com.teylor.currencyexchanges.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
    private static final SimpleDateFormat EXCHANGE_RATE_DATE_FORMATTER;
    static{
        EXCHANGE_RATE_DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
        EXCHANGE_RATE_DATE_FORMATTER.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public static Date getExchangeDate(String dateStr) throws ParseException {
        return EXCHANGE_RATE_DATE_FORMATTER.parse(dateStr);
    }

}
