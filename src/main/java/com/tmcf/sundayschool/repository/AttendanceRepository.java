package com.tmcf.sundayschool.repository;

import com.tmcf.sundayschool.entity.Attendance;
import com.tmcf.sundayschool.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    // Find attendance for a student on a specific date
    Optional<Attendance> findByStudentAndAttendanceDate(Student student, LocalDate date);

    // Get all attendance records of a student
    List<Attendance> findByStudent(Student student);

    // Get all attendance records for a date
    List<Attendance> findByAttendanceDate(LocalDate date);
}
