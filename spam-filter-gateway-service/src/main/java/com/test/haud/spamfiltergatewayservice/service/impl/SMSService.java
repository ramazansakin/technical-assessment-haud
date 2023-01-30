package com.test.haud.spamfiltergatewayservice.service.impl;

import com.test.haud.spamfiltergatewayservice.model.pojo.SMS;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SMSService {

    private final BlockedDestinationService blockedDestinationService;
    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.queue}")
    private String queue;

    // private ConcurrentLinkedDeque<SMS> smsBatch = new ConcurrentLinkedDeque<>();
    private List<SMS> smsBatch = new ArrayList<>();

    public void receiveSMS(SMS sms) {
        // submit messages (fire-and-forget) to the charging module irrespective of the spam filter`s outcome
        log.debug("Outgoing sms to rabbitMQ : " + sms);
        smsBatch.add(sms);
        if (!blockedDestinationService.isBlocked(sms.getDestination())) {
            // Send the SMS to the related person if the destination is not blocked
            log.info("SMS sent to destination");
        } else log.info("SMS could not be sent to destination");
    }

    @Scheduled(fixedDelay = 100)
    public void sendBatch() {
        log.debug("Sending batch of SMS messages to RabbitMQ...");
        rabbitTemplate.convertAndSend(queue, smsBatch);
        smsBatch.clear();
    }

}