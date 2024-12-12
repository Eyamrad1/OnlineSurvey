package com.example.sondage.Service;

import com.example.sondage.entity.Sondage;
import com.example.sondage.entity.User;

import java.util.List;
import java.util.Map;

public interface SondageService {

    public Sondage addSondagesToUser(Sondage sondage, Integer idUser);

    public Sondage updateSondage(Sondage sondage,Long idSondage);

    public void deleteSondageById(Long idSondage);

    public List<Sondage> getAllSondages();
    public Sondage findSondageById(Long idSondage);

    List<Sondage> findSondagesByTitle(String title);
    public Map<String, Object> getSurveyStatistics(Long idSondage);

    public Map<String, Integer> getSurveyCompletionRates(Long idSondage);
    public Map<String, Object> getAggregatedSurveyStatistics();
    public double getSurveyCompletionRate(Long idSondage);
}
