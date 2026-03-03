package com.tmcf.sundayschool.service.impl;

import com.tmcf.sundayschool.entity.Student;
import com.tmcf.sundayschool.entity.SundayClass;
import com.tmcf.sundayschool.exception.ResourceNotFoundException;
import com.tmcf.sundayschool.repository.StudentRepository;
import com.tmcf.sundayschool.service.StudentService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student getStudentById(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));
    }

    @Override
    public Student getStudentByUsername(String username) {
        return studentRepository.findByUser_Username(username)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "username", username));
    }

    @Override
    public List<Student> getStudentsByClass(SundayClass sundayClass) {
        return studentRepository.findBySundayClass(sundayClass);
    }

    @Override
    public Student updateStudent(Long studentId, Student updatedStudent) {

        Student existingStudent = getStudentById(studentId);

        existingStudent.setFullName(updatedStudent.getFullName());
        existingStudent.setGender(updatedStudent.getGender());
        existingStudent.setAge(updatedStudent.getAge());
        existingStudent.setActive(updatedStudent.getActive());
        existingStudent.setSundayClass(updatedStudent.getSundayClass());

        return studentRepository.save(existingStudent);
    }

    @Override
    public void deleteStudent(Long studentId) {
        Student student = getStudentById(studentId);
        studentRepository.delete(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public List<Student> searchStudents(String name) {
        return studentRepository.findByFullNameContainingIgnoreCase(name);
    }
}
