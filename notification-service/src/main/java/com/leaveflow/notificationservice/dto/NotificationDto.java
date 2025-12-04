package com.leaveflow.notificationservice.dto;

import com.leaveflow.notificationservice.dao.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    private Long recipientId;
    private Long leaveId;
    private NotificationType type;
    private String message;
}