package com.toeic.learn.service;

import com.toeic.learn.dto.VocabularyDTO;
import com.toeic.learn.entity.Vocabulary;
import com.toeic.learn.repository.VocabularyRepository;
import com.toeic.learn.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VocabularyService {

    private final VocabularyRepository vocabularyRepository;
    private final WordRepository wordRepository;

    public List<VocabularyDTO> findAll() {
        return vocabularyRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public VocabularyDTO findById(Long id) {
        Vocabulary vocabulary = vocabularyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("词汇书不存在: " + id));
        return toDTO(vocabulary);
    }

    @Transactional
    public VocabularyDTO create(VocabularyDTO dto) {
        Vocabulary vocabulary = new Vocabulary();
        vocabulary.setName(dto.getName());
        vocabulary.setDescription(dto.getDescription());
        vocabulary = vocabularyRepository.save(vocabulary);
        return toDTO(vocabulary);
    }

    @Transactional
    public VocabularyDTO update(Long id, VocabularyDTO dto) {
        Vocabulary vocabulary = vocabularyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("词汇书不存在: " + id));
        vocabulary.setName(dto.getName());
        vocabulary.setDescription(dto.getDescription());
        vocabulary = vocabularyRepository.save(vocabulary);
        return toDTO(vocabulary);
    }

    @Transactional
    public void delete(Long id) {
        vocabularyRepository.deleteById(id);
    }

    public List<VocabularyDTO> search(String keyword) {
        return vocabularyRepository.findByNameContainingIgnoreCase(keyword).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private VocabularyDTO toDTO(Vocabulary vocabulary) {
        VocabularyDTO dto = new VocabularyDTO();
        dto.setId(vocabulary.getId());
        dto.setName(vocabulary.getName());
        dto.setDescription(vocabulary.getDescription());
        dto.setWordCount(wordRepository.countByVocabularyId(vocabulary.getId()));
        return dto;
    }
}
