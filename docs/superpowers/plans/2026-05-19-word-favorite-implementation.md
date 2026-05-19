# 收藏单词功能实现计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 在练习页面增加收藏单词功能，用户可收藏/取消收藏单词，并在独立页面查看收藏列表。

**Architecture:** 在 Word 实体增加 `favorited` 字段，WordService 增加收藏相关方法，WordController 增加 API 端点，新建收藏页面，练习页面增加收藏按钮。

**Tech Stack:** Spring Boot, Thymeleaf, JPA, H2 Database

---

## 文件结构

```
src/main/java/com/toeic/learn/
├── entity/Word.java              # 增加 favorited 字段
├── repository/WordRepository.java # 增加 findFavorites, findFavoritesByVocabularyId 方法
├── service/WordService.java       # 增加 toggleFavorite, getFavorites, isFavorited 方法
├── controller/WordController.java # 增加收藏相关 API 和页面路由

src/main/resources/templates/
├── practice/favorites.html       # 新建：收藏页面
├── practice/do.html              # 修改：增加收藏按钮
```

---

### Task 1: Word 实体增加 favorited 字段

**Files:**
- Modify: `src/main/java/com/toeic/learn/entity/Word.java:44-45`

- [ ] **Step 1: 增加 favorited 字段**

在 `Word.java` 的 `difficulty` 字段后增加：

```java
@Column(name = "favorited", nullable = false)
private Boolean favorited = false;
```

- [ ] **Step 2: 提交**

```bash
git add src/main/java/com/toeic/learn/entity/Word.java
git commit -m "feat: add favorited field to Word entity"
```

---

### Task 2: WordRepository 增加收藏查询方法

**Files:**
- Modify: `src/main/java/com/toeic/learn/repository/WordRepository.java:28`

- [ ] **Step 1: 增加查询方法**

在 `WordRepository.java` 的 `countByVocabularyId` 方法后增加：

```java
@Query("SELECT w FROM Word w WHERE w.favorited = true ORDER BY w.createdAt DESC")
List<Word> findFavorites();

@Query("SELECT w FROM Word w WHERE w.vocabulary.id = :vocabId AND w.favorited = true ORDER BY w.createdAt DESC")
List<Word> findFavoritesByVocabularyId(@Param("vocabId") Long vocabId);
```

- [ ] **Step 2: 提交**

```bash
git add src/main/java/com/toeic/learn/repository/WordRepository.java
git commit -m "feat: add findFavorites methods to WordRepository"
```

---

### Task 3: WordService 增加收藏方法

**Files:**
- Modify: `src/main/java/com/toeic/learn/service/WordService.java`

- [ ] **Step 1: 增加收藏相关方法**

在 `WordService.java` 的 `search` 方法后增加：

```java
@Transactional
public boolean toggleFavorite(Long id) {
    Word word = wordRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("单词不存在: " + id));
    word.setFavorited(!word.getFavorited());
    wordRepository.save(word);
    return word.getFavorited();
}

public boolean isFavorited(Long id) {
    Word word = wordRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("单词不存在: " + id));
    return word.getFavorited();
}

public List<WordDTO> getFavorites() {
    return wordRepository.findFavorites().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
}

public List<WordDTO> getFavoritesByVocabulary(Long vocabularyId) {
    return wordRepository.findFavoritesByVocabularyId(vocabularyId).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
}
```

- [ ] **Step 2: toDTO 方法增加 favorited 字段**

修改 `toDTO` 方法，在 `dto.setDifficulty(word.getDifficulty())` 后增加：

```java
dto.setFavorited(word.getFavorited());
```

同时在 `WordDTO` 类中需要增加 `favorited` 字段（如果没有的话）。

- [ ] **Step 3: 提交**

```bash
git add src/main/java/com/toeic/learn/service/WordService.java
git commit -m "feat: add favorite methods to WordService"
```

---

### Task 4: WordDTO 增加 favorited 字段

**Files:**
- Modify: `src/main/java/com/toeic/learn/dto/WordDTO.java`

- [ ] **Step 1: 增加 favorited 字段**

在 `WordDTO.java` 中增加：

