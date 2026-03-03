package com.tmcf.sundayschool.service.impl;

import com.tmcf.sundayschool.entity.User;
import com.tmcf.sundayschool.entity.Role;
import com.tmcf.sundayschool.exception.DuplicateResourceException;
import com.tmcf.sundayschool.exception.InvalidCredentialsException;
import com.tmcf.sundayschool.exception.ResourceNotFoundException;
import com.tmcf.sundayschool.repository.UserRepository;
import com.tmcf.sundayschool.service.UserService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final String TEACHER_SECRET_KEY = "TMCF_SUNDAY_SCHOOL_TEACHER";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 1️⃣ Register a normal student user
     */
    @Override
    public User registerStudent(User user) {

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateResourceException("User", "username", user.getUsername());
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException("User", "email", user.getEmail());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_STUDENT);
        return userRepository.save(user);
    }

    /**
     * 2️⃣ Register a teacher using secret key
     */
    @Override
    public User registerTeacher(User user, String secretKey) {

        if (!TEACHER_SECRET_KEY.equals(secretKey)) {
            throw new InvalidCredentialsException("Invalid teacher secret key");
        }

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateResourceException("User", "username", user.getUsername());
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException("User", "email", user.getEmail());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_TEACHER);
        return userRepository.save(user);
    }

    /**
     * 3️⃣ Find user by username
     */
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
    }

    /**
     * 4️⃣ Find user by email
     */
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }

    /**
     * 5️⃣ Check if username already exists
     */
    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * 6️⃣ Find user by ID
     */
    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }
}
