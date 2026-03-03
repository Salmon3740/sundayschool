package com.tmcf.sundayschool.controller;

import com.tmcf.sundayschool.entity.Test;
import com.tmcf.sundayschool.entity.TestMark;
import com.tmcf.sundayschool.service.TestService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tests")
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    // ✅ 1. Create a new test for a class
    @PostMapping
    public ResponseEntity<Test> createTest(@RequestBody Test test) {
        return ResponseEntity.ok(testService.createTest(test));
    }

    // ✅ 2. Get all tests for a class
    @GetMapping("/class/{classId}")
    public ResponseEntity<List<Test>> getTestsByClass(@PathVariable Long classId) {
        return ResponseEntity.ok(testService.getTestsByClass(classId));
    }

    // ✅ 3. Assign / update marks for a student on a test
    @PostMapping("/marks")
    public ResponseEntity<TestMark> assignMarks(
            @RequestParam Long testId,
            @RequestParam Long studentId,
            @RequestParam Integer marks,
            @RequestParam Boolean present) {

        TestMark testMark = testService.assignMarks(testId, studentId, marks, present);
        return ResponseEntity.ok(testMark);
    }

    // ✅ 4. Get all marks for a test (test-wise results)
    @GetMapping("/marks/test/{testId}")
    public ResponseEntity<List<TestMark>> getMarksByTest(@PathVariable Long testId) {
        return ResponseEntity.ok(testService.getMarksByTest(testId));
    }

    // ✅ 5. Get all marks of a student (student report / total marks)
    @GetMapping("/marks/student/{studentId}")
    public ResponseEntity<List<TestMark>> getMarksByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(testService.getMarksByStudent(studentId));
    }
}
