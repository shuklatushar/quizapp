package com.example.quizapp.exceptionHandling;

import com.example.quizapp.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceNotFoundException extends RuntimeException {
    String resourceName;
    String fieldName;
    String fieldValue;
    long IntValue;

    Logger logger=LoggerFactory.getLogger(ResourceNotFoundException.class);

    public ResourceNotFoundException(String resourceName, String fieldName, long IntValue) {
       super(String.format("%s not found with %s : %s",resourceName,fieldName,IntValue));
       logger.error("wait we have encountered and error : id not found");
       this.resourceName=resourceName;
       this.fieldName=fieldName;
       this.IntValue=IntValue;
    }
    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s not found with %s : %s",resourceName,fieldName,fieldValue));
        logger.error("wait we have encountered and error : category not found");
        this.resourceName=resourceName;
        this.fieldName=fieldName;
        this.fieldValue=fieldValue;
    }
}
