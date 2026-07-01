package com.examreview.examrevieworganizer.repository;

import com.examreview.examrevieworganizer.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
