package com.example.quizapp.controller;

import com.example.quizapp.model.Question;
import com.example.quizapp.model.Response;
import com.example.quizapp.model.QuestionWrapper;
import com.example.quizapp.service.QuizService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {
    Logger logger= LoggerFactory.getLogger(QuizController.class);

    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int numQ,@RequestParam String title){
        logger.info("Reached QuizController : Requesting from service to create quiz");
        return quizService.createQuiz(category, numQ, title);
    }
    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id){
          logger.info("Reached QuizController : Requesting from service to get all questions from quiz with id : "+id);
           return quizService.getQuizQuestions(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses){
       logger.info("Reached QuizController : Requesting from service to score for the quiz with id : "+id);
       return quizService.calculateResult(id,responses);
    }
}
