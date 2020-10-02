package com.example.ManagementSystem.model;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@Table
public class Periods {
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED,ordinal = 0)
    private int id;
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED,ordering = Ordering.DESCENDING, ordinal = 1)
    private String period_name;
    private String event_name;
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordinal = 1)
    @DateTimeFormat(pattern = "dd-M-yyyy hh:mm:ss")
    private Date event_data = new Date();
    private String weak_race;
    private String strong_race;

    public Periods() {
    }

    public Periods(int id, String period_name, String event_name, Date event_data, String weak_race, String strong_race) {
        this.id = id;
        this.period_name = period_name;
        this.event_name = event_name;
        this.event_data = event_data;
        this.weak_race = weak_race;
        this.strong_race = strong_race;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPeriod_name() {
        return period_name;
    }

    public void setPeriod_name(String period_name) {
        this.period_name = period_name;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public Date getEvent_data() {
        return event_data;
    }

    public void setEvent_data(Date event_data) {
        this.event_data = event_data;
    }

    public String getWeak_race() {
        return weak_race;
    }

    public void setWeak_race(String weak_race) {
        this.weak_race = weak_race;
    }

    public String getStrong_race() {
        return strong_race;
    }

    public void setStrong_race(String strong_race) {
        this.strong_race = strong_race;
    }

    @Override
    public String toString() {
        return "Periods{" +
                "id=" + id +
                ", period_name='" + period_name + '\'' +
                ", event_name='" + event_name + '\'' +
                ", event_data='" + event_data + '\'' +
                ", weak_race='" + weak_race + '\'' +
                ", strong_race='" + strong_race + '\'' +
                '}';
    }
}
