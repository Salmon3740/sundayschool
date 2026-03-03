package com.tmcf.sundayschool.service.impl;

import com.tmcf.sundayschool.entity.Attendance;
import com.tmcf.sundayschool.entity.Student;
import com.tmcf.sundayschool.entity.SundayClass;
import com.tmcf.sundayschool.exception.ResourceNotFoundException;
import com.tmcf.sundayschool.repository.AttendanceRepository;
import com.tmcf.sundayschool.repository.StudentRepository;
import com.tmcf.sundayschool.repository.SundayClassRepository;
import com.tmcf.sundayschool.service.AttendanceService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final SundayClassRepository sundayClassRepository;

    public AttendanceServiceImpl(
            AttendanceRepository attendanceRepository,
            StudentRepository studentRepository,
            SundayClassRepository sundayClassRepository) {
        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
        this.sundayClassRepository = sundayClassRepository;
    }

    @Override
    public Attendance markAttendance(Long studentId, LocalDate date, boolean present) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));

        Optional<Attendance> existing = attendanceRepository.findByStudentAndAttendanceDate(student, date);
        if (existing.isPresent()) {
            Attendance att = existing.get();
            att.setPresent(present);
            return attendanceRepository.save(att);
        }

        Attendance attendance = new Attendance();
        attendance.setStudent(student);
        attendance.setAttendanceDate(date);
        attendance.setPresent(present);

        return attendanceRepository.save(attendance);
    }

    @Override
    public void markAttendanceForClass(Long classId, LocalDate date, boolean present) {

        SundayClass sundayClass = sundayClassRepository.findById(classId)
                .orElseThrow(() -> new ResourceNotFoundException("SundayClass", "id", classId));

        List<Student> students = studentRepository.findBySundayClass(sundayClass);

        for (Student student : students) {
            markAttendance(student.getStudentId(), date, present);
        }
    }

    @Override
    public List<Attendance> getAttendanceByStudent(Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));

        return attendanceRepository.findByStudent(student);
    }

    @Override
    public List<Attendance> getAttendanceByDate(LocalDate date) {
        return attendanceRepository.findByAttendanceDate(date);
    }

    @Override
    public boolean isAttendanceAlreadyMarked(Long studentId, LocalDate date) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));

        return attendanceRepository.findByStudentAndAttendanceDate(student, date).isPresent();
    }

    @Override
    public double calculateAttendancePercentage(Long studentId) {

        List<Attendance> attendanceList = getAttendanceByStudent(studentId);

        if (attendanceList.isEmpty()) {
            return 0.0;
        }

        long presentCount = attendanceList.stream()
                .filter(a -> Boolean.TRUE.equals(a.getPresent()))
                .count();

        return (presentCount * 100.0) / attendanceList.size();
    }

    @Override
    public List<Attendance> getAttendanceByClassAndDate(Long classId, LocalDate date) {

        SundayClass sundayClass = sundayClassRepository.findById(classId)
                .orElseThrow(() -> new ResourceNotFoundException("SundayClass", "id", classId));

        List<Student> students = studentRepository.findBySundayClass(sundayClass);

        return students.stream()
                .map(student -> attendanceRepository.findByStudentAndAttendanceDate(student, date)
                        .orElse(null))
                .filter(a -> a != null)
                .collect(java.util.stream.Collectors.toList());
    }
}
