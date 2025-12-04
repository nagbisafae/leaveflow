package com.leaveflow.calendarservice.controller;

import com.leaveflow.calendarservice.dto.HolidayResponse;
import com.leaveflow.calendarservice.dto.WorkingDayResponse;
import com.leaveflow.calendarservice.service.CalendarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
@Slf4j
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping("/holidays")
    public ResponseEntity<List<HolidayResponse>> getAllHolidays() {
        log.info("GET /calendar/holidays - Fetching all holidays");

        List<HolidayResponse> holidays = calendarService.getAllHolidays();

        return ResponseEntity.ok(holidays);
    }

    @GetMapping("/is-working-day")
    public ResponseEntity<WorkingDayResponse> isWorkingDay(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        log.info("GET /calendar/is-working-day?date={} - Checking working day", date);

        WorkingDayResponse response = calendarService.isWorkingDay(date);

        return ResponseEntity.ok(response);
    }
}