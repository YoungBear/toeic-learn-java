package com.toeic.learn.repository;

import com.toeic.learn.entity.PracticeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PracticeRecordRepository extends JpaRepository<PracticeRecord, Long> {

    List<PracticeRecord> findByWordId(Long wordId);

    @Query("SELECT pr FROM PracticeRecord pr WHERE pr.practicedAt >= :since ORDER BY pr.practicedAt DESC")
    List<PracticeRecord> findRecentRecords(LocalDateTime since);

    @Query("SELECT COUNT(pr) FROM PracticeRecord pr WHERE pr.isCorrect = true")
    long countCorrectAnswers();

    @Query("SELECT COUNT(pr) FROM PracticeRecord pr WHERE pr.isCorrect = false")
    long countIncorrectAnswers();

    @Query("SELECT COUNT(pr) FROM PracticeRecord pr")
    long countTotalAnswers();

    @Query("SELECT pr FROM PracticeRecord pr WHERE pr.word.vocabulary.id = :vocabularyId ORDER BY pr.practicedAt DESC")
    List<PracticeRecord> findByVocabularyId(Long vocabularyId);
}
