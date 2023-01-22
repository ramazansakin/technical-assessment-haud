package com.test.haud.spamfilterchargingservice.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncomingSMS {
    private String source;
    private String destination;
    private String message;
    private String timeStamp;
}
