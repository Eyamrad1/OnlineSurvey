package com.example.sondage.Service;

import com.example.sondage.Repository.OptionRepository;
import com.example.sondage.Repository.QuestionRepository;
import com.example.sondage.Repository.SondageRepository;
import com.example.sondage.Repository.UserRepository;
import com.example.sondage.entity.Option;
import com.example.sondage.entity.Question;
import com.example.sondage.entity.Sondage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class SondageServiceImpl implements SondageService {

    @Autowired
    SondageRepository sondageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private QuestionRepository questionRepository;




    @Override
    public Sondage addSondagesToUser(Sondage sondage, Integer idUser) {
        var user = userRepository.findById(idUser).orElseThrow(() -> new RuntimeException("User not found"));
        sondage.setUser(user);
        return sondageRepository.save(sondage);
    }
    @Override
    public Map<String, Integer> getSurveyCompletionRates(Long idSondage) {
        List<Question> questions = questionRepository.findBySondage_IdSondage(idSondage);
        Map<String, Integer> completionRates = new LinkedHashMap<>();

        for (int i = 6; i >= 0; i--) {
            YearMonth month = YearMonth.now().minusMonths(i);
            int completions = 0;

            for (Question question : questions) {
                List<Option> options = optionRepository.findByQuestion_IdQuestion(question.getIdQuestion());

                for (Option option : options) {
                    completions += option.getReponses().stream()
                            .filter(response -> YearMonth.from(response.getDateReponse()).equals(month))
                            .count();
                }
            }

            completionRates.put(month.toString(), completions);
        }

        return completionRates;
    }


    @Override
    public Map<String, Object> getAggregatedSurveyStatistics() {
        Map<String, Object> aggregatedStats = new HashMap<>();
        List<Sondage> allSondages = sondageRepository.findAll();

        int totalResponses = 0;
        double totalCompletionRate = 0.0;
        int totalSurveys = allSondages.size();

        for (Sondage sondage : allSondages) {
            List<Question> questions = questionRepository.findBySondage_IdSondage(sondage.getIdSondage());
            int sondageResponses = 0;

            for (Question question : questions) {
                List<Option> options = optionRepository.findByQuestion_IdQuestion(question.getIdQuestion());
                for (Option option : options) {
                    sondageResponses += option.getReponses().size();
                }
            }

            totalResponses += sondageResponses;
            totalCompletionRate += getSurveyCompletionRate(sondage.getIdSondage());
        }

        double averageCompletionRate = totalSurveys > 0 ? totalCompletionRate / totalSurveys : 0;

        aggregatedStats.put("totalSurveys", totalSurveys);
        aggregatedStats.put("totalResponses", totalResponses);
        aggregatedStats.put("averageCompletionRate", averageCompletionRate);

        return aggregatedStats;
    }

@Override
    public double getSurveyCompletionRate(Long idSondage) {
        List<Question> questions = questionRepository.findBySondage_IdSondage(idSondage);
        int totalQuestions = questions.size();

        if (totalQuestions == 0) {
            return 0.0; // To avoid division by zero
        }


        int totalResponses = 0;
        int completeResponses = 0;

        for (Question question : questions) {
            List<Option> options = optionRepository.findByQuestion_IdQuestion(question.getIdQuestion());

            for (Option option : options) {
                int responseCount = option.getReponses().size();
                totalResponses += responseCount;


                if (responseCount > 0) {
                    completeResponses++;
                }
            }
        }

        // Calculate the completion rate as a percentage
        return (double) completeResponses / totalResponses * 100;
    }



    public Map<String, Object> getSurveyStatistics(Long idSondage) {
        List<Question> questions = questionRepository.findBySondage_IdSondage(idSondage);

        Map<String, Object> statistics = new HashMap<>();

        for (Question question : questions) {
            List<Option> options = optionRepository.findByQuestion_IdQuestion(question.getIdQuestion());

            Map<String, Integer> optionStats = new HashMap<>();
            int totalResponses = 0;

            for (Option option : options) {
                int responseCount = option.getReponses().size();
                optionStats.put(option.getTexte(), responseCount);
                totalResponses += responseCount;
            }

            Map<String, Double> optionPercentageStats = new HashMap<>();
            for (Map.Entry<String, Integer> entry : optionStats.entrySet()) {
                double percentage = (double) entry.getValue() / totalResponses * 100;
                optionPercentageStats.put(entry.getKey(), percentage);
            }

            Map<String, Object> questionStats = new HashMap<>();
            questionStats.put("totalResponses", totalResponses);
            questionStats.put("optionStats", optionStats);
            questionStats.put("optionPercentageStats", optionPercentageStats);

            statistics.put(question.getText(), questionStats);
        }

        return statistics;
    }


    @Override
    public Sondage updateSondage(Sondage sondage, Long idSondage) {
        sondage.setIdSondage(idSondage);
        return sondageRepository.save(sondage);
    }

    @Override
    public void deleteSondageById(Long idSondage) {
        sondageRepository.deleteById(idSondage);
    }

    @Override
    public List<Sondage> getAllSondages() {
        List<Sondage>sondageList =sondageRepository.findAll();
        return sondageList;
    }

    @Override
    public Sondage findSondageById(Long idSondage) {
        return sondageRepository.findById(idSondage).orElse(null);
    }
    @Override
    public List<Sondage> findSondagesByTitle(String title) {
        return sondageRepository.findByTitleContainingIgnoreCase(title);
    }
}
