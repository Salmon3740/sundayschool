package com.tmcf.sundayschool.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tmcf.sundayschool.entity.Student;
import com.tmcf.sundayschool.entity.SundayClass;
import com.tmcf.sundayschool.entity.User;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findBySundayClass(SundayClass sundayClass);

    Optional<Student> findByUser(User user);

    Optional<Student> findByUser_Username(String username);

    List<Student> findByFullNameContainingIgnoreCase(String name);
}
