package com.toeic.learn.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PracticeQuestionDTO {

    private String questionId;

    private String type;  // CHOICE, SPELL

    private WordDTO word;

    private List<String> options;  // 仅选择题有
}
