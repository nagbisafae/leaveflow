package com.leaveflow.leaveservice.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "leaves")
@AllArgsConstructor
@NoArgsConstructor
public class Leave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long employeeId;
    private Long managerId;
    private String employeeName;
    private String team;

    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private LeaveType leaveType;
    private String leaveReason;
    @Enumerated(EnumType.STRING)
    private LeaveStatus leaveStatus;
    @Transient
    private Boolean overlapWarning;


}
