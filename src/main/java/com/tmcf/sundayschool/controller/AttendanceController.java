package com.tmcf.sundayschool.controller;

import com.tmcf.sundayschool.entity.Attendance;
import com.tmcf.sundayschool.service.AttendanceService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    // ✅ 1. Mark attendance for a single student
    @PostMapping("/mark")
    public ResponseEntity<Attendance> markAttendance(
            @RequestParam Long studentId,
            @RequestParam String date,
            @RequestParam boolean present) {

        LocalDate localDate = LocalDate.parse(date);
        Attendance attendance = attendanceService.markAttendance(studentId, localDate, present);
        return ResponseEntity.ok(attendance);
    }

    // ✅ 2. Mark attendance for all students of a class (present all / absent all)
    @PostMapping("/mark-class")
    public ResponseEntity<Map<String, String>> markAttendanceForClass(
            @RequestParam Long classId,
            @RequestParam String date,
            @RequestParam boolean present) {

        LocalDate localDate = LocalDate.parse(date);
        attendanceService.markAttendanceForClass(classId, localDate, present);
        return ResponseEntity.ok(Map.of("message",
                "Attendance marked for all students in class " + classId));
    }

    // ✅ 3. Get attendance records for a student
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Attendance>> getAttendanceByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(attendanceService.getAttendanceByStudent(studentId));
    }

    // ✅ 4. Get attendance records by date
    @GetMapping("/date/{date}")
    public ResponseEntity<List<Attendance>> getAttendanceByDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return ResponseEntity.ok(attendanceService.getAttendanceByDate(localDate));
    }

    // ✅ 5. Check if attendance already marked
    @GetMapping("/check")
    public ResponseEntity<Map<String, Boolean>> isAttendanceMarked(
            @RequestParam Long studentId,
            @RequestParam String date) {

        LocalDate localDate = LocalDate.parse(date);
        boolean marked = attendanceService.isAttendanceAlreadyMarked(studentId, localDate);
        return ResponseEntity.ok(Map.of("marked", marked));
    }

    // ✅ 6. Get attendance percentage for a student
    @GetMapping("/percentage/{studentId}")
    public ResponseEntity<Map<String, Double>> getAttendancePercentage(@PathVariable Long studentId) {
        double percentage = attendanceService.calculateAttendancePercentage(studentId);
        return ResponseEntity.ok(Map.of("percentage", percentage));
    }

    // ✅ 7. Get attendance for a whole class on a specific date (for teacher marking
    // view)
    @GetMapping("/class/{classId}/date/{date}")
    public ResponseEntity<List<Attendance>> getAttendanceByClassAndDate(
            @PathVariable Long classId,
            @PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return ResponseEntity.ok(attendanceService.getAttendanceByClassAndDate(classId, localDate));
    }
}
