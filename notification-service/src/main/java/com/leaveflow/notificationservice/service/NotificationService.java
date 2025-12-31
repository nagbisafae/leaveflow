package com.leaveflow.notificationservice.service;

import com.leaveflow.notificationservice.dao.Notification;
import com.leaveflow.notificationservice.dao.NotificationType;
import com.leaveflow.notificationservice.dto.LeaveEventDto;
import com.leaveflow.notificationservice.dto.NotificationDto;
import com.leaveflow.notificationservice.dto.NotificationResponse;
import com.leaveflow.notificationservice.repositories.NotificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public List<NotificationResponse> getAllNotifications() {
        return notificationRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Créer une notification
    @Transactional
    public NotificationResponse createNotification(NotificationDto notificationDto) {
        Notification notification = new Notification();
        notification.setRecipientId(notificationDto.getRecipientId());
        notification.setLeaveId(notificationDto.getLeaveId());
        notification.setType(notificationDto.getType());
        notification.setMessage(notificationDto.getMessage());
        notification.setIsRead(false);

        Notification saved = notificationRepository.save(notification);
        log.info("Notification créée: {} pour l'utilisateur {}", saved.getId(), saved.getRecipientId());

        return mapToResponse(saved);
    }

    // Traiter un événement de congé depuis RabbitMQ
    @Transactional
    public void processLeaveEvent(LeaveEventDto event) {
        log.info("Traitement de l'événement: {} pour le congé {}", event.getLeaveStatus(), event.getLeaveId());

        String status = event.getLeaveStatus();

        if ("Pending_Manager".equalsIgnoreCase(status)) {
            status = "PENDING";
        } else if ("Approved".equalsIgnoreCase(status)) {
            status = "APPROVED";
        } else if ("Rejected".equalsIgnoreCase(status)) {
            status = "REJECTED";
        }

        event.setLeaveStatus(status);

        switch (event.getLeaveStatus()) {

            case "PENDING":
                if (event.getManagerId() != null) {
                    createNotificationForManager(event);
                }
                createNotificationForEmployee(
                        event,
                        NotificationType.LEAVE_SUBMITTED,
                        "Votre demande de congé a été soumise et est en attente d'approbation."
                );
                break;

            case "APPROVED":
                createNotificationForEmployee(
                        event,
                        NotificationType.LEAVE_APPROVED,
                        "Votre demande de congé du " + event.getStartDate() +
                                " au " + event.getEndDate() + " a été approuvée !"
                );
                break;

            case "REJECTED":
                String rejectionMsg = "Votre demande de congé a été rejetée.";
                if (event.getLeaveReason() != null) {
                    rejectionMsg += " Raison: " + event.getLeaveReason();
                }
                createNotificationForEmployee(
                        event,
                        NotificationType.LEAVE_REJECTED,
                        rejectionMsg
                );
                break;

            default:
                log.warn("Statut de congé inconnu: {}", event.getLeaveStatus());
        }
    }


    // Créer notification pour l'employé
    private void createNotificationForEmployee(LeaveEventDto event, NotificationType type, String message) {
        NotificationDto dto = new NotificationDto();
        dto.setRecipientId(event.getEmployeeId());
        dto.setLeaveId(event.getLeaveId());
        dto.setType(type);
        dto.setMessage(message);
        createNotification(dto);
    }

    // Créer notification pour le manager
    private void createNotificationForManager(LeaveEventDto event) {
        NotificationDto dto = new NotificationDto();
        dto.setRecipientId(event.getManagerId());
        dto.setLeaveId(event.getLeaveId());
        dto.setType(NotificationType.LEAVE_SUBMITTED);
        dto.setMessage(event.getEmployeeName() + " a soumis une demande de congé du " +
                event.getStartDate() + " au " + event.getEndDate());
        createNotification(dto);
    }

    // Obtenir toutes les notifications d'un utilisateur
    public List<NotificationResponse> getNotificationsForUser(Long userId) {
        return notificationRepository.findByRecipientIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Obtenir les notifications non lues
    public List<NotificationResponse> getUnreadNotifications(Long userId) {
        return notificationRepository.findByRecipientIdAndIsReadFalseOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Compter les notifications non lues
    public Long countUnreadNotifications(Long userId) {
        return notificationRepository.countByRecipientIdAndIsReadFalse(userId);
    }

    // Marquer comme lue
    @Transactional
    public NotificationResponse markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification non trouvée: " + notificationId));

        notification.setIsRead(true);
        notification.setReadAt(LocalDateTime.now());

        Notification updated = notificationRepository.save(notification);
        log.info("Notification {} marquée comme lue", notificationId);

        return mapToResponse(updated);
    }

    // Marquer toutes les notifications comme lues
    @Transactional
    public void markAllAsRead(Long userId) {
        List<Notification> unreadNotifications =
                notificationRepository.findByRecipientIdAndIsReadFalseOrderByCreatedAtDesc(userId);

        unreadNotifications.forEach(notification -> {
            notification.setIsRead(true);
            notification.setReadAt(LocalDateTime.now());
        });

        notificationRepository.saveAll(unreadNotifications);
        log.info("{} notifications marquées comme lues pour l'utilisateur {}",
                unreadNotifications.size(), userId);
    }

    // Supprimer une notification
    @Transactional
    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
        log.info("Notification {} supprimée", notificationId);
    }

    // Mapper vers Response
    private NotificationResponse mapToResponse(Notification notification) {
        NotificationResponse response = new NotificationResponse();
        response.setId(notification.getId());
        response.setRecipientId(notification.getRecipientId());
        response.setLeaveId(notification.getLeaveId());
        response.setType(notification.getType());
        response.setMessage(notification.getMessage());
        response.setIsRead(notification.getIsRead());
        response.setCreatedAt(notification.getCreatedAt());
        response.setReadAt(notification.getReadAt());
        return response;
    }
}