package com.tmcf.sundayschool.dto.response;

import java.time.LocalDate;
import java.util.List;

public class StudentReportResponse {

    private Long studentId;
    private String fullName;
    private String className;
    private double attendancePercentage;
    private int totalMarks;
    private List<AttendanceRecord> attendanceHistory;
    private List<TestMarkRecord> testResults;

    public StudentReportResponse() {
    }

    // Getters & Setters

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public double getAttendancePercentage() {
        return attendancePercentage;
    }

    public void setAttendancePercentage(double attendancePercentage) {
        this.attendancePercentage = attendancePercentage;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    public List<AttendanceRecord> getAttendanceHistory() {
        return attendanceHistory;
    }

    public void setAttendanceHistory(List<AttendanceRecord> attendanceHistory) {
        this.attendanceHistory = attendanceHistory;
    }

    public List<TestMarkRecord> getTestResults() {
        return testResults;
    }

    public void setTestResults(List<TestMarkRecord> testResults) {
        this.testResults = testResults;
    }

    // ===== Inner DTOs =====

    public static class AttendanceRecord {
        private LocalDate date;
        private boolean present;

        public AttendanceRecord() {
        }

        public AttendanceRecord(LocalDate date, boolean present) {
            this.date = date;
            this.present = present;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public boolean isPresent() {
            return present;
        }

        public void setPresent(boolean present) {
            this.present = present;
        }
    }

    public static class TestMarkRecord {
        private String testTitle;
        private LocalDate testDate;
        private int marksObtained;
        private int maxMarks;
        private boolean present;

        public TestMarkRecord() {
        }

        public TestMarkRecord(String testTitle, LocalDate testDate, int marksObtained, int maxMarks, boolean present) {
            this.testTitle = testTitle;
            this.testDate = testDate;
            this.marksObtained = marksObtained;
            this.maxMarks = maxMarks;
            this.present = present;
        }

        public String getTestTitle() {
            return testTitle;
        }

        public void setTestTitle(String testTitle) {
            this.testTitle = testTitle;
        }

        public LocalDate getTestDate() {
            return testDate;
        }

        public void setTestDate(LocalDate testDate) {
            this.testDate = testDate;
        }

        public int getMarksObtained() {
            return marksObtained;
        }

        public void setMarksObtained(int marksObtained) {
            this.marksObtained = marksObtained;
        }

        public int getMaxMarks() {
            return maxMarks;
        }

        public void setMaxMarks(int maxMarks) {
            this.maxMarks = maxMarks;
        }

        public boolean isPresent() {
            return present;
        }

        public void setPresent(boolean present) {
            this.present = present;
        }
    }
}
