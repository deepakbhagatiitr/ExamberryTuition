package examberry.model;

public class Booking {
    private Student student;
    private Lesson lesson;
    private Status status;

    public Booking(Student student, Lesson lesson) {
        this.student = student;
        this.lesson = lesson;
        this.status = Status.BOOKED;
    }

    public boolean reschedule(Lesson newLesson) {
        if (newLesson.getSubject() != lesson.getSubject() || !newLesson.addBooking(this)) {
            return false;
        }
        lesson = newLesson;
        return true;
    }

    public void cancel() {
        status = Status.CANCELLED;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Student getStudent() {
        return student;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public Status getStatus() {
        return status;
    }
}