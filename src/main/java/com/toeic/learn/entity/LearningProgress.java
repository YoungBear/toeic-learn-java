package com.toeic.learn.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "learning_progress")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LearningProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id", nullable = false, unique = true)
    private Word word;

    @Column(name = "mastery_level")
    private Integer masteryLevel = 0;

    @Column(name = "last_practiced_at")
    private LocalDateTime lastPracticedAt;

    @Column(name = "next_review_at")
    private LocalDateTime nextReviewAt;

    public void incrementMastery() {
        if (masteryLevel < 5) {
            masteryLevel++;
        }
        lastPracticedAt = LocalDateTime.now();
        calculateNextReview();
    }

    public void resetMastery() {
        masteryLevel = 0;
        lastPracticedAt = LocalDateTime.now();
        nextReviewAt = LocalDateTime.now();
    }

    private void calculateNextReview() {
        int daysToAdd = switch (masteryLevel) {
            case 0 -> 0;
            case 1 -> 1;
            case 2 -> 3;
            case 3 -> 7;
            case 4 -> 14;
            case 5 -> 30;
            default -> 0;
        };
        nextReviewAt = LocalDateTime.now().plusDays(daysToAdd);
    }
}
