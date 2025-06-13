package examberry.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Lesson {
    private Subject subject;
    private LocalDateTime dateTime;
    private TimeSlot slot;
    private double price;
    private int maxCapacity = 4;
    private List<Booking> bookings;
    private List<Review> reviews;

    public Lesson(Subject subject, LocalDateTime dateTime, TimeSlot slot, double price) {
        this.subject = subject;
        this.dateTime = dateTime;
        this.slot = slot;
        this.price = price;
        this.bookings = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    public boolean addBooking(Booking booking) {
        if (bookings.size() >= maxCapacity)
            return false;
        bookings.add(booking);
        return true;
    }

    public boolean checkIn(Student student) {
        for (Booking booking : bookings) {
            if (booking.getStudent().equals(student) && booking.getStatus() == Status.BOOKED) {
                booking.setStatus(Status.ATTENDED);
                return true;
            }
        }
        return false;
    }

    public void addReview(Review review) {
        reviews.add(review);
    }

    public double getAverageRating() {
        if (reviews.isEmpty())
            return 0.0;
        return reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
    }

    public int getStudentCount() {
        return bookings.size();
    }
public int getMaxCapacity() {
    return maxCapacity;
}
    public Subject getSubject() {
        return subject;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public TimeSlot getSlot() {
        return slot;
    }

    public double getPrice() {
        return price;
    }

    public List<Booking> getBookings() {
        return new ArrayList<>(bookings);
    }
}