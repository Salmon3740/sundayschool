---
description: Standard workflow for understanding and working on the Sunday School Management backend
---

# Sunday School Management App — Agent Workflow

## 1. Project Overview
- **Stack**: Spring Boot 4.0.2, Java 17, MySQL, Spring Data JPA, Spring Security
- **Package**: `com.tmcf.sundayschool`
- **Port**: 8080, MySQL on 3307

## 2. Architecture Layers
```
controller/ → REST endpoints (@RestController)
service/    → Interfaces
service/impl/ → Business logic (@Service)
repository/ → Spring Data JPA repos
entity/     → JPA entities + enums
config/     → SecurityConfig (all requests open)
```

## 3. Entity Model
| Entity | Table | Key Relationships |
|--------|-------|-------------------|
| User | users | Has role (STUDENT/TEACHER) |
| Student | students | OneToOne→User, ManyToOne→SundayClass |
| SundayClass | sunday_classes | ManyToOne→User(teacher), OneToMany→Student |
| Attendance | attendance | ManyToOne→Student |
| Test | tests | ManyToOne→SundayClass |
| TestMark | test_marks | ManyToOne→Test, ManyToOne→Student |
| WeeklyLesson | weekly_lessons | ManyToOne→SundayClass |
| SongStory | songs_stories | Standalone (title, type, link) |

## 4. Build & Run
// turbo-all
```bash
# Build
./mvnw clean compile

# Run
./mvnw spring-boot:run

# Test
./mvnw test
```

## 5. Conventions
- Constructor injection (no @Autowired)
- Service interfaces with `*Impl` classes
- RuntimeException for error handling
- `@PrePersist` for auto-setting timestamps
- Entities use manual getters/setters (no Lombok)

## 6. Known Gaps
- Missing controllers: Attendance, SundayClass, Test, SongStory, WeeklyLesson
- Some service impls use `findAll()` + stream filter instead of repository queries
- Update methods don't update all fields
- No global exception handler
- No DTO layer (entities exposed directly)
- No password hashing
- Security is fully open (no auth)
