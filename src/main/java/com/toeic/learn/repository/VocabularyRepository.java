package com.toeic.learn.repository;

import com.toeic.learn.entity.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VocabularyRepository extends JpaRepository<Vocabulary, Long> {

    @Query("SELECT v FROM Vocabulary v LEFT JOIN FETCH v.words")
    List<Vocabulary> findAllWithWordCount();

    List<Vocabulary> findByNameContainingIgnoreCase(String name);
}
