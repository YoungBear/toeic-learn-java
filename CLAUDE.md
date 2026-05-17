# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 项目概述

托业(TOEIC)英语学习系统 - 前后端不分离的 Spring Boot 应用，使用 Thymeleaf 模板引擎渲染页面。

## 常用命令

```bash
# 开发运行
mvn spring-boot:run

# 打包
mvn clean package -DskipTests

# 编译
mvn clean compile

# 清理数据库（重新初始化）
rm -f data/toeic-learn.mv.db && mvn spring-boot:run
```

## 架构概览

### 分层结构
```
Controller → Service → Repository → Entity
                ↓
              DTO
```

- **Controller**: 处理 HTTP 请求，返回 Thymeleaf 模板或 JSON API
- **Service**: 业务逻辑，包含练习题目生成、进度计算等
- **Repository**: Spring Data JPA 接口
- **Entity**: JPA 实体，与数据库表一一对应
- **DTO**: 数据传输对象，用于 API 响应

### 核心实体关系
```
Vocabulary (1) ←→ (N) Word (1) ←→ (1) LearningProgress
                         ↓
                   PracticeRecord
```

- `Vocabulary`: 词汇书
- `Word`: 单词，属于某个词汇书
- `LearningProgress`: 每个单词的学习进度（掌握等级 0-5）
- `PracticeRecord`: 每次练习的记录

### 学习进度算法 (LearningProgress)
```
答对: masteryLevel += 1 (上限5)
答错: masteryLevel = 0

根据 masteryLevel 计算下次复习时间:
0级 → 当天 | 1级 → 明天 | 2级 → 3天后 | 3级 → 7天后 | 4级 → 14天后 | 5级 → 30天后
```

## 关键实现

### 练习题目生成 (PracticeService.generateQuestions)
1. 从指定词汇书或全部单词中随机获取 N 个
2. 对每个单词生成 4 个选项（1 个正确 + 3 个干扰项）
3. 干扰项从同一词汇书的其他单词中文释义中随机选取

### 导入导出 (ExportImportService)
- Excel: 使用 Apache POI 读写 `.xlsx` 文件
- JSON: 使用 Jackson ObjectMapper
- 导入时校验必填字段（english, chinese），可选（phonetic, exampleSentence, difficulty, partOfSpeech）

### 统一响应格式 (ApiResponse)
```java
{
    "success": true/false,
    "data": {...},
    "error": {"code": "...", "message": "..."},
    "timestamp": "..."
}
```

### 预留扩展接口
`ListeningController` 和 `ReadingController` 已定义基础接口，返回 `status: "reserved"` 状态。实现新模块时：
1. 在对应 Controller 中替换 `@GetMapping("/practice")` 和 `@PostMapping("/submit")` 的实现
2. 创建新的 Service 类处理业务逻辑
3. 添加 Thymeleaf 页面

## 配置

- **数据库**: H2 文件数据库，位置 `./data/toeic-learn.mv.db`
- **H2 Console**: http://localhost:8080/h2-console
- **端口**: 8080
- **Java 版本**: 21

## 前端页面结构

```
templates/
├── index.html              # 首页
├── listening.html          # 预留-听力
├── reading.html           # 预留-阅读
├── vocabulary/
│   ├── list.html          # 词汇书列表
│   ├── detail.html        # 词汇书详情(单词列表)
│   └── form.html          # 新建/编辑词汇书
├── word/
│   └── form.html          # 新建/编辑单词
├── practice/
│   ├── index.html         # 练习中心
│   ├── do.html            # 练习页面(含JS答题逻辑)
│   └── review.html        # 复习页面
└── import-export/
    └── index.html         # 导入导出页面
```

## 注意事项

- `data.sql` 在应用启动时自动执行，会先 DELETE 所有表再 INSERT 预置数据
- 前端模板使用 Thymeleaf 语法 `th:text`, `th:if`, `th:each`
- Controller 同时处理页面请求和 API 请求（页面返回视图，API 返回 `@ResponseBody` JSON）
