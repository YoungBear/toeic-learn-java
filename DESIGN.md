# 托业学习系统 - 设计文档

## 1. 项目概述

### 1.1 项目背景
托业(TOEIC)英语学习系统，提供词汇记忆与练习功能，支持导出导入，预留听力、阅读扩展接口。

### 1.2 项目目标
- 建立完整的词汇管理及练习体系
- 支持批量导入导出，便于教学管理
- 预留扩展能力，支持未来添加听力、阅读模块

### 1.3 技术选型

| 类别 | 技术 |
|------|------|
| 后端框架 | Spring Boot 3.2.x |
| 前端模板 | Thymeleaf |
| ORM | Spring Data JPA |
| 数据库 | H2 (开发) / MySQL 8.0 (生产) |
| Excel处理 | Apache POI 5.x |
| 构建工具 | Maven |
| Java版本 | Java 17 |

---

## 2. 项目结构

```
toeic-learn/
├── src/main/java/com/toeic/learn/
│   ├── ToeicLearnApplication.java
│   ├── config/
│   │   └── WebConfig.java
│   ├── controller/
│   │   ├── VocabularyController.java
│   │   ├── WordController.java
│   │   ├── PracticeController.java
│   │   ├── ImportExportController.java
│   │   ├── ListeningController.java      # 预留
│   │   └── ReadingController.java         # 预留
│   ├── service/
│   │   ├── VocabularyService.java
│   │   ├── WordService.java
│   │   ├── PracticeService.java
│   │   └── ExportImportService.java
│   ├── repository/
│   │   ├── VocabularyRepository.java
│   │   ├── WordRepository.java
│   │   ├── PracticeRecordRepository.java
│   │   └── LearningProgressRepository.java
│   ├── entity/
│   │   ├── Vocabulary.java
│   │   ├── Word.java
│   │   ├── PracticeRecord.java
│   │   └── LearningProgress.java
│   ├── dto/
│   │   ├── VocabularyDTO.java
│   │   ├── WordDTO.java
│   │   ├── PracticeQuestionDTO.java
│   │   ├── PracticeResultDTO.java
│   │   └── ImportDTO.java
│   └── exception/
│       └── GlobalExceptionHandler.java
├── src/main/resources/
│   ├── templates/
│   │   ├── layout/
│   │   │   └── main.html
│   │   ├── vocabulary/
│   │   │   ├── list.html
│   │   │   ├── detail.html
│   │   │   └── form.html
│   │   ├── practice/
│   │   │   ├── index.html
│   │   │   └── result.html
│   │   └── index.html
│   ├── static/
│   │   ├── css/
│   │   │   └── style.css
│   │   └── js/
│   │       └── app.js
│   ├── application.yml
│   └── data.sql
├── src/test/java/
├── pom.xml
└── README.md
```

---

## 3. 数据库设计

### 3.1 ER 图

```ER
┌─────────────────┐       ┌─────────────────┐
│   vocabulary    │       │      word       │
├─────────────────┤       ├─────────────────┤
│ id (PK)         │──┐    │ id (PK)         │
│ name            │  │    │ vocabulary_id(FK)│←─┐
│ description     │  └───→│ english         │  │
│ created_at      │       │ chinese         │  │
│ updated_at      │       │ phonetic        │  │
└─────────────────┘       │ audio_url       │  │
                           │ example_sentence│  │
                           │ difficulty      │  │
                           │ part_of_speech  │  │
                           │ created_at      │  │
                           └─────────────────┘  │
                                                   │
        ┌─────────────────┐       ┌─────────────────┐
        │ practice_record │       │learning_progress│
        ├─────────────────┤       ├─────────────────┤
        │ id (PK)         │       │ id (PK)         │
        │ word_id (FK)    │←──────│ word_id (FK)    │←─┘
        │ is_correct      │       │ mastery_level   │
        │ response_time_ms │       │ last_practiced  │
        │ practiced_at     │       │ next_review_at  │
        └─────────────────┘       └─────────────────┘
```

