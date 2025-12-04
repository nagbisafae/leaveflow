package com.leaveflow.calendarservice.service;

import com.leaveflow.calendarservice.dto.HolidayResponse;
import com.leaveflow.calendarservice.dto.WorkingDayResponse;
import com.leaveflow.calendarservice.model.Holiday;
import com.leaveflow.calendarservice.repository.HolidayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CalendarServiceImpl implements CalendarService {

    private final HolidayRepository holidayRepository;

    @Override
    @Transactional(readOnly = true)
    public List<HolidayResponse> getAllHolidays() {
        log.info("Fetching all holidays");

        List<Holiday> holidays = holidayRepository.findAll();

        log.info("Total holidays found: {}", holidays.size());

        return holidays.stream()
                .map(HolidayResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public WorkingDayResponse isWorkingDay(LocalDate date) {
        log.info("Checking if date is working day: {}", date);

        DayOfWeek dayOfWeek = date.getDayOfWeek();

        if (dayOfWeek == DayOfWeek.SATURDAY) {
            log.info("Date {} is Saturday - not a working day", date);
            return new WorkingDayResponse(date, false, "Saturday");
        }

        if (dayOfWeek == DayOfWeek.SUNDAY) {
            log.info("Date {} is Sunday - not a working day", date);
            return new WorkingDayResponse(date, false, "Sunday");
        }

        boolean isHoliday = holidayRepository.existsByDate(date);

        if (isHoliday) {
            Holiday holiday = holidayRepository.findByDate(date).orElse(null);
            String reason = holiday != null ? holiday.getDescription() : "Public Holiday";
            log.info("Date {} is a holiday: {}", date, reason);
            return new WorkingDayResponse(date, false, reason);
        }

        log.info("Date {} is a working day", date);
        return new WorkingDayResponse(date, true, "Working day");
    }
}