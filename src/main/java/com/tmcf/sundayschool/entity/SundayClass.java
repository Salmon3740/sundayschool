package com.tmcf.sundayschool.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "sunday_classes")
public class SundayClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private Long classId;

    @Column(name = "class_name", nullable = false, unique = true)
    private String className;

    @Column(name = "lesson_name", nullable = false)
    private String lessonName;

    @Column(name = "word_of_the_week")
    private String wordOfTheWeek;

    @Column(name = "week_date", nullable = false)
    private LocalDate weekDate;

    /* ---------- Relationships ---------- */

    // Teacher who handles this class
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = true)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "password" })
    private User teacher;

    // Students in this class
    @OneToMany(mappedBy = "sundayClass", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({ "sundayClass", "user" })
    private List<Student> students;

    /* ---------- Constructors ---------- */

    public SundayClass() {
    }

    /* ---------- Getters & Setters ---------- */

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getWordOfTheWeek() {
        return wordOfTheWeek;
    }

    public void setWordOfTheWeek(String wordOfTheWeek) {
        this.wordOfTheWeek = wordOfTheWeek;
    }

    public LocalDate getWeekDate() {
        return weekDate;
    }

    public void setWeekDate(LocalDate weekDate) {
        this.weekDate = weekDate;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
