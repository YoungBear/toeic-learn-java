package com.toeic.learn.controller;

import com.toeic.learn.dto.ApiResponse;
import com.toeic.learn.dto.MistakeDTO;
import com.toeic.learn.dto.PracticeQuestionDTO;
import com.toeic.learn.dto.PracticeResultDTO;
import com.toeic.learn.dto.PracticeStatsDTO;
import com.toeic.learn.dto.VocabularyDTO;
import com.toeic.learn.service.PracticeService;
import com.toeic.learn.service.VocabularyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/practice")
@RequiredArgsConstructor
public class PracticeController {

    private final PracticeService practiceService;
    private final VocabularyService vocabularyService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("stats", practiceService.getStats());
        model.addAttribute("vocabularies", vocabularyService.findAll());
        return "practice/index";
    }

    @GetMapping("/do")
    public String doPractice(
            @RequestParam(required = false, defaultValue = "20") Integer count,
            @RequestParam(required = false) Long vocabularyId,
            @RequestParam(required = false, defaultValue = "CHOICE") String type,
            Model model) {
        List<PracticeQuestionDTO> questions = practiceService.generateQuestions(vocabularyId, count, type);
        model.addAttribute("questions", questions);
        model.addAttribute("count", count);
        model.addAttribute("vocabularyId", vocabularyId);
        model.addAttribute("type", type);
        return "practice/do";
    }

    @PostMapping("/submit")
    @ResponseBody
    public ResponseEntity<ApiResponse<PracticeResultDTO>> submitAnswer(@Valid @RequestBody PracticeResultDTO result) {
        return ResponseEntity.ok(ApiResponse.success(practiceService.submitAnswer(result)));
    }

    @GetMapping("/stats")
    @ResponseBody
    public ResponseEntity<ApiResponse<PracticeStatsDTO>> getStats() {
        return ResponseEntity.ok(ApiResponse.success(practiceService.getStats()));
    }

    @GetMapping("/review")
    public String review(Model model) {
        model.addAttribute("words", practiceService.getWordsForReview());
        return "practice/review";
    }

    // ==================== API ====================

    @GetMapping("/api/questions")
    @ResponseBody
    public ResponseEntity<ApiResponse<List<PracticeQuestionDTO>>> apiGetQuestions(
            @RequestParam(required = false, defaultValue = "20") Integer count,
            @RequestParam(required = false) Long vocabularyId,
            @RequestParam(required = false, defaultValue = "CHOICE") String type) {
        List<PracticeQuestionDTO> questions = practiceService.generateQuestions(vocabularyId, count, type);
        return ResponseEntity.ok(ApiResponse.success(questions));
    }

    @PostMapping("/api/submit")
    @ResponseBody
    public ResponseEntity<ApiResponse<PracticeResultDTO>> apiSubmitAnswer(@Valid @RequestBody PracticeResultDTO result) {
        return ResponseEntity.ok(ApiResponse.success(practiceService.submitAnswer(result)));
    }

    @GetMapping("/api/stats")
    @ResponseBody
    public ResponseEntity<ApiResponse<PracticeStatsDTO>> apiGetStats() {
        return ResponseEntity.ok(ApiResponse.success(practiceService.getStats()));
    }

    @GetMapping("/mistakes")
    public String mistakesPage(Model model) {
        model.addAttribute("mistakes", practiceService.getMistakes(null));
        model.addAttribute("vocabularies", vocabularyService.findAll());
        return "practice/mistakes";
    }

    @GetMapping("/api/mistakes")
    @ResponseBody
    public ResponseEntity<ApiResponse<List<MistakeDTO>>> getMistakes(
            @RequestParam(required = false) Long vocabularyId) {
        return ResponseEntity.ok(ApiResponse.success(practiceService.getMistakes(vocabularyId)));
    }

    @GetMapping("/api/mistakes/questions")
    @ResponseBody
    public ResponseEntity<ApiResponse<List<PracticeQuestionDTO>>> getMistakeQuestions(
            @RequestParam(defaultValue = "20") int count) {
        return ResponseEntity.ok(ApiResponse.success(practiceService.generateMistakeQuestions(count)));
    }
}
