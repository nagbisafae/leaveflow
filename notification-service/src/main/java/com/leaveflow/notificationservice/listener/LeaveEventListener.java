package com.leaveflow.notificationservice.listener;

import com.leaveflow.notificationservice.config.RabbitMQConfig;
import com.leaveflow.notificationservice.dto.LeaveEventDto;
import com.leaveflow.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LeaveEventListener {

    private final NotificationService notificationService;

    @RabbitListener(queues = RabbitMQConfig.LEAVE_EVENTS_QUEUE)
    public void handleLeaveEvent(LeaveEventDto leaveEvent) {
        log.info("Message RabbitMQ reçu: Congé {} - Statut {}",
                leaveEvent.getLeaveId(), leaveEvent.getLeaveStatus());

        try {
            notificationService.processLeaveEvent(leaveEvent);
            log.info("Événement traité avec succès pour le congé {}", leaveEvent.getLeaveId());
        } catch (Exception e) {
            log.error("Erreur lors du traitement de l'événement: {}", e.getMessage(), e);
            // Dans un cas réel, on pourrait renvoyer le message dans une Dead Letter Queue
        }
    }
}