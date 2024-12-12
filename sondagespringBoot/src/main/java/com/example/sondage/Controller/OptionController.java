package com.example.sondage.Controller;

import com.example.sondage.Service.OptionService;
import com.example.sondage.entity.Option;
import com.example.sondage.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class OptionController {
    @Autowired
    OptionService optionService;



    @PostMapping("/addOption/{idQuestion}")
    public ResponseEntity<Void> addOptions(@RequestBody List<Option> options, @PathVariable Long idQuestion) {
        optionService.addOptionsAndAssignToQuestion(options, idQuestion);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/deleteOptionById/{IdOption}")
    public String deleteOptionById(@PathVariable Long IdOption){
        optionService.deleteOptionById(IdOption);
        return ("Option deleted successfully");
    }

    @GetMapping("/getAllOptions")
    public List<Option> getAllOptions() {
        List<Option> options = optionService.getAllOptions();

        return options;
    }


    @PutMapping("/updateOption/{IdOption}")
    public Option updateOption(@RequestBody Option option,@PathVariable Long IdOption) {
        return optionService.updateOption(option,IdOption) ;
    }




        @GetMapping("/questionstat/{idQuestion}")
        public List<Option> getStatisticsForQuestion(@PathVariable Long idQuestion) {
            List<Option> options = optionService.getStatisticsForQuestion(idQuestion);
            return options;
        }


    @GetMapping("/findOptionById/{IdOption}")
    public Option findOption(@PathVariable Long IdOption){
        return optionService.findOptionById(IdOption);
    }




    }




