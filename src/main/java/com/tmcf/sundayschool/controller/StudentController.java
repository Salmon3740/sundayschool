package com.tmcf.sundayschool.controller;

import com.tmcf.sundayschool.dto.request.StudentCreateRequest;
import com.tmcf.sundayschool.dto.response.StudentDetailResponse;
import com.tmcf.sundayschool.entity.Gender;
import com.tmcf.sundayschool.entity.Student;
import com.tmcf.sundayschool.entity.SundayClass;
import com.tmcf.sundayschool.entity.User;
import com.tmcf.sundayschool.service.AttendanceService;
import com.tmcf.sundayschool.service.StudentService;
import com.tmcf.sundayschool.service.SundayClassService;
import com.tmcf.sundayschool.service.TestService;
import com.tmcf.sundayschool.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;
    private final AttendanceService attendanceService;
    private final TestService testService;
    private final SundayClassService sundayClassService;
    private final UserService userService;

    public StudentController(StudentService studentService,
            AttendanceService attendanceService,
            TestService testService,
            SundayClassService sundayClassService,
            UserService userService) {
        this.studentService = studentService;
        this.attendanceService = attendanceService;
        this.testService = testService;
        this.sundayClassService = sundayClassService;
        this.userService = userService;
    }

    // ✅ 1. Create student (teacher only)
    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody StudentCreateRequest request) {

        Student student = new Student();
        student.setFullName(request.getFullName());
        student.setGender(Gender.valueOf(request.getGender().toUpperCase()));
        student.setAge(request.getAge());

        if (request.getUserId() != null) {
            User user = userService.findById(request.getUserId());
            student.setUser(user);
        }

        if (request.getClassId() != null) {
            SundayClass sundayClass = sundayClassService.getSundayClassById(request.getClassId());
            student.setSundayClass(sundayClass);
        }

        return ResponseEntity.ok(studentService.createStudent(student));
    }

    // ✅ 2. Get all students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    // ✅ 3. Get student by ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    // ✅ 4. Get enriched student details (with attendance % and total marks)
    @GetMapping("/{id}/details")
    public ResponseEntity<StudentDetailResponse> getStudentDetails(@PathVariable Long id) {
        return ResponseEntity.ok(buildStudentDetailResponse(studentService.getStudentById(id)));
    }

    // ✅ 5. Get student by username
    @GetMapping("/username/{username}")
    public ResponseEntity<Student> getByUsername(@PathVariable String username) {
        return ResponseEntity.ok(studentService.getStudentByUsername(username));
    }

    // ✅ 6. Search students by name
    @GetMapping("/search")
    public ResponseEntity<List<Student>> searchStudents(@RequestParam String name) {
        return ResponseEntity.ok(studentService.searchStudents(name));
    }

    // ✅ 7. Get students by class (with enriched details)
    @GetMapping("/class/{classId}")
    public ResponseEntity<List<StudentDetailResponse>> getStudentsByClassWithDetails(
            @PathVariable Long classId) {

        SundayClass sundayClass = sundayClassService.getSundayClassById(classId);
        List<Student> students = studentService.getStudentsByClass(sundayClass);

        List<StudentDetailResponse> responses = students.stream()
                .map(this::buildStudentDetailResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ✅ 8. Update student
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @RequestBody Student student) {
        return ResponseEntity.ok(studentService.updateStudent(id, student));
    }

    // ✅ 9. Delete student
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok("Student deleted successfully");
    }

    // ===== Helper =====

    private StudentDetailResponse buildStudentDetailResponse(Student student) {
        double attendancePercentage = attendanceService.calculateAttendancePercentage(student.getStudentId());
        int totalMarks = testService.calculateTotalMarks(student.getStudentId());

        StudentDetailResponse resp = new StudentDetailResponse();
        resp.setStudentId(student.getStudentId());
        resp.setFullName(student.getFullName());
        resp.setGender(student.getGender() != null ? student.getGender().name() : null);
        resp.setAge(student.getAge());
        resp.setClassName(student.getSundayClass() != null
                ? student.getSundayClass().getClassName()
                : null);
        resp.setAttendancePercentage(attendancePercentage);
        resp.setTotalMarks(totalMarks);
        resp.setActive(Boolean.TRUE.equals(student.getActive()));
        resp.setJoinedDate(student.getJoinedDate());
        resp.setUsername(student.getUser() != null
                ? student.getUser().getUsername()
                : null);
        return resp;
    }
}
