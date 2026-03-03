package com.tmcf.sundayschool.controller;

import com.tmcf.sundayschool.entity.WeeklyLesson;
import com.tmcf.sundayschool.service.WeeklyLessonService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lessons")
public class WeeklyLessonController {

    private final WeeklyLessonService weeklyLessonService;

    public WeeklyLessonController(WeeklyLessonService weeklyLessonService) {
        this.weeklyLessonService = weeklyLessonService;
    }

    // ✅ 1. Add a weekly lesson
    @PostMapping
    public ResponseEntity<WeeklyLesson> addLesson(@RequestBody WeeklyLesson lesson) {
        return ResponseEntity.ok(weeklyLessonService.addLesson(lesson));
    }

    // ✅ 2. Get lesson by ID
    @GetMapping("/{id}")
    public ResponseEntity<WeeklyLesson> getLessonById(@PathVariable Long id) {
        return ResponseEntity.ok(weeklyLessonService.getLessonById(id));
    }

    // ✅ 3. Get all lessons for a class
    @GetMapping("/class/{classId}")
    public ResponseEntity<List<WeeklyLesson>> getLessonsByClass(@PathVariable Long classId) {
        return ResponseEntity.ok(weeklyLessonService.getLessonsByClass(classId));
    }

    // ✅ 4. Update a lesson
    @PutMapping("/{id}")
    public ResponseEntity<WeeklyLesson> updateLesson(
            @PathVariable Long id,
            @RequestBody WeeklyLesson lesson) {
        return ResponseEntity.ok(weeklyLessonService.updateLesson(id, lesson));
    }

    // ✅ 5. Delete a lesson
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteLesson(@PathVariable Long id) {
        weeklyLessonService.deleteLesson(id);
        return ResponseEntity.ok(Map.of("message", "Lesson deleted successfully"));
    }

    // ✅ 6. Get the latest (current week) lesson for a class
    @GetMapping("/class/{classId}/latest")
    public ResponseEntity<?> getLatestLesson(@PathVariable Long classId) {
        return weeklyLessonService.getLatestLessonByClass(classId)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }
}
