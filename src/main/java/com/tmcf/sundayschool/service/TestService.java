package com.tmcf.sundayschool.service;

import com.tmcf.sundayschool.entity.Test;
import com.tmcf.sundayschool.entity.TestMark;

import java.util.List;

public interface TestService {

    // create a new test for a class
    Test createTest(Test test);

    // get all tests for a class
    List<Test> getTestsByClass(Long classId);

    // assign / update marks for a student
    TestMark assignMarks(Long testId, Long studentId, Integer marks, Boolean present);

    // get marks of all students for a test
    List<TestMark> getMarksByTest(Long testId);

    // get all marks of a student (used for report / total marks)
    List<TestMark> getMarksByStudent(Long studentId);

    // calculate total marks of a student dynamically
    int calculateTotalMarks(Long studentId);
}
