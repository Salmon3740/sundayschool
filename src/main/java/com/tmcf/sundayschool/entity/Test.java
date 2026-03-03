package com.tmcf.sundayschool.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tests")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_id")
    private Long testId;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "students", "teacher" })
    private SundayClass sundayClass;

    @Column(name = "max_marks", nullable = false)
    private Integer maxMarks;

    @Column(name = "test_date", nullable = false)
    private LocalDate testDate;

    @Column(nullable = false)
    private Boolean active = true;

    /* ---------- Constructors ---------- */

    public Test() {
    }

    /* ---------- Getters & Setters ---------- */

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SundayClass getSundayClass() {
        return sundayClass;
    }

    public void setSundayClass(SundayClass sundayClass) {
        this.sundayClass = sundayClass;
    }

    public Integer getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(Integer maxMarks) {
        this.maxMarks = maxMarks;
    }

    public LocalDate getTestDate() {
        return testDate;
    }

    public void setTestDate(LocalDate testDate) {
        this.testDate = testDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
