package com.github.iweinzierl.timetracking.model;

import android.support.annotation.NonNull;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

import java.util.Date;

@Table(name = "checkinout")
public class CheckInOut extends SugarRecord implements Comparable {

    public enum Type {
        CHECKIN,
        CHECKOUT
    }

    private Date dateTime;

    private Type type;

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public int compareTo(@NonNull Object another) {
        if (another instanceof CheckInOut) {
            CheckInOut other = (CheckInOut) another;
            return dateTime.compareTo(other.getDateTime());
        } else {
            return -1;
        }
    }

    public static long getNumberOfCheckins() {
        return CheckInOut.count(
                CheckInOut.class,
                "type = 'CHECKIN'",
                null);
    }

    public static long getNumberOfCheckouts() {
        return CheckInOut.count(
                CheckInOut.class,
                "type = 'CHECKOUT'",
                null);
    }
}
