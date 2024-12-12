package com.example.sondage.Repository;

import com.example.sondage.entity.NotificationAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationAdminRepo extends JpaRepository<NotificationAdmin, Integer> {
}
