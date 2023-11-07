package com.example.quizapp.service;

import com.example.quizapp.controller.QuestionController;
import com.example.quizapp.dao.QuestionDao;
import com.example.quizapp.dao.QuizDao;
import com.example.quizapp.exceptionHandling.ResourceNotFoundException;
import com.example.quizapp.model.Question;
import com.example.quizapp.model.QuestionWrapper;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    Logger logger= LoggerFactory.getLogger(QuizService.class);

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category,int numQ,String title){
        logger.info("Reached QuizService : Performing operation and connecting with database to create quiz with title"+title);
        List<Question> questions=questionDao.findRandomQuestionByCategory(category,numQ);
        if(questions.isEmpty())throw new ResourceNotFoundException("question","category",category);

        Quiz quiz=new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);

          return new ResponseEntity<>("success : Quiz created with title :"+title +" category : "+category, HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        logger.info("Reached QuizService : Performing operation and fetching results from database ");
          Quiz quiz= quizDao.findById(id).orElseThrow(()-> new ResourceNotFoundException("quiz","id",id));
          List<Question> questionsDB=quiz.getQuestions();
        List<QuestionWrapper> questionForUser= new ArrayList<>();
        for(Question q:questionsDB){
            QuestionWrapper questions=new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
            questionForUser.add(questions);
        }
        logger.info("Reached Database : operation successful returning quiz questions");

          return new ResponseEntity<List<QuestionWrapper>>(questionForUser,HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        logger.info("Reached QuizService : Performing operation and calculating results ");
        Quiz quiz=quizDao.findById(id).
                orElseThrow(()->new ResourceNotFoundException("quiz","id",id));
        List<Question> questions=quiz.getQuestions();
        int score=0;
        int i=0;
        for(Response response:responses){
            if(response.getResponse().equals(questions.get(i).getRightAnswer()))score++;
            i++;
        }
        logger.info("Operation successful returning quiz score ");
        return new ResponseEntity<Integer>(score,HttpStatus.OK);
    }
}
