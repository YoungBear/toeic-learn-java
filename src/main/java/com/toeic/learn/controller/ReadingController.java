package com.toeic.learn.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 阅读模块预留接口
 * 未来实现时可继承此类或实现相同接口
 */
@RestController
@RequestMapping("/api/reading")
public class ReadingController {

    /**
     * 获取阅读练习题
     * 预留扩展接口
     */
    @GetMapping("/practice")
    public ResponseEntity<Map<String, Object>> getReadingPractice(
            @RequestParam(required = false) Long vocabularyId,
            @RequestParam(required = false, defaultValue = "5") Integer count) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "reserved");
        response.put("message", "阅读模块正在开发中");
        response.put("estimatedDelivery", "v2.0");
        return ResponseEntity.ok(response);
    }

    /**
     * 提交阅读答案
     * 预留扩展接口
     */
    @PostMapping("/submit")
    public ResponseEntity<Map<String, Object>> submitReadingAnswer(
            @RequestBody(required = false) Object answer) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "reserved");
        response.put("message", "阅读模块正在开发中");
        response.put("estimatedDelivery", "v2.0");
        return ResponseEntity.ok(response);
    }

    /**
     * 获取阅读统计
     * 预留扩展接口
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getReadingStats() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "reserved");
        response.put("message", "阅读模块正在开发中");
        return ResponseEntity.ok(response);
    }
}
