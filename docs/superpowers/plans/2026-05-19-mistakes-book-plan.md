# 错题本功能实现计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 新增错题本功能，允许用户查看历史练习中答错的单词，并支持重新练习错题。

**Architecture:** 在现有 PracticeRecord 实体上添加 selectedAnswer 字段存储用户选择的答案，通过新增的 Service/Repository 方法查询错题列表，新增独立页面展示错题并支持练习。

**Tech Stack:** Spring Boot, Spring Data JPA, Thymeleaf, H2 Database

---

## 文件清单

### 需要修改的文件

| 文件 | 变更 |
|------|------|
| `src/main/java/com/toeic/learn/entity/PracticeRecord.java` | 添加 selectedAnswer 字段 |
| `src/main/java/com/toeic/learn/service/PracticeService.java` | 修改 submitAnswer() 保存答案，新增 getMistakes() |
| `src/main/java/com/toeic/learn/repository/PracticeRecordRepository.java` | 新增查询方法 |
| `src/main/java/com/toeic/learn/dto/MistakeDTO.java` | 新增 DTO |
| `src/main/java/com/toeic/learn/controller/PracticeController.java` | 新增端点 |
| `src/main/resources/templates/practice/mistakes.html` | 新增页面 |
| `src/main/resources/templates/layout/main.html` | 添加导航入口 |
| `src/main/resources/data.sql` | 添加 ALTER TABLE 语句 |

### 需要创建的文件

| 文件 | 说明 |
|------|------|
| `src/main/java/com/toeic/learn/dto/MistakeDTO.java` | 错题 DTO |
| `src/main/resources/templates/practice/mistakes.html` | 错题本页面 |

---

## 任务清单

### Task 1: 修改 PracticeRecord 实体

**Files:**
- Modify: `src/main/java/com/toeic/learn/entity/PracticeRecord.java`

- [ ] **Step 1: 读取当前 PracticeRecord.java**

```java
// 确认当前字段结构
```

- [ ] **Step 2: 添加 selectedAnswer 字段**

在 `private Word word;` 后添加：

```java
@Column(name = "selected_answer", length = 500)
private String selectedAnswer;
```

- [ ] **Step 3: 提交**

```bash
git add src/main/java/com/toeic/learn/entity/PracticeRecord.java
git commit -m "feat: add selectedAnswer field to PracticeRecord"
```

---

### Task 2: 修改 data.sql 添加 ALTER TABLE

**Files:**
- Modify: `src/main/resources/data.sql`

- [ ] **Step 1: 在 data.sql 末尾添加 ALTER TABLE 语句**

在 `learning_progress` 初始化语句后添加：

```sql
-- 为 practice_record 添加 selected_answer 字段（H2 兼容语法）
ALTER TABLE practice_record ADD COLUMN selected_answer VARCHAR(500);
```

- [ ] **Step 2: 提交**

```bash
git add src/main/resources/data.sql
git commit -m "feat: add selected_answer column to practice_record"
```

---

### Task 3: 修改 submitAnswer 保存 selectedAnswer

**Files:**
- Modify: `src/main/java/com/toeic/learn/service/PracticeService.java`

- [ ] **Step 1: 读取当前 submitAnswer 方法**

确认当前实现，找到创建 PracticeRecord 的位置。

- [ ] **Step 2: 修改 PracticeRecord 创建逻辑**

在创建 PracticeRecord 时设置 selectedAnswer：

```java
PracticeRecord record = PracticeRecord.builder()
        .word(word)
        .isCorrect(isCorrect)
        .responseTimeMs(responseTimeMs)
        .selectedAnswer(selectedAnswer)  // 新增
        .build();
```

- [ ] **Step 3: 提交**

```bash
git add src/main/java/com/toeic/learn/service/PracticeService.java
git commit -m "feat: save selectedAnswer in submitAnswer"
```

---

### Task 4: 新增 MistakeDTO

**Files:**
- Create: `src/main/java/com/toeic/learn/dto/MistakeDTO.java`

- [ ] **Step 1: 创建 MistakeDTO**

```java
package com.toeic.learn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MistakeDTO {
    private Long wordId;
    private String english;
    private String chinese;
    private String phonetic;
    private String exampleSentence;
    private String wrongAnswer;
    private String correctAnswer;
    private Long errorCount;
    private LocalDateTime lastErrorAt;
}
```

- [ ] **Step 2: 提交**

```bash
git add src/main/java/com/toeic/learn/dto/MistakeDTO.java
git commit -m "feat: add MistakeDTO"
```

---

### Task 5: 新增 Repository 查询方法

**Files:**
- Modify: `src/main/java/com/toeic/learn/repository/PracticeRecordRepository.java`

- [ ] **Step 1: 添加查询方法**

```java
@Query("SELECT pr FROM PracticeRecord pr WHERE pr.isCorrect = false AND pr.word.vocabulary.id = :vocabularyId ORDER BY pr.practicedAt DESC")
List<PracticeRecord> findWrongAnswersByVocabularyId(@Param("vocabularyId") Long vocabularyId);

@Query("SELECT pr FROM PracticeRecord pr WHERE pr.isCorrect = false ORDER BY pr.practicedAt DESC")
List<PracticeRecord> findAllWrongAnswers();

@Query("SELECT pr.word.id, COUNT(pr) as errorCount FROM PracticeRecord pr WHERE pr.isCorrect = false GROUP BY pr.word.id ORDER BY errorCount DESC")
List<Object[]> countErrorsByWordId();
```

- [ ] **Step 2: 提交**

