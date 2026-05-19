# 托业学习系统 (toeic-learn)

一款面向托业(TOEIC)考试的英语学习系统，提供词汇记忆与练习功能。

## 技术栈

- **后端**: Java 21 + Spring Boot 3.2.5
- **前端**: Thymeleaf (前后端不分离)
- **数据库**: H2 (文件持久化)
- **Excel处理**: Apache POI 5.2.5
- **构建工具**: Maven

## 功能特性

### 已完成
- [x] 词汇书管理 (CRUD)
- [x] 单词管理 (CRUD + 批量添加)
- [x] 词汇选择题练习
- [x] 学习进度追踪 (艾宾浩斯遗忘曲线)
- [x] 练习统计 (正确率、已掌握词汇数等)
- [x] Excel 导入导出
- [x] JSON 导入导出
- [x] 搜索条件过滤导出
- [x] 预置约90个托业核心词汇
- [x] 单词收藏功能
- [x] 错题本功能

### 预留接口
- [ ] 听力模块 (计划 v2.0)
- [ ] 阅读模块 (计划 v2.0)

## 快速开始

### 1. 编译项目

```bash
mvn clean package -DskipTests
```

### 2. 运行项目

```bash
mvn spring-boot:run
```

或

```bash
java -jar target/toeic-learn-1.0.0.jar
```

### 3. 访问系统

- 系统地址: http://localhost:8080
- H2 控制台: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:file:./data/toeic-learn`
  - 用户名: `sa`
  - 密码: (空)

## 项目结构

```
src/main/java/com/toeic/learn/
├── ToeicLearnApplication.java    # 应用入口
├── config/                        # 配置
├── controller/                    # 控制器
│   ├── VocabularyController.java # 词汇书管理
│   ├── WordController.java       # 单词管理
│   ├── PracticeController.java   # 练习功能
│   ├── ImportExportController.java # 导入导出
│   ├── ListeningController.java  # 预留-听力
│   └── ReadingController.java    # 预留-阅读
├── service/                       # 业务逻辑
├── repository/                   # 数据访问
├── entity/                       # 实体类
├── dto/                          # 数据传输对象
└── exception/                    # 异常处理
```

## API 接口

### 词汇书管理
- `GET /api/vocabularies` - 获取词汇书列表
- `POST /api/vocabularies` - 创建词汇书
- `PUT /api/vocabularies/{id}` - 更新词汇书
- `DELETE /api/vocabularies/{id}` - 删除词汇书

### 单词管理
- `GET /api/words/vocabulary/{vocabId}` - 获取单词列表
- `POST /api/words` - 添加单词
- `POST /api/words/batch` - 批量添加
- `PUT /api/words/{id}` - 更新单词
- `DELETE /api/words/{id}` - 删除单词

### 练习
- `GET /api/practice/questions?count=20&vocabularyId=1` - 获取练习题
- `POST /api/practice/submit` - 提交答案
- `GET /api/practice/stats` - 获取统计

### 错题本
- `GET /practice/mistakes` - 错题本页面
- `GET /api/practice/mistakes` - 获取错题列表
- `GET /api/practice/mistakes/questions` - 生成错题练习题目

### 导入导出
- `POST /api/import/excel` - Excel导入
- `POST /api/import/json` - JSON导入
- `GET /api/export/excel?vocabularyId=1&keyword=xxx` - Excel导出
- `GET /api/export/json?vocabularyId=1&keyword=xxx` - JSON导出
- `GET /api/export/template` - 下载导入模板

## 配置说明

配置文件: `src/main/resources/application.yml`

```yaml
app:
  practice:
    default-question-count: 20    # 默认练习题数
    option-count: 4              # 选择题选项数
```

## 数据说明

- 数据库文件位置: `./data/toeic-learn.mv.db`
- 预置数据包含13个词汇书，约1100+个核心词汇
- 初始数据在 `src/main/resources/data.sql`

## 扩展预留

### 添加听力/阅读模块

1. 在 `ListeningController.java` 或 `ReadingController.java` 中实现具体逻辑
2. 创建对应的 Service 类
3. 添加前端页面

预留接口已定义，可直接继承使用。

## License

MIT
