package com.example.sondage.Service;

import com.example.sondage.entity.Option;
import com.example.sondage.entity.Question;
import com.example.sondage.entity.Sondage;
import com.example.sondage.entity.User;

import java.util.List;

public interface QuestionService {

    public Question addQuestionAndAssignToSondage(Question question,Long idSondage);

    public List<Question> getAllQuestions();

    public void deleteQuestionById(Long idQuestion);

    public Question updateQuestionAndOptions(Question question, Long idQuestion, List<Option> options);

    public Question findQuestionById(Long idQuestion);

    public List<Question> getAllQuestionsWithOptions(Long idSondage);

}
