package com.example.sondage.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Reponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idReponse;

    @ManyToOne
    @JoinColumn(name = "user_id_user")
    private User user;

    @ManyToOne
    @JsonBackReference(value = "option-reponses")
    private Option option;

    @ManyToOne
    @JsonBackReference(value = "question-reponses")
    private Question question;

    @Column(nullable = false)
    private LocalDateTime dateReponse;

    @PrePersist
    protected void onCreate() {
        this.dateReponse = LocalDateTime.now();
    }
}
