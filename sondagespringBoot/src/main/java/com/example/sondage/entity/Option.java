package com.example.sondage.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idOption;
    String texte;
    private int compteur;

    @ManyToOne
    @JsonBackReference(value = "question-options")
    private Question question;

    @OneToMany(mappedBy = "option", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference(value = "option-reponses")
    private List<Reponse> reponses;
}
