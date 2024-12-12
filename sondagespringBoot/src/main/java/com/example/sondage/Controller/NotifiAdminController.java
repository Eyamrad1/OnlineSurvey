package com.example.sondage.Controller;

import com.example.sondage.entity.NotificationAdmin;
import com.example.sondage.Service.serviceUser.NotifiAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Message")
@CrossOrigin("http://localhost:4200")
public class NotifiAdminController {
    @Autowired
    private NotifiAdminService notifiAdminService;

    @PostMapping("/create")
    public void createMsg(@RequestBody NotificationAdmin adminMsg) {
        notifiAdminService.createMessage(adminMsg);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteMsg(@PathVariable Integer id) {
        notifiAdminService.deleteMessage(id);
    }

    @GetMapping("/get/{id}")
    public NotificationAdmin getMsg(@PathVariable Integer id) {
        return notifiAdminService.getMessage(id);
    }

    @GetMapping("/getAll")
    public List<NotificationAdmin> getAllMsg() {
        return notifiAdminService.getallMessages();
    }

    @PostMapping("/send")
    public void sendMsg(@RequestParam Integer id, @RequestParam List<Integer> userId) {
        notifiAdminService.sendMessage(id, userId);
    }

    @PostMapping("/sendNotification")
    public void sendNotification(@RequestParam Integer id, @RequestParam String message, @RequestParam List<Integer> users) {
        notifiAdminService.sendNotification(id, message, users);
    }
}
