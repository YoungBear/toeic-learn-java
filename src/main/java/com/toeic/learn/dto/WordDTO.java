package com.toeic.learn.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WordDTO {

    private Long id;

    private Long vocabularyId;

    @NotBlank(message = "英文单词不能为空")
    @Size(max = 100, message = "英文单词不能超过100字符")
    private String english;

    @NotBlank(message = "中文释义不能为空")
    @Size(max = 200, message = "中文释义不能超过200字符")
    private String chinese;

    @Size(max = 100, message = "音标不能超过100字符")
    private String phonetic;

    @Size(max = 500, message = "音频URL不能超过500字符")
    private String audioUrl;

    @Size(max = 500, message = "例句不能超过500字符")
    private String exampleSentence;

    @Size(max = 20, message = "词性不能超过20字符")
    private String partOfSpeech;

    private Integer difficulty = 1;
}