```java
private Boolean favorited = false;
```

并增加 getter/setter：

```java
public Boolean getFavorited() {
    return favorited;
}

public void setFavorited(Boolean favorited) {
    this.favorited = favorited;
}
```

- [ ] **Step 2: 提交**

```bash
git add src/main/java/com/toeic/learn/dto/WordDTO.java
git commit -m "feat: add favorited field to WordDTO"
```

---

### Task 5: WordController 增加收藏 API 和页面路由

**Files:**
- Modify: `src/main/java/com/toeic/learn/controller/WordController.java`

- [ ] **Step 1: 增加收藏 API 端点**

在 `WordController.java` 的 `@GetMapping("/api/search")` 后增加：

```java
@PostMapping("/api/{id}/favorite")
@ResponseBody
public ResponseEntity<ApiResponse<Boolean>> toggleFavorite(@PathVariable Long id) {
    boolean favorited = wordService.toggleFavorite(id);
    return ResponseEntity.ok(ApiResponse.success(favorited));
}

@DeleteMapping("/api/{id}/favorite")
@ResponseBody
public ResponseEntity<ApiResponse<Boolean>> unfavorite(@PathVariable Long id) {
    boolean favorited = wordService.toggleFavorite(id);
    return ResponseEntity.ok(ApiResponse.success(favorited));
}

@GetMapping("/api/favorites")
@ResponseBody
public ResponseEntity<ApiResponse<List<WordDTO>>> getFavorites(
        @RequestParam(required = false) Long vocabularyId) {
    List<WordDTO> favorites;
    if (vocabularyId != null) {
        favorites = wordService.getFavoritesByVocabulary(vocabularyId);
    } else {
        favorites = wordService.getFavorites();
    }
    return ResponseEntity.ok(ApiResponse.success(favorites));
}

@GetMapping("/api/{id}/favorite/status")
@ResponseBody
public ResponseEntity<ApiResponse<Boolean>> isFavorited(@PathVariable Long id) {
    return ResponseEntity.ok(ApiResponse.success(wordService.isFavorited(id)));
}
```

- [ ] **Step 2: 增加收藏页面路由**

在 `@GetMapping("/new")` 前增加：

```java
@GetMapping("/favorites")
public String showFavoritesPage(Model model) {
    model.addAttribute("favorites", wordService.getFavorites());
    return "practice/favorites";
}
```

- [ ] **Step 3: 提交**

```bash
git add src/main/java/com/toeic/learn/controller/WordController.java
git commit -m "feat: add favorite API endpoints and page route"
```

---

### Task 6: 新建收藏页面 favorites.html

**Files:**
- Create: `src/main/resources/templates/practice/favorites.html`

- [ ] **Step 1: 创建收藏页面**

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>收藏单词 - 托业学习系统</title>
    <link rel="stylesheet" th:href="@{/css/style.css}">
</head>
<body>
    <div class="container">
        <div class="page-header">
            <h1>我的收藏</h1>
            <a th:href="@{/practice}" class="btn btn-outline">返回练习中心</a>
        </div>

        <div th:if="${#lists.isEmpty(favorites)}" class="empty-state">
            <h2>暂无收藏</h2>
            <p>在练习时点击星标收藏单词</p>
            <a th:href="@{/practice}" class="btn btn-primary">去练习</a>
        </div>

        <div th:if="${not #lists.isEmpty(favorites)}" class="word-list">
            <table class="data-table">
                <thead>
                    <tr>
                        <th>收藏</th>
                        <th>英文</th>
                        <th>中文</th>
                        <th>音标</th>
                        <th>例句</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <tr th:each="word : ${favorites}" th:data-id="${word.id}">
                        <td>
                            <button class="favorite-btn favorited" th:data-id="${word.id}" onclick="toggleFavorite(this)">
                                <svg viewBox="0 0 24 24" fill="currentColor" stroke="currentColor" stroke-width="2" width="20" height="20">
                                    <polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2"></polygon>
                                </svg>
                            </button>
                        </td>
                        <td><strong th:text="${word.english}">-</strong></td>
                        <td th:text="${word.chinese}">-</td>
                        <td class="text-muted" th:text="${word.phonetic}">-</td>
                        <td class="text-muted" th:text="${word.exampleSentence}">-</td>
                        <td>
                            <button class="btn btn-sm btn-outline" onclick="unfavoriteAndReload([[${word.id}]])">取消收藏</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>

    <script th:inline="javascript">
        function toggleFavorite(btn) {
            const wordId = btn.getAttribute('data-id');
            fetch('/word/api/' + wordId + '/favorite', {
                method: 'POST'
            })
            .then(res => res.json())
            .then(data => {
                if (data.success) {
                    // 移除收藏行
                    btn.closest('tr').remove();
                    // 检查是否为空
                    const tbody = document.querySelector('tbody');
                    if (tbody.children.length === 0) {
                        location.reload();
                    }
                }
            });
        }

        function unfavoriteAndReload(wordId) {
            if (confirm('确定要取消收藏吗？')) {
                fetch('/word/api/' + wordId + '/favorite', {
                    method: 'DELETE'
                })
                .then(res => res.json())
                .then(data => {
                    if (data.success) {
                        location.reload();
                    }
                });
            }
        }
    </script>
