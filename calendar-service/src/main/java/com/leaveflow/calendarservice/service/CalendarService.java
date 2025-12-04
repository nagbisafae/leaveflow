package com.leaveflow.calendarservice.service;

import com.leaveflow.calendarservice.dto.HolidayResponse;
import com.leaveflow.calendarservice.dto.WorkingDayResponse;

import java.time.LocalDate;
import java.util.List;

public interface CalendarService {

    List<HolidayResponse> getAllHolidays();

    WorkingDayResponse isWorkingDay(LocalDate date);
}