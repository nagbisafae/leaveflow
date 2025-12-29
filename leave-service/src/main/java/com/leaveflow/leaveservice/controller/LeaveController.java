package com.leaveflow.leaveservice.controller;

import com.leaveflow.leaveservice.dao.LeaveStatus;
import com.leaveflow.leaveservice.dto.CreateLeaveRequest;
import com.leaveflow.leaveservice.dto.LeaveResponse;
import com.leaveflow.leaveservice.dto.RejectLeaveRequest;
import com.leaveflow.leaveservice.service.LeaveService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leaves")
public class LeaveController {
    private final LeaveService leaveService;
    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LeaveResponse createValid(@Valid @RequestBody CreateLeaveRequest createLeaveRequest) {
        return leaveService.createLeave(createLeaveRequest);
    }
    @GetMapping("/{id}")
    public LeaveResponse getValid(@PathVariable Long id) {
        return leaveService.getLeave(id);
    }
    @GetMapping
    public Object getLeaves(@RequestParam(required = false) Long employeeId,
                            @RequestParam(required = false) Long managerId,
                            @RequestParam(required = false) LeaveStatus leaveStatus) {
        if(employeeId ==null && managerId == null && leaveStatus == null) {
            return leaveService.getAllLeaves();
        }else if(employeeId != null) {
            return leaveService.getLeavesForEmploye(employeeId);
        }else if(managerId != null && leaveStatus != null) {
            return leaveService.getLeavesForManagersAndStatus(managerId, leaveStatus);
        }else
        {
            throw new IllegalArgumentException("Either employeeId or (managerId & status) must be provided");
        }
    }
    @PostMapping("/{id}/approved")
    public LeaveResponse approveLeave(@PathVariable Long id,@RequestParam Long managerId) {
        return leaveService.approveLeave(id, managerId);
    }
    @PostMapping("/{id}/rejected")
    public LeaveResponse rejectLeave(@PathVariable Long id,
                                     @RequestParam Long managerId,
                                     @Valid @RequestBody RejectLeaveRequest request) {
        return leaveService.rejectLeave(id,managerId,request);

    }
}
