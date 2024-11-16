package com.shivender.quetionservice.dao;

import com.shivender.quetionservice.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionDao extends JpaRepository<Question, String> {

    Boolean existsByCategory(String category);
    List<Question> findByCategory(String category);

    @Query(value = "SELECT * FROM questions WHERE category = :category ORDER BY RAND() LIMIT :numQ", nativeQuery = true)
    List<Question> findNRandomByCategory(String category, Integer numQ);

}
