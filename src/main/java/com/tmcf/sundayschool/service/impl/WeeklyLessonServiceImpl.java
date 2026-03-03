package com.tmcf.sundayschool.service.impl;

import com.tmcf.sundayschool.entity.SundayClass;
import com.tmcf.sundayschool.entity.WeeklyLesson;
import com.tmcf.sundayschool.exception.ResourceNotFoundException;
import com.tmcf.sundayschool.repository.SundayClassRepository;
import com.tmcf.sundayschool.repository.WeeklyLessonRepository;
import com.tmcf.sundayschool.service.WeeklyLessonService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WeeklyLessonServiceImpl implements WeeklyLessonService {

    private final WeeklyLessonRepository weeklyLessonRepository;
    private final SundayClassRepository sundayClassRepository;

    public WeeklyLessonServiceImpl(
            WeeklyLessonRepository weeklyLessonRepository,
            SundayClassRepository sundayClassRepository) {
        this.weeklyLessonRepository = weeklyLessonRepository;
        this.sundayClassRepository = sundayClassRepository;
    }

    @Override
    public WeeklyLesson addLesson(WeeklyLesson lesson) {
        return weeklyLessonRepository.save(lesson);
    }

    @Override
    public WeeklyLesson getLessonById(Long lessonId) {
        return weeklyLessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("WeeklyLesson", "id", lessonId));
    }

    @Override
    public List<WeeklyLesson> getLessonsByClass(Long classId) {
        SundayClass sundayClass = sundayClassRepository.findById(classId)
                .orElseThrow(() -> new ResourceNotFoundException("SundayClass", "id", classId));

        return weeklyLessonRepository.findBySundayClass(sundayClass);
    }

    @Override
    public WeeklyLesson updateLesson(Long lessonId, WeeklyLesson updatedLesson) {
        WeeklyLesson existing = getLessonById(lessonId);

        existing.setLessonName(updatedLesson.getLessonName());
        existing.setWordOfWeek(updatedLesson.getWordOfWeek());
        existing.setWeekDate(updatedLesson.getWeekDate());
        existing.setWeekNumber(updatedLesson.getWeekNumber());
        existing.setSundayClass(updatedLesson.getSundayClass());

        return weeklyLessonRepository.save(existing);
    }

    @Override
    public void deleteLesson(Long lessonId) {
        WeeklyLesson lesson = getLessonById(lessonId);
        weeklyLessonRepository.delete(lesson);
    }

    @Override
    public java.util.Optional<WeeklyLesson> getLatestLessonByClass(Long classId) {
        SundayClass sundayClass = sundayClassRepository.findById(classId)
                .orElseThrow(() -> new ResourceNotFoundException("SundayClass", "id", classId));
        return weeklyLessonRepository.findTopBySundayClassOrderByWeekDateDesc(sundayClass);
    }
}
