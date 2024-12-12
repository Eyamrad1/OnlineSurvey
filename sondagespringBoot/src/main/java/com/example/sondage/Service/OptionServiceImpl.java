package com.example.sondage.Service;

import com.example.sondage.Repository.OptionRepository;
import com.example.sondage.Repository.QuestionRepository;
import com.example.sondage.entity.Option;
import com.example.sondage.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionServiceImpl implements OptionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private OptionRepository optionRepository;

    @Override
    public void addOptionsAndAssignToQuestion(List<Option> options, Long idQuestion) {
        Question question = questionRepository.findById(idQuestion).orElse(null);
        if (question != null) {
            for (Option option : options) {
                option.setQuestion(question);
                optionRepository.save(option);
            }
        } else {
            throw new IllegalArgumentException("Question not found for ID: " + idQuestion);
        }
    }


    @Override
    public List<Option> getAllOptions() {
        return optionRepository.findAll();
    }

    @Override
    public void deleteOptionById(Long idOption) {
        optionRepository.deleteById(idOption);
    }

    @Override
    public Option updateOption(Option option, Long idOption) {
        option.setIdOption(idOption);
        return optionRepository.save(option);
    }

    @Override
    public List<Option> getStatisticsForQuestion(Long idQuestion) {
        Question question = questionRepository.findById(idQuestion).orElse(null);
        if (question == null) {
            return List.of();
        }
        return optionRepository.findByQuestion(question);
    }

    @Override
    public Option findOptionById(Long IdOption) {
        return optionRepository.findById(IdOption).orElse(null);
    }
}
