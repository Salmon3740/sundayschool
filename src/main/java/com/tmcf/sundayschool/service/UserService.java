package com.tmcf.sundayschool.service;

import com.tmcf.sundayschool.entity.User;

public interface UserService {

    // 1️⃣ Register a normal student user
    User registerStudent(User user);

    // 2️⃣ Register a teacher using secret key
    User registerTeacher(User user, String secretKey);

    // 3️⃣ Find user by username (used for login & other services)
    User findByUsername(String username);

    // 4️⃣ Find user by email
    User findByEmail(String email);

    // 5️⃣ Check if username already exists
    boolean existsByUsername(String username);

    // 6️⃣ Find user by ID
    User findById(Long userId);
}
