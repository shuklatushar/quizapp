package com.example.quizapp.service;

import ch.qos.logback.classic.spi.IThrowableProxy;
import com.example.quizapp.exceptionHandling.ResourceNotFoundException;
import com.example.quizapp.model.Question;
import com.example.quizapp.dao.QuestionDao;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    Logger logger=LoggerFactory.getLogger(QuestionService.class);

    @Autowired
    QuestionDao questionDao;
    public ResponseEntity<List<Question>> getAllQuestions() {
        logger.info("Reached QuestionService : Performing operation and fetching results from database ");
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }
        catch (Exception e){
            logger.info("There is some error ");
            e.printStackTrace();
        }
        logger.info("Returning all Questions from database ");
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<List<Question>> getAllQuestionsByCategory(String category){
            List<Question> questionList=questionDao.findByCategory(category);
            logger.info("Reached QuestionService : Performing operation and fetching results from database ");
            if (questionList.isEmpty()) {

                throw new ResourceNotFoundException("questions","category",category);
            }
            logger.info("Returning Questions from database by category :"+category);
            return new ResponseEntity<>(questionList,HttpStatus.OK);


    }

    public ResponseEntity<String> addQuestion(Question question){
        logger.info("Reached QuestionService : Performing operation and connecting with database ");
        questionDao.save(question);
        logger.info("Reached Database : operation successful questions added");
        return new ResponseEntity<>("success",HttpStatus.CREATED);
    }

    public String deleteQuestion(Integer id) {
        logger.info("Reached QuestionService : Performing operation and connecting with database ");
         Question question = questionDao.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));

         questionDao.deleteById(id);

        logger.info("Operation successful : deleted question with id"+ id + " from database ");
        return "success";
    }

    public ResponseEntity<String> updateQuestion(String difficultylevel, int id) {
        logger.info("Reached QuestionService : Performing operation and fetching results from database ");
        Question question = questionDao.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));

        try{
        questionDao.updateRightAnswer(difficultylevel, id);

        }
        catch(Exception e){

            logger.warn("No query returned from database");
            logger.error("error : "+e);
        }

        return new ResponseEntity<String>("success: 1 row affected",HttpStatus.OK);
    }
}
