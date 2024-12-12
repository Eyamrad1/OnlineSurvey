package com.example.sondage.Service.serviceUser;

import com.example.sondage.entity.NotificationAdmin;
import com.example.sondage.Repository.NotificationAdminRepo;
import com.example.sondage.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NotifiAdminServiceImp implements NotifiAdminService {
    @Autowired
    private NotificationAdminRepo NOTIFICATION_ADMIN_REPO;

    @Autowired
    private UserRepository userRepository;
    public void createMessage (NotificationAdmin notificationAdmin) {
        // You might want to add some validation or processing logic here before saving
        NOTIFICATION_ADMIN_REPO.save(notificationAdmin);
    }

    public void deleteMessage(Integer id) {
        NOTIFICATION_ADMIN_REPO.deleteById(id);
    }

    public NotificationAdmin getMessage(Integer id) {
        Optional<NotificationAdmin> adminMsgOptional = NOTIFICATION_ADMIN_REPO.findById(id);
        return adminMsgOptional.orElse(null);
    }

    public List<NotificationAdmin> getallMessages() {
        return NOTIFICATION_ADMIN_REPO.findAll();
    }

    public void sendMessage(Integer id, List<Integer> userId) {
        NotificationAdmin notificationAdmin = getMessage(id);
        if (notificationAdmin == null) {
            // Handle the case where the admin message with the given ID does not exist
            return;
        }
        for(Integer idUser:userId){

        }
    }
    public void sendNotification(Integer adminMsgId, String message, List<Integer>  recipients) {
        // Create a new notification
        NotificationAdmin notificationAdmin = NotificationAdmin.builder()
                .date(new Date())
                .title(message)
                .users(userRepository.findAllById(recipients))
                .build();

        // Save the notification
        NOTIFICATION_ADMIN_REPO.save(notificationAdmin);

        // Update the flag in AdminMsg indicating that notification has been sent
        Optional<NotificationAdmin> adminMsgOptional = NOTIFICATION_ADMIN_REPO.findById(adminMsgId);
        adminMsgOptional.ifPresent(adminMsg -> {
            adminMsg.setNotificationSent(true);
            NOTIFICATION_ADMIN_REPO.save(adminMsg);
        });
    }

}
