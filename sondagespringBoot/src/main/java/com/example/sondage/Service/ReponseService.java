package com.example.sondage.Service;

import com.example.sondage.entity.Question;
import com.example.sondage.entity.Reponse;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ReponseService {
    public  Reponse addReponseToQuestion(Long idQuestion, Long IdOption, Reponse reponse);
    public List<Reponse> getAllReponse();

    public void deleteReponseById(Long IdReponse);

    public Reponse updateReponse(Reponse reponse,Long IdReponse);

    public Reponse findReponseById(Long IdReponse);

}
