package com.tmcf.sundayschool.service;

import com.tmcf.sundayschool.entity.SundayClass;

import java.util.List;

public interface SundayClassService {

    // 1️⃣ Create Sunday Class
    SundayClass createSundayClass(SundayClass sundayClass);

    // 2️⃣ Get Class by ID
    SundayClass getSundayClassById(Long classId);

    // 3️⃣ Get Class by Name
    SundayClass getSundayClassByName(String className);

    // 4️⃣ Get All Classes
    List<SundayClass> getAllSundayClasses();

    // 5️⃣ Update Class
    SundayClass updateSundayClass(Long classId, SundayClass updatedClass);
}
