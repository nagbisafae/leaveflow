package com.leaveflow.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveEventDto {
    private Long leaveId;
    private Long employeeId;
    private Long managerId;
    private String employeeName;
    private String leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String leaveStatus;  // PENDING, APPROVED, REJECTED
    private String leaveReason;
}