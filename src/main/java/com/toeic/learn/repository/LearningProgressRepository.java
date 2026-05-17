package com.toeic.learn.repository;

import com.toeic.learn.entity.LearningProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LearningProgressRepository extends JpaRepository<LearningProgress, Long> {

    Optional<LearningProgress> findByWordId(Long wordId);

    @Query("SELECT lp FROM LearningProgress lp WHERE lp.nextReviewAt <= :now OR lp.nextReviewAt IS NULL")
    List<LearningProgress> findDueForReview(@Param("now") LocalDateTime now);

    @Query("SELECT lp FROM LearningProgress lp WHERE lp.masteryLevel >= :level")
    List<LearningProgress> findByMasteryLevelGreaterThanEqual(@Param("level") Integer level);

    @Query("SELECT COUNT(lp) FROM LearningProgress lp WHERE lp.masteryLevel >= 3")
    long countMasteredWords();

    @Query("SELECT COUNT(lp) FROM LearningProgress lp")
    long countTotalProgress();
}
