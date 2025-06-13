package examberry.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewTest {
    private Student student;
    private Lesson lesson;
    private Review review;

    @BeforeEach
    void setUp() {
        student = new Student("Alice", "F", LocalDate.of(2010, 5, 15), "123 High St", "Parent: 555-0101");
        lesson = new Lesson(Subject.ENGLISH, LocalDateTime.of(2025, 6, 7, 9, 0), TimeSlot.MORNING, 50.0);
        review = new Review(student, lesson, "Great lesson!", 4);
    }

    @Test
    void testReviewCreation() {
        assertEquals(student, review.getStudent());
        assertEquals(lesson, review.getLesson());
        assertEquals("Great lesson!", review.getText());
        assertEquals(4, review.getRating());
    }
}