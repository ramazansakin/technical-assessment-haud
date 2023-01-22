package com.test.haud.spamfiltergatewayservice.service;

import java.util.List;

public interface BlockedDestinationServiceInt {

    void addBlockedDestination(final String destination);

    void updateBlockedDestination(final String destination);

    void deleteBlockedDestination(final String destination);

    List<String> getBlockedDestinations();

    boolean isBlocked(final String destination);

}
