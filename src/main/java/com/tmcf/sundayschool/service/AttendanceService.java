package com.tmcf.sundayschool.service;

import com.tmcf.sundayschool.entity.Attendance;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {

    // 1️⃣ Mark attendance for a student
    Attendance markAttendance(Long studentId, LocalDate date, boolean present);

    // 2️⃣ Mark attendance for all students of a class
    void markAttendanceForClass(Long classId, LocalDate date, boolean present);

    // 3️⃣ Get attendance by student
    List<Attendance> getAttendanceByStudent(Long studentId);

    // 4️⃣ Get attendance by date
    List<Attendance> getAttendanceByDate(LocalDate date);

    // 5️⃣ Check if attendance already marked
    boolean isAttendanceAlreadyMarked(Long studentId, LocalDate date);

    // 6️⃣ Calculate attendance percentage
    double calculateAttendancePercentage(Long studentId);

    // 7️⃣ Get attendance for a whole class on a specific date
    List<Attendance> getAttendanceByClassAndDate(Long classId, LocalDate date);
}
