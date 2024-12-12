package com.example.sondage.Service;

import com.example.sondage.Repository.OptionRepository;
import com.example.sondage.Repository.QuestionRepository;
import com.example.sondage.Repository.ReponseRepository;
import com.example.sondage.Repository.UserRepository;
import com.example.sondage.entity.Option;
import com.example.sondage.entity.Question;
import com.example.sondage.entity.Reponse;
import com.example.sondage.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ReponseServiceImpl implements ReponseService{


    @Autowired
    ReponseRepository reponseRepository;
    @Autowired
    OptionRepository optionRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    UserRepository userRepository;
    @Override

    public Reponse addReponseToQuestion(Long idQuestion, Long IdOption, Reponse reponse) {

        Question question = questionRepository.findById(idQuestion).orElse(null);
        Option option = optionRepository.findById(IdOption).orElse(null);

        if (question != null && option != null) {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
                String username = ((UserDetails) authentication.getPrincipal()).getUsername();
                User user = userRepository.findByUsername(username).orElse(null);
                if (user != null) {

                    reponse.setUser(user);
                }
            }


            reponse.setQuestion(question);
            reponse.setOption(option);

            Reponse savedReponse = reponseRepository.save(reponse);


            option.setCompteur(option.getCompteur() + 1);
            optionRepository.save(option);

            return savedReponse;
        }

        return null;
    }


    @Override
    public List<Reponse> getAllReponse() {
        List<Reponse>reponseList=reponseRepository.findAll();
        return reponseList;
    }

    @Override
    public void deleteReponseById(Long IdReponse) {
        reponseRepository.deleteById(IdReponse);

    }

    @Override
    public Reponse updateReponse(Reponse reponse, Long IdReponse) {
        reponse.setIdReponse(IdReponse);
        return reponseRepository.save(reponse);
    }

    @Override
    public Reponse findReponseById(Long IdReponse) {
        return reponseRepository.findById(IdReponse).orElse(null);
    }
}