### 3.2 表结构定义

#### vocabulary (词汇书表)
| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | BIGINT | PK, AUTO_INCREMENT | 主键 |
| name | VARCHAR(100) | NOT NULL | 词汇书名称 |
| description | VARCHAR(500) | | 描述 |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | 创建时间 |
| updated_at | TIMESTAMP | ON UPDATE CURRENT_TIMESTAMP | 更新时间 |

#### word (单词表)
| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | BIGINT | PK, AUTO_INCREMENT | 主键 |
| vocabulary_id | BIGINT | FK → vocabulary.id, NOT NULL | 所属词汇书 |
| english | VARCHAR(100) | NOT NULL | 英文单词 |
| chinese | VARCHAR(200) | NOT NULL | 中文释义 |
| phonetic | VARCHAR(100) | | 音标 |
| audio_url | VARCHAR(500) | | 音频URL |
| example_sentence | VARCHAR(500) | | 例句 |
| difficulty | INT | DEFAULT 1 | 难度等级 1-5 |
| part_of_speech | VARCHAR(20) | | 词性 |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | 创建时间 |

#### practice_record (练习记录表)
| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | BIGINT | PK, AUTO_INCREMENT | 主键 |
| word_id | BIGINT | FK → word.id, NOT NULL | 练习的单词 |
| is_correct | BOOLEAN | NOT NULL | 是否正确 |
| response_time_ms | INT | | 响应时间(毫秒) |
| practiced_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | 练习时间 |

#### learning_progress (学习进度表)
| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | BIGINT | PK, AUTO_INCREMENT | 主键 |
| word_id | BIGINT | FK → word.id, UNIQUE, NOT NULL | 单词ID |
| mastery_level | INT | DEFAULT 0 | 掌握程度 0-5 |
| last_practiced_at | TIMESTAMP | | 上次练习时间 |
| next_review_at | TIMESTAMP | | 下次复习时间 |

---

## 4. API 设计

### 4.1 词汇书管理 API

| 方法 | 路径 | 描述 | 请求体 | 响应 |
|------|------|------|--------|------|
| GET | `/api/vocabularies` | 获取词汇书列表 | - | `List<Vocabulary>` |
| GET | `/api/vocabularies/{id}` | 获取词汇书详情 | - | `Vocabulary` |
| POST | `/api/vocabularies` | 创建词汇书 | `VocabularyDTO` | `Vocabulary` |
| PUT | `/api/vocabularies/{id}` | 更新词汇书 | `VocabularyDTO` | `Vocabulary` |
| DELETE | `/api/vocabularies/{id}` | 删除词汇书 | - | 204 No Content |

### 4.2 单词管理 API

| 方法 | 路径 | 描述 | 请求体 | 响应 |
|------|------|------|--------|------|
| GET | `/api/vocabularies/{vocabId}/words` | 获取单词列表 | - | `List<Word>` |
| GET | `/api/words/{id}` | 获取单词详情 | - | `Word` |
| POST | `/api/words` | 添加单词 | `WordDTO` | `Word` |
| PUT | `/api/words/{id}` | 更新单词 | `WordDTO` | `Word` |
| DELETE | `/api/words/{id}` | 删除单词 | - | 204 No Content |
| POST | `/api/words/batch` | 批量添加单词 | `List<WordDTO>` | `List<Word>` |

### 4.3 练习 API

| 方法 | 路径 | 描述 | 请求体 | 响应 |
|------|------|------|--------|------|
| GET | `/api/practice/question` | 获取练习题 | - | `PracticeQuestionDTO` |
| POST | `/api/practice/submit` | 提交答案 | `PracticeResultDTO` | `PracticeResultDTO` |
| GET | `/api/practice/stats` | 获取统计信息 | - | `PracticeStatsDTO` |
| GET | `/api/practice/review` | 获取待复习单词 | - | `List<Word>` |

### 4.4 导入导出 API

