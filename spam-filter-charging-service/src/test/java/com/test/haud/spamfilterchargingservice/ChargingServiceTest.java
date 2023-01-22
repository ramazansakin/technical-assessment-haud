package com.test.haud.spamfilterchargingservice;

import com.test.haud.spamfilterchargingservice.model.entity.SMS;
import com.test.haud.spamfilterchargingservice.model.pojo.IncomingSMS;
import com.test.haud.spamfilterchargingservice.repository.SMSRepository;
import com.test.haud.spamfilterchargingservice.service.ChargingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ChargingServiceTest {

    @InjectMocks
    private ChargingService chargingService;

    @Mock
    private SMSRepository smsRepository;

    @Test
    public void whenChargeSMS_thenSaveToRepository() {
        IncomingSMS incomingSMS = new IncomingSMS();
        incomingSMS.setSource("+123123132");
        incomingSMS.setDestination("+45345435");
        incomingSMS.setMessage("´Test Message!");
        incomingSMS.setTimeStamp("2022-01-01");

        chargingService.chargeSMS(incomingSMS);

        verify(smsRepository).save(any(SMS.class));
    }
}