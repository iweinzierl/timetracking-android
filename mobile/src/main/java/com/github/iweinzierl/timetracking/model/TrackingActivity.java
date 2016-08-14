package com.github.iweinzierl.timetracking.model;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Table;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "activities")
public class TrackingActivity extends SugarRecord {

    @Column(name = "uuid")
    public String uuid;

    @Column(name = "uid")
    public String uid;

    @Column(name = "begin")
    public Date begin;

    @Column(name = "end")
    public Date end;

    @Column(name = "description")
    public String description;

    @Column(name = "bucket")
    public String bucket;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public static List<TrackingActivity> listUnsynchedActivities() {
        TrackingActivity a = new TrackingActivity();
        a.setBegin(new Date());
        a.setEnd(new Date());
        a.setDescription("Desc A");

        TrackingActivity b = new TrackingActivity();
        b.setBegin(new Date());
        b.setEnd(new Date());
        b.setDescription("Desc B");

        List<TrackingActivity> res = new ArrayList<>();
        res.add(a);
        res.add(b);
        return res;

        /*
        return new Select()
                .from(TrackingActivity.class)
                .where("uuid = null")
                .execute();
                */
    }

    public static long getNumberOfUnsynchedActivities() {
        return TrackingActivity.count(
                TrackingActivity.class,
                "uuid = null",
                null);
    }
}
