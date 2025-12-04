package com.leaveflow.leaveservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RejectLeaveRequest {
    @NotBlank
    private String leaveReason;



}
