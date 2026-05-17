package com.toeic.learn.service;

import com.toeic.learn.dto.WordDTO;
import com.toeic.learn.entity.Vocabulary;
import com.toeic.learn.entity.Word;
import com.toeic.learn.repository.VocabularyRepository;
import com.toeic.learn.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WordService {

    private final WordRepository wordRepository;
    private final VocabularyRepository vocabularyRepository;

    public List<WordDTO> findByVocabularyId(Long vocabularyId) {
        return wordRepository.findByVocabularyId(vocabularyId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public WordDTO findById(Long id) {
        Word word = wordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("单词不存在: " + id));
        return toDTO(word);
    }

    @Transactional
    public WordDTO create(WordDTO dto) {
        Vocabulary vocabulary = vocabularyRepository.findById(dto.getVocabularyId())
                .orElseThrow(() -> new RuntimeException("词汇书不存在: " + dto.getVocabularyId()));

        Word word = new Word();
        word.setVocabulary(vocabulary);
        updateWordFromDTO(word, dto);
        word = wordRepository.save(word);
        return toDTO(word);
    }

    @Transactional
    public List<WordDTO> createBatch(List<WordDTO> dtos) {
        return dtos.stream()
                .map(this::create)
                .collect(Collectors.toList());
    }

    @Transactional
    public WordDTO update(Long id, WordDTO dto) {
        Word word = wordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("单词不存在: " + id));

        if (dto.getVocabularyId() != null && !dto.getVocabularyId().equals(word.getVocabulary().getId())) {
            Vocabulary vocabulary = vocabularyRepository.findById(dto.getVocabularyId())
                    .orElseThrow(() -> new RuntimeException("词汇书不存在: " + dto.getVocabularyId()));
            word.setVocabulary(vocabulary);
        }

        updateWordFromDTO(word, dto);
        word = wordRepository.save(word);
        return toDTO(word);
    }

    @Transactional
    public void delete(Long id) {
        wordRepository.deleteById(id);
    }

    public List<WordDTO> search(String keyword) {
        return wordRepository.searchByKeyword(keyword).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private void updateWordFromDTO(Word word, WordDTO dto) {
        word.setEnglish(dto.getEnglish());
        word.setChinese(dto.getChinese());
        word.setPhonetic(dto.getPhonetic());
        word.setAudioUrl(dto.getAudioUrl());
        word.setExampleSentence(dto.getExampleSentence());
        word.setPartOfSpeech(dto.getPartOfSpeech());
        word.setDifficulty(dto.getDifficulty() != null ? dto.getDifficulty() : 1);
    }

    private WordDTO toDTO(Word word) {
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
