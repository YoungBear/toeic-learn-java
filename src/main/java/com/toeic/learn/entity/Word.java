package com.toeic.learn.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "word")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vocabulary_id", nullable = false)
    private Vocabulary vocabulary;

    @Column(nullable = false, length = 100)
    private String english;

    @Column(nullable = false, length = 200)
    private String chinese;

    @Column(length = 100)
    private String phonetic;

    @Column(name = "audio_url", length = 500)
    private String audioUrl;

    @Column(name = "example_sentence", length = 500)
    private String exampleSentence;

    @Column(name = "part_of_speech", length = 20)
    private String partOfSpeech;

    @Column(name = "difficulty")
    private Integer difficulty = 1;

    @Column(name = "favorited", nullable = false)
    private Boolean favorited = false;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
