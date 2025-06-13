package examberry.service;

import examberry.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        // Check capacity and conflicts
        if (bookings.get(lesson).size() >= lesson.getMaxCapacity()) {
            return null;
        }
        for (Booking b : student.getBookings()) {
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
        return booking.reschedule(newLesson);
    }

    public boolean cancelBooking(Booking booking) {
        if (booking.getStatus() != Status.BOOKED)
            return false;
        booking.cancel();
        return true;
    }

    public boolean checkIn(Student student, Lesson lesson) {
        return lesson.checkIn(student);
    }

    public Review submitReview(Student student, Lesson lesson, String text, int rating) {
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