```bash
git add src/main/java/com/toeic/learn/repository/PracticeRecordRepository.java
git commit -m "feat: add wrong answers query methods to PracticeRecordRepository"
```

---

### Task 6: 新增 PracticeService 错题方法

**Files:**
- Modify: `src/main/java/com/toeic/learn/service/PracticeService.java`

- [ ] **Step 1: 添加 getMistakes 方法**

```java
public List<MistakeDTO> getMistakes(Long vocabularyId) {
    List<PracticeRecord> wrongRecords;
    if (vocabularyId != null) {
        wrongRecords = practiceRecordRepository.findWrongAnswersByVocabularyId(vocabularyId);
    } else {
        wrongRecords = practiceRecordRepository.findAllWrongAnswers();
    }

    // 按单词聚合
    Map<Long, List<PracticeRecord>> recordsByWord = wrongRecords.stream()
            .collect(Collectors.groupingBy(r -> r.getWord().getId()));

    List<MistakeDTO> mistakes = new ArrayList<>();
    for (Map.Entry<Long, List<PracticeRecord>> entry : recordsByWord.entrySet()) {
        Word word = entry.getValue().get(0).getWord();
        PracticeRecord latestError = entry.getValue().get(0);
        mistakes.add(MistakeDTO.builder()
                .wordId(word.getId())
                .english(word.getEnglish())
                .chinese(word.getChinese())
                .phonetic(word.getPhonetic())
                .exampleSentence(word.getExampleSentence())
                .wrongAnswer(latestError.getSelectedAnswer())
                .correctAnswer(word.getEnglish())
                .errorCount((long) entry.getValue().size())
                .lastErrorAt(latestError.getPracticedAt())
                .build());
    }

    mistakes.sort((a, b) -> Long.compare(b.getErrorCount(), a.getErrorCount()));
    return mistakes;
}
```

- [ ] **Step 2: 添加 generateMistakeQuestions 方法**

```java
public List<QuestionDTO> generateMistakeQuestions(int count) {
    List<MistakeDTO> mistakes = getMistakes(null);
    if (mistakes.isEmpty()) {
        return Collections.emptyList();
    }

    // 随机选取
    Collections.shuffle(mistakes);
    List<MistakeDTO> selected = mistakes.stream().limit(count).collect(Collectors.toList());

    List<QuestionDTO> questions = new ArrayList<>();
    for (MistakeDTO mistake : selected) {
        Word word = wordRepository.findById(mistake.getWordId()).orElse(null);
        if (word != null) {
            questions.add(createQuestionFromWord(word));
        }
    }
    return questions;
}
```

- [ ] **Step 3: 提交**

```bash
git add src/main/java/com/toeic/learn/service/PracticeService.java
git commit -m "feat: add getMistakes and generateMistakeQuestions to PracticeService"
```

---

### Task 7: 新增 Controller 端点

**Files:**
- Modify: `src/main/java/com/toeic/learn/controller/PracticeController.java`

- [ ] **Step 1: 添加页面跳转和 API 端点**

```java
@GetMapping("/mistakes")
public String mistakesPage(Model model) {
    model.addAttribute("mistakes", practiceService.getMistakes(null));
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
public ResponseEntity<ApiResponse<List<QuestionDTO>>> getMistakeQuestions(
        @RequestParam(defaultValue = "20") int count) {
    return ResponseEntity.ok(ApiResponse.success(practiceService.generateMistakeQuestions(count)));
}
```

- [ ] **Step 2: 提交**

```bash
git add src/main/java/com/toeic/learn/controller/PracticeController.java
git commit -m "feat: add mistakes page and API endpoints"
```

---

### Task 8: 创建错题本页面

**Files:**
- Create: `src/main/resources/templates/practice/mistakes.html`

- [ ] **Step 1: 创建页面模板**

参考现有的 `practice/review.html` 结构，创建错题本页面，包含：
- 错题列表表格
- 错误次数排序
- 练习错题按钮
- 词汇书筛选下拉框

- [ ] **Step 2: 提交**

```bash
git add src/main/resources/templates/practice/mistakes.html
git commit -m "feat: add mistakes book page template"
```

---

### Task 9: 添加导航入口

**Files:**
- Modify: `src/main/resources/templates/layout/main.html`

- [ ] **Step 1: 在导航菜单添加错题本入口**

参考收藏页面的导航位置，添加错题本链接。

- [ ] **Step 2: 提交**

```bash
git add src/main/resources/templates/layout/main.html
git commit -m "feat: add mistakes page link to navigation"
```

---

### Task 10: 更新 README.md

**Files:**
- Modify: `README.md`

- [ ] **Step 1: 在已完成功能列表添加错题本功能**

- [ ] **Step 2: 提交**

```bash
git add README.md
git commit -m "docs: update README with mistakes feature"
```

---

## 验证清单

完成实现后，验证以下功能：

1. [ ] 数据库新字段存在（selected_answer）
2. [ ] 练习提交时保存了用户选择的答案
3. [ ] 访问 /practice/mistakes 可以看到错题列表
4. [ ] 错题按错误次数降序排列
5. [ ] 可以筛选不同词汇书的错题
6. [ ] 点击练习错题按钮可以进入练习模式
7. [ ] 重新练习错题后，错误次数正确累加

---

## 顺序执行建议

1. 先执行 Task 1-2（修改实体和数据库）
2. 然后执行 Task 3（修改保存逻辑）
3. 执行 Task 4-6（DTO、Repository、Service）
4. 执行 Task 7-9（Controller、页面、导航）
5. 最后执行 Task 10（文档）

每次修改后可以运行 `mvn spring-boot:run` 测试。
