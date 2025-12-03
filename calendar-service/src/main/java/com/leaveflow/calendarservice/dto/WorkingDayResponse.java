package com.leaveflow.calendarservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkingDayResponse {

    private LocalDate date;
    private boolean working;
    private String reason;
}