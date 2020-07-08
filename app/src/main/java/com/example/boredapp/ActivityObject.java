package com.example.boredapp;

public class ActivityObject {
    String type;
    String price;
    String participants;
    String key;
    String activity;

    public  ActivityObject(String key,String activity,String type,String price,String participants){
        this.key=key;
        this.activity=activity;
        this.participants=participants;
        this.type=type;
        this.price=price;
    }
}
