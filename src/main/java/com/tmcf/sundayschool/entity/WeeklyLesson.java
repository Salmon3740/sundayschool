package com.tmcf.sundayschool.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "weekly_lessons")
public class WeeklyLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private Long lessonId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "students", "teacher" })
    private SundayClass sundayClass;

    @Column(name = "week_date", nullable = false)
    private LocalDate weekDate;

    @Column(name = "word_of_week", nullable = false)
    private String wordOfWeek;

    @Column(name = "week_number")
    private Integer weekNumber;

    @Column(name = "lesson_name", nullable = false)
    private String lessonName;

    // ===== Constructors =====

    public WeeklyLesson() {
    }

    // ===== Getters & Setters =====

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public SundayClass getSundayClass() {
        return sundayClass;
    }

    public void setSundayClass(SundayClass sundayClass) {
        this.sundayClass = sundayClass;
    }

    public LocalDate getWeekDate() {
        return weekDate;
    }

    public void setWeekDate(LocalDate weekDate) {
        this.weekDate = weekDate;
    }

    public String getWordOfWeek() {
        return wordOfWeek;
    }

    public void setWordOfWeek(String wordOfWeek) {
        this.wordOfWeek = wordOfWeek;
    }

    public Integer getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(Integer weekNumber) {
        this.weekNumber = weekNumber;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }
}