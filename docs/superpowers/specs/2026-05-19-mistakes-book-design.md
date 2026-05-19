# 错题本功能设计

## 概述

为托业学习系统新增"错题本"功能，允许用户查看练习中答错的单词，并支持重新练习这些错题。

## 需求确认

- **查看范围**: 所有历史记录中曾经答错的单词
- **页面入口**: 独立页面，像收藏一样单独一个入口
- **显示内容**: 完整版（英文、中文释义、用户错选的答案、正确答案、例句）
- **练习功能**: 可以重新练习错题，像正常练习一样
- **重复错题处理**: 合并显示，按错误次数排序
- **筛选功能**: 可按词汇书筛选

## 数据模型变更

### 修改 PracticeRecord 实体

```java
@Entity
@Table(name = "practice_record")
public class PracticeRecord {
    // ... 现有字段 ...

    // 新增：用户选择的答案
    @Column(name = "selected_answer", length = 500)
    private String selectedAnswer;
}
```

### SQL 变更

```sql
ALTER TABLE practice_record ADD COLUMN selected_answer VARCHAR(500);
```

## 新增页面

### 路径

`/practice/mistakes` → `templates/practice/mistakes.html`

### 功能

1. 显示错题列表
   - 按错误次数降序排序
   - 显示：单词(英文)、中文释义、用户错选的答案、正确答案、错误次数、错题日期
   - 支持按词汇书筛选

2. 练习错题
   - 点击"练习错题"按钮，随机选取错题进入练习模式
   - 练习结束后，同样记录答案，更新错误次数

## API 接口

| 接口 | 方法 | 说明 |
|------|------|------|
| `/practice/api/mistakes` | GET | 获取错题列表，支持 `?vocabularyId=` 筛选 |
| `/practice/api/mistakes/questions` | GET | 生成错题练习题目 |

### GET /practice/api/mistakes

**Response:**
```json
{
  "success": true,
  "data": [
    {
      "wordId": 1,
      "english": "negotiate",
      "chinese": "谈判",
      "phonetic": "/nɪˈɡəʊʃieɪt/",
      "exampleSentence": "We need to negotiate a better price.",
      "wrongAnswer": "contract",
      "correctAnswer": "negotiate",
      "errorCount": 3,
      "lastErrorAt": "2026-05-19T10:30:00"
    }
  ],
  "error": null
}
```

### GET /practice/api/mistakes/questions

**Query params:**
- `count`: 题目数量（默认20）

**Response:** 同 `/practice/api/questions`

## 组件清单

| 组件 | 类型 | 说明 |
|------|------|------|
| `PracticeController` | Controller | 新增 `/mistakes` 页面和 API |
| `PracticeService` | Service | 新增 `getMistakes()`, `generateMistakeQuestions()` |
| `PracticeRecordRepository` | Repository | 新增查询方法 |
| `MistakeDTO` | DTO | 错题数据传输对象 |
| `practice/mistakes.html` | Template | 错题本页面 |

## 实现步骤

1. 修改 `PracticeRecord` 实体，添加 `selectedAnswer` 字段
2. 创建 SQL 迁移脚本，添加新字段
3. 修改 `PracticeService.submitAnswer()`，保存用户选择的答案
4. 新增 `PracticeRecordRepository` 查询方法
5. 新增 `MistakeDTO`
6. 新增 `PracticeService` 错题相关方法
7. 新增 Controller 端点和页面
8. 更新前端导航（添加错题本入口）
9. 更新 `data-extra.sql` 添加测试数据（如需要）
10. 更新文档

## 注意事项

- 答对时也保存 `selectedAnswer`（填空题型需要）
- 错题练习结束后，同样创建 `PracticeRecord`，错误次数累加
- 使用 `COUNT()` 和 `GROUP BY` 按单词聚合错误次数
