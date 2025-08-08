# 📚 Study Forger – Smart Study Scheduler

**Study-Forger** is a smart, modular study management system that helps students organize subjects, track topics, and master revision using the **SM-2 Spaced Repetition Algorithm**. Designed to be practical, scalable, and focused on results — not clutter.












---

## 🚀 Tech Stack

- **Backend**: Java, Spring Boot
- **Database**: MySQL
- **Architecture**: RESTful APIs, Loosely Coupled Modules
- **Tools**: Postman, Maven, JUnit

---

## ✅ Modules Implemented (MVP Complete)

### 🔹 User Module
- Create, update, delete users
- Fetch individual user profiles

### 🔹 Subject Module
- Create subjects linked to users
- Edit and delete subjects
- Fetch all subjects by user ID

### 🔹 Topic Module
- Create/update/delete topics under subjects
- Categorize topics for structured learning
- Tracks difficulty, revision count, and progress

### 🔹 Revision Module (SM-2 Based)
- Implements Spaced Repetition scheduling logic
- Calculates next review date based on performance
- Tracks ease factor, interval, and repetition count

### 🔹 Dashboard Module
- Displays total topics, due reviews, and overdue topics
- Shows recent performance and upcoming schedule

---

## 🧠 SM-2 Spaced Repetition (Implemented)

- **Ease Factor** starts at 2.5 and adjusts (1.3–2.5)
- **Interval** grows or shrinks based on user memory score (0–5)
- **Next Review Date** is dynamically recalculated
- **Performance History** logged for every topic review

---

### 🔐 Authentication & Roles
- Implemented basic user management
- Role management (Admin, Normal) is Done
- Planned JWT-based login and session management

## 🧪 API Tested with Postman

- All major routes (User, Subject, Topic, Revision, Dashboard) are fully tested for:
    - CRUD operations
    - Edge case handling
    - Validation and performance impact

---

## 📅 Features & Future Plans

### 🔔 Notification System
- Daily reminders for scheduled reviews
- Push/email integration (Phase 2)

### 🔐 Authentication & Roles
- JWT-based login and session management
- Role-based access (Admin, Student)

### 🌐 Frontend Dashboard (Planned)
- Built with **React.js**
- Minimal UI for scheduling, tracking, and visual insights

### 🧠 AI-Assisted Study Plans *(Future Phase)*
- Personalized study path based on exam dates, topic weightage, and pace

---


## 📂 Project Structure

```
Study-Forger/
├── 📁 src/
│   ├── 📁 main/
│   │   ├── 📁 java/com/studyForger/Study_Forger/
│   │   │   ├── 📁 Configuration/          # Spring Boot configurations
│   │   │   ├── 📁 Controller/             # REST API controllers
│   │   │   ├── 📁 Dto/                    # Data Transfer Objects
│   │   │   ├── 📁 Exception/              # Custom exception handling
│   │   │   ├── 📁 Files/                  # File management utilities
│   │   │   ├── 📁 Helper/                 # Utility and helper classes
│   │   │   ├── 📁 Revision/               # Revision module (SM-2 algorithm)
│   │   │   │   ├── Revision.java          # Revision entity
│   │   │   │   ├── RevisionRepository.java
│   │   │   │   ├── RevisionService.java
│   │   │   │   └── RevisionServiceImpl.java
│   │   │   ├── 📁 Role/                   # Role management
│   │   │   │   ├── Role.java              # Role entity
│   │   │   │   ├── RoleDto.java
│   │   │   │   └── RoleRepository.java
│   │   │   ├── 📁 Subject/                # Subject module
│   │   │   │   ├── Subject.java           # Subject entity
│   │   │   │   ├── SubjectDto.java
│   │   │   │   ├── SubjectRepository.java
│   │   │   │   └── SubjectService.java
│   │   │   ├── 📁 Topic/                  # Topic module
│   │   │   │   ├── Topic.java             # Topic entity
│   │   │   │   ├── TopicRequestDto.java
│   │   │   │   ├── TopicRepository.java
│   │   │   │   └── TopicService.java
│   │   │   ├── 📁 User/                   # User management
│   │   │   │   ├── User.java              # User entity
│   │   │   │   ├── UserDto.java
│   │   │   │   ├── UserRepository.java
│   │   │   │   └── UserService.java
│   │   │   └── StudyForgerApplication.java # Main application class
│   │   └── 📁 resources/
│   │       ├── 📁 static/                 # Static web resources
│   │       ├── 📁 templates/              # Template files
│   │       └── application.properties     # Application configuration
│   └── 📁 test/
│       └── 📁 java/com/studyForger/Study_Forger/
│           ├── 📁 RevisionTest/           # Revision module tests
│           ├── 📁 SubjectTest/            # Subject module tests
│           ├── 📁 TopicTest/              # Topic module tests
│           ├── 📁 UserTest/               # User module tests
│           └── StudyForgerApplicationTests.java
├── 📁 .mvn/                              # Maven wrapper files
├── 📁 images/                            # Project documentation images
├── 📁 target/                            # Compiled classes and build artifacts
├── pom.xml                               # Maven project configuration
├── mvnw & mvnw.cmd                       # Maven wrapper scripts
├── MockData.txt                          # Sample data for testing
├── README.md                             # Project documentation
├── .gitignore                            # Git ignore rules
└── .gitattributes                        # Git attributes configuration
```

## 🧩 Contributing & Feedback

- Feature suggestions, PRs, and testing feedback are welcome.
- This project is under active development — MVP focused, but scalable.

---

## 📅 Roadmap

| Milestone                        | Status        |
|-------------------------------|----------------|
| User/Subject/Topic Modules     | ✅ Completed    |
| Revision Scheduling (SM-2)     | ✅ Completed    |
| Dashboard Module               | ✅ Completed  |
| Notification System            | 🔄 In Progress   |
| JWT Authentication             | 🔜 Next Up     |
| React Frontend                 | 🔜 Phase 2     |
| AI-Assisted Plans              | 🧠 Future Plan |
