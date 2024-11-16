package com.shivender.quetionservice.controller;

import com.shivender.quetionservice.model.Question;
import com.shivender.quetionservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAll(){
        return new ResponseEntity<>(questionService.getAllQuestions(), HttpStatus.OK);
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getByCategory(@PathVariable String category){
        return new ResponseEntity<>(questionService.getQuestionsByCategory(category), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return new ResponseEntity<>(questionService.addQuestion(question), HttpStatus.CREATED);
    }
}
