package com.github.iweinzierl.timetracking.util;

import org.junit.Test;

import java.util.Date;
import java.util.Locale;

public class DateFormatterTest {

    @Test
    public void parse() throws Exception {
        String dateStr1 = "2016-08-01T08:00:00+0200";
        new DateFormatter(Locale.getDefault()).parseTechnical(dateStr1);
    }

    @Test
    public void format() throws Exception {
        String dateStr = new DateFormatter(Locale.getDefault()).formatHuman(new Date());
        System.out.println(dateStr);
    }
}