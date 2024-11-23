package com.shivender.quetionservice.service;

import com.shivender.quetionservice.dao.QuestionDao;
import com.shivender.quetionservice.exceptions.ApiException;
import com.shivender.quetionservice.model.Question;
import com.shivender.quetionservice.model.QuestionScoreDTO;
import com.shivender.quetionservice.model.QuestionWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);
    @Autowired
    QuestionDao questionDao;

    public List<Question> getAllQuestions(){
        return questionDao.findAll();
    }

    public List<Question> getQuestionsByCategory(String category) {
        Boolean isCateGoryExist = questionDao.existsByCategory(category);
        if(!isCateGoryExist){
            throw new ApiException("Category "+category+" not exists.", HttpStatus.NOT_FOUND);
        }
        return questionDao.findByCategory(category);
    }

    public List<String> getNRandomQuestionsByCategory(String category, Integer numQ) {
        Boolean isCateGoryExist = questionDao.existsByCategory(category);
        if(!isCateGoryExist){
            throw new ApiException("Category "+category+" not exists.", HttpStatus.NOT_FOUND);
        }
        return questionDao.findNRandomByCategory(category, numQ);
    }

    public String addQuestion(Question question) {
        questionDao.save(question);
        return "Question added successfully.";
    }

    public List<QuestionWrapper> getQuizQuestions(Set<String> questionIds) {
        List<Question> questionList = questionDao.findAllById(questionIds);
        if(questionList.isEmpty()){
            throw new ApiException("No questions found.", HttpStatus.NOT_FOUND);
        }
        List<QuestionWrapper> questionWrapperList = new ArrayList<QuestionWrapper>();
        for(Question q : questionList){
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
            questionWrapperList.add(qw);
        }
        return questionWrapperList;
    }

    public List<String> getRandom(String category, Integer numQ) {
        if(!questionDao.existsByCategory(category)){
            throw new ApiException("Category "+category+" not exists.", HttpStatus.NOT_FOUND);
        }else if(numQ == null || numQ < 1){
            throw new ApiException("Please insert number of questions.", HttpStatus.BAD_REQUEST);
        }
        List<String> questionIdList = this.getNRandomQuestionsByCategory(category, numQ);
        return  questionIdList;
    }

    public Integer getScore(Set<QuestionScoreDTO> questionScoreDTOList){
        if(questionScoreDTOList.isEmpty()){ return 0;}
        for(Object result : questionScoreDTOList){
            System.out.println(">>>>>"+result.toString());
        }
        List<String> questionIds = questionScoreDTOList.stream()
                .map(QuestionScoreDTO::getId).toList();
        List<Question> correctQuestionAnsList = questionDao.findByIdIn(questionIds);


        Map<String, String> correctQuestionAns = correctQuestionAnsList.stream()
                .collect(Collectors.toMap(Question::getId, Question::getAnswer));

        Integer score = 0;
        for(QuestionScoreDTO questionScoreDTO : questionScoreDTOList){
            if(correctQuestionAns.containsKey(questionScoreDTO.getId())){
                if(correctQuestionAns.get(questionScoreDTO.getId()).equals(questionScoreDTO.getResponse())){
                    score++;
                }
            }
        }
        return score;
    }
}
