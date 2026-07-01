package com.examreview.examrevieworganizer.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "questions")
public class
Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paper_id", nullable = false)
    private Paper paper;

    @Column(name = "question_number", nullable = false)
    private Integer questionNumber;

    @Column(name = "content_text", nullable = false, columnDefinition = "TEXT")
    private String contentText;

    @Column(name = "page_number")
    private Integer pageNumber;

    @Column(name = "order_index")
    private Integer orderIndex;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "question_tags", joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "knowledge_point_id"))
    private Set<KnowledgePoint> knowledgePoints = new HashSet<>();

    protected Question() {
    }

    public Question(Paper paper, Integer questionNumber, String contentText, Integer pageNumber, Integer orderIndex) {
        this.paper = paper;
        this.questionNumber = questionNumber;
        this.contentText = contentText;
        this.pageNumber = pageNumber;
        this.orderIndex = orderIndex;
    }

    public Long getId() {
        return id;
    }

    public Paper getPaper() {
        return paper;
    }

    public Integer getQuestionNumber() {
        return questionNumber;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public Set<KnowledgePoint> getKnowledgePoints() {
        return knowledgePoints;
    }
}
