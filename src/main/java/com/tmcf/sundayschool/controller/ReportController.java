package com.tmcf.sundayschool.controller;

import com.tmcf.sundayschool.dto.response.StudentDetailResponse;
import com.tmcf.sundayschool.dto.response.StudentReportResponse;
import com.tmcf.sundayschool.entity.Attendance;
import com.tmcf.sundayschool.entity.Student;
import com.tmcf.sundayschool.entity.SundayClass;
import com.tmcf.sundayschool.entity.TestMark;
import com.tmcf.sundayschool.service.AttendanceService;
import com.tmcf.sundayschool.service.StudentService;
import com.tmcf.sundayschool.service.SundayClassService;
import com.tmcf.sundayschool.service.TestService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final StudentService studentService;
    private final AttendanceService attendanceService;
    private final TestService testService;
    private final SundayClassService sundayClassService;

    public ReportController(StudentService studentService,
            AttendanceService attendanceService,
            TestService testService,
            SundayClassService sundayClassService) {
        this.studentService = studentService;
        this.attendanceService = attendanceService;
        this.testService = testService;
        this.sundayClassService = sundayClassService;
    }

    /**
     * Full student report: attendance history, attendance %, test marks, total
     * marks
     */
    @GetMapping("/student/{studentId}")
    public ResponseEntity<StudentReportResponse> getStudentReport(@PathVariable("studentId") Long studentId) {

        Student student = studentService.getStudentById(studentId);

        // Build attendance history
        List<Attendance> attendanceList = attendanceService.getAttendanceByStudent(studentId);
        List<StudentReportResponse.AttendanceRecord> attendanceHistory = attendanceList.stream()
                .map(a -> new StudentReportResponse.AttendanceRecord(
                        a.getAttendanceDate(),
                        Boolean.TRUE.equals(a.getPresent())))
                .collect(Collectors.toList());

        // Build test results
        List<TestMark> testMarks = testService.getMarksByStudent(studentId);
        List<StudentReportResponse.TestMarkRecord> testResults = testMarks.stream()
                .map(tm -> new StudentReportResponse.TestMarkRecord(
                        tm.getTest() != null ? tm.getTest().getTitle() : null,
                        tm.getTest() != null ? tm.getTest().getTestDate() : null,
                        tm.getMarksObtained(),
                        tm.getTest() != null ? tm.getTest().getMaxMarks() : 0,
                        Boolean.TRUE.equals(tm.getPresent())))
                .collect(Collectors.toList());

        // Calculations
        double attendancePercentage = attendanceService.calculateAttendancePercentage(studentId);
        int totalMarks = testService.calculateTotalMarks(studentId);

        // Build response
        StudentReportResponse report = new StudentReportResponse();
        report.setStudentId(student.getStudentId());
        report.setFullName(student.getFullName());
        report.setClassName(student.getSundayClass() != null
                ? student.getSundayClass().getClassName()
                : null);
        report.setAttendancePercentage(attendancePercentage);
        report.setTotalMarks(totalMarks);
        report.setAttendanceHistory(attendanceHistory);
        report.setTestResults(testResults);

        return ResponseEntity.ok(report);
    }

    /**
     * Class-wide report: all students with their attendance % and total marks
     */
    @GetMapping("/class/{classId}")
    public ResponseEntity<List<StudentDetailResponse>> getClassReport(@PathVariable("classId") Long classId) {

        SundayClass sundayClass = sundayClassService.getSundayClassById(classId);
        List<Student> students = studentService.getStudentsByClass(sundayClass);

        List<StudentDetailResponse> report = students.stream()
                .map(student -> {
                    double attendancePercentage = attendanceService
                            .calculateAttendancePercentage(student.getStudentId());
                    int totalMarks = testService.calculateTotalMarks(student.getStudentId());

                    StudentDetailResponse resp = new StudentDetailResponse();
                    resp.setStudentId(student.getStudentId());
                    resp.setFullName(student.getFullName());
                    resp.setGender(student.getGender() != null ? student.getGender().name() : null);
                    resp.setAge(student.getAge());
                    resp.setClassName(sundayClass.getClassName());
                    resp.setAttendancePercentage(attendancePercentage);
                    resp.setTotalMarks(totalMarks);
                    resp.setActive(Boolean.TRUE.equals(student.getActive()));
                    resp.setJoinedDate(student.getJoinedDate());
                    resp.setUsername(student.getUser() != null
                            ? student.getUser().getUsername()
                            : null);
                    return resp;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(report);
    }
}
