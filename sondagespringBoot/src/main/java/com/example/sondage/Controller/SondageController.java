package com.example.sondage.Controller;

import com.example.sondage.Service.SondageService;
import com.example.sondage.entity.Sondage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class SondageController {
    @Autowired
    SondageService sondageService;




    @PostMapping("/addSondageAffectUser/{idUser}")
    public ResponseEntity<Sondage> addSondageToUser(@RequestBody Sondage sondage, @PathVariable("idUser") Integer idUser) {
        Sondage savedSondage = sondageService.addSondagesToUser(sondage, idUser);

        System.out.println("Saved Sondage: " + savedSondage);
        return ResponseEntity.ok(savedSondage);
    }

    @GetMapping("/statistics/{idSondage}")
    public Map<String, Object> getSurveyStatistics(@PathVariable Long idSondage) {
        return sondageService.getSurveyStatistics(idSondage);
    }

    @GetMapping("/completion-rates/{idSondage}")
    public Map<String, Integer> getSurveyCompletionRates(@PathVariable Long idSondage) {
        return sondageService.getSurveyCompletionRates(idSondage);
    }


    @DeleteMapping("/deleteSondageById/{idSondage}")
    public String deleteSondageById(@PathVariable Long idSondage){
        sondageService.deleteSondageById(idSondage);
        return ("sondage deleted successfully");
    }





    @GetMapping("/statisticLine")
    public ResponseEntity<Map<String, Object>> getAggregatedSurveyStatistics() {
        Map<String, Object> aggregatedStats = sondageService.getAggregatedSurveyStatistics();
        return ResponseEntity.ok(aggregatedStats);
    }

    @GetMapping("/getAllSondages")
    public List<Sondage> getAllSondages() {
        List<Sondage> sondages = sondageService.getAllSondages();

        return sondages;
    }

    @PutMapping("/updateSondage/{idSondage}")
    public Sondage updateSondage(@RequestBody Sondage sondage,@PathVariable Long idSondage) {
        return sondageService.updateSondage(sondage,idSondage);
    }

    @GetMapping("/findSondageById/{idSondage}")
    public Sondage findSondage(@PathVariable Long idSondage){
        return sondageService.findSondageById(idSondage);
    }

    @GetMapping("/searchSondagesByTitle")
    public ResponseEntity<List<Sondage>> searchSondagesByTitle(@RequestParam("title") String title) {
        List<Sondage> sondages = sondageService.findSondagesByTitle(title);
        return ResponseEntity.ok(sondages);
    }


}
