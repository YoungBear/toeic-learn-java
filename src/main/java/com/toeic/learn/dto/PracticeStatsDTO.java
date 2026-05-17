package com.toeic.learn.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PracticeStatsDTO {

    private long totalWords;
    private long masteredWords;
    private long dueForReview;
    private long totalPracticeCount;
    private long correctCount;
    private long incorrectCount;
    private double accuracyRate;
}
