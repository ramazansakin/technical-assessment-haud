package com.test.haud.spamfiltergatewayservice;

import com.test.haud.spamfiltergatewayservice.controller.BlockedDestinationController;
import com.test.haud.spamfiltergatewayservice.service.BlockedDestinationServiceInt;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BlockedDestinationController.class)
public class BlockedDestinationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BlockedDestinationServiceInt blockedDestinationService;

    private final String BLOCKED_DESTINATIONS_URL = "/api/v1/blocked-destinations";

    @Test
    public void testGetBlockedDestinations() throws Exception {
        List<String> destinations = Arrays.asList("+123123123", "+35323242342");

        when(blockedDestinationService.getBlockedDestinations()).thenReturn(destinations);

        mockMvc.perform(get(BLOCKED_DESTINATIONS_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]", is("+123123123")))
                .andExpect(jsonPath("$[1]", is("+35323242342")));

        verify(blockedDestinationService, times(1)).getBlockedDestinations();
    }

    @Test
    public void testAddBlockedDestination() throws Exception {
        String destination = "13123123123";

        mockMvc.perform(post(BLOCKED_DESTINATIONS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(destination))
                .andExpect(status().isOk());

        verify(blockedDestinationService, times(1)).addBlockedDestination(destination);
    }

    @Test
    public void testUpdateBlockedDestination() throws Exception {
        String destination = "112312312312";

        mockMvc.perform(put(BLOCKED_DESTINATIONS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(destination))
                .andExpect(status().isOk());

        verify(blockedDestinationService, times(1)).updateBlockedDestination(destination);
    }

    @Test
    public void testDeleteBlockedDestination() throws Exception {
        String destination = "123123213123";

        mockMvc.perform(delete(BLOCKED_DESTINATIONS_URL + "/{destination}", destination))
                .andExpect(status().isOk());

        verify(blockedDestinationService, times(1)).deleteBlockedDestination(destination);
    }

}