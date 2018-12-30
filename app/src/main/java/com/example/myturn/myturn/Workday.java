package com.example.myturn.myturn;


import android.util.Pair;

enum Days {Sun(1),Mon(2),Tue(3),Wed(4),Thu(5);
    public final int value;
    Days(int value) { this.value = value; }
}

public class Workday {
    private Days day;
    private Pair<Double,Double> HoursPerDay;

    Workday(Days day,double from, double to){
        this.day=day;
        this.HoursPerDay = new Pair<>(from,to);
    }

    public String toStirng(){
        return (day.toString()+", from: "+HoursPerDay.first+" to: "+HoursPerDay.second);
    }

    public String getDay() {
        return day.toString();
    }
}
