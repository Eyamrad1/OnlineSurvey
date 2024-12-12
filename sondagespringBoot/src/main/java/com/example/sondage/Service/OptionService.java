package com.example.sondage.Service;

import com.example.sondage.entity.Option;
import com.example.sondage.entity.Question;

import java.util.List;

public interface OptionService {

    public void addOptionsAndAssignToQuestion(List<Option> options, Long questionId);

    public List<Option> getAllOptions();

    public void deleteOptionById(Long IdOption);

    public Option updateOption(Option option,Long IdOption);

    public List<Option> getStatisticsForQuestion(Long IdQuestion);

    public Option findOptionById(Long IdOption);

}
