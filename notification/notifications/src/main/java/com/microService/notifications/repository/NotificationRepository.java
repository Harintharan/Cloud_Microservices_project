package com.microService.notifications.repository;

import com.microService.notifications.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {

   List< Notification> findByUserId(Long userId);
    List<Notification> findBySaloonId(Long saloonId);

}
