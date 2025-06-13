package examberry.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import examberry.model.Booking;
import examberry.model.Lesson;
import examberry.model.Status;
import examberry.model.Student;
import examberry.model.Subject;
import examberry.model.TimeSlot;
import examberry.model.Timetable;

public class BookingServiceTest {
    private BookingService service;
    private Student student;
    private Lesson lesson1;
    private Lesson lesson2;

    @BeforeEach
    void setUp() {
        service = new BookingService();
        student = new Student(
                "Alice", "F", LocalDate.of(2010, 5, 15),
                "123 High St", "Parent: 555-0101");
        service.addStudent(student);

        Timetable timetable = new Timetable();
        lesson1 = new Lesson(
                Subject.ENGLISH,
                LocalDateTime.of(2025, 6, 7, 9, 0),
                TimeSlot.MORNING,
                50.0);
        lesson2 = new Lesson(
                Subject.ENGLISH,
                LocalDateTime.of(2025, 6, 7, 13, 0),
                TimeSlot.AFTERNOON,
                50.0);
        timetable.addLesson(lesson1);
        timetable.addLesson(lesson2);
        service.setTimetable(timetable);
    }

    @Test
    void testBookLessonSuccess() {
        Booking booking = service.bookLesson(student, lesson1);
        assertNotNull(booking);
        assertEquals(Status.BOOKED, booking.getStatus());
        assertEquals(1, lesson1.getStudentCount());
    }

    @Test
    void testBookLessonMaxCapacity() {
        for (int i = 0; i < 4; i++) {
            Student s = new Student("Student" + i, "M", LocalDate.now(), "Addr", "Contact");
            service.addStudent(s);
            assertNotNull(service.bookLesson(s, lesson1));
        }
        Booking booking = service.bookLesson(student, lesson1);
        assertNull(booking);
    }

    @Test
    void testBookLessonTimeConflict() {
        service.bookLesson(student, lesson1);
        Booking booking = service.bookLesson(student, lesson1);
        assertNull(booking);
    }

    @Test
    void testBookLessonDuplicate() {
        service.bookLesson(student, lesson1);
        Booking booking = service.bookLesson(student, lesson1);
        assertNull(booking);
    }

    @Test
    void testRescheduleBooking() {
        Booking booking = service.bookLesson(student, lesson1);
        boolean result = service.rescheduleBooking(booking, lesson2);
        assertTrue(result);
        assertEquals(lesson2, booking.getLesson());
    }

    @Test
    void testRescheduleBookingFullLesson() {
        for (int i = 0; i < 4; i++) {
            Student s = new Student("Student" + i, "M", LocalDate.now(), "Addr", "Contact");
            service.addStudent(s);
            assertNotNull(service.bookLesson(s, lesson2));
        }
        Booking booking = service.bookLesson(student, lesson1);
        boolean result = service.rescheduleBooking(booking, lesson2);
        assertFalse(result);
    }

    @Test
    void testCheckIn() {
        service.bookLesson(student, lesson1);
        boolean result = service.checkIn(student, lesson1);
        assertTrue(result);
        assertEquals(Status.ATTENDED, student.getBookings().get(0).getStatus());
    }
}