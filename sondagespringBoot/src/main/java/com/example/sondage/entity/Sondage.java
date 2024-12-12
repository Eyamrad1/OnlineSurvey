package com.example.sondage.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Sondage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSondage;
    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endDate;

    @ManyToOne
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "sondage", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Question> questions;
}
