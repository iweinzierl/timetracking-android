package com.github.iweinzierl.timetracking.util;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class DateFormatterTest {

    @Test
    public void parse() throws Exception {
        String dateStr1 = "2016-08-01T08:00:00+0200";
        new DateFormatter().parse(dateStr1);
    }

    @Test
    public void format() throws Exception {
        String dateStr = new DateFormatter().format(new Date());
        System.out.println(dateStr);
    }
}