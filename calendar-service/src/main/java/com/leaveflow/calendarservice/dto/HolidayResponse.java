package com.leaveflow.calendarservice.dto;

import com.leaveflow.calendarservice.model.Holiday;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HolidayResponse {

    private Long id;
    private LocalDate date;
    private String description;

    public HolidayResponse(Holiday holiday) {
        this.id = holiday.getId();
        this.date = holiday.getDate();
        this.description = holiday.getDescription();
    }
}