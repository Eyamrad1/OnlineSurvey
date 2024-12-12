package com.example.sondage.Service.serviceUser;

import com.example.sondage.entity.NotificationAdmin;

import java.util.List;

public interface NotifiAdminService {
    void createMessage (NotificationAdmin messageAdmin);
    void deleteMessage (Integer id);
    NotificationAdmin getMessage (Integer id);
    List<NotificationAdmin> getallMessages ();
    void sendMessage (Integer id, List<Integer> userId);
    public void sendNotification(Integer adminMsgId, String message, List<Integer>  recipients);
}
