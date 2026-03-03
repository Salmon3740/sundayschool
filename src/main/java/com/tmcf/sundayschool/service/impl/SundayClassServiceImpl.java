package com.tmcf.sundayschool.service.impl;

import com.tmcf.sundayschool.entity.SundayClass;
import com.tmcf.sundayschool.exception.ResourceNotFoundException;
import com.tmcf.sundayschool.repository.SundayClassRepository;
import com.tmcf.sundayschool.service.SundayClassService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SundayClassServiceImpl implements SundayClassService {

    private final SundayClassRepository sundayClassRepository;

    public SundayClassServiceImpl(SundayClassRepository sundayClassRepository) {
        this.sundayClassRepository = sundayClassRepository;
    }

    @Override
    public SundayClass createSundayClass(SundayClass sundayClass) {
        return sundayClassRepository.save(sundayClass);
    }

    @Override
    public SundayClass getSundayClassById(Long classId) {
        return sundayClassRepository.findById(classId)
                .orElseThrow(() -> new ResourceNotFoundException("SundayClass", "id", classId));
    }

    @Override
    public SundayClass getSundayClassByName(String className) {
        return sundayClassRepository.findByClassName(className)
                .orElseThrow(() -> new ResourceNotFoundException("SundayClass", "name", className));
    }

    @Override
    public List<SundayClass> getAllSundayClasses() {
        return sundayClassRepository.findAll();
    }

    @Override
    public SundayClass updateSundayClass(Long classId, SundayClass updatedClass) {

        SundayClass existingClass = getSundayClassById(classId);

        existingClass.setClassName(updatedClass.getClassName());
        existingClass.setLessonName(updatedClass.getLessonName());
        existingClass.setWordOfTheWeek(updatedClass.getWordOfTheWeek());
        existingClass.setWeekDate(updatedClass.getWeekDate());
        existingClass.setTeacher(updatedClass.getTeacher());

        return sundayClassRepository.save(existingClass);
    }
}
