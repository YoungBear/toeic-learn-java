package com.toeic.learn.controller;

import com.toeic.learn.dto.ApiResponse;
import com.toeic.learn.dto.WordDTO;
import com.toeic.learn.service.WordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/word")
@RequiredArgsConstructor
public class WordController {

    private final WordService wordService;

    @GetMapping("/new")
    public String showCreateForm(@RequestParam Long vocabId, Model model) {
        WordDTO word = new WordDTO();
        word.setVocabularyId(vocabId);
        model.addAttribute("word", word);
        return "word/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("word", wordService.findById(id));
        return "word/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute WordDTO word) {
        wordService.create(word);
        return "redirect:/vocabulary/" + word.getVocabularyId();
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute WordDTO word) {
        WordDTO updated = wordService.update(id, word);
        return "redirect:/vocabulary/" + updated.getVocabularyId();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, @RequestParam Long vocabId) {
        wordService.delete(id);
        return "redirect:/vocabulary/" + vocabId;
    }

    // ==================== API ====================

    @GetMapping("/api/vocabulary/{vocabId}")
    @ResponseBody
    public ResponseEntity<ApiResponse<List<WordDTO>>> apiListByVocabulary(@PathVariable Long vocabId) {
        return ResponseEntity.ok(ApiResponse.success(wordService.findByVocabularyId(vocabId)));
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<ApiResponse<WordDTO>> apiGet(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(wordService.findById(id)));
    }

    @PostMapping("/api")
    @ResponseBody
    public ResponseEntity<ApiResponse<WordDTO>> apiCreate(@Valid @RequestBody WordDTO word) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(wordService.create(word)));
    }

    @PostMapping("/api/batch")
    @ResponseBody
    public ResponseEntity<ApiResponse<List<WordDTO>>> apiCreateBatch(@Valid @RequestBody List<WordDTO> words) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(wordService.createBatch(words)));
    }

    @PutMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<ApiResponse<WordDTO>> apiUpdate(@PathVariable Long id, @Valid @RequestBody WordDTO word) {
        return ResponseEntity.ok(ApiResponse.success(wordService.update(id, word)));
    }

    @DeleteMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Void> apiDelete(@PathVariable Long id) {
        wordService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/search")
    @ResponseBody
    public ResponseEntity<ApiResponse<List<WordDTO>>> apiSearch(@RequestParam String keyword) {
        return ResponseEntity.ok(ApiResponse.success(wordService.search(keyword)));
    }
}
