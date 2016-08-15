package com.github.iweinzierl.timetracking.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatter {

    private static final String DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";

    private final DateFormat dateFormat;

    public DateFormatter(Locale locale) {
        this.dateFormat = new SimpleDateFormat(DATETIME_PATTERN, locale);
    }

    public String format(Date date) {
        return dateFormat.format(date);
    }

    public Date parse(String date) throws ParseException {
        return dateFormat.parse(date);
    }
}
