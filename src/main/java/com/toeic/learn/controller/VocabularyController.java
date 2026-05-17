package com.toeic.learn.controller;

import com.toeic.learn.dto.ApiResponse;
import com.toeic.learn.dto.VocabularyDTO;
import com.toeic.learn.service.VocabularyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/vocabulary")
@RequiredArgsConstructor
public class VocabularyController {

    private final VocabularyService vocabularyService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("vocabularies", vocabularyService.findAll());
        return "vocabulary/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("vocabulary", vocabularyService.findById(id));
        return "vocabulary/detail";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("vocabulary", new VocabularyDTO());
        return "vocabulary/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("vocabulary", vocabularyService.findById(id));
        return "vocabulary/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute VocabularyDTO vocabulary) {
        vocabularyService.create(vocabulary);
        return "redirect:/vocabulary";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute VocabularyDTO vocabulary) {
        vocabularyService.update(id, vocabulary);
        return "redirect:/vocabulary";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        vocabularyService.delete(id);
        return "redirect:/vocabulary";
    }

    // ==================== API ====================

    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<ApiResponse<List<VocabularyDTO>>> apiList(
            @RequestParam(required = false) String keyword) {
        List<VocabularyDTO> vocabularies;
        if (keyword != null && !keyword.isBlank()) {
            vocabularies = vocabularyService.search(keyword);
        } else {
            vocabularies = vocabularyService.findAll();
        }
        return ResponseEntity.ok(ApiResponse.success(vocabularies));
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<ApiResponse<VocabularyDTO>> apiGet(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(vocabularyService.findById(id)));
    }

    @PostMapping("/api")
    @ResponseBody
    public ResponseEntity<ApiResponse<VocabularyDTO>> apiCreate(@Valid @RequestBody VocabularyDTO vocabulary) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(vocabularyService.create(vocabulary)));
    }

    @PutMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<ApiResponse<VocabularyDTO>> apiUpdate(
            @PathVariable Long id, @Valid @RequestBody VocabularyDTO vocabulary) {
        return ResponseEntity.ok(ApiResponse.success(vocabularyService.update(id, vocabulary)));
    }

    @DeleteMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Void> apiDelete(@PathVariable Long id) {
        vocabularyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
