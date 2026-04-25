package com.tmcf.sundayschool;

import com.tmcf.sundayschool.entity.*;
import com.tmcf.sundayschool.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Seeds the database with all required demo data on startup.
 * - 5 Sunday School classes
 * - 5 teacher accounts
 * - 25 students (5 per class), each linked to a user account
 * - Weekly lessons for each class (last 4 weeks)
 * - Attendance records (last 4 Sundays)
 * - Tests + marks for each class
 * - Songs & stories
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final SundayClassRepository classRepo;
    private final com.tmcf.sundayschool.repository.UserRepository userRepo;
    private final StudentRepository studentRepo;
    private final AttendanceRepository attendanceRepo;
    private final WeeklyLessonRepository lessonRepo;
    private final TestRepository testRepo;
    private final TestMarkRepository testMarkRepo;
    private final SongStoryRepository songStoryRepo;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(
            SundayClassRepository classRepo,
            com.tmcf.sundayschool.repository.UserRepository userRepo,
            StudentRepository studentRepo,
            AttendanceRepository attendanceRepo,
            WeeklyLessonRepository lessonRepo,
            TestRepository testRepo,
            TestMarkRepository testMarkRepo,
            SongStoryRepository songStoryRepo,
            PasswordEncoder passwordEncoder) {
        this.classRepo = classRepo;
        this.userRepo = userRepo;
        this.studentRepo = studentRepo;
        this.attendanceRepo = attendanceRepo;
        this.lessonRepo = lessonRepo;
        this.testRepo = testRepo;
        this.testMarkRepo = testMarkRepo;
        this.songStoryRepo = songStoryRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        seedClasses();
        seedTeachers();
        seedStudents();
        seedLessons();
        seedAttendance();
        seedTests();
        seedSongsStories();
        System.out.println("[DataInitializer] ✅ All seed data loaded successfully.");
    }

    // ===== 1. Classes =====
    private void seedClasses() {
        String[][] classes = {
                { "Beginners", "The Lord is my Shepherd", "Grace" },
                { "Primerie", "Daniel in the Lions Den", "Faith" },
                { "Juniors", "Sermon on the Mount", "Love" },
                { "Seniors", "The Parable of the Sower", "Hope" },
                { "Super Seniors", "Paul's Journey to Rome", "Courage" }
        };
        for (String[] c : classes) {
            if (classRepo.findByClassName(c[0]).isEmpty()) {
                SundayClass sc = new SundayClass();
                sc.setClassName(c[0]);
                sc.setLessonName(c[1]);
                sc.setWordOfTheWeek(c[2]);
                sc.setWeekDate(LocalDate.now());
                classRepo.save(sc);
            }
        }
    }

    // ===== 2. Teachers =====
    private void seedTeachers() {
        String[][] teachers = {
                { "teacher_john", "john.teacher@tmcf.com", "John Mensah" },
                { "teacher_grace", "grace.teacher@tmcf.com", "Grace Asante" },
                { "teacher_david", "david.teacher@tmcf.com", "David Owusu" },
                { "teacher_mary", "mary.teacher@tmcf.com", "Mary Boateng" },
                { "teacher_peter", "peter.teacher@tmcf.com", "Peter Adjei" },
                { "admin", "admin@tmcf.com", "Administrator" }
        };
        for (String[] t : teachers) {
            if (!userRepo.existsByUsername(t[0])) {
                User u = new User();
                u.setUsername(t[0]);
                u.setEmail(t[1]);
                u.setPassword(passwordEncoder.encode("TMCF_SUNDAY_SCHOOL_TEACHER"));
                u.setRole(Role.ROLE_TEACHER);
                userRepo.save(u);
            }
        }
    }

    // ===== 3. Students (5 per class) =====
    private void seedStudents() {
        List<SundayClass> classes = classRepo.findAll();
        if (studentRepo.count() > 0)
            return; // already seeded

        String[][][] studentsByClass = {
                // Beginners
                {
                        { "beginners_alice", "alice@tmcf.com", "Alice Mensah", "FEMALE", "7" },
                        { "beginners_bob", "bob@tmcf.com", "Bob Owusu", "MALE", "8" },
                        { "beginners_clara", "clara@tmcf.com", "Clara Acheampong", "FEMALE", "6" },
                        { "beginners_dan", "dan@tmcf.com", "Daniel Boateng", "MALE", "7" },
                        { "beginners_emma", "emma@tmcf.com", "Emma Asante", "FEMALE", "8" }
                },
                // Primerie
                {
                        { "primerie_frank", "frank@tmcf.com", "Frank Adjei", "MALE", "10" },
                        { "primerie_grace", "grace2@tmcf.com", "Grace Darko", "FEMALE", "11" },
                        { "primerie_henry", "henry@tmcf.com", "Henry Osei", "MALE", "10" },
                        { "primerie_iris", "iris@tmcf.com", "Iris Ofori", "FEMALE", "12" },
                        { "primerie_james", "james@tmcf.com", "James Ampofo", "MALE", "11" }
                },
                // Juniors
                {
                        { "juniors_kate", "kate@tmcf.com", "Kate Asare", "FEMALE", "13" },
                        { "juniors_liam", "liam@tmcf.com", "Liam Frimpong", "MALE", "14" },
                        { "juniors_mia", "mia@tmcf.com", "Mia Tetteh", "FEMALE", "13" },
                        { "juniors_noah", "noah@tmcf.com", "Noah Appiah", "MALE", "14" },
                        { "juniors_olivia", "olivia@tmcf.com", "Olivia Bonsu", "FEMALE", "15" }
                },
                // Seniors
                {
                        { "seniors_paul", "paul@tmcf.com", "Paul Antwi", "MALE", "16" },
                        { "seniors_queen", "queen@tmcf.com", "Queen Asare", "FEMALE", "17" },
                        { "seniors_ryan", "ryan@tmcf.com", "Ryan Koranteng", "MALE", "16" },
                        { "seniors_sarah", "sarah@tmcf.com", "Sarah Agyeman", "FEMALE", "17" },
                        { "seniors_tom", "tom@tmcf.com", "Tom Mensah", "MALE", "18" }
                },
                // Super Seniors
                {
                        { "supersr_uma", "uma@tmcf.com", "Uma Amponsah", "FEMALE", "19" },
                        { "supersr_victor", "victor@tmcf.com", "Victor Sarpong", "MALE", "20" },
                        { "supersr_wendy", "wendy@tmcf.com", "Wendy Boateng", "FEMALE", "19" },
                        { "supersr_xander", "xander@tmcf.com", "Xander Kwame", "MALE", "21" },
                        { "supersr_yaa", "yaa@tmcf.com", "Yaa Afriyie", "FEMALE", "20" }
                }
        };

        for (int i = 0; i < classes.size() && i < studentsByClass.length; i++) {
            SundayClass sc = classes.get(i);
            for (String[] sd : studentsByClass[i]) {
                // Create user account
                User u = null;
                if (!userRepo.existsByUsername(sd[0])) {
                    u = new User();
                    u.setUsername(sd[0]);
                    u.setEmail(sd[1]);
                    u.setPassword(passwordEncoder.encode("Student@123"));
                    u.setRole(Role.ROLE_STUDENT);
                    u = userRepo.save(u);
                } else {
                    u = userRepo.findByUsername(sd[0]).orElse(null);
                }

                // Create student profile
                Student student = new Student();
                student.setFullName(sd[2]);
                student.setGender(Gender.valueOf(sd[3]));
                student.setAge(Integer.parseInt(sd[4]));
                student.setSundayClass(sc);
                student.setUser(u);
                student.setActive(true);
                studentRepo.save(student);
            }
        }
    }

    // ===== 4. Weekly Lessons (4 weeks per class) =====
    private void seedLessons() {
        if (lessonRepo.count() > 0)
            return;

        List<SundayClass> classes = classRepo.findAll();
        String[][] lessonData = {
                { "The Lord is My Shepherd", "Grace", "Psalm 23 — God provides all we need" },
                { "The Sermon on the Mount", "Blessed", "Matthew 5 — The Beatitudes" },
                { "The Prodigal Son", "Forgiveness", "Luke 15 — Unconditional love" },
                { "Feeding the 5000", "Miracle", "John 6 — Believing in the impossible" }
        };

        for (SundayClass sc : classes) {
            for (int w = 0; w < lessonData.length; w++) {
                WeeklyLesson lesson = new WeeklyLesson();
                lesson.setLessonName(lessonData[w][0] + " (" + sc.getClassName() + ")");
                lesson.setWordOfWeek(lessonData[w][1]);
                lesson.setWeekNumber(w + 1);
                lesson.setWeekDate(LocalDate.now().minusWeeks(lessonData.length - 1 - w));
                lesson.setSundayClass(sc);
                lessonRepo.save(lesson);
            }
        }
    }

    // ===== 5. Attendance (4 Sundays per student) =====
    private void seedAttendance() {
        if (attendanceRepo.count() > 0)
            return;

        List<Student> students = studentRepo.findAll();
        boolean[][] patterns = {
                { true, true, true, true }, // always present
                { true, true, true, false }, // mostly present
                { true, false, true, true }, // mostly present
                { false, true, true, true }, // mostly present
                { true, false, false, true } // sometimes present
        };

        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            boolean[] pattern = patterns[i % patterns.length];
            for (int w = 0; w < pattern.length; w++) {
                Attendance att = new Attendance();
                att.setStudent(student);
                att.setAttendanceDate(LocalDate.now().minusWeeks(pattern.length - 1 - w));
                att.setPresent(pattern[w]);
                attendanceRepo.save(att);
            }
        }
    }

    // ===== 6. Tests + Marks =====
    private void seedTests() {
        if (testRepo.count() > 0)
            return;

        List<SundayClass> classes = classRepo.findAll();
        int[] sampleMarks = { 85, 72, 90, 65, 78 };

        for (SundayClass sc : classes) {
            // Create 2 tests per class
            for (int t = 1; t <= 2; t++) {
                Test test = new Test();
                test.setTitle("Term " + t + " Test — " + sc.getClassName());
                test.setMaxMarks(100);
                test.setTestDate(LocalDate.now().minusWeeks(t * 3L));
                test.setSundayClass(sc);
                test = testRepo.save(test);

                // Assign marks to all students in the class
                List<Student> students = studentRepo.findBySundayClass(sc);
                for (int j = 0; j < students.size(); j++) {
                    TestMark mark = new TestMark();
                    mark.setTest(test);
                    mark.setStudent(students.get(j));
                    mark.setMarksObtained(sampleMarks[j % sampleMarks.length] - (t * 3));
                    mark.setPresent(true);
                    testMarkRepo.save(mark);
                }
            }
        }
    }

    // ===== 7. Songs & Stories =====
    private void seedSongsStories() {
        if (songStoryRepo.count() > 0)
            return;

        String[][] items = {
                { "SONG", "Praise Him Praise Him", "https://www.youtube.com/watch?v=example1" },
                { "SONG", "Jesus Loves Me", "https://www.youtube.com/watch?v=example2" },
                { "SONG", "This Little Light of Mine", "https://www.youtube.com/watch?v=example3" },
                { "SONG", "Amazing Grace", "https://www.youtube.com/watch?v=example4" },
                { "SONG", "Lord I Lift Your Name on High", "https://www.youtube.com/watch?v=example5" },
                { "STORY", "David and Goliath", "https://www.biblegateway.com/passage/?search=1+Samuel+17" },
                { "STORY", "Noah's Ark", "https://www.biblegateway.com/passage/?search=Genesis+6" },
                { "STORY", "Moses and the Burning Bush", "https://www.biblegateway.com/passage/?search=Exodus+3" },
                { "STORY", "Daniel in the Lions Den", "https://www.biblegateway.com/passage/?search=Daniel+6" },
                { "STORY", "Jonah and the Whale", "https://www.biblegateway.com/passage/?search=Jonah+1" }
        };

        for (String[] item : items) {
            SongStory ss = new SongStory();
            ss.setType(item[0]);
            ss.setTitle(item[1]);
            ss.setLink(item[2]);
            songStoryRepo.save(ss);
        }
    }
}
