package com.examreview.examrevieworganizer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "knowledge_points")
public class KnowledgePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_task_id", nullable = false)
    private ReviewTask reviewTask;

    @Column(nullable = false)
    private String name;

    @Column(name = "order_index")
    private Integer orderIndex;

    protected KnowledgePoint() {
    }

    public KnowledgePoint(ReviewTask reviewTask, String name, Integer orderIndex) {
        this.reviewTask = reviewTask;
        this.name = name;
        this.orderIndex = orderIndex;
    }

    public Long getId() {
        return id;
    }

    public ReviewTask getReviewTask() {
        return reviewTask;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }
}
