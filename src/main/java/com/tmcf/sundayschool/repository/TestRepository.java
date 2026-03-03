package com.tmcf.sundayschool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tmcf.sundayschool.entity.Test;
import com.tmcf.sundayschool.entity.SundayClass;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

    List<Test> findBySundayClass(SundayClass sundayClass);
}
