# 📘 Examberry Tuition Centre

A **Java-based console application** that manages weekend tuition bookings for a tuition centre.

---

## 📌 Project Overview

The system handles bookings for four subjects:

* English
* Maths
* Verbal Reasoning
* Non-verbal Reasoning

It runs across **8 weekends**, with morning and afternoon slots, and supports up to **4 students per lesson**.

Key highlights:

* Pure Java (no external DB)
* Modular and maintainable
* Fully tested with JUnit 5

---

## 🚀 Features

### 👩‍🎓 Student Management

* 10 preloaded students with personal details.

### 📅 Timetable Scheduling

* 8 weekends starting from **June 7, 2025**.
* Morning and afternoon sessions per subject.

### 📚 Booking System

* Max **4 students per lesson**.
* Prevents **double bookings**, **time clashes**, and **duplicates**.
* Supports **rescheduling** and **cancellation**.

### ✅ Check-In

* Marks student attendance per lesson.

### ⭐ Review System

* Attended students can give 1-5 ratings with comments.

### 📊 Reporting

* Per-lesson: student count + average rating.
* Income: earnings by subject + top earner.

### 🛡️ Input Validation

* Case-insensitive (e.g., `english`, `ENGLISH`).
* Handles invalid dates and slots.

### 🧪 Testing

* JUnit 5 test suite covers core logic and edge cases.

---

## 📁 Project Structure

```
ExamberryTuition/
├── src/
│   ├── main/java/examberry/
│   │   ├── model/          # Student, Lesson, Booking, etc.
│   │   ├── service/        # BookingService, ReportGenerator
│   │   ├── util/           # TimetableGenerator, LessonFactory
│   │   └── Main.java       # CLI entry point
│   ├── test/java/examberry/
│   │   ├── model/
│   │   └── service/
├── docs/
│   ├── report.tex, report.pdf
│   ├── uml_diagram.mmd, uml_diagram.png
│   └── screenshots/
├── out/
│   └── ExamberryTuition.jar
├── screencast/demo.mp4
├── pom.xml
└── README.md
```

---

## 🛠️ Prerequisites

* Java 17
* Maven (3.8.6+)
* Git
* TeX Live (for LaTeX report)
* Mermaid CLI *(optional)*

---

## 🧩 Setup Instructions

### 1️⃣ Clone the Repo

```bash
git clone https://github.com/deepakbhagatiitr/ExamberryTuition.git
cd ExamberryTuition
```

### 2️⃣ Build the Project

```bash
mvn clean package
mkdir -p out
cp target/ExamberryTuition-1.0-SNAPSHOT.jar out/ExamberryTuition.jar
```

### 3️⃣ Run the App

```bash
java -jar out/ExamberryTuition.jar
```

### 4️⃣ Run Tests

```bash
mvn test
```

### 5️⃣ Generate UML Diagram

```bash
mmdc -i docs/uml_diagram.mmd -o docs/uml_diagram.png
# Or use Mermaid Live Editor
```

### 6️⃣ Compile LaTeX Report

```bash
cd docs
latexmk -pdf report.tex
```

---

## 📋 Usage Instructions

* Launch using: `java -jar out/ExamberryTuition.jar`
* Interact via CLI menu:

  * Book/reschedule/cancel lessons
  * Check in students
  * Submit reviews
  * Generate reports

**Note:** Valid dates are weekends between **June 7, 2025** and **July 27, 2025**.

---

## ✅ Testing Summary

### JUnit 5 Tests Include:

* **BookingServiceTest**

  * Booking logic, rescheduling, capacity, duplicates
* **ReviewTest**

  * Review creation and rating validation

Run all tests:

```bash
mvn test
```

---

## 📂 Version Control

* Git used for managing versions  
* Track changes: `git log --oneline`  
* Meaningful commits per milestone (setup, features, tests, docs)  


Happy Booking! 🎓


