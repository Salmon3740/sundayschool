package com.tmcf.sundayschool.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tmcf.sundayschool.entity.SundayClass;
import com.tmcf.sundayschool.entity.WeeklyLesson;

@Repository
public interface WeeklyLessonRepository extends JpaRepository<WeeklyLesson, Long> {

    List<WeeklyLesson> findBySundayClass(SundayClass sundayClass);

    // Get the latest (most recent) lesson for a class
    Optional<WeeklyLesson> findTopBySundayClassOrderByWeekDateDesc(SundayClass sundayClass);
}
