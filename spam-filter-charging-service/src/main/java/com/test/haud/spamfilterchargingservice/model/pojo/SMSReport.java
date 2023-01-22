package com.test.haud.spamfilterchargingservice.model.pojo;

// Projection on SMS
public interface SMSReport {

    String getSource();

    String getDestination();

    int getCount();

}
