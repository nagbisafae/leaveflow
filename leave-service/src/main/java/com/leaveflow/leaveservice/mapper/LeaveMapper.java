package com.leaveflow.leaveservice.mapper;

import com.leaveflow.leaveservice.dao.Leave;
import com.leaveflow.leaveservice.dto.LeaveResponse;
import com.leaveflow.leaveservice.dto.ManagerLeaveResponse;
import org.springframework.stereotype.Component;

@Component
public class LeaveMapper {
    public LeaveResponse toLeaveResponse(Leave leave) {
        if (leave == null) {
            return null;
        }
        LeaveResponse leaveResponse = new LeaveResponse();
        leaveResponse.setId(leave.getId());
        leaveResponse.setLeaveReason(leave.getLeaveReason());
        leaveResponse.setLeaveType(leave.getLeaveType());
        leaveResponse.setStartDate(leave.getStartDate());
        leaveResponse.setEndDate(leave.getEndDate());
        leaveResponse.setTeam(leave.getTeam());
        leaveResponse.setEmployeeId(leave.getEmployeeId());
        leaveResponse.setEmployeeName(leave.getEmployeeName());
        leaveResponse.setLeaveStatus(leave.getLeaveStatus());
        leaveResponse.setManagerId(leave.getManagerId());
        leaveResponse.setOverlapWarning(leave.getOverlapWarning());
        return leaveResponse;
    }
    public ManagerLeaveResponse toManagerLeaveDto(Leave leave) {
        if (leave == null) return null;

        ManagerLeaveResponse dto = new ManagerLeaveResponse();
        dto.setId(leave.getId());
        dto.setEmployeeId(leave.getEmployeeId());
        dto.setEmployeeName(leave.getEmployeeName());
        dto.setTeam(leave.getTeam());
        dto.setStartDate(leave.getStartDate());
        dto.setEndDate(leave.getEndDate());
        dto.setLeaveType(leave.getLeaveType());
        dto.setLeaveStatus(leave.getLeaveStatus());
        dto.setOverlapWarning(leave.getOverlapWarning());

        return dto;
    }

}
