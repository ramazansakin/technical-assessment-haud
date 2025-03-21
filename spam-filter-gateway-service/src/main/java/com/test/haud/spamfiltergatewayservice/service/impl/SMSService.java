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
//
//    private final Queue<SMS> smsBuffer = new LinkedList<>();
//    private final int bufferSize = 500; // adjust this number as needed
//    private final long batchInterval = 450; // adjust this number as needed, in milliseconds
//
//    public SMSService(RabbitTemplate rabbitTemplate, BlockedDestinationService blockedDestinationService) {
//        this.rabbitTemplate = rabbitTemplate;
//        this.blockedDestinationService = blockedDestinationService;
//        startBufferThread();
//    }
//
//    private void startBufferThread() {
//        Thread bufferThread = new Thread(() -> {
//            while (true) {
//                try {
//                    Thread.sleep(batchInterval);
//                } catch (InterruptedException e) {
//                    log.error("Error while sleeping buffer thread", e);
//                }
//                if (!smsBuffer.isEmpty()) {
//                    List<SMS> smsBatch = new ArrayList<>();
//                    int count = 0;
//                    while (!smsBuffer.isEmpty() && count < bufferSize) {
//                        smsBatch.add(smsBuffer.poll());
//                        count++;
//                    }
//                    log.debug("Sending batch of {} SMS messages to charging service", smsBatch.size());
//                    rabbitTemplate.convertAndSend(queue, smsBatch);
//                }
//            }
//        });
//        bufferThread.start();
//    }

    public boolean receiveSMS(SMS sms) {
        // submit messages (fire-and-forget) to the charging module irrespective of the spam filter`s outcome
        log.debug("Outgoing sms to rabbitMQ : " + sms);
        rabbitTemplate.convertAndSend(queue, sms);
        if (!blockedDestinationService.isBlocked(sms.getDestination())) {
            // send the sms to related person if destination is not blocked
            // It can be SMS message sending via f.e Twilio or mail sending via f.e JavaMailSender
            log.info("SMS sent to destination");
            return true;
        }
        log.info("SMS could not be sent to destination");
        return false;
    }
}