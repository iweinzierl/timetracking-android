package com.github.iweinzierl.timetracking.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatter {

    private static final String TECHNICAL_DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";
    private static final String HUMAN_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private final DateFormat technicalDateFormat;
    private final DateFormat humanDateFormat;

    public DateFormatter(Locale locale) {
        this.technicalDateFormat = new SimpleDateFormat(TECHNICAL_DATETIME_PATTERN, locale);
        this.humanDateFormat = new SimpleDateFormat(HUMAN_DATETIME_PATTERN, locale);
    }

    public String formatHuman(Date date) {
        return date == null ? null : humanDateFormat.format(date);
    }

    public String formatTechnical(Date date) {
        return date == null ? null : technicalDateFormat.format(date);
    }

    public Date parseHuman(String date) throws ParseException {
        return date == null ? null : humanDateFormat.parse(date);
    }

    public Date parseTechnical(String date) throws ParseException {
        return date == null ? null : technicalDateFormat.parse(date);
    }
}
