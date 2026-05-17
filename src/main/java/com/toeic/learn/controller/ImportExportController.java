package com.toeic.learn.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toeic.learn.dto.ApiResponse;
import com.toeic.learn.dto.ImportResultDTO;
import com.toeic.learn.dto.VocabularyDTO;
import com.toeic.learn.dto.WordDTO;
import com.toeic.learn.service.ExportImportService;
import com.toeic.learn.service.VocabularyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/import-export")
@RequiredArgsConstructor
public class ImportExportController {

    private final ExportImportService exportImportService;
    private final VocabularyService vocabularyService;
    private final ObjectMapper objectMapper;

    @GetMapping
    public String page(Model model) {
        model.addAttribute("vocabularies", vocabularyService.findAll());
        return "import-export/index";
    }

    // ==================== 导出 API ====================

    @GetMapping("/api/export/excel")
    public ResponseEntity<byte[]> exportExcel(
            @RequestParam(required = false) Long vocabularyId,
            @RequestParam(required = false) String keyword) {
        byte[] data = exportImportService.exportToExcel(vocabularyId, keyword);
        String filename = exportImportService.getExportFileName(vocabularyId, "xlsx");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(data);
    }

    @GetMapping("/api/export/json")
    public ResponseEntity<byte[]> exportJson(
            @RequestParam(required = false) Long vocabularyId,
            @RequestParam(required = false) String keyword) {
        List<WordDTO> words = exportImportService.exportToJson(vocabularyId, keyword);
        String filename = exportImportService.getExportFileName(vocabularyId, "json");

        try {
            byte[] data = objectMapper.writeValueAsBytes(words);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(data);
        } catch (Exception e) {
            throw new RuntimeException("JSON导出失败", e);
        }
    }

    @GetMapping("/api/export/template")
    public ResponseEntity<byte[]> downloadTemplate() {
        byte[] data = exportImportService.getExcelTemplate();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"word_import_template.xlsx\"")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(data);
    }

    // ==================== 导入 API ====================

    @PostMapping("/api/import/excel")
    @ResponseBody
    public ResponseEntity<ApiResponse<ImportResultDTO>> importExcel(
            @RequestParam MultipartFile file,
            @RequestParam Long vocabularyId) {
        ImportResultDTO result = exportImportService.importFromExcel(file, vocabularyId);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PostMapping("/api/import/json")
    @ResponseBody
    public ResponseEntity<ApiResponse<ImportResultDTO>> importJson(
            @RequestBody String json,
            @RequestParam Long vocabularyId) {
        try {
            List<WordDTO> words = objectMapper.readValue(json, new TypeReference<List<WordDTO>>() {});
            ImportResultDTO result = exportImportService.importFromJson(words, vocabularyId);
            return ResponseEntity.ok(ApiResponse.success(result));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("IMPORT_ERROR", "JSON解析失败: " + e.getMessage()));
        }
    }
}
