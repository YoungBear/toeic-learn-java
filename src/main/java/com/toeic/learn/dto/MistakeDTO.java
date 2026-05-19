package com.toeic.learn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MistakeDTO {
    private Long wordId;
    private String english;
    private String chinese;
    private String phonetic;
    private String exampleSentence;
    private String wrongAnswer;
    private String correctAnswer;
    private Long errorCount;
    private LocalDateTime lastErrorAt;
}