| 方法 | 路径 | 描述 | 请求体/参数 | 响应 |
|------|------|------|-------------|------|
| POST | `/api/import/excel` | 导入Excel | MultipartFile | `ImportResultDTO` |
| POST | `/api/import/json` | 导入JSON | JSON body | `ImportResultDTO` |
| GET | `/api/export/excel/{vocabularyId}` | 导出Excel | - | Excel文件下载 |
| GET | `/api/export/json/{vocabularyId}` | 导出JSON | - | JSON文件下载 |
| GET | `/api/export/template/excel` | 下载Excel模板 | - | Excel文件下载 |

### 4.5 预留扩展 API

```java
// ListeningController.java
@RestController
@RequestMapping("/api/listening")
public class ListeningController {
    @GetMapping("/practice")
    public ListenableFuture<ListeningQuestionDTO> getListeningPractice();

    @PostMapping("/submit")
    public ListenableFuture<ListeningResultDTO> submitListeningAnswer(@RequestBody ListeningAnswerDTO answer);
}

// ReadingController.java
@RestController
@RequestMapping("/api/reading")
public class ReadingController {
    @GetMapping("/practice")
    public ListenableFuture<ReadingQuestionDTO> getReadingPractice();

    @PostMapping("/submit")
    public ListenableFuture<ReadingResultDTO> submitReadingAnswer(@RequestBody ReadingAnswerDTO answer);
}
```

---

## 5. DTO 定义

### 5.1 VocabularyDTO
```java
{
    "name": "商务词汇",
    "description": "托业商务场景常用词汇"
}
```

### 5.2 WordDTO
```java
{
    "vocabularyId": 1,
    "english": "agreement",
    "chinese": "协议",
    "phonetic": "/əˈɡriːmənt/",
    "audioUrl": "",
    "exampleSentence": "We reached an agreement on the terms.",
    "difficulty": 2,
    "partOfSpeech": "noun"
}
```

### 5.3 PracticeQuestionDTO
```java
{
    "questionId": "uuid",
    "type": "CHOICE",           // CHOICE | SPELL
    "word": {
        "id": 1,
        "english": "agreement",
        "phonetic": "/əˈɡriːmənt/"
    },
    "options": [                // 仅选择题有
        "协议",
        "争议",
        "会议",
        "决定"
    ]
}
```

### 5.4 PracticeResultDTO
```java
{
    "questionId": "uuid",
    "wordId": 1,
    "selectedAnswer": "协议",
    "correctAnswer": "协议",
    "isCorrect": true,
    "responseTimeMs": 2500
}
```

### 5.5 ImportResultDTO
```java
{
    "totalCount": 100,
    "successCount": 98,
    "failCount": 2,
    "failRecords": [
        {"row": 5, "error": "必填字段缺失"},
        {"row": 12, "error": "格式错误"}
    ]
}
```

---

## 6. 页面结构

### 6.1 页面导航

```
┌─────────────────────────────────────────────────┐
│  托业学习系统                                    │
├──────────┬──────────┬──────────┬────────────────┤
│ 首页     │ 词汇管理  │ 练习中心  │ 预留:听力/阅读  │
└──────────┴──────────┴──────────┴────────────────┘
```

### 6.2 页面清单

| 页面 | 路径 | 说明 |
|------|------|------|
| 首页 | `/` | 欢迎页，显示学习统计概览 |
| 词汇书列表 | `/vocabulary` | 展示所有词汇书 |
| 词汇书详情 | `/vocabulary/{id}` | 词汇书单词列表 |
| 词汇书表单 | `/vocabulary/new`, `/vocabulary/{id}/edit` | 新建/编辑词汇书 |
| 单词表单 | `/word/new?vocabId={id}` | 添加单词 |
| 练习首页 | `/practice` | 选择练习模式 |
| 练习页面 | `/practice/do` | 执行练习 |
| 练习结果 | `/practice/result` | 显示练习结果 |
| 导入 | `/import` | 导入数据页面 |
| 预留-听力 | `/listening` | 预留页面 |
| 预留-阅读 | `/reading` | 预留页面 |

