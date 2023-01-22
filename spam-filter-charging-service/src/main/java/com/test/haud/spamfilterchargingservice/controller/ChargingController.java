package com.test.haud.spamfilterchargingservice.controller;

import com.test.haud.spamfilterchargingservice.model.pojo.SMSReport;
import com.test.haud.spamfilterchargingservice.repository.SMSRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/charging")
public class ChargingController {

    // We can create also SMSService to deliver business logic but now there is extra logic,
    // so  I just directly use the repository
    private final SMSRepository smsRepository;

    @GetMapping("/monthly-report/{year}/{month}")
    public List<SMSReport> getMonthlyReport(@PathVariable int year, @PathVariable int month) {

        // Because we get formatted LocalDate from gateway,
        // also we need to parse and re-convert it as the same with gateways'
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // first day of the month
        LocalDate startDate = LocalDate.of(year, month, 1);
        String formattedStartDateStr = startDate.format(dateTimeFormatter);
        // last day of the month
        LocalDate endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());
        String formattedEndDateStr = endDate.format(dateTimeFormatter);

        return smsRepository.getMonthlyReport(
                LocalDate.parse(formattedStartDateStr, dateTimeFormatter),
                LocalDate.parse(formattedEndDateStr, dateTimeFormatter));
    }

}
