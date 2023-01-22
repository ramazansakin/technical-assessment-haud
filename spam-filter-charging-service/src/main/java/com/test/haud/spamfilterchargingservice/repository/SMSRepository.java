package com.test.haud.spamfilterchargingservice.repository;

import com.test.haud.spamfilterchargingservice.model.entity.SMS;
import com.test.haud.spamfilterchargingservice.model.pojo.SMSReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SMSRepository extends JpaRepository<SMS, Long> {

    @Query("SELECT sms.source as source, sms.destination as destination, count(sms) as count "
            + "FROM SMS sms "
            + "WHERE sms.timeStamp >= :startDate and sms.timeStamp <= :endDate "
            + "GROUP BY sms.source, sms.destination "
            + "ORDER BY sms.source ")
    List<SMSReport> getMonthlyReport(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
