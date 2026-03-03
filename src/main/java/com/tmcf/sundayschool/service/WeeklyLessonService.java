package com.tmcf.sundayschool.service;

import com.tmcf.sundayschool.entity.WeeklyLesson;

import java.util.List;
import java.util.Optional;

public interface WeeklyLessonService {

    // 1️⃣ Add a weekly lesson
    WeeklyLesson addLesson(WeeklyLesson lesson);

    // 2️⃣ Get lesson by ID
    WeeklyLesson getLessonById(Long lessonId);

    // 3️⃣ Get all lessons for a class
    List<WeeklyLesson> getLessonsByClass(Long classId);

    // 4️⃣ Update a lesson
    WeeklyLesson updateLesson(Long lessonId, WeeklyLesson lesson);

    // 5️⃣ Delete a lesson
    void deleteLesson(Long lessonId);

    // 6️⃣ Get the most recent lesson for a class (current week)
    Optional<WeeklyLesson> getLatestLessonByClass(Long classId);
}