### 6.3 页面原型摘要

#### 6.3.1 首页 (`index.html`)
```
┌─────────────────────────────────────────┐
│  托业学习系统                    [开始练习]│
├─────────────────────────────────────────┤
│  学习概览                                 │
│  ┌─────────┐ ┌─────────┐ ┌─────────┐    │
│  │ 词汇总量 │ │ 已掌握  │ │ 待复习  │    │
│  │   500   │ │   120   │ │   45    │    │
│  └─────────┘ └─────────┘ └─────────┘    │
│                                         │
│  近期练习正确率: 78%                      │
│  [████████░░] 78%                       │
└─────────────────────────────────────────┘
```

#### 6.3.2 词汇管理列表 (`vocabulary/list.html`)
```
┌─────────────────────────────────────────────────┐
│  词汇书管理                        [+ 新建词汇书]│
├─────────────────────────────────────────────────┤
│  名称        │ 单词数 │ 描述            │ 操作   │
│ ─────────────────────────────────────────────── │
│  商务词汇     │  200   │ 商务场景常用... │ [查看] │
│  日常词汇     │  150   │ 日常生活常用... │ [查看] │
│  [导入] [导出]                               │
└─────────────────────────────────────────────────┘
```

#### 6.3.3 练习页面 (`practice/do.html`)
```
┌─────────────────────────────────────────────────┐
│  词汇练习                    进度: 5/20   [退出] │
├─────────────────────────────────────────────────┤
│                                                   │
│  单词: agreement                                 │
│  音标: /əˈɡriːmənt/                              │
│                                                   │
│  请选择中文释义:                                   │
│  ┌─────────────────────────────────────────────┐│
│  │  A. 协议                                    ││
│  └─────────────────────────────────────────────┘│
│  ┌─────────────────────────────────────────────┐│
│  │  B. 争议                                    ││
│  └─────────────────────────────────────────────┘│
│  ┌─────────────────────────────────────────────┐│
│  │  C. 会议                                    ││
│  └─────────────────────────────────────────────┘│
│  ┌─────────────────────────────────────────────┐│
│  │  D. 决定                                    ││
│  └─────────────────────────────────────────────┘│
│                                                   │
│                              [下一题]            │
└─────────────────────────────────────────────────┘
```

---

## 7. 功能模块详细设计

### 7.1 词汇管理模块

#### 功能清单
1. **词汇书CRUD**
   - 创建词汇书（名称、描述）
   - 编辑词汇书信息
   - 删除词汇书（级联删除单词）
   - 查看词汇书列表及单词数量

2. **单词CRUD**
   - 添加单词（支持批量添加）
   - 编辑单词信息
   - 删除单词
   - 查看单词列表（支持分页）

3. **单词字段**
   - 英文单词（必填）
   - 中文释义（必填）
   - 音标（可选）
   - 音频URL（可选，预留）
   - 例句（可选）
   - 难度等级（1-5，默认1）
   - 词性（noun/verb/adj/adv等）

### 7.2 练习模块

#### 练习模式

1. **选择题模式**
   - 显示单词英文和音标
   - 显示4个中文选项（1个正确，3个干扰项）
   - 干扰项从同一词汇书随机选取
   - 记录响应时间

2. **拼写模式**
   - 显示中文释义
   - 用户输入英文拼写
   - 支持模糊匹配（大小写不敏感）

#### 练习流程
```
开始练习 → 获取题目 → 用户选择/输入 → 记录结果 → 更新进度 → 下一题 → 结束
```

#### 进度算法
- 初始 mastery_level = 0
- 答对: mastery_level += 1 (上限5)
- 答错: mastery_level = 0
- 根据 mastery_level 计算下次复习时间：
  - 0: 当天
  - 1: 明天
  - 2: 3天后
  - 3: 7天后
  - 4: 14天后
  - 5: 30天后

