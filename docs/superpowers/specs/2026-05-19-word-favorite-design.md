# 收藏单词功能设计

## 概述

在练习页面增加收藏单词的功能，用户可以收藏觉得重要或难记的单词，并查看/管理收藏列表。

## 数据模型

### Word 实体变更

在 `Word` 实体增加字段：

```java
@Column(name = "favorited", nullable = false)
private Boolean favorited = false;
```

## 页面改动

### 练习页面 (`/practice/do`)

每道题目增加一个⭐收藏按钮：
- 未收藏时显示空心星标，点击收藏
- 已收藏时显示实心星标，点击取消收藏
- 收藏状态通过 AJAX 实时调用 API 保存

### 收藏页面 (`/practice/favorites`)

新建页面：
- 路径：`src/main/resources/templates/practice/favorites.html`
- 展示所有已收藏的单词列表
- 每行显示：英文、中文、音标、例句
- 每行有取消收藏按钮
- 支持按词汇书筛选

## API 设计

| 接口 | 方法 | 说明 |
|------|------|------|
| `/api/words/{id}/favorite` | POST | 收藏单词 |
| `/api/words/{id}/favorite` | DELETE | 取消收藏 |
| `/api/words/favorites` | GET | 获取收藏列表 |

### Response 格式

```json
{
  "success": true,
  "data": {
    "id": 1,
    "favorited": true
  }
}
```

## Service 层

在 `WordService` 增加方法：

- `toggleFavorite(Long wordId)` - 切换收藏状态
- `getFavorites()` - 获取收藏列表
- `getFavoritesByVocabulary(Long vocabularyId)` - 按词汇书获取收藏

## 路由

| 路径 | Controller 方法 | 说明 |
|------|----------------|------|
| GET `/practice/favorites` | `favorites(Model model)` | 收藏页面 |
| POST `/api/words/{id}/favorite` | `toggleFavorite(@PathVariable Long id)` | 切换收藏 |
| DELETE `/api/words/{id}/favorite` | `toggleFavorite(@PathVariable Long id)` | 取消收藏 |
| GET `/api/words/favorites` | `getFavorites()` | 获取收藏列表 |

## 实现步骤

1. Word 实体增加 `favorited` 字段
2. WordRepository 增加查询方法
3. WordService 增加收藏相关方法
4. WordController 增加 API 端点和页面路由
5. 新建收藏页面 `favorites.html`
6. 练习页面 `do.html` 增加收藏按钮及 JS 逻辑
