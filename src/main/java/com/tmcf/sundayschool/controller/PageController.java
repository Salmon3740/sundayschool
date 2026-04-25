package com.tmcf.sundayschool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/dashboard")
    public String dashboardPage() {
        return "dashboard";
    }

    @GetMapping("/students")
    public String studentsPage() {
        return "students";
    }

    @GetMapping("/students/{id}/profile")
    public String studentProfilePage(@PathVariable("id") Long id) {
        return "student-profile";
    }

    @GetMapping("/attendance")
    public String attendancePage() {
        return "attendance";
    }

    @GetMapping("/lessons")
    public String lessonsPage() {
        return "lessons";
    }

    @GetMapping("/tests")
    public String testsPage() {
        return "tests";
    }

    @GetMapping("/songs-stories")
    public String songsStoriesPage() {
        return "songs-stories";
    }

    @GetMapping("/reports")
    public String reportsPage() {
        return "reports";
    }

    @GetMapping("/class/{id}")
    public String classPage(@PathVariable("id") Long id) {
        return "class";
    }
}
