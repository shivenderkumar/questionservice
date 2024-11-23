package com.shivender.quetionservice.controller;

import com.shivender.quetionservice.model.Question;
import com.shivender.quetionservice.model.QuestionScoreDTO;
import com.shivender.quetionservice.model.QuestionWrapper;
import com.shivender.quetionservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @PostMapping("quiz")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@RequestBody Set<String> questionIds){
        return new ResponseEntity<>(questionService.getQuizQuestions(questionIds), HttpStatus.OK);
    }

    // generate questions for quiz
    @GetMapping("random")
    public ResponseEntity<List<String>> generateQuiz(@RequestParam String category, @RequestParam Integer numQ){
        return new ResponseEntity<>(questionService.getRandom(category, numQ), HttpStatus.OK);
    }

    // getScore for response questoins
    @PostMapping("score")
    public ResponseEntity<Integer> getScore(@RequestBody Set<QuestionScoreDTO> questionScoreDTOList){
        return new ResponseEntity<>(questionService.getScore(questionScoreDTOList), HttpStatus.OK);
    }
}
