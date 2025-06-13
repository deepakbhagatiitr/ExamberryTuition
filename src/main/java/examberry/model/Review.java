package examberry.model;

public class Review {
    private Student student;
    private Lesson lesson;
    private String text;
    private int rating;

    public Review(Student student, Lesson lesson, String text, int rating) {
        this.student = student;
        this.lesson = lesson;
        this.text = text;
        this.rating = rating;
    }

    public Student getStudent() {
        return student;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public String getText() {
        return text;
    }

    public int getRating() {
        return rating;
    }
}