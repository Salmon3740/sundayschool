package com.tmcf.sundayschool.controller;

import com.tmcf.sundayschool.entity.SundayClass;
import com.tmcf.sundayschool.entity.Student;
import com.tmcf.sundayschool.service.SundayClassService;
import com.tmcf.sundayschool.service.StudentService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class SundayClassController {

    private final SundayClassService sundayClassService;
    private final StudentService studentService;

    public SundayClassController(SundayClassService sundayClassService,
            StudentService studentService) {
        this.sundayClassService = sundayClassService;
        this.studentService = studentService;
    }

    // ✅ 1. Create Sunday Class
    @PostMapping
    public ResponseEntity<SundayClass> createClass(@RequestBody SundayClass sundayClass) {
        return ResponseEntity.ok(sundayClassService.createSundayClass(sundayClass));
    }

    // ✅ 2. Get class by ID
    @GetMapping("/{id}")
    public ResponseEntity<SundayClass> getClassById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(sundayClassService.getSundayClassById(id));
    }

    // ✅ 3. Get class by name
    @GetMapping("/name/{className}")
    public ResponseEntity<SundayClass> getClassByName(@PathVariable("className") String className) {
        return ResponseEntity.ok(sundayClassService.getSundayClassByName(className));
    }

    // ✅ 4. Get all classes
    @GetMapping
    public ResponseEntity<List<SundayClass>> getAllClasses() {
        return ResponseEntity.ok(sundayClassService.getAllSundayClasses());
    }

    // ✅ 5. Update class (teacher updates word of the week, lesson name, etc.)
    @PutMapping("/{id}")
    public ResponseEntity<SundayClass> updateClass(
            @PathVariable("id") Long id,
            @RequestBody SundayClass sundayClass) {
        return ResponseEntity.ok(sundayClassService.updateSundayClass(id, sundayClass));
    }

    // ✅ 6. Get all students in a class (when a class card is clicked)
    @GetMapping("/{id}/students")
    public ResponseEntity<List<Student>> getStudentsInClass(@PathVariable("id") Long id) {
        SundayClass sundayClass = sundayClassService.getSundayClassById(id);
        return ResponseEntity.ok(studentService.getStudentsByClass(sundayClass));
    }
}
