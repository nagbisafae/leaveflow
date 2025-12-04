package com.leaveflow.notificationservice.controller;

import com.leaveflow.notificationservice.dto.NotificationDto;
import com.leaveflow.notificationservice.dto.NotificationResponse;
import com.leaveflow.notificationservice.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // Créer une notification manuellement (pour tests)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NotificationResponse createNotification(@Valid @RequestBody NotificationDto notificationDto) {
        return notificationService.createNotification(notificationDto);
    }

    // Obtenir toutes les notifications d'un utilisateur
    @GetMapping("/user/{userId}")
    public List<NotificationResponse> getUserNotifications(@PathVariable Long userId) {
        return notificationService.getNotificationsForUser(userId);
    }

    // Obtenir les notifications non lues
    @GetMapping("/user/{userId}/unread")
    public List<NotificationResponse> getUnreadNotifications(@PathVariable Long userId) {
        return notificationService.getUnreadNotifications(userId);
    }

    // Compter les notifications non lues
    @GetMapping("/user/{userId}/unread/count")
    public Long countUnreadNotifications(@PathVariable Long userId) {
        return notificationService.countUnreadNotifications(userId);
    }

    // Marquer une notification comme lue
    @PutMapping("/{notificationId}/read")
    public NotificationResponse markAsRead(@PathVariable Long notificationId) {
        return notificationService.markAsRead(notificationId);
    }

    // Marquer toutes les notifications comme lues
    @PutMapping("/user/{userId}/read-all")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void markAllAsRead(@PathVariable Long userId) {
        notificationService.markAllAsRead(userId);
    }

    // Supprimer une notification
    @DeleteMapping("/{notificationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNotification(@PathVariable Long notificationId) {
        notificationService.deleteNotification(notificationId);
    }
}