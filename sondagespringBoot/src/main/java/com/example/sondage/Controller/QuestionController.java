package com.example.sondage.Controller;

import com.example.sondage.Service.QuestionService;
import com.example.sondage.Service.SondageService;
import com.example.sondage.entity.Question;
import com.example.sondage.entity.Sondage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    SondageService sondageService;

    @PostMapping("/addQuestion/{idSondage}")
    public ResponseEntity<Question> addQuestionAndAssignToSondage(@RequestBody Question question, @PathVariable Long idSondage) {
        Question savedQuestion = questionService.addQuestionAndAssignToSondage(question, idSondage);
        System.out.println("Saved question ID: " + savedQuestion.getIdQuestion());
        return ResponseEntity.ok(savedQuestion);
    }

    @DeleteMapping("/deleteQuestionById/{idQuestion}")
    public String deleteQuestionById(@PathVariable Long idQuestion){
        questionService.deleteQuestionById(idQuestion);
        return ("Question deleted successfully");
    }

    @GetMapping("/getAllQuestions")
    public List<Question> getAllQuestions(@RequestParam Long idSondage) {
        return questionService.getAllQuestionsWithOptions(idSondage);
    }





    @GetMapping("/findQuestionById/{idQuestion}")
    public Question findQuestion(@PathVariable Long idQuestion){
        return questionService.findQuestionById(idQuestion);
    }

}