### 7.3 导入导出模块

#### 导入功能

1. **Excel导入**
   - 支持 .xlsx 格式
   - 必需列：english, chinese
   - 可选列：phonetic, example_sentence, difficulty, part_of_speech
   - 返回导入结果（成功数、失败数、错误详情）

2. **JSON导入**
   - 接收数组格式
   - 每项字段同 Excel
   - 返回导入结果

#### 导出功能

1. **Excel导出**
   - 导出指定词汇书的所有单词
   - 包含所有字段
   - 文件名格式：`{词汇书名称}_{日期}.xlsx`

2. **JSON导出**
   - 导出指定词汇书的所有单词
   - 包含所有字段
   - 文件名格式：`{词汇书名称}_{日期}.json`

3. **Excel模板下载**
   - 预定义表头行
   - 用户填写后导入

### 7.4 预留扩展模块

#### 接口规范
为未来听力、阅读模块预留标准化接口：

```java
// 统一的练习题接口
public interface PracticeProvider {
    PracticeQuestionDTO getNextQuestion(Long vocabularyId);
    PracticeResultDTO submitAnswer(Long wordId, Object answer);
}

// 统一的进度接口
public interface ProgressTracker {
    LearningProgress getProgress(Long wordId);
    void updateProgress(Long wordId, boolean isCorrect);
}
```

---

## 8. 配置设计

### 8.1 application.yml
```yaml
spring:
  application:
    name: toeic-learn
  datasource:
    url: jdbc:h2:file:./data/toeic-learn
    driver-class-name: org.h2.Driver
    username: sa
    password:
  h2:
    console:
      enabled: true
      path: /h2-console
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: false
    properties:
      hibernate:
        format_sql: true
  servlet:
    multipart:
      max-file-size: 10MB
      max-request-size: 10MB

server:
  port: 8080

app:
  practice:
    question-count: 20        # 每次练习题数
    option-count: 4            # 选择题选项数
  export:
    excel-template-path: /templates/excel-template.xlsx
```

---

## 9. 错误处理

### 9.1 异常类型

| 异常类 | HTTP状态码 | 说明 |
|--------|-----------|------|
| ResourceNotFoundException | 404 | 资源不存在 |
| ValidationException | 400 | 参数校验失败 |
| ImportException | 400 | 导入失败 |
| FileProcessingException | 500 | 文件处理错误 |

### 9.2 统一响应格式
```json
{
    "success": true,
    "data": {},
    "error": null,
    "timestamp": "2024-01-15T10:30:00Z"
}
```

```json
{
    "success": false,
    "data": null,
    "error": {
        "code": "VALIDATION_ERROR",
        "message": "字段校验失败",
        "details": [
            {"field": "english", "message": "不能为空"}
        ]
    },
    "timestamp": "2024-01-15T10:30:00Z"
}
```

---

## 10. 验收标准

### 10.1 功能验收

- [ ] 可以创建、编辑、删除词汇书
- [ ] 可以添加、编辑、删除单词
- [ ] 可以进行选择题练习
- [ ] 练习结果正确记录
- [ ] 学习进度正确更新
- [ ] 可以导出Excel
- [ ] 可以导出JSON
- [ ] 可以导入Excel
- [ ] 可以导入JSON
- [ ] 预留接口可访问

### 10.2 非功能验收

- [ ] 页面加载时间 < 2秒
- [ ] API响应时间 < 500ms
- [ ] 支持1000+单词规模
- [ ] 错误信息友好提示
- [ ] 移动端基本可用

---

## 11. 确认事项

| 项目 | 决策 |
|------|------|
| 数据库 | H2 (文件持久化模式 `./data/toeic-learn`) |
| 示例数据 | 是，预置约100个托业核心词汇 |
| 练习题量 | 支持动态选择：10/20/50题 |
| 批量导出 | 支持全部词汇书导出，且支持搜索条件过滤 |
