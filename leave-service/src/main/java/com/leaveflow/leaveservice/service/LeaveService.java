package com.leaveflow.leaveservice.service;

import com.leaveflow.leaveservice.Repositories.LeaveRepository;
import com.leaveflow.leaveservice.client.EmployeeClient;
import com.leaveflow.leaveservice.config.RabbitMQConfig;
import com.leaveflow.leaveservice.dao.Leave;
import com.leaveflow.leaveservice.dao.LeaveStatus;
import com.leaveflow.leaveservice.dto.CreateLeaveRequest;
import com.leaveflow.leaveservice.dto.EmployeeDto;
import com.leaveflow.leaveservice.dto.LeaveEventDto;
import com.leaveflow.leaveservice.dto.LeaveResponse;
import com.leaveflow.leaveservice.dto.RejectLeaveRequest;
import com.leaveflow.leaveservice.exception.ForbiddenActionException;
import com.leaveflow.leaveservice.exception.LeaveNotFoundException;
import com.leaveflow.leaveservice.mapper.LeaveMapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;



@Service
@Transactional
public class LeaveService {
    private final LeaveRepository leaveRepository;
    private final LeaveMapper leaveMapper;
    private final EmployeeClient employeeClient;
    private static final Logger log = LoggerFactory.getLogger(LeaveService.class);
    private final RabbitTemplate rabbitTemplate;
    public LeaveService(LeaveMapper leaveMapper,LeaveRepository leaveRepository, EmployeeClient employeeClient, RabbitTemplate rabbitTemplate) {
        this.leaveRepository = leaveRepository;
        this.employeeClient = employeeClient;
        this.leaveMapper = leaveMapper;
        this.rabbitTemplate = rabbitTemplate;
    }
    public LeaveResponse createLeave(CreateLeaveRequest request)
    {
        if(request.getStartDate().isAfter(request.getEndDate())) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        EmployeeDto employee=employeeClient.getEmployee(request.getEmployeeId());
        Leave leave = new Leave();
        leave.setEmployeeId(employee.getId());
        leave.setStartDate(request.getStartDate());
        leave.setLeaveReason(request.getLeaveReason());
        leave.setLeaveType(request.getLeaveType());
        leave.setLeaveStatus(LeaveStatus.Pending_Manager);
        leave.setEmployeeName(employee.getName());
        leave.setManagerId(employee.getManagerId());
        leave.setTeam(employee.getTeam());
        leave.setEndDate(request.getEndDate());
        boolean overlap=hasOverlapInTeam(leave);
        leave.setOverlapWarning(overlap);
        // Save leave
        Leave savedLeave = leaveRepository.save(leave);

        // Publish event to RabbitMQ
        publishLeaveEvent(savedLeave, "CREATED");

        return leaveMapper.toLeaveResponse(savedLeave);
    }
    public LeaveResponse getLeave(Long id)
    {
        Leave leave=leaveRepository.findById(id).orElseThrow(()->new LeaveNotFoundException(id));
        return leaveMapper.toLeaveResponse(leave);
    }
    public List<LeaveResponse> getLeavesForEmploye(Long employeeId)
    {
       return leaveRepository.findByEmployeeId(employeeId).stream().
                map(leaveMapper::toLeaveResponse).
                collect(Collectors.toList());
    }
    public List<LeaveResponse> getLeavesForManagersAndStatus(Long managerId, LeaveStatus leaveStatus)
    {
       return leaveRepository.findByManagerIdAndLeaveStatus(managerId,leaveStatus).stream().
                map(leaveMapper::toLeaveResponse).
                collect(Collectors.toList());
    }
    public LeaveResponse approveLeave(Long leaveId,Long managerId) {
        Leave leave = leaveRepository.findById(leaveId).orElseThrow(()->new LeaveNotFoundException(leaveId));
        if(!managerId.equals(leave.getManagerId())) {
            throw new ForbiddenActionException("Manager is not allowed to approve this leave");
        }
        leave.setLeaveStatus(LeaveStatus.Approved);
        Leave savedLeave = leaveRepository.save(leave);

        // Publish event to RabbitMQ
        publishLeaveEvent(savedLeave, "APPROVED");

        return leaveMapper.toLeaveResponse(savedLeave);
    }
    public LeaveResponse rejectLeave(Long leaveId,Long managerId, RejectLeaveRequest request) {

        Leave leave = leaveRepository.findById(leaveId).
                orElseThrow(()->new LeaveNotFoundException(leaveId));

        if(!managerId.equals(leave.getManagerId())) {
            throw new ForbiddenActionException("Manager is not allowed to reject this leave");
        }

        leave.setLeaveStatus(LeaveStatus.Rejected);
        leave.setLeaveReason(request.getLeaveReason());
        Leave savedLeave = leaveRepository.save(leave);

        // Publish event to RabbitMQ
        publishLeaveEvent(savedLeave, "REJECTED");

        return leaveMapper.toLeaveResponse(savedLeave);
    }

    private void publishLeaveEvent(Leave leave, String action) {
        try {
            LeaveEventDto event = new LeaveEventDto(
                    leave.getId(),
                    leave.getEmployeeId(),
                    leave.getEmployeeName(),
                    leave.getStartDate(),
                    leave.getEndDate(),
                    leave.getLeaveType().toString(),
                    leave.getLeaveStatus().toString(),
                    action
            );

            log.info("Publishing leave event: {}", event);

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE,
                    RabbitMQConfig.ROUTING_KEY,
                    event
            );

            log.info("Successfully published leave event for leave ID: {}", leave.getId());
        } catch (Exception e) {
            log.error("Failed to publish leave event for leave ID: {}", leave.getId(), e);
            // Don't fail the transaction if event publishing fails
        }
    }

    private boolean hasOverlapInTeam(Leave leave)
    {
        List<Leave> overlaps=leaveRepository.findByTeamAndLeaveStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                leave.getTeam(),
                LeaveStatus.Approved,
                leave.getEndDate(),
                leave.getStartDate()
                );
        overlaps.addAll(
        leaveRepository.findByTeamAndLeaveStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                leave.getTeam(),
                LeaveStatus.Pending_Manager,
                leave.getEndDate(),
                leave.getStartDate()
        )
        );
        return !overlaps.isEmpty();

    }
    public List<LeaveResponse> getAllLeaves() {
        return leaveRepository.findAll()
                .stream()
                .map(leaveMapper::toLeaveResponse)
                .collect(Collectors.toList());
    }
}
