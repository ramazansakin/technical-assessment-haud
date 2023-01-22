package com.test.haud.spamfiltergatewayservice.controller;

import com.test.haud.spamfiltergatewayservice.service.BlockedDestinationServiceInt;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/blocked-destinations")
@RequiredArgsConstructor
public class BlockedDestinationController {

    private final BlockedDestinationServiceInt blockedDestinationService;

    // CRUD operation APIs to manage blocked destinations
    @GetMapping
    public List<String> getBlockedDestinations() {
        return blockedDestinationService.getBlockedDestinations();
    }

    @PostMapping
    public void addBlockedDestination(@RequestBody String destination) {
        blockedDestinationService.addBlockedDestination(destination);
    }

    @PutMapping
    public void updateBlockedDestination(@RequestBody String destination) {
        blockedDestinationService.updateBlockedDestination(destination);
    }

    @DeleteMapping("/{destination}")
    public void deleteBlockedDestination(@PathVariable String destination) {
        blockedDestinationService.deleteBlockedDestination(destination);
    }

}
