package com.tmcf.sundayschool.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tmcf.sundayschool.entity.Test;
import com.tmcf.sundayschool.entity.TestMark;
import com.tmcf.sundayschool.entity.Student;

@Repository
public interface TestMarkRepository extends JpaRepository<TestMark, Long> {

    List<TestMark> findByStudent(Student student);

    List<TestMark> findByTest(Test test);

    Optional<TestMark> findByTestAndStudent(Test test, Student student);
}
