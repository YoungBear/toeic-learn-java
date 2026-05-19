package com.toeic.learn.service;

import com.toeic.learn.dto.*;
import com.toeic.learn.entity.LearningProgress;
import com.toeic.learn.entity.PracticeRecord;
import com.toeic.learn.entity.Word;
import com.toeic.learn.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PracticeService {

    private final WordRepository wordRepository;
    private final PracticeRecordRepository practiceRecordRepository;
    private final LearningProgressRepository learningProgressRepository;

    @Transactional
    public List<PracticeQuestionDTO> generateQuestions(Long vocabularyId, int count, String type) {
        List<Word> words;
        if (vocabularyId != null && vocabularyId > 0) {
            words = wordRepository.findRandomByVocabularyId(vocabularyId);
        } else {
            words = wordRepository.findRandomAll();
        }

        if (words.isEmpty()) {
            return Collections.emptyList();
        }

        // 如果单词数少于请求数，调整实际数量
        if (words.size() < count) {
            count = words.size();
        }

        List<PracticeQuestionDTO> questions = new ArrayList<>();
        Set<Long> usedWordIds = new HashSet<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            Word word = words.get(i);
            if (usedWordIds.contains(word.getId())) {
                continue;
            }
            usedWordIds.add(word.getId());

            PracticeQuestionDTO question = PracticeQuestionDTO.builder()
                    .questionId(UUID.randomUUID().toString())
                    .type(type)
                    .word(toWordDTO(word))
                    .build();

            if ("CHOICE".equals(type)) {
                List<String> options = generateOptions(word, words, 4);
                question.setOptions(options);
            }

            questions.add(question);
        }

        return questions;
    }

    @Transactional
    public PracticeResultDTO submitAnswer(PracticeResultDTO result) {
        Word word = wordRepository.findById(result.getWordId())
                .orElseThrow(() -> new RuntimeException("单词不存在: " + result.getWordId()));

        // 保存练习记录
        PracticeRecord record = new PracticeRecord();
        record.setWord(word);
        record.setIsCorrect(result.getIsCorrect());
        record.setResponseTimeMs(result.getResponseTimeMs());
        record.setSelectedAnswer(result.getSelectedAnswer());
        practiceRecordRepository.save(record);

        // 更新学习进度
        LearningProgress progress = learningProgressRepository.findByWordId(word.getId())
                .orElseGet(() -> {
                    LearningProgress p = new LearningProgress();
                    p.setWord(word);
                    return p;
                });

        if (result.getIsCorrect()) {
            progress.incrementMastery();
        } else {
            progress.resetMastery();
        }

        learningProgressRepository.save(progress);

        result.setCorrectAnswer(word.getChinese());
        return result;
    }

    public PracticeStatsDTO getStats() {
        long totalWords = wordRepository.count();
        long masteredWords = learningProgressRepository.countMasteredWords();
        long dueForReview = learningProgressRepository.findDueForReview(LocalDateTime.now()).size();
        long totalPractice = practiceRecordRepository.countTotalAnswers();
        long correctCount = practiceRecordRepository.countCorrectAnswers();
        long incorrectCount = practiceRecordRepository.countIncorrectAnswers();

        double accuracyRate = totalPractice > 0 ? (double) correctCount / totalPractice * 100 : 0;

        return PracticeStatsDTO.builder()
                .totalWords(totalWords)
                .masteredWords(masteredWords)
                .dueForReview(dueForReview)
                .totalPracticeCount(totalPractice)
                .correctCount(correctCount)
                .incorrectCount(incorrectCount)
                .accuracyRate(Math.round(accuracyRate * 100.0) / 100.0)
                .build();
    }

    public List<WordDTO> getWordsForReview() {
        List<LearningProgress> dueList = learningProgressRepository.findDueForReview(LocalDateTime.now());
        return dueList.stream()
                .map(lp -> toWordDTO(lp.getWord()))
                .collect(Collectors.toList());
    }

    private List<String> generateOptions(Word correctWord, List<Word> allWords, int count) {
        List<String> options = new ArrayList<>();
        options.add(correctWord.getChinese());

        List<Word> otherWords = allWords.stream()
                .filter(w -> !w.getId().equals(correctWord.getId()))
                .collect(Collectors.toList());

        Random random = new Random();
        int optionCount = Math.min(count - 1, otherWords.size());

        Set<Integer> usedIndices = new HashSet<>();
        while (options.size() < count) {
            int idx = random.nextInt(otherWords.size());
            if (!usedIndices.contains(idx)) {
                usedIndices.add(idx);
                options.add(otherWords.get(idx).getChinese());
            }
        }

        // 打乱选项顺序
        Collections.shuffle(options, random);
        return options;
    }

    private WordDTO toWordDTO(Word word) {
        WordDTO dto = new WordDTO();
        dto.setId(word.getId());
        dto.setVocabularyId(word.getVocabulary().getId());
        dto.setEnglish(word.getEnglish());
        dto.setChinese(word.getChinese());
        dto.setPhonetic(word.getPhonetic());
        dto.setAudioUrl(word.getAudioUrl());
        dto.setExampleSentence(word.getExampleSentence());
        dto.setPartOfSpeech(word.getPartOfSpeech());
        dto.setDifficulty(word.getDifficulty());
        return dto;
    }
}
