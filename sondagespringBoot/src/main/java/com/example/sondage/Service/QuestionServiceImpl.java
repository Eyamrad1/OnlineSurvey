package com.example.sondage.Service;

import com.example.sondage.Repository.OptionRepository;
import com.example.sondage.Repository.QuestionRepository;
import com.example.sondage.Repository.SondageRepository;
import com.example.sondage.entity.Option;
import com.example.sondage.entity.Question;
import com.example.sondage.entity.Sondage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private SondageRepository sondageRepository;

    @Autowired
    private OptionRepository optionRepository;

    @Override
    public Question addQuestionAndAssignToSondage(Question question, Long idSondage) {
        Sondage sondage = sondageRepository.findById(idSondage).orElse(null);
        if (sondage != null) {
            question.setSondage(sondage);
        }
        Question savedQuestion = questionRepository.save(question);
        System.out.println("Saved question ID: " + savedQuestion.getIdQuestion());
        return savedQuestion;
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public void deleteQuestionById(Long idQuestion) {
        questionRepository.deleteById(idQuestion);
    }

    @Override
    public Question updateQuestionAndOptions(Question question, Long idQuestion, List<Option> options) {

        question.setIdQuestion(idQuestion);
        Question updatedQuestion = questionRepository.save(question);


        for (Option option : options) {
            if (option.getIdOption() != null) {

                option.setQuestion(updatedQuestion);
                optionRepository.save(option);
            } else {

                option.setQuestion(updatedQuestion);
                optionRepository.save(option);
            }
        }

        return updatedQuestion;
    }


    @Override
    public Question findQuestionById(Long idQuestion) {
        return questionRepository.findById(idQuestion).orElse(null);
    }


    @Override
    public List<Question> getAllQuestionsWithOptions(Long idSondage) {

        List<Question> questions = questionRepository.findBySondage_IdSondage(idSondage);

        for (Question question : questions) {
            List<Option> options = optionRepository.findByQuestion_IdQuestion(question.getIdQuestion());
            question.setOptions(options);
        }
        return questions;
    }
}
