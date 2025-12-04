package com.leaveflow.leaveservice.dto;

import com.leaveflow.leaveservice.dao.LeaveStatus;
import com.leaveflow.leaveservice.dao.LeaveType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LeaveResponse {
    private Long id;
    private Long employeeId;
    private String employeeName;
    private Long managerId;
    private String team;
    private LocalDate startDate;
    private LocalDate endDate;
    private LeaveType leaveType;
    private String leaveReason;
    private LeaveStatus leaveStatus;
    private Boolean overlapWarning;

}
