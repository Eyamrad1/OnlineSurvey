package com.example.sondage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Date date=new Date();
    private String email;
    private String username;
    private String message;

    private boolean notificationSent;
    private String title;
    @JsonIgnore
    @ManyToMany
    private List<User> users;
}
