package com.tmcf.sundayschool.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tmcf.sundayschool.entity.SundayClass;

@Repository
public interface SundayClassRepository extends JpaRepository<SundayClass, Long> {

    Optional<SundayClass> findByClassName(String className);

    boolean existsByClassName(String className);
}
