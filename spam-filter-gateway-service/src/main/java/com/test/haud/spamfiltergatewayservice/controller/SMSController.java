package com.test.haud.spamfiltergatewayservice.controller;

import com.test.haud.spamfiltergatewayservice.model.pojo.SMS;
import com.test.haud.spamfiltergatewayservice.service.impl.SMSService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sms")
@RequiredArgsConstructor
public class SMSController {

    private final SMSService smsService;

    // Rest API for receiving short messages from the sender
    @PostMapping
    public void receiveSMS(@RequestBody SMS sms) {
        smsService.receiveSMS(sms);
    }
}
