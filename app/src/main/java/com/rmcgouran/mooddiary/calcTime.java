package com.rmcgouran.mooddiary;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class calcTime {

    public String getHowLong(long duration){
        Date now = new Date();

        long seconds = TimeUnit.MILLISECONDS.toSeconds(now.getTime() - duration);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(now.getTime() - duration);
        long hours = TimeUnit.MILLISECONDS.toHours(now.getTime() - duration);
        long days = TimeUnit.MILLISECONDS.toDays(now.getTime() - duration);
        long years = days/365;

        String howLong = "";

        if(seconds < 60){
            howLong = "Just Now";
        }else if(minutes == 1){
            howLong = "A Minute Ago";
        }else if(minutes > 1 && minutes < 60){
            howLong = minutes + " Minutes Ago";
        }else if(hours == 1){
            howLong = "An Hour Ago";
        }else if(hours > 1 && hours < 24){
            howLong = hours + " Hours Ago";
        }else if(days == 1){
            howLong = "A Day Ago";
        }else if(days > 1 && days < 365){
            howLong = days + " Days Ago";
        }else if(days > 365 && days <= 730){
            howLong = "A Year Ago";
        }else if(days > 730 && days <= 1095){
            howLong = "2 Years Ago";
        }else if(days == 1095){
            howLong = "3 Years Ago";
        }else{
            howLong = "More Than 3 Years Ago";
        }

        return howLong;
    }
}
