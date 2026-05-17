package com.toeic.learn.repository;

import com.toeic.learn.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {

    List<Word> findByVocabularyId(Long vocabularyId);

    Optional<Word> findByEnglishIgnoreCase(String english);

    @Query("SELECT w FROM Word w WHERE w.vocabulary.id = :vocabId ORDER BY FUNCTION('RAND')")
    List<Word> findRandomByVocabularyId(@Param("vocabId") Long vocabularyId);

    @Query("SELECT w FROM Word w ORDER BY FUNCTION('RAND')")
    List<Word> findRandomAll();

    @Query("SELECT w FROM Word w WHERE w.english LIKE %:keyword% OR w.chinese LIKE %:keyword%")
    List<Word> searchByKeyword(@Param("keyword") String keyword);

    int countByVocabularyId(Long vocabularyId);
}
