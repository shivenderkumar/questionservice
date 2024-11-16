package com.shivender.quetionservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "questions")
@Where(clause = "is_deleted = false")   // Automatically filter out deleted records
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    private String id;

    @Column(nullable = false)
    private String title;

    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String answer;
    private String category;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp modifiedAt;

    private Boolean isDeleted = false;
}
