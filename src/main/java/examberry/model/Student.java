package examberry.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Student {
    private String name;
    private String gender;
    private LocalDate dob;
    private String address;
    private String emergencyContact;
    private List<Booking> bookings;
    private List<Review> reviews;

    public Student(String name, String gender, LocalDate dob, String address, String emergencyContact) {
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.address = address;
        this.emergencyContact = emergencyContact;
        this.bookings = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    public Booking bookLesson(Lesson lesson) {
        Booking booking = new Booking(this, lesson);
        bookings.add(booking);
        return booking;
    }

    public Review submitReview(Lesson lesson, String text, int rating) {
        if (rating < 1 || rating > 5)
            throw new IllegalArgumentException("Rating must be 1-5");
        Review review = new Review(this, lesson, text, rating);
        reviews.add(review);
        lesson.addReview(review);
        return review;
    }

    public List<Booking> getBookings() {
        return Collections.unmodifiableList(bookings);
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getAddress() {
        return address;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }
}