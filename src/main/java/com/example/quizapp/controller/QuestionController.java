package com.example.quizapp.controller;

import com.example.quizapp.model.Question;
import com.example.quizapp.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("question")
public class QuestionController {

    Logger logger=LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    QuestionService questionService;
    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        logger.info("Reached QuestionController : Requesting from service to get all questions");
        return  questionService.getAllQuestions();
    }
    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getAllQuestionsByCategory(@PathVariable String category){
        logger.info("Reached QuestionController : Requesting from service to get all questions by cateegory"+category);
        return questionService.getAllQuestionsByCategory(category);
    }
    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
       logger.info("Reached QuestionController : Requesting from service to add questions");
       return  questionService.addQuestion(question);
    }
    @DeleteMapping("delete/{id}")
    public String deleteQuestion(@PathVariable String id){
        logger.info("Reached QuestionController : Requesting from service to get delete question by Id"+id);
        return questionService.deleteQuestion(Integer.parseInt(id));
    }
    @PutMapping("update")
    public ResponseEntity<String> updateQuestion(@RequestParam int id, @RequestParam String difficultylevel){
        logger.info("Reached QuestionController : Requesting from service to update question by Id "+id+" and difficultylevel"+difficultylevel);
        return questionService.updateQuestion(difficultylevel,id);
    }
}
