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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
@Service
@RequiredArgsConstructor
public class SMSService {

    private final BlockedDestinationService blockedDestinationService;
    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.queue}")
    private String queue;

    private final BlockingQueue<SMS> smsQueue = new LinkedBlockingQueue<>();

    public void receiveSMS(SMS sms) {
        // submit messages (fire-and-forget) to the charging module irrespective of the spam filter`s outcome
        log.debug("Outgoing sms to rabbitMQ : " + sms);
        smsQueue.add(sms);
        log.debug("SMS added to queue");
        if (!blockedDestinationService.isBlocked(sms.getDestination())) {
            // Send the SMS to the related person if the destination is not blocked
        } else log.debug("SMS could not be sent to destination");
    }

    @Scheduled(fixedDelay = 500)
    public void processQueue() {
        List<SMS> batch = new ArrayList<>();
        smsQueue.drainTo(batch);
        if (!batch.isEmpty()) {
            rabbitTemplate.convertAndSend(queue, batch);
        }
    }

}