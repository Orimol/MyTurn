package com.example.myturn.myturn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Queue {
    private String customer;
    private String business;
    private Date start;
    private long during_minutes;
    private Date end;

    public Queue(String customer, String business, String time, long during) throws ParseException{
        this.customer=customer;
        this.during_minutes=during;
        this.business=business;
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = dt.parse(time);
        this.start=date;
        this.end.setTime(start.getTime()+during_minutes);
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setDuring_minutes(long during_minutes) {
        this.during_minutes = during_minutes;
    }

    public Date getStart() {
        return start;
    }

    public long getDuring_minutes() {
        return during_minutes;
    }
};