package com.test.haud.spamfiltergatewayservice.service.impl;

import com.test.haud.spamfiltergatewayservice.model.entity.BlockedDestination;
import com.test.haud.spamfiltergatewayservice.repository.BlockedDestinationRepository;
import com.test.haud.spamfiltergatewayservice.service.BlockedDestinationServiceInt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlockedDestinationService implements BlockedDestinationServiceInt {

    // CRUD operations to manage blocked destinations
    private final BlockedDestinationRepository blockedDestinationRepository;

    public void addBlockedDestination(final String destination) {
        blockedDestinationRepository.save(new BlockedDestination(destination));
    }

    public void updateBlockedDestination(final String destination) {
        BlockedDestination blockedDestination = blockedDestinationRepository.findByDestination(destination);
        blockedDestination.setDestination(destination);
        blockedDestinationRepository.save(blockedDestination);
    }

    public void deleteBlockedDestination(final String destination) {
        blockedDestinationRepository.deleteByDestination(destination);
    }

    public List<String> getBlockedDestinations() {
        return blockedDestinationRepository.findAll()
                .stream().map(BlockedDestination::getDestination).collect(Collectors.toList());
    }

    public boolean isBlocked(final String destination) {
        return blockedDestinationRepository.existsByDestination(destination);
    }
}
