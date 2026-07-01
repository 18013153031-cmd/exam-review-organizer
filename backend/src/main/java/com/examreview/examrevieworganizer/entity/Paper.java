package com.examreview.examrevieworganizer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "papers")
public class Paper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_task_id", nullable = false)
    private ReviewTask reviewTask;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(nullable = false)
    private Integer year;

    @Column(name = "uploaded_at", nullable = false, updatable = false)
    private LocalDateTime uploadedAt;

    protected Paper() {
    }

    public Paper(ReviewTask reviewTask, String fileName, Integer year) {
        this.reviewTask = reviewTask;
        this.fileName = fileName;
        this.year = year;
    }

    @PrePersist
    protected void onUpload() {
        this.uploadedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public ReviewTask getReviewTask() {
        return reviewTask;
    }

    public String getFileName() {
        return fileName;
    }

    public Integer getYear() {
        return year;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }
}
