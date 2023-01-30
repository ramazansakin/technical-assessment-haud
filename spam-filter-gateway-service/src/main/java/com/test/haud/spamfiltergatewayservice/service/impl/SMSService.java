package com.test.haud.spamfiltergatewayservice.service.impl;

import com.test.haud.spamfiltergatewayservice.model.pojo.SMS;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SMSService {

    private final BlockedDestinationService blockedDestinationService;
    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.queue}")
    private String queue;

    public void receiveSMS(SMS sms) {
        // submit messages (fire-and-forget) to the charging module irrespective of the spam filter`s outcome
        log.debug("Outgoing sms to rabbitMQ : " + sms);
        rabbitTemplate.convertAndSend(queue, sms);
        if (!blockedDestinationService.isBlocked(sms.getDestination())) {
            // send the sms to related person if destination is not blocked
            log.debug("SMS sent to destination");
        } else log.debug("SMS could not be sent to destination");
    }
}