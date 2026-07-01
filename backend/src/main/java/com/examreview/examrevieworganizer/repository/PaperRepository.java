package com.examreview.examrevieworganizer.repository;

import com.examreview.examrevieworganizer.entity.Paper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaperRepository extends JpaRepository<Paper, Long> {
}
