package com.test.haud.spamfiltergatewayservice.model.pojo;

import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class SMS {

    private String source;
    private String destination;
    private String message;
    private String timeStamp = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

}
