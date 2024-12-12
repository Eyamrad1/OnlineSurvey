package com.example.sondage.Controller;

import com.example.sondage.Service.ReponseService;

import com.example.sondage.entity.Reponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ReponseController {
    @Autowired
    ReponseService reponseService;

    @PostMapping("/addReponse")
    public Reponse addReponse(@RequestParam Long idQuestion, @RequestParam Long IdOption, @RequestBody Reponse reponse) {
        if (idQuestion == null || IdOption == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid question or option ID");
        }
        return reponseService.addReponseToQuestion(idQuestion, IdOption, reponse);
    }


    @DeleteMapping("/deleteReponseById/{IdReponse}")
    public String deleteReponseById(@PathVariable Long IdReponse){
       reponseService.deleteReponseById(IdReponse);
        return ("Reponse deleted successfully");
    }

    @GetMapping("/getAllReponses")
    public List<Reponse> getAllReponses() {
        List<Reponse> reponses = reponseService.getAllReponse();

        return reponses;
    }

    @PutMapping("/updateReponses/{IdReponse}")
    public Reponse updateReponses(@RequestBody Reponse reponse,@PathVariable Long IdReponse) {
        return reponseService.updateReponse(reponse,IdReponse);
    }




    @GetMapping("/findReponseById/{IdReponse}")
    public Reponse findReponse(@PathVariable Long IdReponse){
        return reponseService.findReponseById(IdReponse);
    }
}
