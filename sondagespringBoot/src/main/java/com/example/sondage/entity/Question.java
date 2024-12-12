package com.example.sondage.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idQuestion;
    String text;

    @ManyToOne
    @JoinColumn(name = "sondage_id_sondage")
    private Sondage sondage;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "question-options")
    private List<Option> options;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "question-reponses")
    private List<Reponse> reponses;
}
