package com.examreview.examrevieworganizer.repository;

import com.examreview.examrevieworganizer.entity.KnowledgePoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KnowledgePointRepository extends JpaRepository<KnowledgePoint, Long> {
}