</body>
</html>
```

- [ ] **Step 2: 提交**

```bash
git add src/main/resources/templates/practice/favorites.html
git commit -m "feat: add favorites page template"
```

---

### Task 7: 练习页面 do.html 增加收藏按钮

**Files:**
- Modify: `src/main/resources/templates/practice/do.html`

- [ ] **Step 1: 在题目卡片增加收藏按钮**

在 `questionCard` 的 `question-word` div 后增加：

```html
<button id="favoriteBtn" class="favorite-btn" onclick="toggleFavorite()" style="background:none;border:none;cursor:pointer;position:absolute;top:10px;right:10px;">
    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" width="24" height="24">
        <polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2"></polygon>
    </svg>
</button>
```

给 `question-card` div 增加 `style="position:relative;"` 以支持绝对定位。

- [ ] **Step 2: 增加收藏状态查询和切换 JS**

在 `<script>` 的 `questions` 定义后增加：

```javascript
let currentWordId = null;

function updateFavoriteStatus(wordId) {
    currentWordId = wordId;
    fetch('/word/api/' + wordId + '/favorite/status')
        .then(res => res.json())
        .then(data => {
            updateFavoriteIcon(data.data);
        });
}

function updateFavoriteIcon(isFavorited) {
    const btn = document.getElementById('favoriteBtn');
    if (isFavorited) {
        btn.querySelector('svg').setAttribute('fill', 'currentColor');
    } else {
        btn.querySelector('svg').setAttribute('fill', 'none');
    }
}

function toggleFavorite() {
    if (!currentWordId) return;
    fetch('/word/api/' + currentWordId + '/favorite', {
        method: 'POST'
    })
    .then(res => res.json())
    .then(data => {
        updateFavoriteIcon(data.data);
    });
}
```

- [ ] **Step 3: 在 showQuestion 函数中调用 updateFavoriteStatus**

在 `showQuestion` 函数中，`document.getElementById('questionWord').textContent = q.word.english;` 后增加：

```javascript
updateFavoriteStatus(q.word.id);
```

- [ ] **Step 4: 提交**

```bash
git add src/main/resources/templates/practice/do.html
git commit -m "feat: add favorite button to practice page"
```

---

### Task 8: 整体测试

- [ ] **Step 1: 编译项目**

```bash
mvn clean compile
```

- [ ] **Step 2: 启动应用**

```bash
mvn spring-boot:run
```

- [ ] **Step 3: 测试收藏功能**

1. 访问 http://localhost:8080/practice/do?count=5
2. 检查星标按钮是否显示
3. 点击星标，检查是否高亮
4. 访问 http://localhost:8080/word/favorites 检查收藏列表
5. 点击取消收藏，检查是否从列表移除

---

## 自检清单

- [ ] 所有 API 端点已实现并测试
- [ ] 收藏页面可正常显示和操作
- [ ] 练习页面收藏按钮正常工作
- [ ] 取消收藏后列表正确更新
