package com.toeic.learn.service;

import com.toeic.learn.dto.ImportResultDTO;
import com.toeic.learn.dto.WordDTO;
import com.toeic.learn.entity.Vocabulary;
import com.toeic.learn.entity.Word;
import com.toeic.learn.repository.VocabularyRepository;
import com.toeic.learn.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExportImportService {

    private final WordRepository wordRepository;
    private final VocabularyRepository vocabularyRepository;
    private final WordService wordService;

    @Transactional
    public ImportResultDTO importFromExcel(MultipartFile file, Long vocabularyId) {
        ImportResultDTO result = new ImportResultDTO();

        Vocabulary vocabulary = vocabularyRepository.findById(vocabularyId)
                .orElseThrow(() -> new RuntimeException("词汇书不存在: " + vocabularyId));

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            int rowCount = 0;

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    // 跳过表头
                    continue;
                }
                rowCount++;

                try {
                    String english = getCellValueAsString(row.getCell(0));
                    String chinese = getCellValueAsString(row.getCell(1));

                    if (english == null || english.isBlank() || chinese == null || chinese.isBlank()) {
                        result.addFail(rowCount, "必填字段缺失");
                        continue;
                    }

                    WordDTO dto = new WordDTO();
                    dto.setVocabularyId(vocabularyId);
                    dto.setEnglish(english.trim());
                    dto.setChinese(chinese.trim());
                    dto.setPhonetic(getCellValueAsString(row.getCell(2)));
                    dto.setExampleSentence(getCellValueAsString(row.getCell(3)));
                    dto.setDifficulty(getCellValueAsInt(row.getCell(4), 1));
                    dto.setPartOfSpeech(getCellValueAsString(row.getCell(5)));

                    wordService.create(dto);
                    result.addSuccess();
                } catch (Exception e) {
                    result.addFail(rowCount, e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Excel文件读取失败", e);
        }

        return result;
    }

    @Transactional
    public ImportResultDTO importFromJson(List<WordDTO> words, Long vocabularyId) {
        ImportResultDTO result = new ImportResultDTO();

        Vocabulary vocabulary = vocabularyRepository.findById(vocabularyId)
                .orElseThrow(() -> new RuntimeException("词汇书不存在: " + vocabularyId));

        int row = 0;
        for (WordDTO dto : words) {
            row++;
            try {
                if (dto.getEnglish() == null || dto.getEnglish().isBlank() ||
                    dto.getChinese() == null || dto.getChinese().isBlank()) {
                    result.addFail(row, "必填字段缺失");
                    continue;
                }

                dto.setVocabularyId(vocabularyId);
                wordService.create(dto);
                result.addSuccess();
            } catch (Exception e) {
                result.addFail(row, e.getMessage());
            }
        }

        return result;
    }

    public byte[] exportToExcel(Long vocabularyId, String keyword) {
        List<Word> words;

        if (vocabularyId != null && vocabularyId > 0) {
            if (keyword != null && !keyword.isBlank()) {
                words = wordRepository.findByVocabularyId(vocabularyId).stream()
                        .filter(w -> w.getEnglish().contains(keyword) || w.getChinese().contains(keyword))
                        .toList();
            } else {
                words = wordRepository.findByVocabularyId(vocabularyId);
            }
        } else {
            if (keyword != null && !keyword.isBlank()) {
                words = wordRepository.searchByKeyword(keyword);
            } else {
                words = wordRepository.findAll();
            }
        }

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("词汇表");

            // 创建表头
            Row headerRow = sheet.createRow(0);
            String[] headers = {"英文", "中文", "音标", "例句", "难度", "词性"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // 填充数据
            int rowNum = 1;
            for (Word word : words) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(word.getEnglish());
                row.createCell(1).setCellValue(word.getChinese());
                row.createCell(2).setCellValue(word.getPhonetic() != null ? word.getPhonetic() : "");
                row.createCell(3).setCellValue(word.getExampleSentence() != null ? word.getExampleSentence() : "");
                row.createCell(4).setCellValue(word.getDifficulty() != null ? word.getDifficulty() : 1);
                row.createCell(5).setCellValue(word.getPartOfSpeech() != null ? word.getPartOfSpeech() : "");
            }

            // 自动调整列宽
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Excel导出失败", e);
        }
    }

    public List<WordDTO> exportToJson(Long vocabularyId, String keyword) {
        List<Word> words;

        if (vocabularyId != null && vocabularyId > 0) {
            if (keyword != null && !keyword.isBlank()) {
                words = wordRepository.findByVocabularyId(vocabularyId).stream()
                        .filter(w -> w.getEnglish().contains(keyword) || w.getChinese().contains(keyword))
                        .toList();
            } else {
                words = wordRepository.findByVocabularyId(vocabularyId);
            }
        } else {
            if (keyword != null && !keyword.isBlank()) {
                words = wordRepository.searchByKeyword(keyword);
            } else {
                words = wordRepository.findAll();
            }
        }

        return words.stream().map(this::toDTO).toList();
    }

    public String getExportFileName(Long vocabularyId, String format) {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String name;

        if (vocabularyId != null && vocabularyId > 0) {
            Vocabulary vocab = vocabularyRepository.findById(vocabularyId).orElse(null);
            name = vocab != null ? vocab.getName() : "vocabulary_" + vocabularyId;
        } else {
            name = "all_vocabulary";
        }

        return name + "_" + date + "." + format;
    }

    public byte[] getExcelTemplate() {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("词汇导入模板");

            Row headerRow = sheet.createRow(0);
            String[] headers = {"英文*", "中文*", "音标", "例句", "难度(1-5)", "词性"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // 添加示例行
            Row exampleRow = sheet.createRow(1);
            exampleRow.createCell(0).setCellValue("example");
            exampleRow.createCell(1).setCellValue("例子;典范");
            exampleRow.createCell(2).setCellValue("/ɪɡˈzæmpəl/");
            exampleRow.createCell(3).setCellValue("This is an example sentence.");
            exampleRow.createCell(4).setCellValue(2);
            exampleRow.createCell(5).setCellValue("noun");

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("模板生成失败", e);
        }
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return null;
        }
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> null;
        };
    }

    private int getCellValueAsInt(Cell cell, int defaultValue) {
        if (cell == null) {
            return defaultValue;
        }
        try {
            return (int) cell.getNumericCellValue();
        } catch (Exception e) {
            return defaultValue;
        }
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
