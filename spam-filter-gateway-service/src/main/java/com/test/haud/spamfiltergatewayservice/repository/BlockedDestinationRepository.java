package com.test.haud.spamfiltergatewayservice.repository;

import com.test.haud.spamfiltergatewayservice.model.entity.BlockedDestination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockedDestinationRepository extends JpaRepository<BlockedDestination, Long> {

    boolean existsByDestination(String destination);

    void deleteByDestination(String destination);

    BlockedDestination findByDestination(String destination);

}
