package com.test.haud.spamfilterchargingservice.service;

import com.test.haud.spamfilterchargingservice.model.entity.SMS;
import com.test.haud.spamfilterchargingservice.model.pojo.IncomingSMS;
import com.test.haud.spamfilterchargingservice.repository.SMSRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChargingService {

    private final SMSRepository smsRepository;

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void chargeSMS(List<IncomingSMS> incomingSMSList) {
        List<SMS> smsBatch = new LinkedList<>();
        for (IncomingSMS incomingSMS : incomingSMSList) {
            log.info("Incoming SMS " + incomingSMS);
            SMS sms = convertToEntity(incomingSMS);
            smsBatch.add(sms);
        }
        // sms entity batching
        smsRepository.saveAll(smsBatch);
    }

    private SMS convertToEntity(final IncomingSMS incomingSMS) {
        // Convert incoming SMS POJO to SMS Entity
        return new SMS(null, incomingSMS.getSource(), incomingSMS.getDestination(), incomingSMS.getMessage(),
                LocalDate.parse(incomingSMS.getTimeStamp()));
    }

}

