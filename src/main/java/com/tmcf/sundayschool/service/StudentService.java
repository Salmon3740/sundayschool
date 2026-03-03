package com.tmcf.sundayschool.service;

import com.tmcf.sundayschool.entity.Student;
import com.tmcf.sundayschool.entity.SundayClass;

import java.util.List;

public interface StudentService {

    // 1️⃣ Create student profile
    Student createStudent(Student student);

    // 2️⃣ Get student by ID
    Student getStudentById(Long studentId);

    // 3️⃣ Get student by username
    Student getStudentByUsername(String username);

    // 4️⃣ Get all students in a class
    List<Student> getStudentsByClass(SundayClass sundayClass);

    // 5️⃣ Update student details
    Student updateStudent(Long studentId, Student updatedStudent);

    // 6️⃣ Delete student
    void deleteStudent(Long studentId);

    // 7️⃣ Get all students
    List<Student> getAllStudents();

    // 8️⃣ Search students by name
    List<Student> searchStudents(String name);
}
