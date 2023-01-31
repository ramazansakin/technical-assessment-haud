package com.test.haud.spamfiltergatewayservice;

import com.test.haud.spamfiltergatewayservice.model.pojo.SMS;
import com.test.haud.spamfiltergatewayservice.service.impl.BlockedDestinationService;
import com.test.haud.spamfiltergatewayservice.service.impl.SMSService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SmsServiceTest {

    @InjectMocks
    private SMSService smsService;

    @Mock
    private BlockedDestinationService destinationService;

    @Mock
    private RabbitTemplate rabbitTemplate;

    // test message to use any case
    private SMS smsRequest;

    @Before
    public void setUp() {
        smsRequest = new SMS();
        smsRequest.setSource("+123123123");
        smsRequest.setDestination("+234234324");
        smsRequest.setMessage("Test Message!");
    }

    @Test
    public void whenSendSms_thenSendToDestinationAndCharging() {
        // stub
        when(destinationService.isBlocked(smsRequest.getDestination())).thenReturn(false);
        // then
        boolean actualIsSend = smsService.receiveSMS(smsRequest);
        // verify
        verify(destinationService).isBlocked(smsRequest.getDestination());
        verify(rabbitTemplate).convertAndSend(any(), (Object) any());
        Assert.assertTrue(actualIsSend);
    }

    @Test
    public void whenSendSmsToBlockedDestination_thenRejected() {
        // stub
        when(destinationService.isBlocked(smsRequest.getDestination())).thenReturn(true);
        // then
        boolean actualIsSend = smsService.receiveSMS(smsRequest);
        // verify
        verify(destinationService).isBlocked(smsRequest.getDestination());
        verify(rabbitTemplate).convertAndSend(any(), (Object) any());
        Assert.assertFalse(actualIsSend);
    }
}
