package com.test.haud.spamfiltergatewayservice.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BlockedDestination {

    @Id
    @GeneratedValue
    private Long id;

    @Getter
    @Setter
    private String destination;

    public BlockedDestination() {
    }

    public BlockedDestination(String destination) {
        this.destination = destination;
    }

}
