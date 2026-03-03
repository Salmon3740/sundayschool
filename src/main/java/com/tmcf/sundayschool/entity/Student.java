package com.tmcf.sundayschool.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "students", uniqueConstraints = {
        @UniqueConstraint(columnNames = "user_id")
})
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;

    /* ---------- Relationships ---------- */

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "password" })
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "students", "teacher" })
    private SundayClass sundayClass;

    /* ---------- Fields ---------- */

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column
    private Integer age;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(name = "joined_date", updatable = false)
    private LocalDate joinedDate;

    /* ---------- Lifecycle ---------- */

    @PrePersist
    protected void onCreate() {
        this.joinedDate = LocalDate.now();
    }

    /* ---------- Constructors ---------- */

    public Student() {
    }

    /* ---------- Getters & Setters ---------- */

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SundayClass getSundayClass() {
        return sundayClass;
    }

    public void setSundayClass(SundayClass sundayClass) {
        this.sundayClass = sundayClass;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDate getJoinedDate() {
        return joinedDate;
    }
}
