package com.toeic.learn.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PracticeResultDTO {

    private String questionId;

    @NotNull(message = "单词ID不能为空")
    private Long wordId;

    private String selectedAnswer;

    private String correctAnswer;

    private Boolean isCorrect;

    private Integer responseTimeMs;
}
