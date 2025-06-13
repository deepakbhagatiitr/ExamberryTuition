package examberry.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import examberry.model.Booking;
import examberry.model.Lesson;
import examberry.model.Review;
import examberry.model.Status;
import examberry.model.Student;
import examberry.model.Subject;
import examberry.model.TimeSlot;
import examberry.model.Timetable;

public class BookingService {
    private List<Student> students;
    private Timetable timetable;
    private Map<Lesson, List<Booking>> bookings;

    public BookingService() {
        students = new ArrayList<>();
        bookings = new HashMap<>();
    }

    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
        for (Lesson lesson : timetable.getLessons()) {
            bookings.put(lesson, new ArrayList<>());
        }
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public Booking bookLesson(Student student, Lesson lesson) {
        if (bookings.get(lesson).size() >= lesson.getMaxCapacity()) {
            return null;
        }
        for (Booking b : student.getBookings()) {
            if (b.getStatus() != Status.CANCELLED &&
                    b.getLesson().equals(lesson)) {
                return null; // Duplicate booking
            }
            if (b.getStatus() != Status.CANCELLED &&
                    b.getLesson().getDateTime().equals(lesson.getDateTime())) {
                return null; // Time conflict
            }
        }

        Booking booking = student.bookLesson(lesson);
        if (lesson.addBooking(booking)) {
            bookings.get(lesson).add(booking);
            return booking;
        }
        return null;
    }

    public boolean rescheduleBooking(Booking booking, Lesson newLesson) {
        if (booking.getStatus() != Status.BOOKED)
            return false;
        Lesson oldLesson = booking.getLesson();
        if (booking.reschedule(oldLesson, newLesson)) {
            bookings.get(oldLesson).remove(booking);
            bookings.get(newLesson).add(booking);
            return true;
        }
        return false;
    }

    public boolean cancelBooking(Booking booking) {
        if (booking.getStatus() != Status.BOOKED)
            return false;
        Lesson lesson = booking.getLesson();
        booking.cancel();
        bookings.get(lesson).remove(booking);
        lesson.getBookings().remove(booking);
        return true;
    }

    public boolean checkIn(Student student, Lesson lesson) {
        return lesson.checkIn(student);
    }

    public Review submitReview(Student student, Lesson lesson, String text, int rating) {
        boolean attended = lesson.getBookings().stream()
                .anyMatch(b -> b.getStudent().equals(student) && b.getStatus() == Status.ATTENDED);
        if (!attended)
            throw new IllegalStateException("Student must attend lesson to submit review");
        return student.submitReview(lesson, text, rating);
    }

    public Student getStudentByName(String name) {
        return students.stream()
                .filter(s -> s.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public Lesson getLesson(Subject subject, LocalDate date, TimeSlot slot) {
        return timetable.getLessonsByDate(date).stream()
                .filter(l -> l.getSubject() == subject && l.getSlot() == slot)
                .findFirst()
                .orElse(null);
    }

    public List<Lesson> getLessons() {
        return timetable.getLessons();
    }
}