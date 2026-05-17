package com.toeic.learn.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.util.HashMap;
import java.util.Map;

/**
 * 听力模块预留接口
 * 未来实现时可继承此类或实现相同接口
 */
@RestController
@RequestMapping("/api/listening")
public class ListeningController {

    /**
     * 获取听力练习题
     * 预留扩展接口
     */
    @GetMapping("/practice")
    public ResponseEntity<Map<String, Object>> getListeningPractice(
            @RequestParam(required = false) Long vocabularyId,
            @RequestParam(required = false, defaultValue = "10") Integer count) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "reserved");
        response.put("message", "听力模块正在开发中");
        response.put("estimatedDelivery", "v2.0");
        return ResponseEntity.ok(response);
    }

    /**
     * 提交听力答案
     * 预留扩展接口
     */
    @PostMapping("/submit")
    public ResponseEntity<Map<String, Object>> submitListeningAnswer(
            @RequestBody(required = false) Object answer) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "reserved");
        response.put("message", "听力模块正在开发中");
        response.put("estimatedDelivery", "v2.0");
        return ResponseEntity.ok(response);
    }

    /**
     * 获取听力统计
     * 预留扩展接口
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getListeningStats() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "reserved");
        response.put("message", "听力模块正在开发中");
        return ResponseEntity.ok(response);
    }
}
