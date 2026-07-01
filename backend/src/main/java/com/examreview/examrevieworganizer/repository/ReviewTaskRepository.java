package com.examreview.examrevieworganizer.repository;

import com.examreview.examrevieworganizer.entity.ReviewTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewTaskRepository extends JpaRepository<ReviewTask, Long> {
}
