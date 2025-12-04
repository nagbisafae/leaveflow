package com.leaveflow.leaveservice.dto;

import com.leaveflow.leaveservice.dao.LeaveType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateLeaveRequest {
    @NotNull
    private Long employeeId;
    @NotNull(message = "StartDay is required")
    private LocalDate startDate;
    @NotNull(message = "EndDate is required")
    private LocalDate endDate;
    @NotNull(message = "LeaveType is required")
    private LeaveType leaveType;
    @NotNull(message = "Reason is required")
    private String leaveReason;





}
