package com.leaveflow.notificationservice.repositories;

import com.leaveflow.notificationservice.dao.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByRecipientIdOrderByCreatedAtDesc(Long recipientId);

    // Trouver les notifications non lues d'un utilisateur
    List<Notification> findByRecipientIdAndIsReadFalseOrderByCreatedAtDesc(Long recipientId);

    // Compter les notifications non lues
    Long countByRecipientIdAndIsReadFalse(Long recipientId);
}
