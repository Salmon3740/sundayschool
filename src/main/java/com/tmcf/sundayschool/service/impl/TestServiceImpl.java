package com.tmcf.sundayschool.service.impl;

import com.tmcf.sundayschool.entity.Test;
import com.tmcf.sundayschool.entity.TestMark;
import com.tmcf.sundayschool.entity.Student;
import com.tmcf.sundayschool.entity.SundayClass;
import com.tmcf.sundayschool.exception.ResourceNotFoundException;
import com.tmcf.sundayschool.repository.TestRepository;
import com.tmcf.sundayschool.repository.TestMarkRepository;
import com.tmcf.sundayschool.repository.StudentRepository;
import com.tmcf.sundayschool.repository.SundayClassRepository;
import com.tmcf.sundayschool.service.TestService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;
    private final TestMarkRepository testMarkRepository;
    private final StudentRepository studentRepository;
    private final SundayClassRepository sundayClassRepository;

    public TestServiceImpl(TestRepository testRepository,
            TestMarkRepository testMarkRepository,
            StudentRepository studentRepository,
            SundayClassRepository sundayClassRepository) {
        this.testRepository = testRepository;
        this.testMarkRepository = testMarkRepository;
        this.studentRepository = studentRepository;
        this.sundayClassRepository = sundayClassRepository;
    }

    @Override
    public Test createTest(Test test) {
        return testRepository.save(test);
    }

    @Override
    public List<Test> getTestsByClass(Long classId) {

        SundayClass sundayClass = sundayClassRepository.findById(classId)
                .orElseThrow(() -> new ResourceNotFoundException("SundayClass", "id", classId));

        return testRepository.findBySundayClass(sundayClass);
    }

    @Override
    public TestMark assignMarks(Long testId, Long studentId, Integer marks, Boolean present) {

        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new ResourceNotFoundException("Test", "id", testId));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));

        TestMark testMark = testMarkRepository.findByTestAndStudent(test, student)
                .orElse(null);

        if (testMark == null) {
            testMark = new TestMark();
            testMark.setTest(test);
            testMark.setStudent(student);
        }

        testMark.setMarksObtained(marks);
        testMark.setPresent(present);

        return testMarkRepository.save(testMark);
    }

    @Override
    public List<TestMark> getMarksByTest(Long testId) {

        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new ResourceNotFoundException("Test", "id", testId));

        return testMarkRepository.findByTest(test);
    }

    @Override
    public List<TestMark> getMarksByStudent(Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));

        return testMarkRepository.findByStudent(student);
    }

    @Override
    public int calculateTotalMarks(Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));

        List<TestMark> marks = testMarkRepository.findByStudent(student);

        return marks.stream()
                .mapToInt(TestMark::getMarksObtained)
                .sum();
    }
}
