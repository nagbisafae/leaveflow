package com.leaveflow.leaveservice.Repositories;

import com.leaveflow.leaveservice.dao.Leave;
import com.leaveflow.leaveservice.dao.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

public interface LeaveRepository extends JpaRepository<Leave, Long> {
    List<Leave> findByEmployeeId(Long employeeId) ;
    List<Leave> findByManagerIdAndLeaveStatus(Long managerId, LeaveStatus leaveStatus);
    List<Leave> findByLeaveStatus(LeaveStatus leaveStatus);
    List<Leave> findByManagerId(Long managerId);
    List<Leave> findByTeamAndLeaveStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            String team,
            LeaveStatus leaveStatus,
            LocalDate endDate,
            LocalDate startDate
    );

}
