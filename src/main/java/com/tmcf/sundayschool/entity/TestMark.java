package com.tmcf.sundayschool.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "test_marks", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "test_id", "student_id" })
})
public class TestMark {

    /* ---------- PRIMARY KEY ---------- */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mark_id")
    private Long markId;

    /* ---------- RELATIONSHIPS ---------- */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id", nullable = false)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "sundayClass" })
    private Test test;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "sundayClass", "user" })
    private Student student;

    /* ---------- OTHER COLUMNS ---------- */

    @Column(name = "marks_obtained", nullable = false)
    private Integer marksObtained = 0;

    @Column(nullable = false)
    private Boolean present = true;

    /* ---------- CONSTRUCTORS ---------- */

    public TestMark() {
    }

    /* ---------- GETTERS & SETTERS ---------- */

    public Long getMarkId() {
        return markId;
    }

    public void setMarkId(Long markId) {
        this.markId = markId;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Integer getMarksObtained() {
        return marksObtained;
    }

    public void setMarksObtained(Integer marksObtained) {
        this.marksObtained = marksObtained;
    }

    public Boolean getPresent() {
        return present;
    }

    public void setPresent(Boolean present) {
        this.present = present;
    }
}
